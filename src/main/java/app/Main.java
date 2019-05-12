package app;

import app.core.OpenRoot;
import app.model.MidiTrackPlayer;
import app.dealer.DatabaseDealer;
import app.dealer.HooktheoryDealer;
import app.dealer.SynthesizerDealer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends OpenRoot {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void employ(Stage primaryStage) {

        openDealer(new HooktheoryDealer(this)).employ();
        openDealer(new SynthesizerDealer(this)).employ();
        openDealer(new DatabaseDealer(this)).employ();

        primaryStage.setTitle("Syntetyzator dzwiekow");
        primaryStage.centerOnScreen();
        openStage(primaryStage).openScene("main").show();
    }
}

