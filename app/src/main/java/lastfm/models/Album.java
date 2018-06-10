package lastfm.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adhu on 6/5/2018.
 */

public class Album implements Serializable {

    private static final long id = 1L;
    private String albumTitle; //Album title
    private String albumArtist; //Artist albumTitle
    private String albumImage; //Album albumImage
    private String playCount; //Album play count
    private ArrayList<Track> tracks; //Album tracks
    private boolean favorite;

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public static long getId() {
        return id;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
