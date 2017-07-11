package com.example.android.newsapiapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 7/5/2017.
 */

// Loader Class
public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {
    String json_string = "";
    private String url;

    public ArticlesLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        HttpURLConnection url_conn = null;
        InputStream input_stream = null;
        ArrayList<Article> articles_list = new ArrayList<Article>();

        try {
            URL request_url = new URL(url);
            url_conn = (HttpURLConnection) request_url.openConnection();
            url_conn.setRequestMethod("GET");
            url_conn.setReadTimeout(10000);
            url_conn.setConnectTimeout(10000);
            url_conn.connect();

            if(url_conn.getResponseCode() == 200) {
                input_stream = url_conn.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(input_stream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                json_string = total.toString();

                Log.v("Connection made ---", "Admit One");
                Log.v("JSON --- ", json_string);

                try {
                    JSONObject json = new JSONObject(json_string);

                    if(!json.has("response")) {
                        Log.v("Error... ", "No Response...");
                    }
                    else {
                        JSONArray articles = json.getJSONObject("response").getJSONArray("results");
                        for(int i = 0; i < articles.length(); i++) {
                            JSONObject obj = articles.getJSONObject(i);


                            String dateOne;
                            String[] splitter = obj.getString("webPublicationDate").split("T");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
                            Date newDate = dateFormat.parse(splitter[0]);
                            dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                            String date = dateFormat.format(newDate);
                            dateOne = date.toString();

                            String title = obj.getString("webTitle");
                            String desc = obj.getString("sectionName");
                            String publish_date = dateOne;
                            String link = obj.getString("webUrl");

                            articles_list.add(new Article(title, desc, publish_date, link));
                        }
                    }

                }
                catch (Exception e) {
                    Log.v("Error... ", String.valueOf(e));
                }
            }

            url_conn.disconnect();
            if(input_stream != null) {
                input_stream.close();
            }
            Log.v("Test....", "TESTING--------------");

        }
        catch (IOException e) {
            Log.v("Error...", String.valueOf(e));
        }

        Log.v("Returning List..." , "Returning...");
        return articles_list;
    }
}
