package ru.omfgdevelop.linkpreview.interfaces;

import io.reactivex.Observable;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public interface MainActivityContract {
    interface View{
        void showData(PreviewObject previewObject);
    }
    interface Model{
        void createRequest(String q);
        Observable<PreviewObject> createRequest2(String q);
    }
    interface Presenter{
        void onCreate();
        void fetchDatafromSourse(String q);
        void onDestroy();
        void onButtonPressed(String s);
    }
}
