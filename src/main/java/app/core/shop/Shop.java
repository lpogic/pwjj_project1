package app.core.shop;

import app.core.shop.contract.Contract;

import java.util.HashMap;
import java.util.function.Supplier;

public class Shop {

    private HashMap<Contract, Product> stock;

    public Shop() {
        stock = new HashMap<>();
    }

    public<T> void offer(Contract<T> contract, Supplier<T> supplier){
        stock.put(contract,new Product(supplier,null));
    }

    public<T> void offer(Class<T> brand, Supplier<T> supplier){
        offer(Contract.forClass(brand),supplier);
    }

    public<T> void deliver(Contract<T> contract, T item){
        stock.put(contract,new Product(null,item));
    }

    public<T> void deliver(Class<T> brand, T item){
        deliver(Contract.forClass(brand),item);
    }

    public<T> boolean validate(Contract<T> contract){
        Product product = stock.get(contract);
        if(product == null)return false;
        if(product.isAvailable())return true;
        cancel(contract);
        return false;
    }

    public void cancel(Contract contract){
        stock.remove(contract);
    }

    public<T> boolean order(Contract<T> contract) {
        Product product = stock.get(contract);
        if(product == null)return false;
        if(product.isAvailable()){
            return contract.fetch(product,false) != null;
        } else {
            cancel(contract);
            return false;
        }
    }

    public<T> boolean order(Class<T> brand){
        return order(Contract.forClass(brand));
    }

    public<T> T deal(Contract<T> contract) {
        Product product = stock.get(contract);
        if(product == null)return null;
        return contract.fetch(product, true);
    }

    public<T> T deal(Contract<T> contract, T substitute){
        T item = deal(contract);
        return item != null ? item : substitute;
    }

    public <T> T deal(Class<T> brand){
        return deal(Contract.forClass(brand));
    }

    public<T> T deal(Class<T> brand, T substitute){
        return deal(Contract.forClass(brand),substitute);
    }

    public<T> T safeDeal(Contract<T> contract) {
        Product product = stock.get(contract);
        if(product == null)throw new NullPointerException("Deal failed: unregistered contract");
        T item = contract.fetch(product, true);
        if(item == null)throw new NullPointerException("Deal failed: invalid contract");
        return item;
    }
}
