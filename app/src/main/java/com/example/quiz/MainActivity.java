package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    public static int resultValue = 0;
    public boolean blockButtons = false;
    private Button promptButton;
    private static final String TAG = "quizLog";
    private static final String QUIZ_TAG = "quizLog";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final int REQUEST_CODE_PROMPT = 0;
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private boolean answerWasShown;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private Question[] questions = new Question[]{
            new Question(R.string.q_1, true),
            new Question(R.string.q_2, false),
            new Question(R.string.q_3, false),
            new Question(R.string.q_4, false),
            new Question(R.string.q_5, true)
    };


    private void checkAnswerCorrectness(boolean userAnswer) {
        int resultMessageId = 0;

        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (!blockButtons) {
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                if (userAnswer == correctAnswer) {
                    resultMessageId = R.string.correct_answer;
                    resultValue++;
                } else {
                    resultMessageId = R.string.incorrect_answer;
                }
            } else {
                resultMessageId = R.string.blocked_answer;
            }
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void updateResults() {
        questionTextView.setText("Wynik: " + resultValue + "/5");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        Log.d(TAG, "+ on Create + ");


        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.prompt_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
                blockButtons = true;
            }

        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
                blockButtons = true;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 4) {
                    updateResults();
                    resultValue = 0;
                    currentIndex = -1;
                } else {
                    blockButtons = false;
                    currentIndex = (currentIndex + 1) % questions.length;
                    answerWasShown = false;
                    setNextQuestion();
                }
            }
        });
        setNextQuestion();

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "+ on Resume +");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "+ on Pause +");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "+ on Stop +");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "+ on Destroy +");
    }
}
