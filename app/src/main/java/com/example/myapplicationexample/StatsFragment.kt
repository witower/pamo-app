package com.example.myapplicationexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : Fragment() {
    private var typeOfChart = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Avoid hardware rendering (force software rendering)
        webViewChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        btnShowBarChart.setOnClickListener {
            showChart(1)
        }
        btnShowPieChart.setOnClickListener {
            showChart(2)
        }
        btnShowPolarChart.setOnClickListener {
            showChart(3)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showChart(typeOfChart: Int) {

        webViewChart.settings.javaScriptEnabled = true //enable it since you're using js to create your chart
        webViewChart.settings.domStorageEnabled = true //incase you're using some DOM in your js
        webViewChart.settings.allowUniversalAccessFromFileURLs = true
        webViewChart.settings.allowFileAccessFromFileURLs = true
        webViewChart.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webViewChart.settings.loadWithOverviewMode = true //This code load your webview into overview mode, fit your html to the screen width
        when (typeOfChart) {
            1 -> webViewChart.loadUrl("file:///android_asset/bar_chart.html")
            2 -> webViewChart.loadUrl("file:///android_asset/pie_chart.html")
            3 -> webViewChart.loadUrl("file:///android_asset/polar_chart.html")
        }
        this.typeOfChart = typeOfChart
    }

    override fun onStart() {
        super.onStart()
        showChart(typeOfChart)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("typeOfChart", typeOfChart)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            typeOfChart = savedInstanceState.getInt("typeOfChart")
        }
    }
}