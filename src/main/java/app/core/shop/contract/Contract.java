package app.core.shop.contract;

import app.core.shop.Product;
import app.core.shop.contract.complex.ListContract;
import app.core.shop.contract.stamp.Stamp;

import java.util.List;

public interface Contract<T> {
    T fetch(Product product, boolean spend);

    static SimpleContract forObject(){
        return new SimpleContract(Stamp.WARRANTY);
    }

    static<T> ClassicContract<T> forObjectOf(Class<T> brand){
        return new ClassicContract<>(brand, Stamp.WARRANTY);
    }

    static<T> ExclusiveContract<T> forClass(Class<T> brand){
        return new ExclusiveContract<>(brand, Stamp.WARRANTY);
    }

    static<T> Contract<List<T>> forListOf(Class<T> brand){
        return new ListContract<>(brand, Stamp.WARRANTY);
    }

    static SimpleContract forObject(Stamp stamp){
        return new SimpleContract(stamp);
    }

    static<T> ClassicContract<T> forObjectOf(Class<T> brand, Stamp stamp){
        return new ClassicContract<>(brand, stamp);
    }

    static<T> ExclusiveContract<T> forClass(Class<T> brand, Stamp stamp){
        return new ExclusiveContract<>(brand, stamp);
    }

    static<T> Contract<List<T>> forListOf(Class<T> brand, Stamp stamp){
        return new ListContract<>(brand, stamp);
    }
}
