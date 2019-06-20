package ru.omfgdevelop.linkpreview.repository;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.utils.RetrofitClient;
import ru.omfgdevelop.linkpreview.interfaces.MainRequestcallback;

public class MainRequest implements MainActivityContract.Model {

    MainRequestcallback requestcallback;
    PreviewObject previewObject;

    public MainRequest(MainRequestcallback mainRequestcallback) {
        this.requestcallback = mainRequestcallback;
    }

    public void createRequest(final PreviewObject q) {
        this.previewObject = q;

         RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getRetrofitInterface().getPreviewObject(Constants.API_KEY, q.getLink())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestcallback.onErrorCallBack(throwable);
                    }
                }).map(new Function<PreviewObject, PreviewObject>() {
            @Override
            public PreviewObject apply(PreviewObject previewObject) throws Exception {
                previewObject.setLink(q.getLink());
                return previewObject;
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
