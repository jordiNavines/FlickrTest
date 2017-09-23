package com.jordi.navines.flickr.flickrtest.ui.gallery;

import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.base.MvpView;

/**
 * Created by jordi on 21/09/2017.
 */

public interface GalleryMvpView extends MvpView {

    void onLoadGallerySuccessful(ImagesResponse response, boolean refresh);

    void onLoadGalleryError();
}
