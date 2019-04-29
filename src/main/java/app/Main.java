package app;

import app.core.OpenRoot;
import app.core.shop.Product;
import app.shipper.DatabaseShipper;
import app.shipper.HooktheoryShipper;
import app.shipper.LoginShipper;
import app.shipper.SynthesizerShipper;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends OpenRoot {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);

        new LoginShipper(this).signContract(getShop());
        new HooktheoryShipper(this).signContract(getShop());
        new SynthesizerShipper(this).signContract(getShop());
        new DatabaseShipper(this).signContract(getShop());

        primaryStage.setTitle("Syntetyzator dzwiekow");
        primaryStage.centerOnScreen();
        openStage(primaryStage).openScene("main").show();

        getShop().deliver("childPath","4", Product.REUSABLE);
    }

    @Override
    public void stop() {

    }
}

