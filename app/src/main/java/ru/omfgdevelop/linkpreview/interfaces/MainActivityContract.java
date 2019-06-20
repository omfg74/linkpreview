package ru.omfgdevelop.linkpreview.interfaces;

import io.reactivex.Observable;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public interface MainActivityContract {
    interface View {
        void showData(PreviewObject previewObject);
        void changeText();
        void addData(int itemNumber, PreviewObject previewObject);
        void removeDataItem(int itemNumber);
    }

    interface Model {
        void createRequest(PreviewObject q);
    }

    interface Presenter {
        void fetchDatafromSourse(PreviewObject previewObject);
        void onButtonPressed(String s);
        void provideNumber(int i, PreviewObject previewObject);
    }
}
