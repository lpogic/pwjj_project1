package app.controller;

import app.core.OpenController;
import app.shipper.DatabaseDealer;
import app.shipper.HooktheoryDealer;
import app.shipper.LoginDealer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class MainController extends OpenController {
    public static final Object setTab1 = new Object();
    public static final Object setTab2 = new Object();

    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;

    @FXML
    private TabPane tabPane;

    @Override
    protected void employ() {
        tab1.setContent(openScene().openPane("synthesizer").getPane());
        tab2.setContent(openScene().openPane("tracksList").getPane());
        tab2.setOnSelectionChanged(event -> {
            if(tab2.isSelected())
                openScene().openPane("tracksList").dress();
        });
        openRoot().getShop().offer(setTab1,()->{
            tabPane.getSelectionModel().select(tab1);
            return null;
        });
        openRoot().getShop().offer(setTab2,()->{
            tabPane.getSelectionModel().select(tab2);
            return null;
        });
    }

    @Override
    protected void dress() {
    }

    @FXML
    public void exitAction(){
        openStage().close();
    }

    @FXML
    public void loginAction(){
        System.out.println(openRoot().getShop().instantOrder(LoginDealer.token));
    }

    @FXML
    void requestAction(ActionEvent event) {
        openRoot().getShop().instantOrder(HooktheoryDealer.showTrends);
    }

    @FXML
    void saveAction(ActionEvent event) {
        openRoot().getShop().instantOrder(DatabaseDealer.textTrackSavingDialog);
    }
}
