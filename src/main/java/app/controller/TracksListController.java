package app.controller;

import app.core.pane.OpenController;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.TrackText;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;

public class TracksListController extends OpenController {

    public static final Contract<String> trackFilter = Contract.forObjectOf(String.class, Stamp.SUPPLY);
    public static final Contract<String> nameFilter = Contract.forObjectOf(String.class, Stamp.SUPPLY);
    public static final Contract<List<TrackText>> getFilteredTracks =
            Contract.forListOf(TrackText.class, Stamp.SUPPLY);


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
        if(shop().order(getFilteredTracks)){
            list.getItems().setAll(shop().deal(getFilteredTracks));
        }
    }

    @FXML
    void filterAction() {
        if(shop().order(getFilteredTracks)){
            list.getItems().setAll(shop().deal(getFilteredTracks));
        }
    }
}
