package app.core.shop;

import java.util.function.Supplier;

public class LimitedProduct implements Product {
    private Supplier supplier;
    private Object stock;
    private int contract;

    public LimitedProduct(Supplier supplier, Object stock, int contract) {
        this.supplier = supplier;
        this.stock = stock;
        this.contract = contract;
    }

    public boolean isStored() {
        return stock != null;
    }

    public boolean isAvailable() {
        return (supplier != null && contract > 0) || stock != null;
    }

    public void stockUp() {
        if (isAvailable() && !isStored()) {
            stock = supplier.get();
            --contract;
        }
    }

    public <T> boolean isCompatible(Class<T> demand) {
        return demand.isInstance(stock);
    }

    public Object spend() {
        Object stock = this.stock;
        this.stock = null;
        return stock;
    }
}
