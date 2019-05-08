package app.core;

import app.core.shop.Dealer;

public abstract class OpenPaneScion {

    private OpenPane openPane;
    private Dealer shop;

    public OpenPaneScion(OpenPane openPane) {
        this();
        setLineage(openPane);
    }

    public OpenPaneScion() {
        shop = new Dealer();
    }

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

    public Dealer getShop() {
        return shop;
    }
}
