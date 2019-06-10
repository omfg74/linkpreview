package ru.omfgdevelop.linkpreview.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import ru.omfgdevelop.linkpreview.LinkPreview;

import ru.omfgdevelop.linkpreview.interfaces.BitmapCallBackInterface;
import ru.omfgdevelop.linkpreview.interfaces.PictureLoaderInterface;

public class NetworkPictureLoader implements PictureLoaderInterface {

    private CustomTarget<Bitmap> requestBuilder;
    private BitmapCallBackInterface bitmapCallBackInterface;

    public NetworkPictureLoader(BitmapCallBackInterface bitmapCallBackInterface) {
        this.bitmapCallBackInterface = bitmapCallBackInterface;
    }

    @Override
    public void getBitmap(String s) {
        requestBuilder = Glide.with(LinkPreview.getContext())
                .as(Bitmap.class)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(s)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmapCallBackInterface.bitmapCallback(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
