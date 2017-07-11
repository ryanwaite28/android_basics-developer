package com.example.android.newsapiapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    Parcelable state;
    String request_url = "https://content.guardianapis.com/search?q=techcrunch&api-key=test";
    ArticleAdapter articleAdapter;
    List<Article> articles_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.v("Create ---", "Create Called");
        return new ArticlesLoader( MainActivity.this, request_url );
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        Log.v("Finished ---", "Finished Called");
        articles_list = data;
        articleAdapter = new ArticleAdapter(MainActivity.this, articles_list);


        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        state = main_view.onSaveInstanceState();
        main_view.setEmptyView(findViewById(R.id.empty_list_item));
        main_view.setAdapter(articleAdapter);
        main_view.onRestoreInstanceState(state);

        main_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article article = articles_list.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.link));
                    if (intent.resolveActivity( MainActivity.this.getPackageManager() ) != null) {
                      startActivity(intent);
                    }

                }
            });

        Toast.makeText(MainActivity.this, "Powered by - The Guardian at: www.theguardian.com", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        Log.v("Reset ---", "Reset Called");
        Toast.makeText(MainActivity.this, "Resetting...", Toast.LENGTH_LONG).show();
        articleAdapter.clear();
    }

    // Custom Adapter
    private class ArticleAdapter extends ArrayAdapter<Article> {

        public ArticleAdapter(Activity context, List<Article> list) {
            super(context, 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Article current_article = getItem(position);

            View list_item_view = convertView;
            if(list_item_view == null) {
                list_item_view = LayoutInflater.from(getContext()).inflate(R.layout.article_item, parent, false);
            }

            TextView articleTitle = list_item_view.findViewById(R.id.article_title);
            TextView articleDesc = list_item_view.findViewById(R.id.article_desc);
            TextView articleDate = list_item_view.findViewById(R.id.article_date);

            articleTitle.setText( current_article.title );
            articleDesc.setText( current_article.desc );
            articleDate.setText( "Published At: " + current_article.date );

            return list_item_view;

        }
    }

}
