
package ru.omfgdevelop.linkpreview.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviewObject {

    public PreviewObject() {
    }

    public PreviewObject(String text) {
        this.text = text;
    }


    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("url")
    @Expose
    private String url;
    private String text;
    private int type;

    public int getType() {
        return type;

    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
