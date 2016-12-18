package io.adev.rxproductsandpricesexample.presenter;

import android.util.Log;
import android.util.Pair;

import java.util.List;

import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;
import io.adev.rxproductsandpricesexample.domain.interactor.GetProductsWithPricesUseCase;
import io.adev.rxproductsandpricesexample.presenter.contract.MainContract;
import io.reactivex.observers.DisposableObserver;

public final class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    private final MainContract.View mView;
    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    private final GetProductsWithPricesUseCase mGetProductsWithPricesUseCase = new GetProductsWithPricesUseCase();
    private DisposableObserver<List<Pair<Product, Price>>> createGetProductsWithPricesObserver() {
        return new DisposableObserver<List<Pair<Product, Price>>>() {
            @Override
            public void onNext(List<Pair<Product, Price>> productsWithPrices) {
                mView.displayProductsWithPrices(productsWithPrices);
            }
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            @Override public void onComplete() {}
        };
    }

    @Override
    public void initialize() {
        mGetProductsWithPricesUseCase.execute(createGetProductsWithPricesObserver());
    }

}
