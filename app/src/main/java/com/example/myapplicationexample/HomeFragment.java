package com.example.myapplicationexample;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    private static DecimalFormat decimalFormatTwoPoints = new DecimalFormat("#.##");

    private int height;
    private int weight;
    private int age;
    private double bmi;

    private boolean isMale;
    private boolean isFemale;

    private Bundle recipeArgs = new Bundle();

    private EditText heightInput;
    private EditText weightInput;
    private EditText ageInput;
    private RadioGroup genderRadio;
    private RadioButton femaleRadio;
    private RadioButton maleRadio;
    private TextView resultBmiTextView;
    private TextView resultCaloriesTextView;

    private Snackbar snacky;
    private Button doCalcButton;
    private Button gotoRecipeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        resultBmiTextView = root.findViewById(R.id.result_bmi);
        resultCaloriesTextView = root.findViewById(R.id.result_calories);

        heightInput = root.findViewById(R.id.input_height);
        weightInput = root.findViewById(R.id.input_weight);
        ageInput = root.findViewById(R.id.input_age);
        genderRadio = root.findViewById(R.id.radio_group_gender);
        femaleRadio = root.findViewById(R.id.radio_button_female);
        maleRadio = root.findViewById(R.id.radio_button_male);

        doCalcButton = root.findViewById(R.id.button_do_calc);
        doCalcButton.setOnClickListener(onDoCalcButtonClickListener);

        gotoRecipeButton = root.findViewById(R.id.button_goto_recipe);
        gotoRecipeButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_navigation_home_to_recipeFragment, recipeArgs));

        Log.println(Log.INFO,"ww","onCreateView(): " + height);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        snacky = Snackbar.make(view, R.string.app_name, Snackbar.LENGTH_LONG);

        Log.println(Log.INFO,"ww","onViewCreated(): " + height);
    }

    private final View.OnClickListener onDoCalcButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            calculate();
        }
    };

    private int almostSafeParse(String s) {
        int r;
        try {
            r = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            r = 0;
        }
        return r;
    }

    private boolean getFormData() {
        height = almostSafeParse(heightInput.getText().toString());
        weight = almostSafeParse(weightInput.getText().toString());
        age = almostSafeParse(ageInput.getText().toString());
        isFemale = femaleRadio.isChecked();
        isMale = maleRadio.isChecked();

        return !(height == 0 | weight == 0 | age == 0 | !(isFemale | isMale));
    }

    private void clearResults(){
        resultBmiTextView.setText("");
        resultCaloriesTextView.setText("");
        recipeArgs.clear();
    }

    private void calculate() {
        if (getFormData()){
            calcBmi();
            calcCalories();
            gotoRecipeButton.setEnabled(true);
        }
        else {
            snacky.setText(R.string.snack_wrong_data).show();
            clearResults();
            gotoRecipeButton.setEnabled(false);
        }
    }

    private void calcBmi() {
        double heightMeters = (double)height/100;
        bmi = weight / (heightMeters * heightMeters);
        recipeArgs.putFloat("bmi", (float)bmi);
        resultBmiTextView.setText(decimalFormatTwoPoints.format(bmi));
    }

    private void calcCalories() {
        double calories;

        if (isFemale)
            calories = 655.1 + (9.563 * weight) + (1.85 * height) - (4.676 * age);
        else if (isMale)
            calories = 66.5 + (13.75 * weight) + (5.003 * height) - (6.775 * age);
        else calories = 0;

        resultCaloriesTextView.setText(decimalFormatTwoPoints.format(calories));
    }

    private void hideKeyboard() {
        try {
            FragmentActivity activity = getActivity();
            InputMethodManager inputManager = (InputMethodManager) (activity != null ? activity.getSystemService(Context.INPUT_METHOD_SERVICE) : null);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        catch (Exception ignored) {}
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.println(Log.INFO, "ww", "onCreate(): " + height);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.println(Log.INFO,"ww","onActivityCreated(): " + height);
    }

    @Override
    public void onConfigurationChanged (@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.println(Log.INFO,"ww","onConfigurationChanged(): " + height);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.println(Log.INFO,"ww","onViewStateRestored(): " + height);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.println(Log.INFO,"ww","onStart(): " + height);
    }

    @Override
    public void onResume() {
        super.onResume();
        gotoRecipeButton.setEnabled(getFormData());
        Log.println(Log.INFO,"ww","onResume(): " + height + " recipeReady: " + getFormData());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.println(Log.INFO,"ww","onPause(): " + height);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.println(Log.INFO,"ww","onStop(): " + height);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.println(Log.INFO,"ww","onDestroyView(): " + height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.println(Log.INFO,"ww","onDestroy(): " + height);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.println(Log.INFO,"ww","onDetach(): " + height);
    }
}