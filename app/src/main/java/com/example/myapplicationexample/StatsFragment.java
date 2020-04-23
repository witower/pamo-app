package com.example.myapplicationexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {

    private Button showBarChartButton;
    private Button showPieChartButton;
    private Button showPolarChartButton;
    private WebView chartWebView;
    private WebSettings webSettings;

    private int typeOfChart=1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        chartWebView = root.findViewById(R.id.web_view_chart);
        // Avoid hardware rendering (force software rendering)
        chartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        showBarChartButton = root.findViewById(R.id.button_show_bar_chart);
        showPieChartButton = root.findViewById(R.id.button_show_pie_chart);
        showPolarChartButton = root.findViewById(R.id.button_show_polar_chart);


        showBarChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfChart = 1;
                showChart(typeOfChart);
            }
        });
        showPieChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfChart = 2;
                showChart(typeOfChart);
            }
        });
        showPolarChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfChart = 3;
                showChart(typeOfChart);
            }
        });

        return root;
    }

    private void showChart(int typeOfChart){

        webSettings = chartWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //enable it since you're using js to create your chart
        webSettings.setDomStorageEnabled(true); //incase you're using some DOM in your js
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadWithOverviewMode(true); //This code load your webview into overview mode, fit your html to the screen width

        switch (typeOfChart){
            case 1:
                chartWebView.loadUrl("file:///android_asset/bar_chart.html");
                break;
            case 2:
                chartWebView.loadUrl("file:///android_asset/pie_chart.html");
                break;
            case 3:
                chartWebView.loadUrl("file:///android_asset/polar_chart.html");
                break;
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        showChart(typeOfChart);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("typeOfChart", typeOfChart);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            typeOfChart = savedInstanceState.getInt("typeOfChart");
        }
    }
}