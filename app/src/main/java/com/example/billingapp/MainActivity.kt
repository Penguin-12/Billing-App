package com.example.billingapp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var alertDialog: AlertDialog
private lateinit var promptView: View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptView = layoutInflater.inflate(R.layout.dialog_form, null)
        alertDialog = AlertDialog.Builder(this)
            .setView(promptView)
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        meterReadingButton.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                val list = ArrayList<String>()
                list.add(Manifest.permission.CAMERA)
                requestPermissions(list.toTypedArray(), 100)
            } else {
                alertDialog.show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alertDialog.show()
            } else {
                Toast.makeText(this, "Need Permission to take Reading", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onConfirm(view: View) {
        if (isEmpty(promptView.findViewById(R.id.accountNoEditText)) && isEmpty(
                promptView.findViewById(
                    R.id.meterReadingEditText
                )
            )
        )
            Toast.makeText(applicationContext, "Both fields are empty", Toast.LENGTH_LONG)
                .show()
        else if (isEmpty(promptView.findViewById(R.id.accountNoEditText)))
            Toast.makeText(applicationContext, "Account Number field is empty", Toast.LENGTH_LONG)
                .show()
        else if (isEmpty(promptView.findViewById(R.id.meterReadingEditText)))
            Toast.makeText(applicationContext, "Meter Reading field is empty", Toast.LENGTH_LONG)
                .show()
        else {
            alertDialog.cancel()
            finish()
            startActivity(Intent(this, ScannerActivity::class.java))
        }
    }

    fun onCancel(view: View) {
        alertDialog.cancel()
    }

    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.length == 0
    }

    override fun onPause() {
        super.onPause()
        alertDialog.cancel()
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press BACK Again to Exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
