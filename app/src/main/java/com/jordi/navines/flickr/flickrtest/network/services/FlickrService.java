package com.jordi.navines.flickr.flickrtest.network.services;

import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by jordi on 21/09/2017.
 */

public interface FlickrService {

    /**
     * Call the endpoint to get the list of images from Flickr
     */
    @Headers("Accept: application/json")
    @GET(Constants.FLICKR_URL)
    Call<ImagesResponse> getOrganizationConfigDetails(@Path(Constants.FORMAT_JSON) String format);


}
