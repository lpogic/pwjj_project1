package app.core;

import app.core.shop.Dealer;

public abstract class OpenSceneScion {

    private Object id;
    private OpenScene openScene;
    private Dealer shop;

    public OpenSceneScion(Object id, OpenScene openScene) {
        this(id);
        setLineage(openScene);
    }

    public OpenSceneScion(Object id) {
        this.id = id;
        shop = new Dealer();
    }

    protected void setLineage(OpenScene openScene){this.openScene = openScene;}

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

    public Dealer getShop() {
        return shop;
    }

}
