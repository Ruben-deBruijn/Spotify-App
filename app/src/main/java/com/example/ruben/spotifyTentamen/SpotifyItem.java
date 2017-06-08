package com.example.ruben.spotifyTentamen;

import java.io.Serializable;

/**
 * Created by Ruben on 16-2-2017.
 */

public class SpotifyItem implements Serializable{

    public String albumUrl;
    public String albumThumbUrl;
    public String albumName;
    public String spotifyId;
    public String albumUri;


    @Override
    public String toString() {
        return "SpotifyItem{" +
                "albumUrl='" + albumUrl + '\'' +
                ", albumThumbUrl='" + albumThumbUrl + '\'' +
                ", albumName='" + albumName + '\'' +
                ", spotifyId='" + spotifyId + '\'' +
                ", albumUri='" + albumUri + '\'' +
                '}';
    }


    public String getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getAlbumThumbUrl() {
        return albumThumbUrl;
    }

    public void setAlbumThumbUrl(String albumThumbUrl) {
        this.albumThumbUrl = albumThumbUrl;
    }



    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }



    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

}