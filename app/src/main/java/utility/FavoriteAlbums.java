package utility;

import android.app.Application;

import java.util.ArrayList;

import lastfm.models.Album;

/**
 * Created by Adhu on 6/8/2018.
 */

public class FavoriteAlbums extends Application {

    private ArrayList<Album> albums;
    private static FavoriteAlbums favoriteAlbums;

    public static FavoriteAlbums getInstance() {
        return favoriteAlbums;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        favoriteAlbums = this;
        albums = new ArrayList<>();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> a) {
        albums = a;
    }
}
