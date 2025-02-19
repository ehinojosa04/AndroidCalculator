package com.example.calculatorpractica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var currentInput = ""
    private var lastOperator = ""
    private var firstOperand = ""
    private var secondOperand = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                currentInput += (it as Button).text
                textView.text = currentInput
            }
        }

        findViewById<Button>(R.id.dot).setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                textView.text = currentInput
            }
        }

        val operatorButtons = mapOf(
            R.id.add to "+",
            R.id.subtract to "-",
            R.id.multiply to "*",
            R.id.divide to "/"
        )

        operatorButtons.forEach { (id, operator) ->
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isEmpty() && operator == "-"){
                    isNewOperation = false
                    currentInput = "-"
                    textView.text = currentInput
                }else if (currentInput.isNotEmpty()) {
                    firstOperand = currentInput
                    lastOperator = operator
                    currentInput = ""
                }
            }
        }

        findViewById<Button>(R.id.solve).setOnClickListener {
            if (firstOperand.isNotEmpty() && currentInput.isNotEmpty()) {
                secondOperand = currentInput
                val result = calculate(firstOperand, secondOperand, lastOperator)
                textView.text = result
                currentInput = result
                isNewOperation = true
            }
        }

        findViewById<Button>(R.id.AC).setOnClickListener {
            resetCalculator()
        }

        findViewById<Button>(R.id.DEL).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                textView.text = currentInput
            }
        }
    }

    private fun calculate(first: String, second: String, operator: String): String {
        return try {
            val num1 = first.toDouble()
            val num2 = second.toDouble()
            val result = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else return "Error"
                else -> 0.0
            }
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun resetCalculator() {
        currentInput = ""
        firstOperand = ""
        secondOperand = ""
        lastOperator = ""
        isNewOperation = true
        textView.text = ""
    }
}
