package ru.omfgdevelop.linkpreview.presenter;

import android.util.Log;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;
import ru.omfgdevelop.linkpreview.interfaces.LinkParserInterface;
import ru.omfgdevelop.linkpreview.repository.Constants;
import ru.omfgdevelop.linkpreview.repository.MainRequest;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.interfaces.MainRequestcallback;
import ru.omfgdevelop.linkpreview.utils.LinkParser;

public class MainAcivityPresenter implements MainActivityContract.Presenter, MainRequestcallback {
    MainActivityContract.View view;
    MainActivityContract.Model model;
    CompositeDisposable compositeDisposable;
    Set<String> waitSet;
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
        waitSet.add(q);
//        Log.d("Log", "Model creAate request");
//        callbackMainRequest(new PreviewObject());
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
        waitSet.add(link);
        view.changeText();
        if (link != null) {
            type =Constants.SNIPPETMESSAGE;
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
        waitSet.remove(previewObject.getUrl());
        for (Iterator<String>iterator = waitSet.iterator();iterator.hasNext();) {

        }
        view.addData(itemNumber, previewObject);
        }

    @Override
    public void onErrorCallBack(Throwable t) {
        view.removeDataItem(itemNumber);
        PreviewObject previewObject = new PreviewObject();
        previewObject.setType(Constants.SIMPLE_MESSAGE);
        previewObject.setText(text);
        view.showData(previewObject);
    }

}
