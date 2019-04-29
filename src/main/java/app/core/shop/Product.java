package app.core.shop;

public interface Product {
    int UNLIMITED = 0;
    int REUSABLE = -1;

    boolean isStored();
    boolean isAvailable();
    void stockUp();
    <T> boolean isCompatible(Class<T> demand);
    Object spend();
}
