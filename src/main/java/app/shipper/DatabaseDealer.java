package app.shipper;

import app.controller.TracksListController;
import app.core.OpenRoot;
import app.core.shop.OpenDealer;
import app.core.shop.Product;
import app.core.shop.voucher.Voucher;
import app.core.shop.voucher.SimpleVoucher;
import app.model.DatabaseConnection;
import app.model.TrackText;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDealer extends OpenDealer {
    public static final Voucher<Object> connect = new Voucher<>(Object.class);
    public static final Voucher<List<TrackText>> getFilteredTracks = new Voucher<List<TrackText>>((List<TrackText>).getBrand());
    public static final SimpleVoucher textTrackSavingDialog = new SimpleVoucher();
    public static final SimpleVoucher saveTrackText = new SimpleVoucher();

    private DatabaseConnection db;

    public DatabaseDealer(OpenRoot openRoot) {
        super(openRoot);
        db = new DatabaseConnection();
    }

    @Override
    public void employ() {

        offer(connect,()->{
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

        offer(getFilteredTracks,()->{
            if(order(connect)){
                String track = order(TracksListController.trackFilter) ?
                        (String)purchase(TracksListController.trackFilter) : "";
                String name = order(TracksListController.nameFilter) ?
                        (String)purchase(TracksListController.nameFilter) : "";
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

        offer(textTrackSavingDialog,()->{
            if(order("TrackText")){
                openRoot().openStage("saveTrack").openScene().openStyle("css/login.css");
                openRoot().openStage("saveTrack").showAndWait();
                return purchase(saveTrackText);
            }
            return null;
        });

        offer(saveTrackText,()->{
            if(order(connect) && order("TrackText", TrackText.class)){
                try{
                    db.saveEntry((TrackText)purchase("TrackText"));
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
