package app.core;

import app.core.shop.Shop;

public abstract class OpenSceneScion {

    private Object id;
    private OpenScene openScene;
    private Shop shop;

    public OpenSceneScion(Object id, OpenScene openScene) {
        this(id);
        setLineage(openScene);
    }

    public OpenSceneScion(Object id) {
        this.id = id;
        shop = new Shop();
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

    public Shop getShop() {
        return shop;
    }

}
