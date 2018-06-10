package esati.adhurim.musicmanagementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.CustomAlbumTracksAdapter;
import lastfm.models.Album;
import lastfm.models.Track;
import utility.AppController;

public class DetailedAlbumActivity extends AppCompatActivity {

    private Album album;
    private NetworkImageView albumImage;
    private TextView albumTitle;
    private TextView albumArtist;
    private ListView albumTracks;
    private CustomAlbumTracksAdapter adapter;
    private ArrayList<Track> tracks = new ArrayList<Track>();

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_album);

        albumImage = (NetworkImageView) findViewById(R.id.artistImage);
        albumTitle = (TextView) findViewById(R.id.albumTitle);
        albumArtist = (TextView) findViewById(R.id.albumArtist);

        //Get album object
        album = (Album) getIntent().getSerializableExtra("albumObj");

        albumImage.setImageUrl(album.getAlbumImage(), imageLoader);
        albumTitle.setText(album.getAlbumTitle());
        albumArtist.setText(album.getAlbumArtist());

        getAlbumTracks(album);

        albumTracks = (ListView) findViewById(R.id.albumTracks);
        adapter = new CustomAlbumTracksAdapter(DetailedAlbumActivity.this, R.layout.album_tracks_row, tracks);
        albumTracks.setAdapter(adapter);

        albumTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }





    private void getAlbumTracks(Album album) {
        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=aba32193fb27a38e7836ae73df892a17&artist="
                + album.getAlbumArtist().replaceAll("\\s", "%20")
                + "&album=" + album.getAlbumTitle().replaceAll("\\s", "%20") + "&format=json";
        Log.v("Adhurim", "URL: " + url);
        tracks.clear();
        //artist.replaceAll("\\s","%20")

        JsonObjectRequest tracksRequest = new JsonObjectRequest(Request.Method.GET, url,
                (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject albumObject = response.getJSONObject("album");
                    JSONObject tracksObject = albumObject.getJSONObject("tracks");
                    JSONArray tracksArray = tracksObject.getJSONArray("track");

                    for(int c = 0; c < tracksArray.length(); c++) {
                        JSONObject jsonObject = tracksArray.getJSONObject(c);

                        String trackName = jsonObject.getString("name");
                        String url = jsonObject.getString("url");
                        Log.v("tracks api", "Got track url: " + url);
                        String duration = jsonObject.getString("duration");
                        Log.v("tracks api", "Got track duration: " + duration);
                        JSONObject artistObject = jsonObject.getJSONObject("artist");
                        String artistName = artistObject.getString("name");
                        Log.v("Adhurim", "GOT ARTIST NAME: " + artistName);


                        Track track = new Track();

                        track.setUrl(url);
                        track.setName(trackName);
                        track.setArtist(artistName);
                        track.setDuration(duration);

                        tracks.add(track);
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
        AppController.getInstance().addToRequestQueue(tracksRequest);
    }


}
