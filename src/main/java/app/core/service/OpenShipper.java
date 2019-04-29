package app.core.service;

import app.core.OpenRoot;
import app.core.shop.Shop;

import java.util.function.Supplier;

public class OpenShipper implements Supplier {

    private OpenRoot openRoot;

    public OpenShipper(OpenRoot openRoot) {
        this.openRoot = openRoot;
    }

    protected OpenRoot openRoot(){
        return openRoot;
    }

    public void signContract(Shop shop){}

    @Override
    public Object get() {
        return null;
    }
}
