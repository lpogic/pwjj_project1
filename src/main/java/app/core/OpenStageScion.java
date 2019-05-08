package app.core;

import app.core.shop.Dealer;

public abstract class OpenStageScion {

    private Object id;
    private OpenStage openStage;
    private Dealer shop;

    public OpenStageScion(Object id, OpenStage openStage) {
        this(id);
        setLineage(openStage);
    }

    public OpenStageScion(Object id) {
        this.id = id;
        shop = new Dealer();
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

    public Dealer getShop() {
        return shop;
    }

}
