package io.adev.rxproductsandpricesexample.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import io.adev.rxproductsandpricesexample.R;
import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;

public final class ProductWithPriceHolder extends RecyclerView.ViewHolder {

    private final TextView tvProductAndPrice;
    public ProductWithPriceHolder(View itemView) {
        super(itemView);
        tvProductAndPrice = (TextView) itemView.findViewById(R.id.tvProductAndPrice);
    }

    public void bind(Pair<Product, Price> productAndPrice) {
        tvProductAndPrice.setText("sku=" + productAndPrice.first.sku + ", price=" + productAndPrice.second.price);
    }

}
