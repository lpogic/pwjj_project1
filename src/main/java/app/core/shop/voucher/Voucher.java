package app.core.shop.voucher;

public class Voucher<T> {
    private Class<T> brand;

    public Voucher(Class<T> brand) {
        this.brand = brand;
    }

    public Class<T> getBrand() {
        return brand;
    }

    public boolean isBranded(Object object){
        return brand.isInstance(object);
    }

    public T label(Object object){
        return brand.cast(object);
    }
}
