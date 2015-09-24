package com.ys.libraryp.mylibraryproject.bean;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class ImageInfo {
    protected String imageAlt;
    protected String imageDes;
    protected int imageUrl;

    public ImageInfo(String imageAlt, String imageDes, int imageUrl) {
        this.imageAlt = imageAlt;
        this.imageDes = imageDes;
        this.imageUrl = imageUrl;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public String getImageDes() {
        return imageDes;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
