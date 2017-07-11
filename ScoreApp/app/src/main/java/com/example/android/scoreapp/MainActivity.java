package com.example.android.scoreapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    Set The Variables
     */
    int redskinsScore = 0;
    int cowboysScore = 0;

    /*
    Methods for Redskins
     */
    public void redSkins_touchDown(View view){
        redskinsScore += 6;
        render();
    }

    public void redSkins_fieldGoal(View view){
        redskinsScore += 3;
        render();
    }

    public void redSkins_twoPointConversion(View view){
        redskinsScore += 2;
        render();
    }

    public void redSkins_touchdownFieldGoal(View view){
        redskinsScore += 1;
        render();
    }

    /*
    Methods for Cowboys
     */
    public void cowBoys_touchDown(View view){
        cowboysScore += 6;
        render();
    }

    public void cowBoys_fieldGoal(View view){
        cowboysScore += 3;
        render();
    }

    public void cowBoys_twoPointConversion(View view){
        cowboysScore += 2;
        render();
    }

    public void cowBoys_touchdownFieldGoal(View view){
        cowboysScore += 1;
        render();
    }

    public void render() {
        TextView redskinsScoreView = (TextView) findViewById(R.id.redskinsscoreview);
        TextView cowboysScoreView = (TextView) findViewById(R.id.cowboysscoreview);
        TextView gamemessageView = (TextView) findViewById(R.id.gamemessage);
        redskinsScoreView.setText("" + redskinsScore);
        cowboysScoreView.setText("" + cowboysScore);

        if(redskinsScore > cowboysScore) {
            gamemessageView.setText("Redskins is in the lead!");
        }
        else if(redskinsScore < cowboysScore) {
            gamemessageView.setText("Cowboys is in the lead!");
        }
        else {
            gamemessageView.setText("Score is tied!");
        }
    }

    /*
    Reset Scores
     */
    public void resetScore(View view){
        redskinsScore = 0;
        cowboysScore = 0;
        render();
    }
}
