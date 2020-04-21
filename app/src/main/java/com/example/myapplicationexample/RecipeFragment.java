package com.example.myapplicationexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    private static final float BMI_THRESHOLD = 25.0f;

    private float bmi;
    private TextView resultRecipeTextView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bmi = getArguments().getFloat("bmi",0.0f);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        resultRecipeTextView = root.findViewById(R.id.result_recipe2);

        return root;
    }

    @Override
    public void onResume() {

        super.onResume();
        resultRecipeTextView.setText(bmi > BMI_THRESHOLD ? R.string.recipe_salad : R.string.recipe_chips);
    }
}
