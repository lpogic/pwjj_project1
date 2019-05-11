package app.core.shop.contract.stamp;

import app.core.shop.Product;

public class WarrantyStamp implements Stamp {

    @Override
    public Object seal(Product product, boolean spend) {
        if(!product.isStored())
            product.set(product.imp());
        return product.get();
    }
}
