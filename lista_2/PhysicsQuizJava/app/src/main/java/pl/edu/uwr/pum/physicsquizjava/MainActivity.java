package pl.edu.uwr.pum.physicsquizjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int QuestionPointer = 0;


    private final Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, true),
            new Question(R.string.question4, true),
            new Question(R.string.question5, true),
            new Question(R.string.question6, true),
            new Question(R.string.question7, true),
            new Question(R.string.question8, true),
            new Question(R.string.question9, true),
            new Question(R.string.question10, true),


    };
    private final int QuestionsNumber = questions.length;

    private int buttonPressed = 0;
    private boolean buttonState;
    int n = 10;
    private int[] answers = new int[QuestionsNumber];
    private boolean answer;
    private int AnsweredQuestions=0;
    private int sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.question_text);

        textView.setText(questions[QuestionPointer].getTextId());
    }




    public void NextQuestion(View view) {
        if (AnsweredQuestions < QuestionsNumber) {
            if (buttonPressed == 1) {
                answer = questions[QuestionPointer].isAnswer();
                if (answer == buttonState)
                    answers[QuestionPointer] = 1;
                else
                    answers[QuestionPointer] = -1;
            }
        }else {
            
            for (int value : answers) {
                sum += value;
            }
            textView.setText(Integer.toString(sum));
        }


        if (QuestionPointer == QuestionsNumber-1)
            QuestionPointer = 0;
        else
            QuestionPointer += 1;

        changeQuestion(QuestionPointer);

    }

    public void PreviousQuestion(View view) {
        if (QuestionPointer == 0)
            QuestionPointer = QuestionsNumber-1;
        else
            QuestionPointer -= 1;

        changeQuestion(QuestionPointer);
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