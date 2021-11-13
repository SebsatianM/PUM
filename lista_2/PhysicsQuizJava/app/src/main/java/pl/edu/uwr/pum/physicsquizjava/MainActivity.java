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

    private TextView textView,resultView,correctView,incorrectView,cheatedAnswersView;
    private Button trueButton,falseButton,nextButton,previousButton,restartButton,showAnswer,browseButton;
    private int QuestionPointer;
    public static final String ANSWER_MESSAGE = "pl.edu.uwr.pum.physicsquizjava.MESSAGE";

    private final Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, true),
            new Question(R.string.question4, false),
            new Question(R.string.question5, true),
            new Question(R.string.question6, true),
            new Question(R.string.question7, false),
            new Question(R.string.question8, true),
            new Question(R.string.question9, true),
            new Question(R.string.question10, true),


    };
    private final int QuestionsNumber = questions.length;   //size of array wih questions
    private int buttonPressed;                          //keep state if true/false button was pressed
    public boolean buttonState;                            //keep state which true/false button was pressed
    //public int[] answers = new int[QuestionsNumber];
    public int[] IsAnswered = new int[QuestionsNumber];     //array that control if user answered to direct question
    public boolean answer;                                  //
    public int AnsweredQuestions;
    public Double score;
    public int correctAnswers;
    public int incorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnsweredQuestions = 0;
        score = 0.0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        QuestionPointer = 0;
        buttonPressed = 0;

        textView = findViewById(R.id.question_text);
        resultView = findViewById(R.id.score_text);
        correctView = findViewById(R.id.correct_answers);
        incorrectView = findViewById(R.id.incorrect_answers);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        restartButton = findViewById(R.id.restart_button);
        showAnswer = findViewById(R.id.showAnswer_button);
        browseButton = findViewById(R.id.browse_button);
        cheatedAnswersView = findViewById(R.id.cheated_answers);
        textView.setText(questions[QuestionPointer].getTextId());



        if(savedInstanceState != null) {

            AnsweredQuestions = savedInstanceState.getInt("AnsweredQuestions_state");
            score = savedInstanceState.getDouble("score_state");
            correctAnswers = savedInstanceState.getInt("correctAnswers_state");
            incorrectAnswers = savedInstanceState.getInt("incorrectAnswers_state");
            QuestionPointer = savedInstanceState.getInt("QuestionPointer_state");

            trueButton.setVisibility(savedInstanceState.getInt("trueButton_state"));
            falseButton.setVisibility(savedInstanceState.getInt("falseButton_state"));
            nextButton.setVisibility(savedInstanceState.getInt("nextButton_state"));
            previousButton.setVisibility(savedInstanceState.getInt("previousButton_state"));
            browseButton.setVisibility(savedInstanceState.getInt("browseButton_state"));
            textView.setVisibility(savedInstanceState.getInt("textView_state"));
            restartButton.setVisibility(savedInstanceState.getInt("restartButton_state"));
            resultView.setVisibility(savedInstanceState.getInt("resultView_state"));
            cheatedAnswersView.setVisibility(savedInstanceState.getInt("cheatedAnswersView_state"));
            correctView.setVisibility(savedInstanceState.getInt("correctView_state"));
            incorrectView.setVisibility(savedInstanceState.getInt("incorrectView_state"));

            IsAnswered = savedInstanceState.getIntArray("IsAnswered_state");
            buttonPressed = savedInstanceState.getInt("buttonPressed_state");
            buttonState = savedInstanceState.getBoolean("buttonState_state");
            answer = savedInstanceState.getBoolean("answer_state");
            textView.setText(questions[QuestionPointer].getTextId());
            resultView.setText("Result: "+Double.toString(score));
            cheatedAnswersView.setText("Cheated answers: "+ Integer.toString(CheatActivity.cheatCounter));
            correctView.setText("Correct answers: " + Integer.toString(correctAnswers));
            incorrectView.setText("Inorrect answers: " + Integer.toString(incorrectAnswers));


        }


    }


    @SuppressLint("SetTextI18n")
    public void NextQuestion(View view) {

        if (AnsweredQuestions < QuestionsNumber) {
            if (IsAnswered[QuestionPointer]!=1) {
                if (buttonPressed == 1) {
                    AnsweredQuestions += 1;
                    answer = questions[QuestionPointer].isAnswer();
                    if (answer == buttonState) {
                        score += 1.0;
                        correctAnswers += 1;
                        IsAnswered[QuestionPointer] = 1;
                    } else {
                        score-= 1.0;
                        incorrectAnswers += 1;
                        IsAnswered[QuestionPointer] = 1;
                    }
                    if (AnsweredQuestions >= QuestionsNumber) {
                        showResults();
                    }

                }
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
        showAnswer.setVisibility(Button.GONE);
    }

    public void changeStateTrue(View view) {
        buttonPressed = 1;
        buttonState = true;
        showAnswer.setVisibility(Button.VISIBLE);
    }

    public void changeStateFalse(View view) {
        buttonPressed = 1;
        buttonState = false;
        showAnswer.setVisibility(Button.VISIBLE);
    }

    public void showResults() {

        trueButton.setVisibility(Button.GONE);
        falseButton.setVisibility(Button.GONE);
        nextButton.setVisibility(Button.GONE);
        previousButton.setVisibility(Button.GONE);
        textView.setVisibility(TextView.GONE);
        restartButton.setVisibility(Button.VISIBLE);
        browseButton.setVisibility(Button.GONE);

        if(CheatActivity.cheatCounter>6){
            score = 0.0;
        }
        else {
            score = score * (1-(CheatActivity.cheatCounter * 0.15));
        }

        resultView.setVisibility(TextView.VISIBLE);
        resultView.setText("Result: "+Double.toString(score));

        cheatedAnswersView.setVisibility(TextView.VISIBLE);
        cheatedAnswersView.setText("Cheated answers "+Integer.toString(CheatActivity.cheatCounter));

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
        browseButton.setVisibility(Button.VISIBLE);
        restartButton.setVisibility(Button.GONE);
        resultView.setVisibility(TextView.GONE);
        cheatedAnswersView.setVisibility(TextView.GONE);
        correctView.setVisibility(TextView.GONE);
        incorrectView.setVisibility(TextView.GONE);
        showAnswer.setVisibility(Button.GONE);

        AnsweredQuestions = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        score = 0.0;
        IsAnswered = new int[IsAnswered.length];
    }
    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);

        saveState.putInt("AnsweredQuestions_state", AnsweredQuestions);
        saveState.putDouble("score_state", score);
        saveState.putInt("correctAnswers_state", correctAnswers);
        saveState.putInt("incorrectAnswers_state", incorrectAnswers);
        saveState.putInt("QuestionPointer_state", QuestionPointer);

        saveState.putInt("trueButton_state", trueButton.getVisibility());
        saveState.putInt("falseButton_state", falseButton.getVisibility());
        saveState.putInt("nextButton_state", nextButton.getVisibility());
        saveState.putInt("previousButton_state", previousButton.getVisibility());
        saveState.putInt("browseButton_state", browseButton.getVisibility());
        saveState.putInt("textView_state", textView.getVisibility());
        saveState.putInt("restartButton_state", restartButton.getVisibility());
        saveState.putInt("resultView_state", resultView.getVisibility());
        saveState.putInt("cheatedAnswersView_state", cheatedAnswersView.getVisibility());
        saveState.putInt("correctView_state", correctView.getVisibility());
        saveState.putInt("incorrectView_state", incorrectView.getVisibility());

        saveState.putIntArray("IsAnswered_state", IsAnswered);
        saveState.putInt("buttonPressed_state", buttonPressed);
        saveState.putBoolean("buttonState_state", buttonState);
        saveState.putBoolean("answer_state", answer);
    }

    public void showCheatActivity(View view) {
        String message = Boolean.toString(questions[QuestionPointer].isAnswer());
        Intent intent = new Intent(this, CheatActivity.class);
        intent.putExtra(ANSWER_MESSAGE, message);
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

