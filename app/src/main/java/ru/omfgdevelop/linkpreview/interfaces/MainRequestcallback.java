package ru.omfgdevelop.linkpreview.interfaces;

import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public interface MainRequestcallback {
    void callbackMainRequest(PreviewObject previewObject);

    void onErrorCallBack(Throwable t);
}
