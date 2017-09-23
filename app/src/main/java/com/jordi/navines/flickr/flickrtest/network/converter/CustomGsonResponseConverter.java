package com.jordi.navines.flickr.flickrtest.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jordi on 21/09/2017.
 */

public class CustomGsonResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private Pattern pattern = Pattern.compile("^jsonFlickrFeed(.\\S*)");

    public CustomGsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        Matcher matcher = pattern.matcher(res);

        String res = response.replaceAll("\\s+", "");
        String res2 = res.replaceAll("jsonFlickrFeed\\(", "");
        String res3 = res2.substring(0,res2.length()-1);

        JsonReader jsonReader = gson.newJsonReader(new StringReader(res3));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
