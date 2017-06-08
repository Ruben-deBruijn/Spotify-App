package com.example.ruben.spotifyTentamen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SpotifyTask.onSpotifyItemAvailable,ListView.OnItemClickListener{

    private ArrayList<SpotifyItem> spotifyItems; // = new ArrayList<SpotifyItem>();
    private ListView spotifyListview;
    private SpotifyAdapter spotifyAdapter;

    private EditText artistSelect =  null;

    private final String SAVED_ITEMS = "SAVED_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);


        artistSelect = (EditText) findViewById(R.id.editText);

        Button getAlbums = (Button) findViewById(R.id.button);
        getAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRequest();
            }
        });

        spotifyItems = new ItemFactory().getSpotifyItems();


        //SpotifyItem spotifyitem = new SpotifyItem();

        spotifyListview = (ListView) findViewById(R.id.albumListView);

        spotifyAdapter = new SpotifyAdapter(this, spotifyItems);

        spotifyListview.setAdapter(spotifyAdapter);

        spotifyAdapter.notifyDataSetChanged();

        spotifyListview.setOnItemClickListener(this);

       /* SpotifyTask task = new SpotifyTask(this);
        String[] urls = new String[]
                {"https://api.spotify.com/v1/search?query=eminem&type=album&offset=0&limit=5"};
        task.execute(urls); **/

        if (savedInstanceState != null){
        spotifyItems = (ArrayList<SpotifyItem>) savedInstanceState.getSerializable(SAVED_ITEMS);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(SAVED_ITEMS, spotifyItems);
    }





    public void fetchRequest(){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        String str = artistSelect.getText().toString();
        try {
            // Safe encode URL
            String artist = URLEncoder.encode(str, "UTF-8");

            // Clear list
            this.spotifyItems.clear();

            // GET albums
            SpotifyTask repoTask = new SpotifyTask(this);
            String[] urls = new String[]
                    {"https://api.spotify.com/v1/search?query=" + artist + "&type=album&offset=0&limit=20"};
            repoTask.execute(urls);
        } catch(UnsupportedEncodingException ex) {}

        artistSelect.setText(null);
    }



    @Override
    public void onSpotifyItemAvailable(SpotifyItem item) {
        // Opslaag in array of mss wel in db?
        spotifyItems.add(item);
        spotifyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

        Intent intent = new Intent(getApplicationContext(), DetailedAlbumActivity.class);
        SpotifyItem item = (SpotifyItem) this.spotifyItems.get(i);
        intent.putExtra("SPOTIFYITEM",item);
        startActivity(intent);

    }
/*
        SpotifyItem item = this.spotifyItems.get(i);

        Uri uri = Uri.parse(item.getAlbumUri() + ":play");
        Intent launcher = new Intent(Intent.ACTION_VIEW, uri);

        launcher.setPackage("com.spotify.mobile.android.ui");

        try {
            startActivity(launcher);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(item.getAlbumUri() + ":play")));
        } **/
    }
