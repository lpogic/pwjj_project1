package app.core.stage;

import app.core.OpenRoot;
import app.core.shop.Shop;

public abstract class OpenRootScion {

    private Object id;
    private OpenRoot openRoot;

    public OpenRootScion(Object id, OpenRoot openRoot) {
        this(id);
        setLineage(openRoot);
    }

    public OpenRootScion(Object id) {
        this.id = id;
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

    public Shop shop() {
        return openRoot.getShop();
    }
}
