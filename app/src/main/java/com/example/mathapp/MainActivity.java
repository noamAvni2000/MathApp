package com.example.mathapp;  // Updated package name

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private TextView tvQuestion;
    private LinearLayout llOptions;
    private Button btnChangeAnswers;

    private int score = 0;
    private int totalQuestions = 0;
    private int numberOfOptions = 4;  // Default number of options
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tvScore);
        tvQuestion = findViewById(R.id.tvQuestion);
        llOptions = findViewById(R.id.llOptions);
        btnChangeAnswers = findViewById(R.id.btnChangeAnswers);

        generateQuestion();

        btnChangeAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeOptionsDialog();
            }
        });
    }

    private void generateQuestion() {
        Random random = new Random();
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        int operation = random.nextInt(6);
        String question = "";
        switch (operation) {
            case 0:
                question = a + " + " + b;
                correctAnswer = a + b;
                break;
            case 1:
                question = a + " - " + b;
                correctAnswer = a - b;
                break;
            case 2:
                question = a + " * " + b;
                correctAnswer = a * b;
                break;
            case 3:
                question = "√" + (a * a);
                correctAnswer = a;
                break;
            case 4:
                question = a + "^2";
                correctAnswer = a * a;
                break;
            case 5:
                question = a + "^3";
                correctAnswer = a * a * a;
                break;
        }
        tvQuestion.setText(question);
        generateOptions();
    }

    private void generateOptions() {
        llOptions.removeAllViews();
        ArrayList<Integer> options = new ArrayList<>();
        options.add(correctAnswer);
        Random random = new Random();
        while (options.size() < numberOfOptions) {
            int option = random.nextInt(200) - 100;
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options);
        for (int i = 0; i < options.size(); i++) {
            final int option = options.get(i);
            Button btnOption = new Button(this);
            btnOption.setText(String.valueOf(option));
            // Set the button width to match_parent and minimal margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 4, 4, 4); // Minimal margins around the button
            btnOption.setLayoutParams(params);
            btnOption.setPadding(0, 40, 0, 40); // Add padding to make the button visually larger
            btnOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(option);
                }
            });
            llOptions.addView(btnOption);
        }
    }

    private void checkAnswer(int selectedOption) {
        totalQuestions++;
        if (selectedOption == correctAnswer) {
            score++;
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Wrong! Correct answer: " + correctAnswer, Toast.LENGTH_SHORT).show();
        }
        tvScore.setText("Score: " + score + "/" + totalQuestions);
        generateQuestion();
    }

    private void showChangeOptionsDialog() {
        final String[] options = {"3", "4", "5", "6"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select number of answers")
                .setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 'which' is the index position of the selected item
                        numberOfOptions = Integer.parseInt(options[which]);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        generateQuestion();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}


