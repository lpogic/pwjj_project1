package app;

import app.core.OpenRoot;
import app.core.shop.contract.Contract;
import app.model.MidiTrackPlayer;
import app.dealer.DatabaseDealer;
import app.dealer.HooktheoryDealer;
import app.dealer.LoginDealer;
import app.dealer.SynthesizerDealer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends OpenRoot {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void employ(Stage primaryStage) {

        openDealer(new LoginDealer(this)).employ();
        openDealer(new HooktheoryDealer(this)).employ();
        openDealer(new SynthesizerDealer(this)).employ();
        openDealer(new DatabaseDealer(this)).employ();

        getShop().deliver(MidiTrackPlayer.childPath,"4");

        primaryStage.setTitle("Syntetyzator dzwiekow");
        primaryStage.centerOnScreen();
        openStage(primaryStage).openScene("main").show();
    }
}

