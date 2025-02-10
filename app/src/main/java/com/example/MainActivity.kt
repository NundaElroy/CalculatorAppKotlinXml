package com.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivityMainBinding
import org.mozilla.javascript.Scriptable


class MainActivity : AppCompatActivity() {

    // Declare the binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object by inflating the layout
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the root view of the layout using binding.root
        setContentView(binding.root)

        // Set onClickListeners for your buttons or other UI actions
        binding.allClearButton.setOnClickListener {
            allClearAction(it)
        }



        listOf(
            binding.button0, binding.button1, binding.button2,
            binding.button3, binding.button4, binding.button5,
            binding.button6, binding.button7, binding.button8,
            binding.button9, binding.buttonadd,binding.buttondiv,
            binding.buttonsub,binding.buttonmultiply
        ).forEach { button ->
            button.setOnClickListener { numberAction(it) }
        }
    }

    // Function for clear button
    fun allClearAction(view: View) {
        // Access and clear the text of the TextViews using View Binding
        binding.workingsTV.text = ""
        binding.resultsTV.text = "0"
    }

    // Define other functions (backSpaceAction, equalsAction, etc.)
    fun backSpaceAction(view: View) {
        val length = binding.workingsTV.length();

        if (length > 0){
            binding.workingsTV.text = binding.workingsTV.text.subSequence(0,length-1)
        }
    }
    private fun validateEquation(equation: String): Boolean {
        val trimmedEquation = equation.trim()
        // Ensure it doesn't end with an operator
        return !trimmedEquation.endsWith("+") && !trimmedEquation.endsWith("-") &&
                !trimmedEquation.endsWith("*") && !trimmedEquation.endsWith("/")
    }

    fun equalsAction(view: View) {
        val equation = binding.workingsTV.text.toString() + ";"
        if (validateEquation(equation)) {
            binding.resultsTV.text = calculateResult(equation)
        }

    }

    fun operationAction(view: View) {
        if (view is Button){
            binding.workingsTV.append(view.text)
        }

    }
    private fun numberAction(view: View) {
        if (view is Button){
            binding.workingsTV.append(view.text)
        }

    }

    private fun calculateResult(equation: String): String? {
        val context: org.mozilla.javascript.Context = org.mozilla.javascript.Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult =
            context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")

        }
        return finalResult
    }
}


