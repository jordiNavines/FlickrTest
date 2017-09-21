package com.jordi.navines.flickr.flickrtest.ui.base;

/**
 * Created by jordi on 22/09/2017.
 */

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface BasePresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
