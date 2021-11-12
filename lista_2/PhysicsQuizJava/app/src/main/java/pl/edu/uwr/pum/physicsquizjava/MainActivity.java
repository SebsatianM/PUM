package pl.edu.uwr.pum.physicsquizjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;






public class MainActivity extends AppCompatActivity {

    private TextView textView,resultView,correctView,incorrectView,debugView;
    private Button trueButton,falseButton,nextButton,previousButton,restartButton;
    private int QuestionPointer;
    public static final String ANSWER = "pl.edu.uwr.pum.physicsquizjava.ANSWER";

    private final Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, true),
            new Question(R.string.question4, true),
            //new Question(R.string.question6, true),
            //new Question(R.string.question7, true),
            //new Question(R.string.question8, true),
            //new Question(R.string.question5, true),
            //new Question(R.string.question9, true),
            //new Question(R.string.question10, true),


    };
    private final int QuestionsNumber = questions.length;   //size of array wih questions
    private int buttonPressed;                          //keep state if true/false button was pressed
    public boolean buttonState;                            //keep state which true/false button was pressed
    //public int[] answers = new int[QuestionsNumber];
    public int[] IsAnswered = new int[QuestionsNumber];     //array that control if user answered to direct question
    public boolean answer;                                  //
    public int AnsweredQuestions;
    public int score;
    public int correctAnswers;
    public int incorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnsweredQuestions = 0;
        score = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        QuestionPointer = 0;
        buttonPressed = 0;

        debugView = findViewById(R.id.debug);
        textView = findViewById(R.id.question_text);
        resultView = findViewById(R.id.score_text);
        correctView = findViewById(R.id.correct_answers);
        incorrectView = findViewById(R.id.incorrect_answers);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        restartButton = findViewById(R.id.restart_button);
        textView.setText(questions[QuestionPointer].getTextId());
        debugView.setText(Integer.toString(AnsweredQuestions)+Integer.toString(QuestionsNumber));


        if(savedInstanceState != null) {

            AnsweredQuestions = savedInstanceState.getInt("AnsweredQuestions_state");
            score = savedInstanceState.getInt("score_state");
            correctAnswers = savedInstanceState.getInt("correctAnswers_state");
            incorrectAnswers = savedInstanceState.getInt("incorrectAnswers_state");
            QuestionPointer = savedInstanceState.getInt("QuestionPointer_state");

            trueButton.setVisibility(savedInstanceState.getInt("trueButton_state"));
            falseButton.setVisibility(savedInstanceState.getInt("falseButton_state"));
            nextButton.setVisibility(savedInstanceState.getInt("nextButton_state"));
            previousButton.setVisibility(savedInstanceState.getInt("previousButton_state"));
            textView.setVisibility(savedInstanceState.getInt("textView_state"));
            restartButton.setVisibility(savedInstanceState.getInt("restartButton_state"));
            resultView.setVisibility(savedInstanceState.getInt("resultView_state"));
            correctView.setVisibility(savedInstanceState.getInt("correctView_state"));
            incorrectView.setVisibility(savedInstanceState.getInt("incorrectView_state"));

            IsAnswered = savedInstanceState.getIntArray("IsAnswered_state");
            buttonPressed = savedInstanceState.getInt("buttonPressed_state");
            buttonState = savedInstanceState.getBoolean("buttonState_state");
            answer = savedInstanceState.getBoolean("answer_state");
            textView.setText(questions[QuestionPointer].getTextId());
            resultView.setText("Result: "+Integer.toString(score));
            correctView.setText("Correct answers: " + Integer.toString(correctAnswers));
            incorrectView.setText("Inorrect answers: " + Integer.toString(incorrectAnswers));


        }


    }


    @SuppressLint("SetTextI18n")
    public void NextQuestion(View view) {

        if (AnsweredQuestions < QuestionsNumber) {
            if (IsAnswered[QuestionPointer]!=1) {
                if (buttonPressed == 1) {
                    AnsweredQuestions+=1;
                        answer = questions[QuestionPointer].isAnswer();
                        if (answer == buttonState) {
                            score+=1;
                            correctAnswers+=1;
                            IsAnswered[QuestionPointer]=1;
                        }else {
                            score -= 1;
                            incorrectAnswers += 1;
                            IsAnswered[QuestionPointer] = 1;
                        }
                        if (AnsweredQuestions >= QuestionsNumber) {
                            showResults();
                        }

                }
                debugView.setText(Integer.toString(AnsweredQuestions)+Integer.toString(QuestionsNumber)+Integer.toString(correctAnswers));
            }

            if (QuestionPointer == QuestionsNumber-1)
                QuestionPointer = 0;
            else
                QuestionPointer += 1;

            changeQuestion(QuestionPointer);

        }else{

        showResults();

        }


        buttonPressed = 0;
    }

    public void PreviousQuestion(View view) {
        if (QuestionPointer == 0)
            QuestionPointer = QuestionsNumber-1;
        else
            QuestionPointer -= 1;

        changeQuestion(QuestionPointer);
        buttonPressed = 0;
    }

    public void changeQuestion(int qstPointer) {
        textView.setText(questions[qstPointer].getTextId());
    }

    public void changeStateTrue(View view) {
        buttonPressed = 1;
        buttonState = true;
    }

    public void changeStateFalse(View view) {
        buttonPressed = 1;
        buttonState = false;
    }

    public void showResults() {

        trueButton.setVisibility(Button.GONE);
        falseButton.setVisibility(Button.GONE);
        nextButton.setVisibility(Button.GONE);
        previousButton.setVisibility(Button.GONE);
        textView.setVisibility(TextView.GONE);
        restartButton.setVisibility(Button.VISIBLE);


        resultView.setVisibility(TextView.VISIBLE);
        resultView.setText("Result: "+Integer.toString(score));


        correctView.setVisibility(TextView.VISIBLE);
        correctView.setText("Correct answers: " + Integer.toString(correctAnswers));

        incorrectView.setVisibility(TextView.VISIBLE);
        incorrectView.setText("Inorrect answers: " + Integer.toString(incorrectAnswers));

    }

    public void reset(View view) {
        trueButton.setVisibility(Button.VISIBLE);
        falseButton.setVisibility(Button.VISIBLE);
        nextButton.setVisibility(Button.VISIBLE);
        previousButton.setVisibility(Button.VISIBLE);
        textView.setVisibility(TextView.VISIBLE);
        restartButton.setVisibility(Button.GONE);
        resultView.setVisibility(TextView.GONE);
        correctView.setVisibility(TextView.GONE);
        incorrectView.setVisibility(TextView.GONE);
        AnsweredQuestions = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        score = 0;
        IsAnswered = new int[IsAnswered.length];
    }
    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);

        saveState.putInt("AnsweredQuestions_state", AnsweredQuestions);
        saveState.putInt("score_state", score);
        saveState.putInt("correctAnswers_state", correctAnswers);
        saveState.putInt("incorrectAnswers_state", incorrectAnswers);
        saveState.putInt("QuestionPointer_state", QuestionPointer);

        saveState.putInt("trueButton_state", trueButton.getVisibility());
        saveState.putInt("falseButton_state", falseButton.getVisibility());
        saveState.putInt("nextButton_state", nextButton.getVisibility());
        saveState.putInt("previousButton_state", previousButton.getVisibility());
        saveState.putInt("textView_state", textView.getVisibility());
        saveState.putInt("restartButton_state", restartButton.getVisibility());
        saveState.putInt("resultView_state", resultView.getVisibility());
        saveState.putInt("correctView_state", correctView.getVisibility());
        saveState.putInt("incorrectView_state", incorrectView.getVisibility());

        saveState.putIntArray("IsAnswered_state", IsAnswered);
        saveState.putInt("buttonPressed_state", buttonPressed);
        saveState.putBoolean("buttonState_state", buttonState);
        saveState.putBoolean("answer_state", answer);
    }

    public void showCheatActivity(View view) {
        Intent intent = new Intent(this, CheatActivity.class);
        startActivity(intent);
    }

    public void showCorrectAnswer(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Correct Answer");
        alertDialog.setMessage(Boolean.toString(questions[QuestionPointer].isAnswer()));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void browseAnswer(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + textView.getText().toString())));
    }

}

