package com.example.myapplicationexample.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myapplicationexample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {

    public FormFragment() {
        // Required empty public constructor
    }

    private EditText heightEditText;
    private EditText weightEditText;
    private EditText ageEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_form, container, false);

        heightEditText = root.findViewById(R.id.input_height);
        weightEditText = root.findViewById(R.id.input_weight);
        ageEditText = root.findViewById(R.id.input_age);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button_do_calc)
                .setOnClickListener(Navigation.createNavigateOnClickListener(
                        R.id.action_formFragment_to_navigation_home));
    }

    private void countMe(View view) {
        Integer height = Integer.parseInt(heightEditText.getText().toString());
        Integer weight = Integer.parseInt(weightEditText.getText().toString());
        Integer age = Integer.parseInt(ageEditText.getText().toString());
        //todo validation
//        count++;
//
//        showCountTextView.setText(count.toString());
//        PPM (kobiety) = SWE (spoczynkowy wydatek energetyczny kcal) = 655,1 + (9,563 x masa ciała [kg]) + (1,85 x wzrost [cm]) – (4,676 x [wiek])
//        PPM (mężczyźni) = SWE (spoczynkowy wydatek energetyczny kcal) = 66,5 + (13,75 x masa ciała [kg]) + (5,003 x wzrost [cm]) – (6,775 x [wiek])
    }
}
