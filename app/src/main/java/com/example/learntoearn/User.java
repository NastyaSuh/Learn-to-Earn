package com.example.learntoearn;
import java.io.Serializable;
public class User implements Serializable {
    private String mPhotoUri;

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }
}