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
    CompositeDisposable compositeDisposable;
    Set<String> waitSet;
    int type;
    String text;
    int itemNumber;
    String link;
    Map<String,Integer> map;
    Map<String,String>textMap;

    public MainAcivityPresenter(MainActivityContract.View view) {
        this.view = view;
        this.model = new MainRequest(this);
        this.compositeDisposable = new CompositeDisposable();
        waitSet = new HashSet<>();
        map = new HashMap<>();
        textMap = new HashMap<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void fetchDatafromSourse(String q) {
        waitSet.add(link);
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
        String lnk = linkParserInterface.parse(s);
        link= lnk;

        view.changeText();
        if (lnk != null) {
            Log.d("Log","link "+lnk);
           lnk= lnk.replace("http://","");
           lnk= lnk.replace("https://","");
           if (!lnk.endsWith("/"))
               lnk=lnk.replace("/","");
//           link= link.replace("https:","https://");
            Log.d("Log","link "+lnk);
            map.put(lnk,-1);
            textMap.put(lnk,s);
            type =Constants.SNIPPETMESSAGE;
            PreviewObject previewObject = new PreviewObject();
            previewObject.setText(text);
            previewObject.setType(type);
            previewObject.setUrl(lnk);
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
        Log.d("Log","map 2"+previewObject.getUrl());
        if (map.get(previewObject.getUrl())==-1){
        this.itemNumber = i;
        map.put(previewObject.getUrl(),i);
        Log.d("Log","map 1"+map.get(previewObject.getUrl()));
//        if (!waitSet.contains(link)) {
            fetchDatafromSourse(previewObject.getUrl());
//        }
        }
    }

    @Override
    public void callbackMainRequest(PreviewObject previewObject) {

        previewObject.setType(type);
        waitSet.remove(previewObject.getUrl());
//ДА наверно лучше заменить парсером

        if (previewObject.getUrl().startsWith("https:")){
            previewObject.setUrl(previewObject.getUrl().replace("https://",""));
        }
        if (previewObject.getUrl().startsWith("http:")){
            previewObject.setUrl(previewObject.getUrl().replace("http://",""));
        }

        if (previewObject.getUrl().startsWith("www.")){
            previewObject.setUrl(previewObject.getUrl().replace("www.",""));
        }
        if (previewObject.getUrl().endsWith("/")){
            previewObject.setUrl(previewObject.getUrl().replace("/",""));
        }
        if (previewObject.getUrl().startsWith("about.")){
            previewObject.setUrl(previewObject.getUrl().replace("about.",""));
        }

//        Log.d("Log", "eq "+link.equals(previewObject.getUrl()));
        Log.d("Log", "eq "+link);
        Log.d("Log", "eq "+previewObject.getUrl());
        Log.d("Log", "url "+previewObject.getUrl());
        Log.d("Log","map "+map.get(previewObject.getUrl()));
        previewObject.setText(textMap.get(previewObject.getUrl()));
        view.addData(map.get(previewObject.getUrl()), previewObject);
        map.remove(previewObject.getUrl());
        }

    @Override
    public void onErrorCallBack(Throwable t) {
        Log.d("Log", "On error callback");
        view.removeDataItem(itemNumber);
        PreviewObject previewObject = new PreviewObject();
        previewObject.setType(Constants.SIMPLE_MESSAGE);
        previewObject.setText(text);
        view.showData(previewObject);
    }

}
