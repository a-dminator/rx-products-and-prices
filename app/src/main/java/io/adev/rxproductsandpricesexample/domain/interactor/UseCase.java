package io.adev.rxproductsandpricesexample.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.disposables.Disposables.empty;
import static io.reactivex.schedulers.Schedulers.io;

public abstract class UseCase<T> {

    public void execute(DisposableObserver<? super T> observer) {
        mDisposable = buildSource()
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribeWith(observer);
    }

    protected abstract Observable<? extends T> buildSource();

    private Disposable mDisposable = empty();
    public final void dispose() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
