package app.core.shop;

import java.util.function.Supplier;

public class ReusableProduct implements Product {
    private Supplier supplier;
    private Object stock;

    public ReusableProduct(Supplier supplier, Object stock) {
        this.supplier = supplier;
        this.stock = stock;
    }

    public boolean isStored(){
        return stock != null;
    }

    public boolean isAvailable(){
        return supplier != null || stock != null;
    }

    public void stockUp(){
        if(isAvailable() && !isStored()) {
            stock = supplier.get();
        }
    }

    public<T> boolean isCompatible(Class<T> demand){
        return demand.isInstance(stock);
    }

    public Object spend(){
        return stock;
    }
}
