package app.core;

import app.core.shop.Shop;

public abstract class OpenStageScion {

    private Object id;
    private OpenStage openStage;
    private Shop shop;

    public OpenStageScion(Object id, OpenStage openStage) {
        this(id);
        setLineage(openStage);
    }

    public OpenStageScion(Object id) {
        this.id = id;
        shop = new Shop();
    }

    protected void setLineage(OpenStage openStage){this.openStage = openStage;}

    public Object getId() {
        return id;
    }

    protected void setId(Object id) {
        this.id = id;
    }

    public OpenRoot openRoot() {
        return openStage.openRoot();
    }

    public OpenStage openStage(){
        return openStage;
    }

    public Shop getShop() {
        return shop;
    }

}
