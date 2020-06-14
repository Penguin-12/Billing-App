package com.example.billingapp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ErrorCallback
import kotlinx.android.synthetic.main.activity_scanner.*


class ScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var scanTimer: CountDownTimer
    private lateinit var loadingTimer: CountDownTimer
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        codeScanner = CodeScanner(this, scannerView)
        alertDialog = AlertDialog.Builder(this)
            .setView(layoutInflater.inflate(R.layout.dialog_loading, null)).setCancelable(false)
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        codeScanner.decodeCallback = DecodeCallback {
//            runOnUiThread {
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//            }
        //}
        loadingTimer = object : CountDownTimer(3000, 3000) {
            override fun onFinish() {
                alertDialog.cancel()
                startActivity(Intent(applicationContext, PaymentActivity::class.java))

            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
        scanTimer = object : CountDownTimer(4000, 4000) {
            override fun onFinish() {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Scan Successful", Toast.LENGTH_LONG).show()

                codeScanner.stopPreview()
                alertDialog.show()
                loadingTimer.start()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
        scanTimer.start()
        progressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        alertDialog.cancel()
        codeScanner.releaseResources()
        scanTimer.cancel()
        loadingTimer.cancel()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        alertDialog.cancel()
        codeScanner.releaseResources()
        scanTimer.cancel()
        loadingTimer.cancel()
        finish()
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}