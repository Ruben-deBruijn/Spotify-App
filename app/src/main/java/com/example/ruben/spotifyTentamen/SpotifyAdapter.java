package com.example.ruben.spotifyTentamen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.logging.Filter;

/**
 * Created by Ruben on 16-2-2017.
 */

public class SpotifyAdapter extends ArrayAdapter {

    private Context context;

    public SpotifyAdapter(Context context, ArrayList<SpotifyItem> spotifyItems) {
        super(context, 0, spotifyItems);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        SpotifyItem item = (SpotifyItem) getItem(position);

        if( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spotify_album_listview_row, parent, false);
        }

        // Display album name in list
        TextView albumName = (TextView) convertView.findViewById(R.id.albumRowName);
        albumName.setText(item.getAlbumName());

        // Spotify
        TextView id = (TextView) convertView.findViewById(R.id.albumRowSpotifyID);
        id.setText("ID : " + item.getSpotifyId());

        ImageView image_thumb = (ImageView) convertView.findViewById(R.id.albumRowImageView);
        Picasso.with(context).load(item.albumThumbUrl).into(image_thumb);


        return convertView;
    }
}

