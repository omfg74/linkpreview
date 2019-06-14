package ru.omfgdevelop.linkpreview.interfaces;

import io.reactivex.Observable;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public interface DataGetter {
    Observable<PreviewObject> createRequestObservable(String q);
}
