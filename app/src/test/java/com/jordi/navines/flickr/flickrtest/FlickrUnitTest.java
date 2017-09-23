package com.jordi.navines.flickr.flickrtest;

import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.client.Client;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryMvpView;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


/**
 * Created by jordi on 23/09/2017.
 */

public class FlickrUnitTest {

    @Mock
    private GalleryMvpView view;

    @Mock
    private ImagesResponse mockResponse;

    private Client client;

    GalleryPresenter galleryPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        galleryPresenter = new GalleryPresenter();
        galleryPresenter.attachView(view);
        client =  new Client();
    }

    @Test
    public void testRetrofitService() throws IOException, InterruptedException {
        Call<ImagesResponse> call = client.getFlickrService().getImagesPublicFeed(Constants.FORMAT_JSON);
        Response<ImagesResponse> response = call.execute();
        assertTrue(response != null);
    }

    @Test
    public void testGalleryPresenterLoadImages() throws IOException, InterruptedException {
        galleryPresenter.loadImages();
        Thread.sleep(1000);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onLoadGallerySuccessful((ImagesResponse) Mockito.any());
    }



}
