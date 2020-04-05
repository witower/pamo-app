package com.example.myapplicationexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplicationexample.R;
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

    private EditText heightInput;
    private EditText weightInput;
    private EditText ageInput;
    private RadioGroup genderRadio;
    private RadioButton femaleRadio;
    private RadioButton maleRadio;
    private TextView resultBmiTextView;
    private TextView resultCaloriesTextView;
    private TextView resultRecipeTextView;

    private TextView testTextView;

    private Snackbar snacky;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        resultBmiTextView = root.findViewById(R.id.result_bmi);
        resultCaloriesTextView = root.findViewById(R.id.result_calories);
        resultRecipeTextView = root.findViewById(R.id.result_recipe);

        heightInput = root.findViewById(R.id.input_height);
        weightInput = root.findViewById(R.id.input_weight);
        ageInput = root.findViewById(R.id.input_age);
        genderRadio = root.findViewById(R.id.radio_group_gender);
        femaleRadio = root.findViewById(R.id.radio_button_female);
        maleRadio = root.findViewById(R.id.radio_button_male);

        Button doCalcButton = root.findViewById(R.id.button_do_calc);
        doCalcButton.setOnClickListener(onDoCalcButtonClickListener);


    return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        snacky = Snackbar.make(view, R.string.app_name, Snackbar.LENGTH_LONG);
    }

    private final View.OnClickListener onDoCalcButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calculate();
        }
    };

    private int safeAlikeParse(String s) {
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
        height = safeAlikeParse(heightInput.getText().toString());
        weight = safeAlikeParse(weightInput.getText().toString());
        age = safeAlikeParse(ageInput.getText().toString());
        isFemale = femaleRadio.isChecked();
        isMale = maleRadio.isChecked();

        return !(height == 0 | weight == 0 | age == 0 | !(isFemale | isMale));
    }

    private void clearResults(){
        testTextView.setText("");
        resultBmiTextView.setText("");
        resultCaloriesTextView.setText("");
        resultRecipeTextView.setText("");
    }

    private void calculate() {
        calcBmi();
        if (getFormData()){
            calcBmi();
            calcCalories();
            showRecipe();
        }
        else {
            snacky.setText(R.string.snack_wrong_data).show();
            clearResults();
        }

    }

    private void calcBmi() {
        double heightMeters = (double)height/100;
        bmi = weight / (heightMeters * heightMeters);
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

    private void showRecipe() {
        if (bmi > 25) {
            resultRecipeTextView.setText(R.string.recipe_salad);
        }
        else {
            resultRecipeTextView.setText(R.string.recipe_chips);
        }

    }

}