package app.core.shop.contract.stamp;

import app.core.shop.Product;

public interface Stamp {
    ServiceStamp SERVICE = new ServiceStamp();
    SupplyStamp SUPPLY = new SupplyStamp();
    WarrantyStamp WARRANTY = new WarrantyStamp();

    Object seal(Product product, boolean spend);
}
