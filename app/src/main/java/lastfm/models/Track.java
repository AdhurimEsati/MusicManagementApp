package lastfm.models;

import java.io.Serializable;

/**
 * Created by Adhu on 6/5/2018.
 */

public class Track implements Serializable {

    private static final long id = 2L;

    private String name;
    private String url;
    private String duration;
    private String artist;

    public String getArtist() { return artist; }

    public void setArtist(String artist) { this.artist = artist; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
