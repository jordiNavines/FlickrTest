package com.jordi.navines.flickr.flickrtest.injection.components;

import com.jordi.navines.flickr.flickrtest.injection.modules.GalleryPresenterModule;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jordi on 21/09/2017.
 */

@Singleton
@Component(modules = GalleryPresenterModule.class)
public interface GalleryComponent {

    void inject(GalleryFragment galleryFragment);

}
