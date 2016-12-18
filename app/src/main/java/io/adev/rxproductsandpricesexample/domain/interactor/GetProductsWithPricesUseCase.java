package io.adev.rxproductsandpricesexample.domain.interactor;

import android.util.Pair;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.adev.rxproductsandpricesexample.domain.entity.Price;
import io.adev.rxproductsandpricesexample.domain.entity.Product;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.schedulers.Schedulers.io;

public final class GetProductsWithPricesUseCase extends UseCase<List<Pair<Product, Price>>> {

    private static final Scheduler sCommonScheduler = Schedulers.from(Executors.newSingleThreadExecutor());

    @Override
    protected Observable<List<Pair<Product, Price>>> buildSource() {

        Observable<List<Product>> productsSource = Observable.create(new ObservableOnSubscribe<List<Product>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Product>> e) {
                e.onNext(fetchProducts());
            }
        }).subscribeOn(io()).observeOn(sCommonScheduler).cache();

        Observable<Collection<Price>> pricesSource = productsSource.flatMap(new Function<List<Product>, ObservableSource<Collection<Price>>>() {
            @Override
            public ObservableSource<Collection<Price>> apply(List<Product> products) {

                List<List<Product>> productParts = Lists.partition(products, 20);
                List<Observable<List<Price>>> pricesPartSources = new ArrayList<>();
                for (List<Product> productPart : productParts) {
                    pricesPartSources.add(
                            Observable.just(productPart)
                                    .flatMap(new Function<List<Product>, ObservableSource<List<Price>>>() {
                                        @Override
                                        public ObservableSource<List<Price>> apply(List<Product> products) {
                                            return Observable.just(fetchPrices(products));
                                        }
                                    }).subscribeOn(io()).observeOn(sCommonScheduler)
                    );
                }

                return Observable.merge(pricesPartSources).
                        collect(new Callable<Collection<Price>>() {
                            @Override
                            public Collection<Price> call() {
                                return new ArrayList<>();
                            }
                        }, new BiConsumer<Collection<Price>, List<Price>>() {
                            @Override
                            public void accept(Collection<Price> prices, List<Price> pricesPart) throws Exception {
                                prices.addAll(pricesPart);
                            }
                        })
                        .toObservable()
                        .subscribeOn(sCommonScheduler)
                        .observeOn(sCommonScheduler);
            }
        });

        return Observable.combineLatest(
                productsSource,
                pricesSource,
                new BiFunction<List<Product>, Collection<Price>, List<Pair<Product, Price>>>() {
                    @Override
                    public List<Pair<Product, Price>> apply(List<Product> products, Collection<Price> prices) {
                        return mergeProductsAndPrices(products, prices);
                    }
                });
    }

    private static List<Pair<Product, Price>> mergeProductsAndPrices(List<Product> products, Collection<Price> prices) {

        List<Pair<Product, Price>> productsWithPrices = new ArrayList<>();
        for (Product product : products) {
            Price price = findPriceWithSku(product.sku, prices);
            productsWithPrices.add(new Pair<>(product, price));
        }

        return productsWithPrices;
    }

    private static Price findPriceWithSku(String sku, Collection<Price> prices) {

        for (Price price : prices) {
            if (price.sku.equals(sku)) {
                return price;
            }
        }

        return null;
    }

    private static List<Product> fetchProducts() {
        return Arrays.asList(new Product("1"), new Product("2"), new Product("3"));
    }

    private static List<Price> fetchPrices(List<Product> products) {
        List<Price> prices = new ArrayList<>();
        for (Product product : products) {
            prices.add(new Price(product.sku, "$" + product.sku));
        }
        return prices;
    }

}
