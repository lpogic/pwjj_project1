package app.core.pane;

import app.core.scene.OpenScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.net.URL;

public class OpenPane extends OpenSceneScion {

    private Pane pane;
    private OpenController controller;

    public OpenPane(Object id, OpenScene openScene) {
        super(id,openScene);
    }

    public Pane getPane(){
        if(pane == null)load();
        return pane;
    }

    public void show(){
        openScene().openPane(getId(),true);
        openScene().show();
    }

    public void show(String fxml){
        load(fxml);
        show();
    }

    public void dress(){
        if(controller == null)load();
        controller.dress();
    }

    public void load(){
        if (getId() instanceof String) {
            load((String) getId());
        } else throw new NullPointerException("Cannot determine pane source");
    }

    public void load(String fxml) {
        URL fxmlUrl = openRoot().getFxmlResource(fxml);
        System.out.println(fxmlUrl.getFile());
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Pane pane;
        try{
            pane = loader.load();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd aplikacji");
            alert.setHeaderText("Błąd podczas ładowania widoku");
            alert.setContentText("Sprawdź plik: " + fxmlUrl.getFile());
            alert.showAndWait();
            throw new ExceptionInInitializerError("Pane load fail");
        }
        OpenController controller = loader.getController();
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd aplikacji");
            alert.setHeaderText("Błąd podczas ładowania kontrolera");
            alert.setContentText("Sprawdź plik: " + fxmlUrl.getFile());
            alert.showAndWait();
            throw new ExceptionInInitializerError("Pane load fail");
        }
        this.pane = pane;
        controller.setLineage(this);
        controller.employ();
        this.controller = controller;
    }
}
