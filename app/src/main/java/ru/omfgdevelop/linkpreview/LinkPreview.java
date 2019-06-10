package ru.omfgdevelop.linkpreview;

import android.app.Application;
import android.content.Context;

public class LinkPreview extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LinkPreview.context = getApplicationContext();
    }

    public static Context getContext() {
        return LinkPreview.context;
    }
}
