package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*


class MainActivity : ComponentActivity() {
    private lateinit var displayEditText: EditText
    private var currentOperator: String? = null
    private var operand1: Double? = null
    private var operand2: Double? = null
    private var isGotResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayEditText = findViewById(R.id.displayEditText)
    }

    fun onDigitClick(view: View) {
        val button = view as Button
        val digit = button.text.toString()
        if (isGotResult) {
            displayEditText.text.clear()
            isGotResult = false
        }
        displayEditText.append(digit)
    }

    fun onOperationClick(view: View) {
        val button = view as Button
        val operation = button.text.toString()

        if (displayEditText.text.isEmpty()) {
            Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
            return
        }

        if (operand1 != null && currentOperator != null) {
            operand2 = displayEditText.text.toString().toDouble()
            operand1 = performOperation(operand1!!, operand2!!, currentOperator!!)
            operand2 = null
            currentOperator = operation
            displayEditText.text.clear()
            return
        }

        currentOperator = operation
        operand1 = displayEditText.text.toString().toDouble()
        displayEditText.text.clear()
    }

    fun onClearClick(view: View) {
        displayEditText.text.clear()
        currentOperator = null
        operand1 = null
        operand2 = null
    }

    fun onEqualsClick(view: View) {
        if (currentOperator != null && operand1 != null) {
            if (displayEditText.text.isEmpty()) {
                Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
                return
            }
            operand2 = displayEditText.text.toString().toDouble()
            try {
                val result = performOperation(operand1!!, operand2!!, currentOperator!!)
                displayEditText.setText(String.format(Locale.getDefault(), "%.2f", result))
                operand1 = result
                operand2 = null
                currentOperator = null
                isGotResult = true
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Недопустимая операция", Toast.LENGTH_SHORT).show()
            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Ошибка деления на ноль", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performOperation(operand1: Double, operand2: Double, operation: String): Double {
        return when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> {
                if (operand2 == 0.0) {
                    throw ArithmeticException()
                }
                operand1 / operand2
            }
            else -> throw IllegalArgumentException()
        }
    }
}

