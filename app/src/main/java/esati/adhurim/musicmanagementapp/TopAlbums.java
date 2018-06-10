package esati.adhurim.musicmanagementapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import data.CustomAlbumsListViewAdapter;
import lastfm.models.Album;
import utility.AppController;

public class TopAlbums extends AppCompatActivity {

    private CustomAlbumsListViewAdapter adapter;
    private ArrayList<Album> albums = new ArrayList<Album>();
    private ListView listView;
    private String artist;
    //public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //loadAlbums();

        //contextOfApplication = getApplicationContext();
        artist = getIntent().getStringExtra("SEARCHED_ARTIST");

        getArtistAlbums(artist);

        listView = (ListView) findViewById(R.id.albumsListView);
        adapter = new CustomAlbumsListViewAdapter(TopAlbums.this, R.layout.album_list_row, albums);
        listView.setAdapter(adapter);


    }

    public void loadAlbums() {
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("favoriteAlbums", null);
        Type type = new TypeToken<ArrayList<Album>>() {}.getType();
        MainActivity.favoriteAlbums = gson.fromJson(json, type);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //this.finish();
    }

    /*public static Context getContextOfApplication() {
        return contextOfApplication;
    }*/

    private void getArtistAlbums(String artist) {
        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="
                + artist.replaceAll("\\s","%20") + "&api_key=aba32193fb27a38e7836ae73df892a17&format=json";
        albums.clear();

        JsonObjectRequest topAlbusRequest = new JsonObjectRequest(Request.Method.GET, url,
                (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject topAlbumsObject = response.getJSONObject("topalbums");
                    JSONArray albumsArray = topAlbumsObject.getJSONArray("album");

                    for(int c = 0; c < albumsArray.length(); c++) {
                        JSONObject jsonObject = albumsArray.getJSONObject(c);

                        String albumTitle = "";
                        if((!jsonObject.getString("name").equals("(null)"))
                                && !jsonObject.getString("name").equals("")) {

                            albumTitle = jsonObject.getString("name");
                        }
                        String playCount = "Play count: " + jsonObject.getString("playcount");

                        JSONObject artistObject = jsonObject.getJSONObject("artist");
                        String artistName = artistObject.getString("name");

                        JSONArray imageArray = jsonObject.getJSONArray("image");
                        JSONObject xLargeImage = imageArray.getJSONObject(3);
                        String albumImage = xLargeImage.getString("#text");

                        Album album = new Album();
                        album.setAlbumTitle(albumTitle);
                        album.setAlbumArtist(artistName);
                        album.setPlayCount(playCount);
                        if(albumImage.equals("")) {
                            album.setAlbumImage("https://i.imgur.com/TZGPbyU.png");
                        }else {
                            album.setAlbumImage(albumImage);
                        }

                        albums.add(album);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(topAlbusRequest);
    }
}
