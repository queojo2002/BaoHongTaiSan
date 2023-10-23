package com.example.baohongtaisan_2.Model;

import androidx.annotation.NonNull;

public class UploadIMG {

    private String mImageUrl;

    public UploadIMG() {
    }

    public UploadIMG(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return getmImageUrl();
    }
}
