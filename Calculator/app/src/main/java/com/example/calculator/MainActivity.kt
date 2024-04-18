package com.example.calculator

import android.text.SpannableStringBuilder
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import org.mariuszgromada.math.mxparser.Expression
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity








class MainActivity : ComponentActivity() {
    private lateinit var displayEditText: EditText
    private lateinit var previousCal: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayEditText = findViewById(R.id.displayEditText)
        previousCal = findViewById(R.id.resultText)
        displayEditText.showSoftInputOnFocus = false

        val scanButton = findViewById<Button>(R.id.scanButton)
        scanButton.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateText(strToAdd:String){

        val oldStr = displayEditText.text.toString()
        val cursorPs = displayEditText.selectionStart
        val leftStr = oldStr.substring(0,cursorPs)
        val rightStr = oldStr.substring(cursorPs)
        displayEditText.setText(String.format("%s%s%s",leftStr,strToAdd,rightStr))
        displayEditText.setSelection(cursorPs + strToAdd.length)
    }
    fun onDigitClick(view: View)
    {
        if(view is Button)
        {
            updateText((view).text.toString())
        }
    }

    fun onDivideClick(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.divideText))
        }
    }

    fun onMultiplyClick(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.multiplyText))
        }
    }
    fun onSubstractClick(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.substractText))
        }
    }

    fun onAddClick(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.addText))
        }
    }

    fun onOpenBracket(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.openBracket))
        }
    }

    fun onCloseBracket(view: View)
    {
        if(view is Button)
        {
            updateText(resources.getString(R.string.closeBracket))
        }
    }

    fun onClearClick(view: View)
    {
        displayEditText.text.clear()
        previousCal.text = ""
    }

    fun onBackspaceClick(view: View)
    {
        val cursorPos = displayEditText.selectionStart
        val textLen = displayEditText.text.length
        if (cursorPos != 0 && textLen != 0){
            val selection = displayEditText.text as SpannableStringBuilder
            selection.replace(cursorPos -1,cursorPos,"")
            displayEditText.text = selection
            displayEditText.setSelection(cursorPos -1)
        }
    }

    fun onEqualsClick(view: View)
    {
        var userExp = displayEditText.text.toString()

        userExp = userExp.replace(resources.getString(R.string.divideText).toRegex(), "/")
        userExp = userExp.replace(resources.getString(R.string.multiplyText).toRegex(), "*")
        val exp = Expression(userExp)
        val result = exp.calculate().toString()
        previousCal.setText(result)
    }

    fun onSquareClick(view: View)
    {
        if(view is Button)
        {
            updateText("^(2)")
        }
    }
    fun onLnClick(view: View)
    {
        if(view is Button)
        {
            updateText("ln(")
        }
    }
    fun onEClick(view: View)
    {
        if(view is Button)
        {
            updateText("e")
        }
    }
    fun onPiClick(view: View)
    {
        if(view is Button)
        {
            updateText("pi")
        }
    }
    fun onSinClick(view: View)
    {
        if(view is Button)
        {
            updateText("sin(")
        }
    }
    fun onCosClick(view: View)
    {
        if(view is Button)
        {
            updateText("cos(")
        }
    }
    fun onTgClick(view: View)
    {
        if(view is Button)
        {
            updateText("tg(")
        }
    }

    fun onCtgClick(view: View)
    {
        if(view is Button)
        {
            updateText("ctg(")
        }
    }

    fun onSqrtClick(view: View)
    {
        if(view is Button)
        {
            updateText("sqrt(")
        }
    }

    fun onPowClick(view: View)
    {
        if(view is Button)
        {
            updateText("^(")
        }
    }
}

