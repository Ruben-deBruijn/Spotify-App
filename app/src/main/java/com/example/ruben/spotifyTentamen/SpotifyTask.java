package com.example.ruben.spotifyTentamen;

/**
 * Created by Ruben on 16-2-2017.
 */
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class SpotifyTask extends AsyncTask<String, Void, String>  {

    // Call back
    private onSpotifyItemAvailable listener = null;



    // Statics
    private static final String TAG = SpotifyTask.class.getSimpleName();
   // private static final String urlString = "https://api.spotify.com/v1/search?query=eminem&type=album&offset=0&limit=5";




    // Constructor, set listener
    public SpotifyTask(onSpotifyItemAvailable listener) {
        this.listener = listener;
    }

    /**
     * doInBackground is de methode waarin de aanroep naar een service op het Internet gedaan wordt.
     */
   /** @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responsCode = -1;

        // Het resultaat dat we gaan retourneren
        String response = "";

        for(String url : params) {
            Log.i(TAG, "doInBackground " + url);
        }

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                // Url
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            responsCode = httpConnection.getResponseCode();

            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                // Log.i(TAG, "doInBackground response = " + response);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }

        return response;
    }
    */

   @Override
   protected String doInBackground(String... params) {

       InputStream inputStream = null;
       BufferedReader reader = null;
       String urlString = "";

       String response = "";

       for (String url : params) {
           Log.i(TAG, url);
       }

       try {
           URL url = new URL(params[0]);
           URLConnection connection = url.openConnection();

           reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           response = reader.readLine().toString();
           String line;
           while ( (line = reader.readLine()) != null ) {
               response += line;
           }

       } catch (MalformedURLException e) {
           Log.e("TAG", e.getLocalizedMessage());
           return null;
       } catch (IOException e) {
           Log.e("TAG", e.getLocalizedMessage());
           return null;
       } catch ( Exception e) {
           Log.e("TAG", e.getLocalizedMessage());
           return null;
       } finally {
           if( reader != null ) {
               try {
                   reader.close();
               } catch (IOException e) {
                   Log.e("TAG", e.getLocalizedMessage());
                   return null;
               }
           }
       }

       return response;
   }

    /**
     * onPostExecute verwerkt het resultaat uit de doInBackground methode.
     *
     * @param response
     */
    protected void onPostExecute(String response) {

        Log.i(TAG, "onPostExecute " + response);

        // parse JSON and inform caller
        JSONObject jsonObject;

        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            // Get all users and start looping
            JSONArray albums = jsonObject.getJSONObject("albums").getJSONArray("items");

            for(int idx = 0; idx < albums.length(); idx++ )
            {
                // New Spotify item
                SpotifyItem item = new SpotifyItem();

                // Haal de naam uit de JSON
                String name = albums.getJSONObject(idx).getString("name");
                item.setAlbumName(name);

                String id = albums.getJSONObject(idx).getString("id");
                item.setSpotifyId(id);

                String uri = albums.getJSONObject(idx).getString("uri");
                item.setAlbumUri(uri);


                // Haal de image_url van het album op
                JSONArray album_urls = albums.getJSONObject(idx).getJSONArray("images");

                // Save 640x640 en de 64x64 image_url
                item.setAlbumUrl(album_urls.getJSONObject(0).getString("url"));
                item.setAlbumThumbUrl(album_urls.getJSONObject(2).getString("url"));



                // Geef gevonden item terug via de call back
                listener.onSpotifyItemAvailable(item);
            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }


    //
    // convert InputStream to String
    //
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // Call back interface
    interface onSpotifyItemAvailable {
        void onSpotifyItemAvailable(SpotifyItem item);
    }
}
