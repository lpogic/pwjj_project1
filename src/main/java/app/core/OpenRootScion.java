package app.core;

import app.core.shop.Shop;

public abstract class OpenRootScion {

    private Object id;
    private OpenRoot openRoot;
    private Shop shop;

    public OpenRootScion(Object id, OpenRoot openRoot) {
        this(id);
        setLineage(openRoot);
    }

    public OpenRootScion(Object id) {
        this.id = id;
        shop = new Shop();
    }

    protected void setLineage(OpenRoot openRoot) {
        this.openRoot = openRoot;
    }

    public Object getId() {
        return id;
    }

    protected void setId(Object id) {
        this.id = id;
    }

    public OpenRoot openRoot() {
        return openRoot;
    }

    public Shop getShop() {
        return shop;
    }
}
