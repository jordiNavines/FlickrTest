package com.jordi.navines.flickr.flickrtest.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.adapter.GalleryAdapter;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GalleryFragment extends Fragment implements GalleryMvpView, SwipeRefreshLayout.OnRefreshListener {

    private int mColumns = 2;
    private OnListFragmentInteractionListener mListener;

    @BindView(R.id.swipe_refresh_list) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.list) RecyclerView mRecyclerView;

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
        ButterKnife.bind(this, view);
        Context context = view.getContext();

        // Set the adapter
        if (mColumns <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mLayoutManager = new GridLayoutManager(context, mColumns);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        mGalleryPresenter.attachView(this);
        mGalleryPresenter.loadImages(false);

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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                    mGalleryPresenter.loadImages(false);
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
    public void onLoadGallerySuccessful(ImagesResponse response, boolean refresh) {
        loading = false;
        if (refresh) {
            mImages.clear();
        }
        mImages.addAll(response.getImages());
        mAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mGalleryPresenter.loadImages(true);
    }

    @Override
    public void onLoadGalleryError() {
        loading = false;
        mListener.showSnacbBarError(getActivity().getResources().getString(R.string.error_fetch_public_feed));
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onNoInternetConnection() {
        loading = false;
        mListener.showSnacbBarError(getActivity().getResources().getString(R.string.error_no_connection));
        mSwipeRefresh.setRefreshing(false);
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
