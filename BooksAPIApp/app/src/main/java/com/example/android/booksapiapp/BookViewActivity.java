package com.example.android.booksapiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class BookViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        //

        final String book_title = getIntent().getExtras().getString("book_title");
        String book_author = getIntent().getExtras().getString("book_author");
        String book_description = getIntent().getExtras().getString("book_description");
        String book_publisher = getIntent().getExtras().getString("book_publisher");
        String book_publishdate = getIntent().getExtras().getString("book_publishdate");
        String book_weblink = getIntent().getExtras().getString("book_weblink");
        String book_imglink = getIntent().getExtras().getString("book_imglink");
        final String book_raw = getIntent().getExtras().getString("book_raw");

        ImageView view_book_icon = (ImageView) findViewById(R.id.book_icon);
        TextView view_book_title = (TextView) findViewById(R.id.book_title);
        TextView view_book_author = (TextView) findViewById(R.id.book_author);
        TextView view_book_description = (TextView) findViewById(R.id.book_description);
        TextView view_book_publisher = (TextView) findViewById(R.id.book_publisher);
        TextView view_book_publishdate = (TextView) findViewById(R.id.book_publishdate);
        TextView view_book_weblink = (TextView) findViewById(R.id.book_weblink);

        Button share_email_btn = (Button) findViewById(R.id.share_email_btn);
        Button share_text_btn = (Button) findViewById(R.id.share_text_btn);

        new DownloadImageTask(view_book_icon).execute(book_imglink);
        view_book_title.setText( book_title );
        view_book_author.setText( book_author );
        view_book_description.setText( book_description );
        view_book_publisher.setText( book_publisher );
        view_book_publishdate.setText( book_publishdate );
        view_book_weblink.setText( String.valueOf(book_weblink) );

        share_email_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));  // This ensures only Email apps respond
                intent.putExtra(Intent.EXTRA_SUBJECT, "Read: " + book_title);
                intent.putExtra(Intent.EXTRA_TEXT, book_raw);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        share_text_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", book_raw);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // Load Img via URL
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
}
