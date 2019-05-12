package app.controller;

import app.core.pane.OpenController;
import app.model.MidiTrackPlayer;
import app.model.TrackText;
import app.dealer.SynthesizerDealer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SynthesizerController extends OpenController {

    @FXML
    private HBox hbox;

    @FXML
    private TextField songText;

    @FXML
    private Slider rate;

    @FXML
    private ImageView image;

    private TrackText trackText;

    @Override
    protected void employ() {

        image.setImage(new Image("img/play.png"));
        trackText = new TrackText();
        songText.textProperty().bindBidirectional(trackText.getTrackProperty());
        rate.valueProperty().bindBidirectional(trackText.getRateProperty());
        shop().deliver(TrackText.class,trackText);
        songText.textProperty().addListener((cl,ov,nv)->{
            if(MidiTrackPlayer.validateTrackText(nv)) {
                hbox.getChildren().clear();
                for (int i = 0; i < nv.length(); ++i) {
                    char ch = nv.charAt(i);
                    Button button = new Button();
                    button.setText("" + ch);
                    HBox.setMargin(button,new Insets(0,0,Integer.parseInt("" + ch) * 10,0));
                    hbox.getChildren().add(button);
                }
            }
        });
    }

    @Override
    protected void dress() {
    }

    @FXML
    void playAction() {
        new Thread(this::playTrack).start();
    }

    private void playTrack(){
        shop().deal(SynthesizerDealer.playTrack);
    }
}
