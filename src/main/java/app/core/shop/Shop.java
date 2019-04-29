package app.core.shop;

import java.util.HashMap;
import java.util.function.Supplier;

public class Shop {

    private HashMap<Object, Product> products;
    private Product nullProduct = new Product() {
        @Override
        public boolean isStored() {
            return false;
        }

        @Override
        public boolean isAvailable() {
            return false;
        }

        @Override
        public void stockUp() {}

        @Override
        public <T> boolean isCompatible(Class<T> demand) {
            return false;
        }

        @Override
        public Object spend() {
            return null;
        }
    };

    public Shop() {
        products = new HashMap<>();
    }

    public void offer(Object fee, Supplier supplier){
        products.put(fee,new UnlimitedProduct(supplier,null));
    }

    public void offer(Object fee, Supplier supplier, int contract){
        Product product;
        switch(contract){
            case Product.REUSABLE:
                product = new ReusableProduct(supplier,null);
                break;
            case Product.UNLIMITED:
                product = new UnlimitedProduct(supplier,null);
                break;
            default:
                product = new LimitedProduct(supplier,null,contract);
        }
        products.put(fee,product);
    }

    public void deliver(Object fee, Object stuff) {
        products.put(fee,new UnlimitedProduct(null,stuff));
    }

    public void deliver(Object fee, Object stuff, int stuffType){
        products.put(fee,stuffType == Product.REUSABLE ? new ReusableProduct(null,stuff)
                : new UnlimitedProduct(null,stuff));
    }

    public boolean isAvailable(Object fee){
        return products.getOrDefault(fee,nullProduct).isAvailable();
    }

    public boolean isOffhandAvailable(Object fee){
        return products.getOrDefault(fee,nullProduct).isStored();
    }

    public boolean order(Object fee) {
        Product product = products.getOrDefault(fee,nullProduct);
        product.stockUp();
        return product.isStored();
    }

    public<T> boolean order(Object fee, Class<T> demand){
        Product product = products.getOrDefault(fee,nullProduct);
        product.stockUp();
        return product.isCompatible(demand);
    }

    public Object purchase(Object fee) {
        return products.getOrDefault(fee,nullProduct).spend();
    }

    public Object instantOrder(Object fee){
        Product product = products.getOrDefault(fee,nullProduct);
        product.stockUp();
        return product.spend();
    }
}
