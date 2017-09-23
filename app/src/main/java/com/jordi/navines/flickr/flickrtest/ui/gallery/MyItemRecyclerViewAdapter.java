package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Photo} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Photo> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mCtx;

    public MyItemRecyclerViewAdapter(Context ctx, List<Photo> items, OnListFragmentInteractionListener listener) {
        mCtx = ctx;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
//        holder.mContentView.setText(mValues.get(position).getTitle());
        Picasso.with(mCtx).load(mValues.get(position).getMedia().getLink()).into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction((ArrayList<Photo>) mValues, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        @BindView(R.id.content) TextView mContentView;
        @BindView(R.id.image) ImageView mImageView;
        public Photo mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
