package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {
    private Button resultButton;
    public static int resultValue = 0;
    public static String textValue = "/5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultButton = findViewById(R.id.result_button);
        textValue = String.valueOf(findViewById(R.id.result_text));

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultValue = 0;
                setContentView(R.layout.activity_main);
            }
        });

    }
}
