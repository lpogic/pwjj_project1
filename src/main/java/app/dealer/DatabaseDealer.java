package app.dealer;

import app.controller.TracksListController;
import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.DatabaseConnection;
import app.model.TrackText;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class DatabaseDealer extends OpenDealer {

    private static final Contract<Object> connect = Contract.forObject();
    public static final Contract<Object> saveTrackDialog = Contract.forObject(Stamp.SERVICE);
    public static final Contract<Object> saveTrack = Contract.forObject(Stamp.SUPPLY);

    private DatabaseConnection db;

    public DatabaseDealer(OpenRoot openRoot) {
        super(openRoot);
        db = new DatabaseConnection();
    }

    @Override
    public void employ(){

        shop().offer(connect,()->{
            try{
                System.out.println("create connection");
                db.createConnection();
                db.createTrackTextTable();
                System.out.println("success");
                return true;
            }catch(SQLException sqle){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad");
                alert.setHeaderText("Wystapil blad podczas proby polaczenia z baza danych");
                alert.showAndWait();
            }
            return null;
        });

        shop().offer(TracksListController.getFilteredTracks,()->{
            if(shop().order(connect)){
                String track = shop().deal(TracksListController.trackFilter,"");
                String name = shop().deal(TracksListController.nameFilter,"");
                try{
                    return db.getTracksByNameLikeAndTrackLike(name, track);
                }catch(SQLException sqle){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Wystapil blad podczas proby pobrania rekordow");
                    alert.showAndWait();
                }
            }
            return null;
        });

        shop().offer(DatabaseDealer.saveTrackDialog,()->{
            if(shop().order(TrackText.class)){
                root().openStage("saveTrack").openScene().openStyle("css/login.css");
                root().openStage("saveTrack").showAndWait();
                return shop().deal(saveTrack);
            }
            return null;
        });

        shop().offer(saveTrack,()->{
            if(shop().order(connect) && shop().order(TrackText.class)){
                try{
                    db.saveEntry(shop().deal(TrackText.class));
                    return true;
                }catch (SQLException sqle){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad");
                    alert.setHeaderText("Wystapil blad podczas proby zapisu");
                    alert.showAndWait();
                }
            }
            return null;
        });
    }

}
