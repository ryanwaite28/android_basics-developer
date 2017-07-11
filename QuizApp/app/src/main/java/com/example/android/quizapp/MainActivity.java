package com.example.android.quizapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class Question {
    String question;
    String answer;
    String type;
    public Question(String q, String a, String t) {
        this.question = q;
        this.answer = a;
        this.type = t;
    }
}

class HistoryGame {
    public Boolean started;
    public Boolean over;
    int currentIndex;
    int correctCount;
    int incorrectCount;
    String currentType;
    String currentChoice = "";
    Question currentQuestion;
    Question[] questionsList = {
            new Question("Christopher Columbus discovered America in 1482.", "false", "radio"),
            new Question("The first satellite launched was called \"Sputnik\" from the Soviet Union.", "true", "radio"),
            new Question("The Great Depression in the U.S was caused by the aftermath of World War 1.", "false", "radio"),
            new Question("Who was the first U.S President?", "true", "text"),
            new Question("Thomas Jefferson was the 16th President of the United States.", "false", "radio"),
            new Question("The first car was invented in France.", "true", "radio"),
            new Question("The Burj Khalifa in Dubai is currently the tallest building in the world (as of 2016).", "true", "radio"),
            new Question("The Elizabethan Era was from 1556 to 1603", "true", "radio"),
            new Question("Which of the following are fruits? Select all that apply.", "true", "checkbox")
    };

    public HistoryGame() {
        this.started = false;
        this.over = false;
        this.currentIndex = 0;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.currentQuestion = this.questionsList[ this.currentIndex ];
        this.currentType = this.currentQuestion.type;
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        Game
     */

    HistoryGame game = new HistoryGame();

    public void switchView() {
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        View checkGroup = (View)findViewById(R.id.check_group_one);
        View textGroup = (View)findViewById(R.id.text_group_one);
        if(game.currentQuestion.type == "radio"){
            radioGroup.setVisibility(View.VISIBLE);
            checkGroup.setVisibility(View.GONE);
            textGroup.setVisibility(View.GONE);
        }
        else if(game.currentQuestion.type == "checkbox") {
            radioGroup.setVisibility(View.GONE);
            checkGroup.setVisibility(View.VISIBLE);
            textGroup.setVisibility(View.GONE);

        }
        else if(game.currentQuestion.type == "text") {
            radioGroup.setVisibility(View.GONE);
            checkGroup.setVisibility(View.GONE);
            textGroup.setVisibility(View.VISIBLE);
        }
        else {
            radioGroup.setVisibility(View.GONE);
            checkGroup.setVisibility(View.GONE);
            textGroup.setVisibility(View.GONE);
        }
    }

    public void submit(View view) {
        if(game.over == true){
            return;
        }

        if(game.currentQuestion.type == "radio") {
            RadioButton trueButton = (RadioButton)findViewById(R.id.true_radio);
            RadioButton falseButton = (RadioButton)findViewById(R.id.false_radio);
            if(trueButton.isChecked()) {
                game.currentChoice = "true";
            }
            else if(falseButton.isChecked()) {
                game.currentChoice = "false";
            }

        }
        else if(game.currentQuestion.type == "checkbox") {
            CheckBox checkboxOne = (CheckBox)findViewById(R.id.checkbox_one);
            CheckBox checkboxTwo = (CheckBox)findViewById(R.id.checkbox_two);
            CheckBox checkboxThree = (CheckBox)findViewById(R.id.checkbox_three);
            Boolean cbOne = checkboxOne.isChecked();
            Boolean cbTwo = checkboxTwo.isChecked();
            Boolean cbThree = checkboxThree.isChecked();
            if(cbOne && cbTwo && !cbThree) {
                game.currentChoice = "true";
            }
            else {
                game.currentChoice = "false";
            }
        }
        else if(game.currentQuestion.type == "text") {
            EditText inputText = (EditText)findViewById(R.id.edit_text_view);
            String inputValue = inputText.getText().toString();
            if(inputValue.equals("George Washington")) {
                game.currentChoice = "true";
            }
            else {
                game.currentChoice = "false";
            }
        }

        Log.v("Current Choice --- ", game.currentChoice);
        Log.v("Current Type --- ", game.currentQuestion.type);
        grade();
    }

    public void grade() {
        Log.v("Game Compare --- ", game.currentChoice + " : " + game.currentQuestion.answer);

        if(game.currentChoice == "") {
            return;
        }
        else if(game.currentChoice == game.currentQuestion.answer) {

            game.correctCount++;
        }
        else {
            game.incorrectCount++;
        }

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.clearCheck();
        game.currentChoice = "";
        game.currentIndex++;
        if(game.currentIndex >= 9) {
            game.over = true;
            double value = (double) game.correctCount / 9;
            DecimalFormat df = new DecimalFormat("#.##");
            String percentStr = "Score: " + df.format(value * 100) + "%";

            Log.v("data ---", percentStr + " -- " + value);

            TextView currentQuestionView = (TextView) findViewById(R.id.current_question_view);
            currentQuestionView.setText( "That All The Question!\nGAME OVER" );
            TextView correctCountView = (TextView) findViewById(R.id.correct_text_view);
            correctCountView.setText( "" + game.correctCount );
            TextView incorrectCountView = (TextView) findViewById(R.id.incorrect_text_view);
            incorrectCountView.setText( "" + game.incorrectCount );

            Context context = getApplicationContext();
            CharSequence textStr = percentStr;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, textStr, duration);
            toast.show();

            return;
        }
        game.currentQuestion = game.questionsList[ game.currentIndex ];
        game.currentType = game.currentQuestion.type;
        update();
        switchView();
    }

    public void update() {
        TextView currentQuestionView = (TextView) findViewById(R.id.current_question_view);
        currentQuestionView.setText( game.currentQuestion.question );

        TextView correctCountView = (TextView) findViewById(R.id.correct_text_view);
        correctCountView.setText( "" + game.correctCount );

        TextView incorrectCountView = (TextView) findViewById(R.id.incorrect_text_view);
        incorrectCountView.setText( "" + game.incorrectCount );
    }

    public void startGame(View view) {
        if(game.started == false) {
            game.started = true;
            update();
            switchView();
        }
    }

    public void resetGame(View view) {
        game = new HistoryGame();

        CheckBox checkboxOne = (CheckBox)findViewById(R.id.checkbox_one);
        CheckBox checkboxTwo = (CheckBox)findViewById(R.id.checkbox_two);
        CheckBox checkboxThree = (CheckBox)findViewById(R.id.checkbox_three);
        checkboxOne.setChecked(false);
        checkboxTwo.setChecked(false);
        checkboxThree.setChecked(false);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.clearCheck();

        EditText inputText = (EditText)findViewById(R.id.edit_text_view);
        inputText.setText("");

        update();
        switchView();
    }
}
