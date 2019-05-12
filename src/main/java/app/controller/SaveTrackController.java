package app.controller;

import app.core.pane.OpenController;
import app.model.MidiTrackPlayer;
import app.model.TrackText;
import app.dealer.DatabaseDealer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.swing.*;

public class SaveTrackController extends OpenController {

    @FXML
    private StackPane stack;

    @FXML
    private BorderPane border;

    @FXML
    private Button save;

    @FXML
    private TextField name;

    @FXML
    private TextField track;

    @FXML
    private Slider rate;

    private ProgressIndicator progress;
    private TrackText trackText;

    @Override
    protected void employ() {
        progress = new ProgressIndicator();
        progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        name.focusedProperty().addListener((v)->name.getStyleClass().remove("invalid"));
        track.focusedProperty().addListener((v)->track.getStyleClass().remove("invalid"));
        save.focusedProperty().addListener((v)->save.getStyleClass().remove("invalid"));
        name.setOnKeyPressed((ke)->{
            if(ke.getCode() == KeyCode.ENTER)track.requestFocus();
        });
        track.setOnKeyPressed((ke)->{
            if(ke.getCode() == KeyCode.ENTER)rate.requestFocus();
        });
        rate.setOnKeyPressed((ke)->{
            if(ke.getCode() == KeyCode.ENTER) FocusManager.getCurrentManager().focusNextComponent();
        });
    }

    @Override
    protected void dress() {
        trackText = shop().deal(TrackText.class,new TrackText());
        name.textProperty().bindBidirectional(trackText.getNameProperty());
        track.textProperty().bindBidirectional(trackText.getTrackProperty());
        rate.valueProperty().bindBidirectional(trackText.getRateProperty());
    }

    @FXML
    void cancelAction() {
        openStage().getStage().close();
    }

    @FXML
    void saveAction() {
        boolean valid = true;
        if(trackText.getName().isBlank()){
            name.getStyleClass().add("invalid");
            valid = false;
        }
        if(!MidiTrackPlayer.validateTrackText(trackText.getTrack())){
            track.getStyleClass().add("invalid");
            valid = false;
        }
        if(valid){
            responseWait();
            new Thread(this::saveTrack).start();
        }
    }

    private void responseWait(){
        border.setDisable(true);
        border.setOpacity(0.5);
        stack.getChildren().add(progress);
    }

    private void responseFail(){
        border.setDisable(false);
        border.setOpacity(1.0);
        stack.getChildren().remove(progress);
        save.getStyleClass().add("invalid");
    }

    private void responseSuccess(){
        border.setDisable(false);
        border.setOpacity(1.0);
        stack.getChildren().remove(progress);
        openStage().close();
    }


    private void saveTrack() {
        Platform.runLater(shop().order(DatabaseDealer.saveTrack) ? this::responseSuccess : this::responseFail);
    }

}
