package com.jordi.navines.flickr.flickrtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.client.Client;
import com.jordi.navines.flickr.flickrtest.network.model.response.ImagesResponse;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryMvpView;
import com.jordi.navines.flickr.flickrtest.ui.gallery.GalleryPresenter;
import com.jordi.navines.flickr.flickrtest.utils.Utils;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


/**
 * Created by jordi on 23/09/2017.
 */

public class FlickrUnitTest {

    @Mock
    private GalleryMvpView view;

    @Mock
    private ImagesResponse mockResponse;

    @Mock
    private Context ctx;

    @Mock
    private Utils utils;

    @Mock
    private ConnectivityManager connectivityManager;

    @Mock
    private NetworkInfo networkInfo;

    private Client client;

    GalleryPresenter galleryPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        galleryPresenter = new GalleryPresenter(ctx);
        galleryPresenter.attachView(view);
        client =  new Client();

        final Context context = Mockito.mock(Context.class);
        final ConnectivityManager connManager = Mockito.mock(ConnectivityManager.class);
        final NetworkInfo networkInfo = Mockito.mock(NetworkInfo.class);
    }

    @Test
    public void test1_RetrofitService() throws IOException, InterruptedException {
        Call<ImagesResponse> call = client.getFlickrService().getImagesPublicFeed(Constants.FORMAT_JSON);
        Response<ImagesResponse> response = call.execute();
        assertTrue(response != null);
    }

    @Test
    public void test2_GalleryPresenterLoadImages() throws IOException, InterruptedException {
        mockConnection(true);

        galleryPresenter.loadImages(false);
        Thread.sleep(1000);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onLoadGallerySuccessful((ImagesResponse) Mockito.any(), eq(false));
    }

    @Test
    public void test3_GalleryPresenterLoadImagesNoConnection() throws IOException, InterruptedException {
        mockConnection(false);

        galleryPresenter.loadImages(false);
        Thread.sleep(1000);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onNoInternetConnection();
    }


    public void mockConnection(boolean result){
        when(ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(result);
    }
}
