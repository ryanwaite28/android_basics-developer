package com.example.android.booksapiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.author;
import static android.R.attr.category;
import static android.media.CamcorderProfile.get;


// Book Class Definition
class Book {
    String book_id;
    String title;
    String author;
    String description;
    String publisher;
    String publish_date;
    String web_link;
    String img_link;

    public Book(String book_id, String title, String author, String description, String publisher, String publish_date, String web_link, String img_link) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.web_link = web_link;
        this.img_link = img_link;
    }

    @Override
    public String toString(){
        String string = "";

        string += "Title: " + this.title + "\n";
        string += "Author: " + this.author + "\n";
        string += "Desctiption: " + this.description + "\n";
        string += "Publisher: " + this.publisher + "\n";
        string += "Publish Date: " + this.publish_date + "\n";
        string += "Web Link: " + this.web_link + "\n";
        string += "Image Link: " + this.img_link + "\n";

        return string;
    }

}

// Activity Class
public class ResultsActivity extends AppCompatActivity {

    ArrayList<Book> books_list;
    Parcelable state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get Input Query
        String query = getIntent().getExtras().getString("query");
        setTitle(getResources().getString(R.string.query_title_pre) + " " + query);
        Log.v("Query --- ", query);
        Log.v("Request Link --- ", "https://www.googleapis.com/books/v1/volumes?q=" + query);

        // Make The Request
        new Client().execute("https://www.googleapis.com/books/v1/volumes?q=" + query);

    }

    // Custom Adapter
    private class BookAdapter extends ArrayAdapter<Book> {

        public BookAdapter(Activity context, ArrayList<Book> spotList) {
            super(context, 0, spotList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Book current_book = getItem(position);

            View list_item_view = convertView;
            if(list_item_view == null) {
                list_item_view = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
            }

            ImageView bookIcon = (ImageView) list_item_view.findViewById(R.id.book_icon);
            TextView bookTitle = (TextView) list_item_view.findViewById(R.id.book_title);
            TextView bookAuthor = (TextView) list_item_view.findViewById(R.id.book_author);

            if(!current_book.img_link.equals("")) {
                new DownloadImageTask(bookIcon).execute(current_book.img_link);
            }
            bookTitle.setText( current_book.title );
            bookAuthor.setText( current_book.author );

            return list_item_view;

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    // Parses the json string and builds the view
    public void parse_and_build(String json_string) {
        try {
            JSONObject json = new JSONObject(json_string);
            books_list = new ArrayList<Book>();

            if(!json.has("items")) {
                Toast.makeText(ResultsActivity.this, "No Results, try another query...", Toast.LENGTH_LONG).show();
            }
            else {
                JSONArray items = json.getJSONArray("items");
                for(int i = 0; i < items.length(); i++) {
                    JSONObject obj = items.getJSONObject(i);

                    String book_id = obj.getString("id");
                    String title = obj.getJSONObject("volumeInfo").getString("title");
                    String author = "Author Not Specified...";
                    if(obj.getJSONObject("volumeInfo").has("authors")) {
                        if(obj.getJSONObject("volumeInfo").getJSONArray("authors").length() > 0) {
                            author = obj.getJSONObject("volumeInfo").getJSONArray("authors").getString(0);
                        }
                    }
                    String description = "No Description...";
                    if(obj.getJSONObject("volumeInfo").has("description")) {
                        description = obj.getJSONObject("volumeInfo").getString("description");
                    }
                    String publisher = "Publisher Not Specified...";
                    if(obj.getJSONObject("volumeInfo").has("publisher")) {
                        publisher = obj.getJSONObject("volumeInfo").getString("publisher");
                    }
                    String publish_date = obj.getJSONObject("volumeInfo").getString("publishedDate");
                    String web_link = "Link Not Specified...";
                    if(obj.getJSONObject("volumeInfo").has("canonicalVolumeLink")) {
                        web_link = obj.getJSONObject("volumeInfo").getString("canonicalVolumeLink");
                    }
                    String img_link = "";
                    if(obj.getJSONObject("volumeInfo").has("imageLinks")) {
                        if(obj.getJSONObject("volumeInfo").getJSONObject("imageLinks").has("smallThumbnail")){
                            img_link = obj.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail");
                        }
                    }

                    books_list.add(new Book(book_id, title, author, description, publisher, publish_date, web_link, img_link));
                }
            }

            BookAdapter bookAdapter = new BookAdapter(ResultsActivity.this, books_list);
            ListView main_view = (ListView)findViewById(R.id.main_list_view);
            state = main_view.onSaveInstanceState();
            main_view.setEmptyView(findViewById(R.id.empty_list_item));
            main_view.setAdapter(bookAdapter);
            main_view.onRestoreInstanceState(state);

            main_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Log.v("Click Event", "Item Clicked");
                    Book book = books_list.get(position);

                    Intent intent = new Intent(ResultsActivity.this, BookViewActivity.class);

                    intent.putExtra("book_title", book.title);
                    intent.putExtra("book_author", book.author);
                    intent.putExtra("book_description", book.description);
                    intent.putExtra("book_publisher", book.publisher);
                    intent.putExtra("book_publishdate", book.publish_date);
                    intent.putExtra("book_weblink", book.web_link);
                    intent.putExtra("book_imglink", book.img_link);
                    intent.putExtra("book_raw", book.toString());

                    startActivity(intent);
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(ResultsActivity.this, "There was an error building the view...", Toast.LENGTH_LONG).show();
            Log.v("Error... ", String.valueOf(e));
        }
    }

    // Private Inner Class
    private class Client extends AsyncTask<String, Void, String> {
        String json = "";

        public Client() {

        }

        protected String doInBackground(String... urls) {
            HttpURLConnection url_conn = null;
            InputStream input_stream = null;

            try {
                URL request_url = new URL(urls[0]);
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
                    json = total.toString();

                    Log.v("Connection made ---", "Admit One");
                    // Log.v("JSON --- ", json);
                }

                if(url_conn != null) {
                    url_conn.disconnect();
                }
                if(input_stream != null) {
                    input_stream.close();
                }
            }
            catch (IOException e) {
                // Toast.makeText(getApplicationContext(), "Error loading data...", Toast.LENGTH_LONG).show();
                Log.v("Error...", String.valueOf(e));
            }
            return json;
        }

        protected void onPostExecute(String str) {
            Log.v("onPostExecute function", "Called");
            Log.v("String input", str);
            parse_and_build(json);
        }
    }

}
