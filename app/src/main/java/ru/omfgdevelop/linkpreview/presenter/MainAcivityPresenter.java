package ru.omfgdevelop.linkpreview.presenter;

import android.graphics.Bitmap;

import io.reactivex.disposables.CompositeDisposable;
import ru.omfgdevelop.linkpreview.interfaces.LinkParserInterface;
import ru.omfgdevelop.linkpreview.repository.Constants;
import ru.omfgdevelop.linkpreview.repository.MainRequest;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;
import ru.omfgdevelop.linkpreview.interfaces.BitmapCallBackInterface;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.interfaces.MainRequestcallback;
import ru.omfgdevelop.linkpreview.interfaces.PictureLoaderInterface;
import ru.omfgdevelop.linkpreview.utils.LinkParser;

public class MainAcivityPresenter implements MainActivityContract.Presenter, MainRequestcallback, BitmapCallBackInterface {
    MainActivityContract.View view;
    MainActivityContract.Model model;
    CompositeDisposable compositeDisposable;
    PictureLoaderInterface pictureLoaderInterface;
    int type;
    String text;
    int itemNumber;
    String link;

    public MainAcivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.model = new MainRequest(this);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void fetchDatafromSourse(String q) {
        model.createRequest(q);
    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onButtonPressed(String s) {
        this.text = s;
        LinkParserInterface linkParserInterface = new LinkParser();
        link = linkParserInterface.parse(s);
        view.changeText();
        if (link != null) {
            type =Constants.SNIPPETMESSAGE;
//            fetchDatafromSourse(s);
            PreviewObject previewObject = new PreviewObject();
            previewObject.setText(text);
            previewObject.setType(type);
            view.showData(previewObject);

        } else {
            type = Constants.SIMPLE_MESSAGE;
            PreviewObject previewObject = new PreviewObject();
            previewObject.setText(s);
            previewObject.setType(type);
            view.showData(previewObject);
        }

    }

    @Override
    public void provideNumber(int i) {
        this.itemNumber = i;
        fetchDatafromSourse(link);

    }

    @Override
    public void callbackMainRequest(PreviewObject previewObject) {
        previewObject.setText(text);
        previewObject.setType(type);
//        view.showData(previewObject);
        view.addData(itemNumber, previewObject);
        }

    @Override
    public void onErrorCallBack(Throwable t) {
        PreviewObject previewObject = new PreviewObject();
        previewObject.setType(Constants.SIMPLE_MESSAGE);
        previewObject.setText(text);
        view.showData(previewObject);
    }

    @Override
    public void bitmapCallback(Bitmap bitmap) {
    }
}
