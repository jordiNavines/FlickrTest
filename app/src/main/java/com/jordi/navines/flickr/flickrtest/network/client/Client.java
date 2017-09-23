package com.jordi.navines.flickr.flickrtest.network.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordi.navines.flickr.flickrtest.constants.Constants;
import com.jordi.navines.flickr.flickrtest.network.converter.CustomConverterFactory;
import com.jordi.navines.flickr.flickrtest.network.converter.CustomGsonResponseConverter;
import com.jordi.navines.flickr.flickrtest.network.services.FlickrService;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jordi on 21/09/2017.
 */

public class Client {

    final ConcurrentHashMap<Class, Object> services;
    static Retrofit retrofit;

    /**
     * Create the rest retrofit client
     */
    public Client() {
        OkHttpClient.Builder httpClient = createClient();

        Gson gson = new GsonBuilder() .setLenient() .create();

        this.services = new ConcurrentHashMap();
        this.retrofit = new Retrofit.Builder().
                baseUrl(Constants.FLICKR_URL).
                addConverterFactory(CustomConverterFactory.create(gson)).
                client(httpClient.build()).
                build();
    }


    public OkHttpClient.Builder createClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request  = original.newBuilder()
                            .build();

                return chain.proceed(request);
            }
        });


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);


        return httpClient;
    }



    public FlickrService getFlickrService() {
        return getAPIService(FlickrService.class);
    }


    protected <T> T getAPIService(Class<T> cls) {
        if(!this.services.contains(cls)) {
            this.services.putIfAbsent(cls, this.retrofit.create(cls));
        }

        return (T) this.services.get(cls);
    }


    public static Retrofit retrofit() {
        return retrofit;
    }
}
