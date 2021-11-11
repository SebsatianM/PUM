package pl.edu.uwr.pum.physicsquizjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView debugView;
    private int QuestionPointer = 0;


    private final Question[] questions = new Question[]{
            new Question(R.string.question1, false),
            new Question(R.string.question2, false),
            new Question(R.string.question3, false),
            new Question(R.string.question4, true),
            new Question(R.string.question6, true),
            new Question(R.string.question7, true),
            //new Question(R.string.question8, true),
            //new Question(R.string.question5, true),
            //new Question(R.string.question9, true),
            //new Question(R.string.question10, true),


    };
    private final int QuestionsNumber = questions.length;   //size of array wih questions
    private int buttonPressed = 0;                          //keep state if true/false button was pressed
    public boolean buttonState;                            //keep state which true/false button was pressed
    //public int[] answers = new int[QuestionsNumber];
    public int[] IsAnswered = new int[QuestionsNumber];     //array that control if user answered to direct question
    public boolean answer;                                  //
    public int AnsweredQuestions=0;
    public int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.question_text);
        debugView = findViewById(R.id.score_text);

        textView.setText(questions[QuestionPointer].getTextId());
    }


    public void NextQuestion(View view) {
        if (AnsweredQuestions < QuestionsNumber) {
            if (IsAnswered[QuestionPointer]!=1) {
                AnsweredQuestions+=1;
                if (buttonPressed == 1) {
                    answer = questions[QuestionPointer].isAnswer();
                    if (answer == buttonState) {
                        score+=1;
                        IsAnswered[QuestionPointer]=1;
                    }else{
                        IsAnswered[QuestionPointer]=1;
                        score-=1;
                    }

                }
                debugView.setText(Integer.toString(score));

            }


            if (QuestionPointer == QuestionsNumber-1)
                QuestionPointer = 0;
            else
                QuestionPointer += 1;

            changeQuestion(QuestionPointer);

        }else{
            debugView.setText("DUUUUPA");
            textView.setText("DUPA DUPA");
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
}