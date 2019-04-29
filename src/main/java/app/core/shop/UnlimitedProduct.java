package app.core.shop;

import java.util.function.Supplier;

public class UnlimitedProduct implements Product{
    private Supplier supplier;
    private Object stock;

    public UnlimitedProduct(Supplier supplier, Object stock) {
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
        if(isAvailable() && !isStored())
            stock = supplier.get();
    }

    public<T> boolean isCompatible(Class<T> demand){
        return demand.isInstance(stock);
    }

    public Object spend(){
        Object stock = this.stock;
        this.stock = null;
        return stock;
    }
}
