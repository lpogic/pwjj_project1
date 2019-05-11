package app.core.pane;

import app.core.OpenRoot;
import app.core.scene.OpenScene;
import app.core.stage.OpenStage;
import app.core.shop.Shop;

public abstract class OpenPaneScion {

    private OpenPane openPane;

    public OpenPaneScion(OpenPane openPane) {
        this();
        setLineage(openPane);
    }

    public OpenPaneScion() {}

    protected void setLineage(OpenPane openPane){this.openPane = openPane;}

    public OpenRoot openRoot() {
        return openPane.openScene().openStage().openRoot();
    }

    public OpenStage openStage(){
        return openPane.openScene().openStage();
    }

    public OpenScene openScene(){
        return openPane.openScene();
    }

    public OpenPane openPane(){
        return openPane;
    }

    public Shop shop(){return openRoot().getShop();}
}
