package app.core.shop.contract.stamp;

import app.core.shop.Product;

public class ServiceStamp implements Stamp {

    @Override
    public Object seal(Product product, boolean spend) {
        return product.imp();
    }
}
