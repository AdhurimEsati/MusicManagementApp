package esati.adhurim.musicmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import data.CustomFavoriteAlbumsAdapter;
import lastfm.models.Album;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Album> favoriteAlbums;
    public static Context contextOfApplication;
    private ListView albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextOfApplication = getApplicationContext();

        loadAlbums();

        albumList = (ListView) findViewById(R.id.favoriteAlbumList);
        CustomFavoriteAlbumsAdapter adapter = new CustomFavoriteAlbumsAdapter(MainActivity.this, R.layout.album_list_row, favoriteAlbums);
        albumList.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.v("Adhurim", "favoriteAlbums.size() = " + favoriteAlbums.size());

        for(int c = 0; c < favoriteAlbums.size(); c++) {
            Log.v("Adhurim", "C=" + c + ". " + favoriteAlbums.get(c).getAlbumTitle() + ".");
        }
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    /*public void saveAlbums() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteAlbums);
        editor.putString("favoriteAlbums", json);
        editor.apply();
    }*/

    //Load favorite albums when app opens
    public void loadAlbums() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("favoriteAlbums", null);
        Type type = new TypeToken<ArrayList<Album>>() {}.getType();
        favoriteAlbums = gson.fromJson(json, type);

        if(favoriteAlbums == null) {
            favoriteAlbums = new ArrayList<>();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //saveAlbums();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();//.inflate(R.menu.main, menu);
        inflater.inflate(R.menu.search_bar, menu);
        MenuItem item = menu.findItem(R.id.searchBar);
        android.widget.SearchView searchBar = (android.widget.SearchView) item.getActionView();

        searchBar.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent intent = new Intent(MainActivity.this, TopAlbums.class);
                intent.putExtra("SEARCHED_ARTIST", s);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.savedAlbums) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.searchPage) {
            startActivity(new Intent(MainActivity.this, SearchPage.class));
        }
        /*if(id == R.id.searchBar) {
            Log.v("Adhurim", "LESH MARIJE");
        }*/

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
