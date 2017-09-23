package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.adapter.GalleryAdapter;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GalleryFragment extends Fragment implements GalleryMvpView {

    private int mColumns = 2;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    GridLayoutManager mLayoutManager;
    GalleryAdapter mAdapter;

    List<Photo> mImages = new ArrayList<Photo>();

    @Inject GalleryPresenter mGalleryPresenter;

    public GalleryFragment() {
    }


    public static GalleryFragment newInstance(int columnCount) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((GalleryActivity) getActivity()).galleryComponent().inject(this);

        mAdapter = new GalleryAdapter(getActivity(), mImages, mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mGalleryPresenter.attachView(this);

        mGalleryPresenter.loadImages();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            if (mColumns <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mLayoutManager = new GridLayoutManager(context, mColumns);
                recyclerView.setLayoutManager(mLayoutManager);
            }
            recyclerView.setAdapter(mAdapter);
        }

        // Init scroll listener
        initiScollListener();

        return view;
    }


    protected int visibleThreshold = 5;
    protected int firstVisibleItem, visibleItemCount, totalItemCount;
    protected int itemAutoPlaying = -1;

    private int previousTotal = 0;
    public boolean loading = true;

    public void initiScollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    loading = true;
                    mGalleryPresenter.loadImages();
                }

                if (itemAutoPlaying != mLayoutManager.findFirstCompletelyVisibleItemPosition() &&
                        mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                    itemAutoPlaying = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mGalleryPresenter.detachView();
    }

    @Override
    public void onLoadGallerySuccessful(ImagesResponse response) {
        loading = false;
        mImages.addAll(response.getImages());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadGalleryError() {
        mListener.showSnacbBarError(getActivity().getResources().getString(R.string.error_fetch_public_feed));
    }

    @Override
    public void onNoInternetConnection() {
        mListener.showSnacbBarError(getActivity().getResources().getString(R.string.error_no_connection));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(ArrayList<Photo> imagesList, int position);

        void showSnacbBarError(String message);
    }
}
