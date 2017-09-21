package com.jordi.navines.flickr.flickrtest.network.model.response;

import com.jordi.navines.flickr.flickrtest.model.Photo;

import java.util.List;

/**
 * Created by jordi on 21/09/2017.
 */

public class ImagesResponse {

    String title;
    String link;
    String description;
    List<Photo> items;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }

    public List<Photo> getImages() {
        return items;
    }

    public void setImages(List<Photo> items) {
        this.items = items;
    }
}
