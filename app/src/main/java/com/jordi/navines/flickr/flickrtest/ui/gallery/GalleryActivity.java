package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.ui.image.ImageActivity;

import java.util.ArrayList;
import java.util.List;

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

//    private ActivityComponent mActivityComponent;
//    public Networkmodule activityComponent() {
//        if (mActivityComponent == null) {
//            mActivityComponent = DaggerActivityComponent.builder()
//                    .activityModule(new ActivityModule(this))
//                    .applicationComponent(AppController.get(this).getComponent())
//                    .build();
//        }
//        return mActivityComponent;
//    }
}
