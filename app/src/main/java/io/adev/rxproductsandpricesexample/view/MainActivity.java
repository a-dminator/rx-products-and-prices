package io.adev.rxproductsandpricesexample.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import java.util.List;

import io.adev.rxproductsandpricesexample.R;
import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;
import io.adev.rxproductsandpricesexample.presenter.MainPresenter;
import io.adev.rxproductsandpricesexample.presenter.contract.MainContract;
import io.adev.rxproductsandpricesexample.view.adapter.ProductsWithPricesAdapter;

import static android.support.v7.widget.RecyclerView.*;

public final class MainActivity extends AppCompatActivity implements MainContract.View {

    private final MainContract.Presenter mPresenter = new MainPresenter(this);

    private RecyclerView rvProductsWithPrices;
    private final ProductsWithPricesAdapter mProductsWithPricesAdapter = new ProductsWithPricesAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mPresenter.initialize();
    }

    private void initView() {
        rvProductsWithPrices = (RecyclerView) findViewById(R.id.rvProductsAndPrices);
        rvProductsWithPrices.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        rvProductsWithPrices.setAdapter(mProductsWithPricesAdapter);
    }

    @Override
    public void displayProductsWithPrices(List<Pair<Product, Price>> productsWithPrices) {
        mProductsWithPricesAdapter.setData(productsWithPrices);
    }

}
