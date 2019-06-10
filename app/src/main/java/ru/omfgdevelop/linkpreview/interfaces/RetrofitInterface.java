package ru.omfgdevelop.linkpreview.interfaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public interface RetrofitInterface {
    @GET("/")
    Observable<PreviewObject> getPreviewObject(@Query("key") String key, @Query("q") String q);
}
