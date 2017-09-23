package com.jordi.navines.flickr.flickrtest.injection.components;

import com.jordi.navines.flickr.flickrtest.injection.modules.ApplicationModule;
import com.jordi.navines.flickr.flickrtest.injection.modules.Networkmodule;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jordi on 21/09/2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class, Networkmodule.class})
public interface ActivityComponent {

    void inject(GalleryFragment galleryFragment);

}
