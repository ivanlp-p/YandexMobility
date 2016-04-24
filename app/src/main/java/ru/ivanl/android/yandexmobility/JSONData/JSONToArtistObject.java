package ru.ivanl.android.yandexmobility.JSONData;

import java.util.List;

public class JSONToArtistObject {

    private long id;
    private String name;
    private List<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    private static class Cover {
        String small;
        String big;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getSmallCover() {
        return cover.small;
    }

    public String getBigCover() {
        return cover.big;
    }
}
