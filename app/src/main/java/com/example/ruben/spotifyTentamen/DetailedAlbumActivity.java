package com.example.ruben.spotifyTentamen;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailedAlbumActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_album);

        SpotifyItem spotifyitem = new SpotifyItem();

        Intent intent = getIntent();
        SpotifyItem item = (SpotifyItem) intent.getSerializableExtra("SPOTIFYITEM");


        //Imageurl meenemen en in de imageView zetten
        ImageView image = (ImageView) findViewById(R.id.detailedImageView);
        Picasso.with(context).load(item.albumUrl).into(image);

        final String albumUri = item.albumUri;

        ImageButton button = (ImageButton) findViewById(R.id.playButton);
        button.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v) {


                String uri = albumUri + ":play";
                Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(uri) );
                startActivity(launcher);

                try {
                    startActivity(launcher);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(albumUri + ":play" )));
                }
            }


        });



    }



}


