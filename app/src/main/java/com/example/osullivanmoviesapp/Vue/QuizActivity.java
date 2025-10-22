package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osullivanmoviesapp.Modele.Question;
import com.example.osullivanmoviesapp.R;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private ProgressBar progressBar;
    private Button button1, button2, button3, button4;
    private ImageButton prev_button, next_button;

    private int score = 0;
    private int currentIndex = 0;

    // garde l'état si la question a déjà été répondue
    private boolean[] answered;

    private Question[] quiz = new Question[]{
            new Question( R.string.question1, R.string.Q1R1, R.string.Q1R2, R.string.Q1R3, R.string.Q1R4, R.string.Q1RC),
            new Question(R.string.question2, R.string.Q2R1, R.string.Q2R2, R.string.Q2R3, R.string.Q2R4, R.string.Q2RC),
            new Question(R.string.question3, R.string.Q3R1, R.string.Q3R2, R.string.Q3R3, R.string.Q3R4, R.string.Q3RC),
            new Question(R.string.question4, R.string.Q4R1, R.string.Q4R2, R.string.Q4R3, R.string.Q4R4, R.string.Q4RC),
            new Question(R.string.question5, R.string.Q5R1, R.string.Q5R2, R.string.Q5R3, R.string.Q5R4, R.string.Q5RC),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.btnQuiz);
        button4 = findViewById(R.id.btnContact);
        prev_button = findViewById(R.id.prevButton);
        next_button = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(quiz.length);


        // initialise le tableau answered
        answered = new boolean[quiz.length];

        // Associe les boutons aux réponses
        View.OnClickListener answerClickListener = view -> {
            Button clickedButton = (Button) view;
            checkAnswer(clickedButton.getText().toString());
        };

        button1.setOnClickListener(answerClickListener);
        button2.setOnClickListener(answerClickListener);
        button3.setOnClickListener(answerClickListener);
        button4.setOnClickListener(answerClickListener);

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex == 0) {
                    Toast.makeText(QuizActivity.this, "Aucune question précédente", Toast.LENGTH_SHORT).show();
                } else {
                    currentIndex--;
                    updateQuestion();
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if(currentIndex < quiz.length - 1) {
                    currentIndex++;
                    updateQuestion();
                } else {
                    finishQuiz();
                }
            }
        });
        updateQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void finishQuiz() {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        prev_button.setVisibility(View.INVISIBLE);
        next_button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        questionTextView.setText("Quiz terminé, votre score est : " + score + "/5");
    }

    private void updateQuestion() {
        questionTextView.setText(quiz[currentIndex].getQuestion());
        button1.setText(quiz[currentIndex].getReponse1());
        button2.setText(quiz[currentIndex].getReponse2());
        button3.setText(quiz[currentIndex].getReponse3());
        button4.setText(quiz[currentIndex].getReponse4());
//        progressBar.incrementProgressBy(1);
        progressBar.setProgress(currentIndex + 1);


        // Si la question a déjà été répondue, désactive les boutons, sinon active-les
//        if (answered[currentIndex]) {
//            setAnswerButtonsEnabled(false);
//        } else {
//            setAnswerButtonsEnabled(true);
//        }
    }

    private void setAnswerButtonsEnabled(boolean enabled) {
        button1.setEnabled(enabled);
        button2.setEnabled(enabled);
        button3.setEnabled(enabled);
        button4.setEnabled(enabled);
    }

    private void checkAnswer(String s) {

        // Si déjà répondu, on ignore
        if (answered[currentIndex]) return;

        String answerTrue = getResources().getString(quiz[currentIndex].getReponseCorrecte());

        if(s == answerTrue) {
            score++;
            Toast.makeText(this, "La réponse est correcte !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La réponse est incorrecte !", Toast.LENGTH_SHORT).show();
        }

        // marque la question comme répondue et désactive les boutons
        answered[currentIndex] = true;
//        setAnswerButtonsEnabled(false);
    }
}