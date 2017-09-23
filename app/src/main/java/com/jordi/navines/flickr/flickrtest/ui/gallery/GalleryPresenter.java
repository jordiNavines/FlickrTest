package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.app.Application;

import com.jordi.navines.flickr.flickrtest.application.AppController;
import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.client.Client;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.base.BasePresenter;
import com.jordi.navines.flickr.flickrtest.utils.Utils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jordi on 21/09/2017.
 */

public class GalleryPresenter implements BasePresenter<GalleryMvpView> {

    private GalleryMvpView mMvpView;

    @Inject
    public GalleryPresenter() {

    }

    public void loadImages(){
        if (!Utils.checkInternetConnection(AppController.getInstance().getApplicationContext())){
            // No connection
            mMvpView.onNoInternetConnection();
            return;
        }

        Call<ImagesResponse> call = new Client().getFlickrService().getImagesPublicFeed(Constants.FORMAT_JSON);
        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                if (response.isSuccessful()) {
                    mMvpView.onLoadGallerySuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                mMvpView.onLoadGalleryError();
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
