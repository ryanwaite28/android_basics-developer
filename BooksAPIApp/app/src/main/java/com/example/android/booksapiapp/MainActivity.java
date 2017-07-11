package com.example.android.booksapiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //

        Button submit_btn = (Button)findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText queryView = (EditText)findViewById(R.id.query_view);
                String query = queryView.getText().toString();
                Log.v("Query --- ", query);

                Activity context = MainActivity.this;
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if(!isConnected){
                    Toast.makeText(context, "Internet Connection is Required.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(query.equals("") || query == null) {
                    return;
                }

                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });
    }
}
