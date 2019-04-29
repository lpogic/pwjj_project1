package app.shipper;

import app.controller.TracksListController;
import app.core.OpenRoot;
import app.core.service.OpenShipper;
import app.core.shop.Product;
import app.core.shop.Shop;
import app.model.DatabaseConnection;
import app.model.TrackText;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class DatabaseShipper extends OpenShipper {
    public static final Object connect = new Object();
    public static final Object getFilteredTracks = new Object();
    public static final Object textTrackSavingDialog = new Object();
    public static final Object saveTrackText = new Object();

    private DatabaseConnection db;

    public DatabaseShipper(OpenRoot openRoot) {
        super(openRoot);
        db = new DatabaseConnection();
    }

    @Override
    public void signContract(Shop shop) {

        shop.offer(connect,()->{
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
        }, Product.REUSABLE);

        shop.offer(getFilteredTracks,()->{
            if(shop.order(connect)){
                String track = shop.order(TracksListController.trackFilter) ?
                        (String)shop.purchase(TracksListController.trackFilter) : "";
                String name = shop.order(TracksListController.nameFilter) ?
                        (String)shop.purchase(TracksListController.nameFilter) : "";
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

        shop.offer(textTrackSavingDialog,()->{
            if(shop.order("TrackText")){
                openRoot().openStage("saveTrack").openScene().openStyle("css/login.css");
                openRoot().openStage("saveTrack").showAndWait();
                return shop.purchase(saveTrackText);
            }
            return null;
        });

        shop.offer(saveTrackText,()->{
            if(shop.order(connect) && shop.order("TrackText", TrackText.class)){
                try{
                    db.saveEntry((TrackText)shop.purchase("TrackText"));
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
