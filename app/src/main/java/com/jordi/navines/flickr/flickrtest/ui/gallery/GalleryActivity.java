package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.application.AppController;
import com.jordi.navines.flickr.flickrtest.injection.components.GalleryComponent;
import com.jordi.navines.flickr.flickrtest.injection.components.DaggerGalleryComponent;
import com.jordi.navines.flickr.flickrtest.injection.modules.GalleryPresenterModule;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.ui.image.ImageActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        Intent intent= new Intent(this, ImageActivity.class);
        intent.putParcelableArrayListExtra(ImageActivity.ARG_IMAGE_LIST, imagesList);
        intent.putExtra(ImageActivity.ARG_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void showSnacbBarError(String message) {
        createSnackBar(message);
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

    /* Snackbar for messages */
    public void createSnackBar(String message){
        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(rootView, message, Snackbar.LENGTH_LONG);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.white));

        snackbar.show();
    }
}
