package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.application.AppController;
import com.jordi.navines.flickr.flickrtest.injection.components.GalleryComponent;
import com.jordi.navines.flickr.flickrtest.injection.components.DaggerGalleryComponent;
import com.jordi.navines.flickr.flickrtest.injection.modules.GalleryPresenterModule;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.ui.image.ImageActivity;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onListFragmentInteraction(ArrayList<Photo> imagesList, int position) {
        Log.d("click", "click!");
        Intent intent= new Intent(this, ImageActivity.class);
        intent.putParcelableArrayListExtra("imageslist", imagesList);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private GalleryComponent mGalleryComponent;
    public GalleryComponent galleryComponent() {

        if (mGalleryComponent == null) {
            mGalleryComponent =DaggerGalleryComponent.builder()
                    //                .sayHelloPresenterModule(new SayHelloPresenterModule(this, new Person()))
                    .galleryPresenterModule(new GalleryPresenterModule(AppController.getInstance()))
                    .build();
        }
        return mGalleryComponent;
    }
}
