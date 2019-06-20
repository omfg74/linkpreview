package ru.omfgdevelop.linkpreview.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
    int type;
    String text;
    int itemNumber;
    String link;
    Map<String,Integer> map;
    Map<String,String>textMap;

    public MainAcivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.model = new MainRequest(this);
        map = new HashMap<>();
        textMap = new HashMap<>();
    }

    @Override
    public void fetchDatafromSourse(PreviewObject q) {
        model.createRequest(q);
    }

    @Override
    public void onButtonPressed(String s) {
        this.text = s;
        LinkParserInterface linkParserInterface = new LinkParser();
        String lnk = linkParserInterface.parse(s);
        link= lnk;

        view.changeText();
        if (lnk != null) {
            map.put(lnk,-1);
            textMap.put(lnk,s);
            type =Constants.SNIPPETMESSAGE;
            PreviewObject previewObject = new PreviewObject();
            previewObject.setText(text);
            previewObject.setType(type);
            previewObject.setUrl(lnk);
            previewObject.setLink(lnk);
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
    public void provideNumber(int i, PreviewObject previewObject) {
        if (map.get(previewObject.getUrl())==-1){
        this.itemNumber = i;
        map.put(previewObject.getUrl(),i);
            fetchDatafromSourse(previewObject);
        }
    }

    @Override
    public void callbackMainRequest(PreviewObject previewObject) {
        previewObject.setType(type);
        previewObject.setText(textMap.get(previewObject.getLink()));
        view.addData(map.get(previewObject.getLink()), previewObject);
        map.remove(previewObject.getUrl());
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
