package com.jordi.navines.flickr.flickrtest.ui.image;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordi.navines.flickr.flickrtest.R;
import com.jordi.navines.flickr.flickrtest.model.Photo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class ImageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private Photo mParam1;


    public ImageFragment() {
        // Required empty public constructor
    }


    public static ImageFragment newInstance(Photo param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_pager_image, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView text_author = (TextView) view.findViewById(R.id.text_author);
        Picasso.with(getActivity()).load(mParam1.getMedia().getLink()).into(image);
        text_author.setText(mParam1.getAuthor());

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
