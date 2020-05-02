package com.example.myapplicationexample

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DecimalFormat

class HomeFragment : Fragment() {
    private var height = 0
    private var weight = 0
    private var age = 0
    private var bmi = 0.0
    private var isMale = false
    private var isFemale = false
    private val recipeArgs = Bundle()
    private var snacky: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.println(Log.INFO, "ww", "onCreateView(): $height")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCalculate.setOnClickListener(onBtnCalculateClickListener)
        btnToRecipe.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_navigation_home_to_recipeFragment, recipeArgs
            ))

        snacky = Snackbar.make(view, R.string.app_name, Snackbar.LENGTH_LONG)
        Log.println(Log.INFO, "ww", "onViewCreated(): $height")
    }

    private val onBtnCalculateClickListener =
        View.OnClickListener {
            hideKeyboard()
            calculate()
        }

    private fun almostSafeParse(s: String): Int {
        return try {
            s.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private val formData: Boolean
        get() {
            height = almostSafeParse(inputHeight.text.toString())
            weight = almostSafeParse(inputWeight.text.toString())
            age = almostSafeParse(inputAge.text.toString())
            isFemale = radioFemale.isChecked
            isMale = radioMale.isChecked
            return !((height == 0) or (weight == 0) or (age == 0) or !(isFemale or isMale))
        }

    private fun clearResults() {
        txtResultBmi.setText("")
        txtResultCalories.setText("")
        recipeArgs.clear()
    }

    private fun calculate() {
        if (formData) {
            calcBmi()
            calcCalories()
            btnToRecipe.isEnabled = true
        } else {
            snacky!!.setText(R.string.snack_wrong_data).show()
            clearResults()
            btnToRecipe.isEnabled = false
        }
    }

    private fun calcBmi() {
        val heightMeters = height.toDouble() / 100
        bmi = weight / (heightMeters * heightMeters)
        recipeArgs.putFloat("bmi", bmi.toFloat())
        txtResultBmi.setText(decimalFormatTwoPoints.format(bmi))
    }

    private fun calcCalories() {
        val calories: Double = when {
            isFemale -> 655.1 + 9.563 * weight + 1.85 * height - 4.676 * age
            isMale -> 66.5 + 13.75 * weight + 5.003 * height - 6.775 * age
            else -> 0.0
        }
        txtResultCalories.setText(decimalFormatTwoPoints.format(calories))
    }

    private fun hideKeyboard() {
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
//        try {
//            val inputManager =
//                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//            inputManager?.hideSoftInputFromWindow(
//                when (activity?.currentFocus) {
//                    null -> null
//                    else -> requireActivity().currentFocus!!.windowToken
//                },
//                InputMethodManager.HIDE_NOT_ALWAYS
//            )
//        } catch (ignored: Exception) {
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.println(Log.INFO, "ww", "onCreate(): $height")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.println(Log.INFO, "ww", "onActivityCreated(): $height")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.println(Log.INFO, "ww", "onConfigurationChanged(): $height")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.println(Log.INFO, "ww", "onViewStateRestored(): $height")
    }

    override fun onStart() {
        super.onStart()
        Log.println(Log.INFO, "ww", "onStart(): $height")
    }

    override fun onResume() {
        super.onResume()
        btnToRecipe.isEnabled = formData
        Log.println(
            Log.INFO,
            "ww",
            "onResume(): $height recipeReady: $formData"
        )
    }

    override fun onPause() {
        super.onPause()
        Log.println(Log.INFO, "ww", "onPause(): $height")
    }

    override fun onStop() {
        super.onStop()
        Log.println(Log.INFO, "ww", "onStop(): $height")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.println(Log.INFO, "ww", "onDestroyView(): $height")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.println(Log.INFO, "ww", "onDestroy(): $height")
    }

    override fun onDetach() {
        super.onDetach()
        Log.println(Log.INFO, "ww", "onDetach(): $height")
    }

    companion object {
        private val decimalFormatTwoPoints =
            DecimalFormat("#.##")
    }
}