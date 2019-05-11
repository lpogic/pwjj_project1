package app.core.shop.contract;

import app.core.shop.contract.stamp.Stamp;
import app.core.shop.Product;

public class SimpleContract implements Contract<Object> {

    private Stamp stamp;

    public SimpleContract(Stamp stamp) {
        this.stamp = stamp;
    }

    @Override
    public Object fetch(Product product, boolean spend) {
        return stamp.seal(product, spend);
    }
}
