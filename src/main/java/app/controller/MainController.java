package app.controller;

import app.core.pane.OpenController;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class MainController extends OpenController {
    public static final Contract<Object> setTab1 = Contract.forObject(Stamp.SERVICE);
    public static final Contract<Object> setTab2 = Contract.forObject(Stamp.SERVICE);
    public static final Contract<Object> getToken = Contract.forObject(Stamp.SERVICE);
    public static final Contract<Object> saveTrack = Contract.forObject(Stamp.SERVICE);

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
        shop().offer(setTab1,()->{
            tabPane.getSelectionModel().select(tab1);
            return null;
        });
        shop().offer(setTab2,()->{
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
        System.out.println(shop().deal(getToken,"nie zdobyto tokena"));
    }

    @FXML
    void requestAction(ActionEvent event) {
        shop().deal(DiagramController.show);
    }

    @FXML
    void saveAction(ActionEvent event) {
        shop().deal(saveTrack);
    }
}
