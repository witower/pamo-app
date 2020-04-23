package com.example.myapplicationexample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends Fragment {

    private int totalGuesses, correctAnswers, buttonsCount, questionIndex, defaultBtnDrawableId;
    private String correctAnswer = "";

    private int[] questionsOrder = null;
    private List<String> questionsDb;
    private int questionsNumber;
    private final int greenColorValue = Color.parseColor("#4CAF50");
    private final int redColorValue = Color.parseColor("#D50000");

    private TextView quizHeaderTextView, questionTextView;
    private LinearLayout buttonsLinearLayout;
    private Handler handler = new Handler();

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putIntArray("questionsOrder", questionsOrder);
        savedInstanceState.putInt("questionIndex", questionIndex);
        savedInstanceState.putInt("totalGuesses", totalGuesses);
        savedInstanceState.putInt("correctAnswers", correctAnswers);

        Log.println(Log.INFO,"ww", "onSaveInstanceState");
        Log.println(Log.INFO,"ww", Arrays.toString(questionsOrder));
        Log.println(Log.INFO,"ww", String.valueOf(questionIndex));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        if (savedInstanceState != null) {
            questionsOrder = savedInstanceState.getIntArray("questionsOrder");
            questionIndex = savedInstanceState.getInt("questionIndex");
            totalGuesses = savedInstanceState.getInt("totalGuesses");
            correctAnswers = savedInstanceState.getInt("correctAnswers");

            Log.println(Log.INFO,"ww", "onViewStateRestored");
            Log.println(Log.INFO,"ww", Arrays.toString(questionsOrder));
            Log.println(Log.INFO,"ww", String.valueOf(questionIndex));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionsDb = Arrays.asList(getResources().getStringArray(R.array.questions_list));
        questionsNumber = questionsDb.size();

        quizHeaderTextView = root.findViewById(R.id.text_quiz_header);
        questionTextView = root.findViewById(R.id.text_question);
        buttonsLinearLayout = root.findViewById(R.id.layout_buttons);

        buttonsCount = buttonsLinearLayout.getChildCount();
        defaultBtnDrawableId = getResources().getIdentifier(
                "@android:drawable/btn_default_material",
                null,
                null);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        nextScreen();
    }

    private void resetQuiz() {
        Log.println(Log.INFO,"ww", "resetQuiz()");

        //Only to make shuffling instead of playing with random
        List<Integer> questionsOrderList = new ArrayList<>();
        for (int i = 0; i < questionsNumber; i++) questionsOrderList.add(i);

        Collections.shuffle(questionsOrderList);
        questionsOrder = new int[questionsNumber];

        for (int i = 0; i < questionsNumber; i++) questionsOrder[i] = questionsOrderList.get(i);

        correctAnswers = 0;
        totalGuesses = 0;
        questionIndex = 0;
    }

    private void loadNextQuestion() {
        Log.println(Log.INFO,"ww", "loadNextQuestion()");
        Log.println(Log.INFO,"ww", "correntAnswers: " + correctAnswers);

        if (questionsOrder == null)
            resetQuiz();

        if (questionsNumber == 0){
            quizIsEmpty();
            return;
        }

        if (questionIndex >= questionsNumber) {
            Log.println(Log.INFO,"ww", "questionIndex >= questionsNumber");
            return;
        }

        quizHeaderTextView.setText(
                getString(R.string.question, questionIndex+1, questionsNumber));

        String nextQuestion = questionsDb.get(questionsOrder[questionIndex]);
        questionTextView.setText(nextQuestion);

        loadNextAnswers();

    }

    private void loadNextAnswers() {
        Log.println(Log.INFO,"ww", "loadNextAnswers()");
        List<String> nextGuessesList;
        int temp = questionsOrder[questionIndex]+1;

        int id = getResources().getIdentifier(
                "q" + temp,
                "array",
                getActivity().getPackageName());

        if (id == 0){
            Log.println(Log.INFO,"ww", "No answers for question " + questionIndex);
            return;
        }
        nextGuessesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(id)));
        correctAnswer = nextGuessesList.get(0);
        Collections.shuffle(nextGuessesList);

        for (int c = 0; c < buttonsCount; c++) {
            Button button = (Button) buttonsLinearLayout.getChildAt(c);
            if (c >= nextGuessesList.size()) {
                button.setVisibility(View.GONE);
            } else {
                button.setOnClickListener(guessButtonListener);
                button.setEnabled(true);
                button.setText(nextGuessesList.get(c));
                id = getResources().getIdentifier(
                        "@android:drawable/btn_default_material",
                        null,
                        null);
                button.setBackground(getResources().getDrawable(id));
            }
        }
    }

    private View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.println(Log.INFO,"ww", "Clicked button " + v.getId());

            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            ++totalGuesses;

            if (guess.equals(correctAnswer)) { // if the guess is correct
                ++correctAnswers;
                Log.println(Log.INFO, "ww", "Correct answer! " + correctAnswers);
                guessButton.setBackgroundColor(greenColorValue);
                disableButtons();
                ++questionIndex;

                handler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            nextScreen();
                        }
                    }, 2000);

            }
            else {
                Log.println(Log.INFO, "ww", "Wrong answer!");
                guessButton.setBackgroundColor(redColorValue);
                guessButton.setEnabled(false); // disable incorrect answer
            }
        }
    };

    private void quizIsEmpty() {
        Log.println(Log.INFO,"ww", "quizIsEmpty()");
    }

    private void disableButtons() {
        for (int i = 0; i < buttonsCount; i++)
                buttonsLinearLayout.getChildAt(i).setEnabled(false);
    }

    private void nextScreen() {
        if (correctAnswers == questionsNumber) {
            Log.println(Log.INFO, "ww", "correctAnswers == questionsCount");
            showSummary();
        }
        else {
            Log.println(Log.INFO,"ww", "answer is correct but quiz is not over");

            loadNextQuestion();
        }
    }

    private void showSummary() {
        for (int c = 0; c < buttonsCount-1; c++) {
            Button button = (Button) buttonsLinearLayout.getChildAt(c);
            button.setVisibility(View.GONE);
        }

        Button button = (Button) buttonsLinearLayout.getChildAt(buttonsCount-1);
        button.setText(R.string.quiz_restart);
        button.setEnabled(true);
        button.setBackground(getResources().getDrawable(defaultBtnDrawableId));
        button.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.navigation_quiz));

        questionTextView.setText(
                getString(R.string.quiz_summary, totalGuesses, correctAnswers));
        quizHeaderTextView.setText(R.string.quiz_end_header);
    }

}