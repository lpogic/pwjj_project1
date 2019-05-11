package app.core.shop;

import java.util.function.Supplier;

public class Product {

    private Supplier supplier;
    private Object item;

    public Product(Supplier supplier, Object prototype) {
        this.supplier = supplier;
        this.item = prototype;
    }

    public boolean isStored() {
        return item != null;
    }

    public boolean isImportable(){
        return supplier != null;
    }

    public boolean isAvailable() {
        return item != null || supplier != null;
    }

    public Object imp(){
        return supplier.get();
    }

    public Object get(){
        return item;
    }

    public void set(Object item){
        this.item = item;
    }
}
