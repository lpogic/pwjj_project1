package app.core.shop.voucher;

import java.util.List;

public class ListVoucher<T> extends Voucher<List<T>> {

    public ListVoucher(Class<T> brand) {
        super(brand);
    }
}
