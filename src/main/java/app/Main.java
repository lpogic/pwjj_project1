package app;

import app.core.OpenRoot;
import app.core.shop.Product;
import app.shipper.DatabaseDealer;
import app.shipper.HooktheoryDealer;
import app.shipper.LoginDealer;
import app.shipper.SynthesizerDealer;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sound.midi.Synthesizer;


public class Main extends OpenRoot {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);


        openDealer(LoginDealer.class,new LoginDealer(this)).employ();
        openDealer(HooktheoryDealer.class,new HooktheoryDealer(this)).employ();
        openDealer(SynthesizerDealer.class,new SynthesizerDealer()).employ();
        openDealer(DatabaseDealer.class,new DatabaseDealer(this)).employ();

        primaryStage.setTitle("Syntetyzator dzwiekow");
        primaryStage.centerOnScreen();
        openStage(primaryStage).openScene("main").show();

        openDealer().deliver("childPath","4", Product.REUSABLE);
    }

    @Override
    public void stop() {

    }
}

