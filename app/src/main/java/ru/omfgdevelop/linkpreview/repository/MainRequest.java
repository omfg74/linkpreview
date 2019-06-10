package ru.omfgdevelop.linkpreview.repository;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.omfgdevelop.linkpreview.utils.RetrofitClient;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.interfaces.MainRequestcallback;

public class MainRequest implements MainActivityContract.Model {

    MainRequestcallback requestcallback;

    public MainRequest(MainRequestcallback mainRequestcallback) {
        this.requestcallback = mainRequestcallback;
    }

    @Override
    public Observable<PreviewObject> createRequest2(String q) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        return retrofitClient.getRetrofitInterface().getPreviewObject(Constants.API_KEY, q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void createRequest(String q) {
        final RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getRetrofitInterface().getPreviewObject(Constants.API_KEY, q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestcallback.onErrorCallBack(throwable);
                    }
                })
                .subscribe(new Observer<PreviewObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PreviewObject previewObject) {
                        requestcallback.callbackMainRequest(previewObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestcallback.onErrorCallBack(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
