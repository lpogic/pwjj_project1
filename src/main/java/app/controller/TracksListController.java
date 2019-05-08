package app.controller;

import app.core.OpenController;
import app.model.TrackText;
import app.shipper.DatabaseDealer;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.Collection;


public class TracksListController extends OpenController {
    public static final Object trackFilter = new Object();
    public static final Object nameFilter = new Object();


    @FXML
    private ListView<TrackText> list;

    @FXML
    private TextField track;

    @FXML
    private TextField name;

    @Override
    protected void employ() {
        openRoot().getShop().offer(trackFilter,()->track.getText());
        openRoot().getShop().offer(nameFilter,()->name.getText());

        list.setOnKeyPressed(ke->{
            if(ke.getCode() == KeyCode.ENTER) {
                TrackText selectedTrackText = list.getSelectionModel().getSelectedItem();
                if (selectedTrackText != null && openRoot().getShop().order("TrackText", TrackText.class)) {
                    TrackText trackText = (TrackText) openRoot().getShop().purchase("TrackText");
                    trackText.set(selectedTrackText);
                    openRoot().getShop().order(MainController.setTab1);
                }
            }
        });
    }

    @Override
    protected void dress() {
        if(openRoot().getShop().order(DatabaseDealer.getFilteredTracks)){
            list.getItems().setAll((Collection)openRoot().getShop().
                    purchase(DatabaseDealer.getFilteredTracks));
        }
    }

    @FXML
    void filterAction() {
        if(openRoot().getShop().order(DatabaseDealer.getFilteredTracks)){
            list.getItems().setAll((Collection)openRoot().getShop().
                    purchase(DatabaseDealer.getFilteredTracks));
        }
    }
}
