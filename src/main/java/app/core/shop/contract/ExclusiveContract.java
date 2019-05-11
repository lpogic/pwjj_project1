package app.core.shop.contract;

import app.core.shop.contract.stamp.Stamp;

public class ExclusiveContract<T> extends ClassicContract<T> {

    public ExclusiveContract(Class<T> brand, Stamp stamp) {
        super(brand, stamp);
    }

    @Override
    public int hashCode() {
        return getBrand().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ExclusiveContract && ((ExclusiveContract) obj).getBrand().equals(getBrand());
    }
}
