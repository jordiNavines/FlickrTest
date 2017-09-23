package com.jordi.navines.flickr.flickrtest.ui.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.adapter.ImagePagerAdapter;
import com.jordi.navines.flickr.flickrtest.model.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jordi on 22/09/2017.
 */

public class ImageActivity extends AppCompatActivity {

    ImagePagerAdapter mAdapter;

    @BindView(R.id.pager) ViewPager mViewPager;

    public static final String ARG_POSITION = "position";
    public static final String ARG_IMAGE_LIST = "imageslist";

    private ArrayList<Photo> mImages;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            position = getIntent().getExtras().getInt(ARG_POSITION);
            mImages = getIntent().getExtras().getParcelableArrayList(ARG_IMAGE_LIST);
        }

        mAdapter= new ImagePagerAdapter(getSupportFragmentManager(), this, mImages);
        mViewPager.setAdapter(mAdapter);

        if (mImages.size() > position) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
