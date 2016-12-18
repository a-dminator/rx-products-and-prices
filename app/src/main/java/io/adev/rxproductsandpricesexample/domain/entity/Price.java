package io.adev.rxproductsandpricesexample.domain.entity;

public final class Price {
    public final String sku;
    public final String price;
    public Price(String sku, String price) {
        this.sku = sku;
        this.price = price;
    }
}
