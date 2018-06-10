package esati.adhurim.musicmanagementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.CustomArtistListViewAdapter;
import lastfm.models.Artist;
import utility.AppController;

public class SearchPage extends AppCompatActivity {

    private ArrayList<Artist> artists = new ArrayList<Artist>();
    private ListView listView;
    private EditText searchBox;
    private Button searchButton;
    private String artist;
    private CustomArtistListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page2);

        listView = (ListView) findViewById(R.id.artistListView);
        adapter = new CustomArtistListViewAdapter(SearchPage.this, R.layout.artist_list_row, artists);

        searchBox = (EditText) findViewById(R.id.searchBox);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchBox.getText().toString().equals("")) {
                    getArtists(searchBox.getText().toString());
                }else {
                    searchBox.setHint("Type in the name of an Artist");
                }
            }
        });
        listView.setAdapter(adapter);
    }

    private void getArtists(String artist) {

        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist="
                    + artist.replaceAll("\\s","%20") + "&api_key=aba32193fb27a38e7836ae73df892a17&format=json";
        artists.clear();

        JsonObjectRequest artistRequest = new JsonObjectRequest(Request.Method.GET, url,
                (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resultsObject = response.getJSONObject("results").getJSONObject("artistmatches");
                    JSONArray artistArray = resultsObject.getJSONArray("artist");

                    for(int c = 0; c < artistArray.length(); c++) {
                        JSONObject jsonObject = artistArray.getJSONObject(c);
                        String name = jsonObject.getString("name");
                        String url = jsonObject.getString("url");
                        JSONArray imageArray = jsonObject.getJSONArray("image");
                        JSONObject xLargeImage = imageArray.getJSONObject(3);
                        String imageUrl = xLargeImage.getString("#text");

                        Artist artist = new Artist();
                        artist.setName(name);
                        artist.setUrl(url);

                        if(imageUrl.equals("")) {
                            artist.setImageUrl("https://i.imgur.com/TZGPbyU.png");
                        }else {
                            artist.setImageUrl(imageUrl);
                        }




                        artists.add(artist);
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
        AppController.getInstance().addToRequestQueue(artistRequest);
    }
}
