package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.media.Image;
import android.util.Log;

import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.client.Client;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.base.BasePresenter;
import com.jordi.navines.flickr.flickrtest.ui.base.MvpView;

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
        Log.d("TEST", "---->>>>>> LOADING ");
        Call<ImagesResponse> call = new Client().getFlickrService().getOrganizationConfigDetails(Constants.FORMAT_JSON);
        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("TEST", "---->>>>>> " + response.body().toString());
                    mMvpView.onLoadGallerySuccessful(response.body());
                } else {
                    Log.d("TEST", "---->>>>>> FAIL");
                }
            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                Log.d("TEST", "---->>>>>> FAIL ERROr " + t.getMessage());
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
