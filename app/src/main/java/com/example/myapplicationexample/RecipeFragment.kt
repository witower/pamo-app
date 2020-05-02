package com.example.myapplicationexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_recipe.*

/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {
    private var bmi = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bmi = requireArguments().getFloat("bmi", 0.0f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onResume() {
        super.onResume()
        textRecipeResult!!.setText(if (bmi > BMI_THRESHOLD) R.string.recipe_salad else R.string.recipe_chips)
    }

    companion object {
        private const val BMI_THRESHOLD = 25.0f
    }
}