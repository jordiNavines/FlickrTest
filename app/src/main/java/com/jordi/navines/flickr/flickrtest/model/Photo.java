package com.jordi.navines.flickr.flickrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jordi on 21/09/2017.
 */

public class Photo implements Serializable, Parcelable {

    String title;
    String link;
    Media media;
    String date_taken;
    String description;
    String published;
    String author;
    String author_id;
    String tags;

    public Photo(String title) {
        this.title = title;
    }


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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", media='" + media.toString() + '\'' +
                ", date_taken='" + date_taken + '\'' +
                ", description='" + description + '\'' +
                ", published='" + published + '\'' +
                ", author='" + author + '\'' +
                ", author_id='" + author_id + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeParcelable(media, flags);
        dest.writeString(date_taken);
        dest.writeString(description);
        dest.writeString(published);
        dest.writeString(author);
        dest.writeString(author_id);
        dest.writeString(tags);
    }

    public Photo(Parcel in) {
        title = in.readString();
        link = in.readString();
        media = in.readParcelable(Media.class.getClassLoader());
        date_taken = in.readString();
        description = in.readString();
        published = in.readString();
        author = in.readString();
        author_id = in.readString();
        tags = in.readString();

    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
