package app.core.pane;

import app.core.OpenRoot;
import app.core.scene.OpenScene;
import app.core.stage.OpenStage;
import app.core.shop.Shop;

public abstract class OpenSceneScion {

    private Object id;
    private OpenScene openScene;

    public OpenSceneScion(Object id, OpenScene openScene) {
        this(id);
        setLineage(openScene);
    }

    public OpenSceneScion(Object id) {
        this.id = id;
    }

    protected void setLineage(OpenScene openScene){
        this.openScene = openScene;
    }

    public Object getId() {
        return id;
    }

    protected void setId(Object id) {
        this.id = id;
    }

    public OpenRoot openRoot() {
        return openScene.openStage().openRoot();
    }

    public OpenStage openStage(){
        return openScene.openStage();
    }

    public OpenScene openScene(){
        return openScene;
    }

    public Shop shop(){return openRoot().getShop();}

}
