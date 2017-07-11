package com.example.android.musicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class SongSelectActivity extends AppCompatActivity {

    private MediaPlayer media_player;
    private String current_song;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_select);

        Button home_btn = (Button)findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongSelectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Play/Pause Actions
        TextView song_listen_btn_one = (TextView)findViewById(R.id.song_listen_btn_one);
        song_listen_btn_one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(current_song == "fatima" && media_player.isPlaying()) {
                        media_player.pause();
                        return;
                    }
                    if(current_song == "fatima" && !media_player.isPlaying()) {
                        media_player.start();
                        return;
                    }

                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }

                media_player = MediaPlayer.create(getApplicationContext(), R.raw.fatima);
                media_player.start();
                current_song = "fatima";

            }
        });
        TextView song_listen_btn_two = (TextView)findViewById(R.id.song_listen_btn_two);
        song_listen_btn_two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(current_song == "alice" && media_player.isPlaying()) {
                        media_player.pause();
                        return;
                    }
                    if(current_song == "alice" && !media_player.isPlaying()) {
                        media_player.start();
                        return;
                    }

                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }

                media_player = MediaPlayer.create(getApplicationContext(), R.raw.alice);
                media_player.start();
                current_song = "alice";
            }
        });
        TextView song_listen_btn_three = (TextView)findViewById(R.id.song_listen_btn_three);
        song_listen_btn_three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(current_song == "adele" && media_player.isPlaying()) {
                        media_player.pause();
                        return;
                    }
                    if(current_song == "adele" && !media_player.isPlaying()) {
                        media_player.start();
                        return;
                    }

                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }

                media_player = MediaPlayer.create(getApplicationContext(), R.raw.adele);
                media_player.start();
                current_song = "adele";

            }
        });

        // Buying Intentions
        TextView song_buy_btn_one = (TextView)findViewById(R.id.song_buy_btn_one);
        song_buy_btn_one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }
                Intent i = new Intent(SongSelectActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
        TextView song_buy_btn_two = (TextView)findViewById(R.id.song_buy_btn_two);
        song_buy_btn_two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }
                Intent i = new Intent(SongSelectActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
        TextView song_buy_btn_three = (TextView)findViewById(R.id.song_buy_btn_three);
        song_buy_btn_three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(media_player != null) {
                    if(media_player.isPlaying()) {
                        media_player.pause();
                    }
                }
                Intent i = new Intent(SongSelectActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
    }
}
