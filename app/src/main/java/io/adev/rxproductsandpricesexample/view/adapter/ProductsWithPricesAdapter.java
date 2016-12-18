package io.adev.rxproductsandpricesexample.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.adev.rxproductsandpricesexample.R;
import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;

import static java.util.Collections.emptyList;

public final class ProductsWithPricesAdapter extends RecyclerView.Adapter<ProductWithPriceHolder> {

    private final Context mContext;
    public ProductsWithPricesAdapter(Context context) {
        mContext = context;
    }

    private List<Pair<Product, Price>> mData = emptyList();
    public void setData(List<Pair<Product, Price>> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ProductWithPriceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_product_with_price, null);
        return new ProductWithPriceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductWithPriceHolder holder, int position) {
        Pair<Product, Price> model = mData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
