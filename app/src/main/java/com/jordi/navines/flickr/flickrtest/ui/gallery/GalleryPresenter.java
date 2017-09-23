package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.jordi.navines.flickr.flickrtest.application.AppController;
import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.client.Client;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.base.BasePresenter;
import com.jordi.navines.flickr.flickrtest.utils.Utils;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jordi on 21/09/2017.
 */

public class GalleryPresenter implements BasePresenter<GalleryMvpView> {

    private GalleryMvpView mMvpView;
    private Context mCtx;

    @Inject
    public GalleryPresenter(Context ctx) {
        mCtx = ctx;
    }

    public void loadImages(final boolean resfresh){
        if (!Utils.checkInternetConnection(mCtx)){
            // No connection
            mMvpView.onNoInternetConnection();
            return;
        }

        Call<ImagesResponse> call = new Client().getFlickrService().getImagesPublicFeed(Constants.FORMAT_JSON);
        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                if (response.isSuccessful()) {
                    mMvpView.onLoadGallerySuccessful(response.body(), resfresh);
                }
            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                if (t instanceof IOException) {  // Network Issue
                    mMvpView.onNoInternetConnection();
                } else {
                    mMvpView.onLoadGalleryError();
                }
            }
        });
    }


    @Override
    public void attachView(GalleryMvpView mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }
}
