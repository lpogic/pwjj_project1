package app.core.shop.contract.stamp;

import app.core.shop.Product;

public class SupplyStamp implements Stamp {

    @Override
    public Object seal(Product product, boolean spend) {
        Object item = product.isStored() ? product.get() : product.imp();
        product.set(spend ? null : item);
        return item;
    }
}
