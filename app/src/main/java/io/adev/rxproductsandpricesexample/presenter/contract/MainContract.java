package io.adev.rxproductsandpricesexample.presenter.contract;

import android.util.Pair;

import java.util.List;

import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;

public interface MainContract {
    interface Presenter {
        void initialize();
    }
    interface View {
        void displayProductsWithPrices(List<Pair<Product,Price>> productsWithPrices);
    }
}
