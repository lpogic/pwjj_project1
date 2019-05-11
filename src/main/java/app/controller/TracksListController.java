package app.controller;

import app.core.pane.OpenController;
import app.core.shop.contract.Contract;
import app.model.TrackText;
import app.dealer.DatabaseDealer;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class TracksListController extends OpenController {
    public static final Contract<String> trackFilter = Contract.forObjectOf(String.class);
    public static final Contract<String> nameFilter = Contract.forObjectOf(String.class);


    @FXML
    private ListView<TrackText> list;

    @FXML
    private TextField track;

    @FXML
    private TextField name;

    @Override
    protected void employ() {
        shop().offer(trackFilter,()->track.getText());
        shop().offer(nameFilter,()->name.getText());

        list.setOnKeyPressed(ke->{
            if(ke.getCode() == KeyCode.ENTER) {
                TrackText selectedTrackText = list.getSelectionModel().getSelectedItem();
                if (selectedTrackText != null && shop().order(TrackText.class)) {
                    TrackText trackText = shop().deal(TrackText.class);
                    trackText.set(selectedTrackText);
                    shop().order(MainController.setTab1);
                }
            }
        });
    }

    @Override
    protected void dress() {
        if(shop().order(DatabaseDealer.getFilteredTracks)){
            list.getItems().setAll(shop().deal(DatabaseDealer.getFilteredTracks));
        }
    }

    @FXML
    void filterAction() {
        if(shop().order(DatabaseDealer.getFilteredTracks)){
            list.getItems().setAll(shop().deal(DatabaseDealer.getFilteredTracks));
        }
    }
}
