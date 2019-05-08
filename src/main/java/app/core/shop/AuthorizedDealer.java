package app.core.shop;

import app.core.shop.voucher.Voucher;

import java.util.HashMap;
import java.util.function.Supplier;

public class AuthorizedDealer {

    private HashMap<Voucher, Product> stuff;
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

    public AuthorizedDealer() {
        stuff = new HashMap<>();
    }

    public<T> void offer(Voucher<T> productVoucher, Supplier<T> supplier){
        stuff.put(productVoucher,new UnlimitedProduct(supplier,null));
    }

    public<T> void offer(Voucher<T> productVoucher, Supplier<T> supplier, int contract){
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
        stuff.put(productVoucher,product);
    }

    public<T> void deliver(Voucher<T> productVoucher, T item) {
        stuff.put(productVoucher,new UnlimitedProduct(null,item));
    }

    public<T> void deliver(Voucher<T> productVoucher, T item, int itemType){
        stuff.put(productVoucher,itemType == Product.REUSABLE ? new ReusableProduct(null,item)
                : new UnlimitedProduct(null,item));
    }

    public<T> boolean isAvailable(Voucher<T> productVoucher){
        return stuff.getOrDefault(productVoucher,nullProduct).isAvailable();
    }

    public<T> boolean isOffhandAvailable(Voucher<T> productVoucher){
        return stuff.getOrDefault(productVoucher,nullProduct).isStored();
    }

    public<T> boolean order(Voucher<T> productVoucher) {
        Product product = stuff.getOrDefault(productVoucher,nullProduct);
        product.stockUp();
        return product.isStored() && productVoucher.isBranded(product);
    }

    public<T> T purchase(Voucher<T> productVoucher) {
        return productVoucher.label(stuff.getOrDefault(productVoucher,nullProduct).spend());
    }

    public<T> T instantOrder(Voucher<T> productVoucher){
        Product product = stuff.getOrDefault(productVoucher,nullProduct);
        product.stockUp();
        return productVoucher.label(product.spend());
    }
}
