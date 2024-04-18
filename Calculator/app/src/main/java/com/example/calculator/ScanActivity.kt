package com.example.calculator

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator

class ScanActivity : ComponentActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val button = findViewById<Button>(R.id.btnOpenMain)
        button.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        val scanButton: Button = findViewById(R.id.scanButton)
        scanButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                startScan()
            } else {
                requestCameraPermission()
            }
        }
        // Получение ссылки на кнопку "Копировать"
        val copyButton: Button = findViewById(R.id.copyButton)

// Установка обработчика нажатия на кнопку "Копировать"
        copyButton.setOnClickListener {
            // Получение результата сканирования
            val resultEditText = findViewById<EditText>(R.id.resultEditText)
            val scanResult: String = resultEditText.text.toString()

            // Копирование результата в буфер обмена
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Scan Result", scanResult)
            clipboard.setPrimaryClip(clip)

            // Отображение сообщения об успешном копировании
            Toast.makeText(applicationContext, "Result Copied", Toast.LENGTH_SHORT).show()
        }


    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun startScan() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {

                // Обработка сканированного QR-кода
                val scannedData = result.contents
                Toast.makeText(
                    this,
                    "Scanned QR Code: $scannedData",
                    Toast.LENGTH_SHORT
                ).show()
                val resultEditText = findViewById<EditText>(R.id.resultEditText)
                resultEditText.setText(scannedData);
            } else {
                Toast.makeText(
                    this,
                    "Scan canceled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}