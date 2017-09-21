package com.jordi.navines.flickr.flickrtest.ui.gallery;

import com.jordi.navines.flickr.flickrtest.ui.base.BasePresenter;
import com.jordi.navines.flickr.flickrtest.ui.base.MvpView;

import javax.inject.Inject;

/**
 * Created by jordi on 21/09/2017.
 */

public class GalleryPresenter implements BasePresenter<GalleryMvpView> {

    private GalleryMvpView mMvpView;

    @Inject
    public GalleryPresenter() {

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
