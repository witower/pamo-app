package com.example.myapplicationexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplicationexample.R;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

    return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        view.findViewById(R.id.button_show_recipes).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_recipeFragment));
//        view.findViewById(R.id.button_go_to_form).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_formFragment));

    }



    private void snackExample() {
        Snackbar snacky = Snackbar.make(getView(), R.string.button_show_recipes, Snackbar.LENGTH_LONG);
        snacky.show();
    }

}