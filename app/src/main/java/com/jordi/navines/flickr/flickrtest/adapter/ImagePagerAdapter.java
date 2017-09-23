package com.jordi.navines.flickr.flickrtest.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.ui.image.ImageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jordi on 22/09/2017.
 */

public class ImagePagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Photo> mImages;

    public ImagePagerAdapter(FragmentManager fm, Context context, ArrayList<Photo> images) {
        super(fm);
        mContext = context;
        mImages = images;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(mImages.get(position));
    }


}
