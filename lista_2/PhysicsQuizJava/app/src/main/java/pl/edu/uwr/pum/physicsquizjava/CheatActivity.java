package pl.edu.uwr.pum.physicsquizjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;


public class CheatActivity extends AppCompatActivity {
    private TextView cheatView;
    private String message;
    private Button showCheatButton;
    public static int cheatCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.ANSWER_MESSAGE);
        cheatView = findViewById(R.id.cheatedAnswerText);
        showCheatButton = findViewById(R.id.showCheatAnswer_button);

        if (savedInstanceState != null) {
            cheatView.setText(savedInstanceState.getString("cheatView_state"));
            cheatCounter = savedInstanceState.getInt("cheatCounter_state");
            showCheatButton.setVisibility(savedInstanceState.getInt("showAnswerBtnVisibility"));
        }
    }

    public void showCheatedAnswer(View view) {
        cheatCounter += 1;
        cheatView.setText("Correct answer is: " + message);
        showCheatButton.setVisibility(Button.GONE);
    }


    @Override
    protected void onSaveInstanceState(Bundle saveCheatState) {
        super.onSaveInstanceState(saveCheatState);
        saveCheatState.putString("cheatView_state", cheatView.getText().toString());
        saveCheatState.putInt("cheatCounter_state", cheatCounter);
        saveCheatState.putInt("showAnswerBtnVisibility", showCheatButton.getVisibility());
    }

}