package com.example.myapplicationexample.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplicationexample.R;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText ageEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        heightEditText = root.findViewById(R.id.input_height);
        weightEditText = root.findViewById(R.id.input_weight);
        ageEditText = root.findViewById(R.id.input_age);

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button_show_recipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snacky = Snackbar.make(v, R.string.button_show_recipes, Snackbar.LENGTH_LONG);
                snacky.show();
            }
        });

        view.findViewById(R.id.button_go_to_form).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countMe(v);
            }
        }));
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