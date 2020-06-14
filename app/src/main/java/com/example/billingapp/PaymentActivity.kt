package com.example.billingapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_payment.*


class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        paytmButton.setOnClickListener {
            paytmButton.cardElevation = 0f
            Handler().postDelayed(Runnable { paytmButton.cardElevation = 5f }, 80)
            val appPackageName = "net.one97.paytm"
            val pm: PackageManager = applicationContext.packageManager
            val appStart = pm.getLaunchIntentForPackage(appPackageName)
            if (null != appStart) {
                applicationContext.startActivity(appStart)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Install PayTm to use this Payment Mode",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        googlePayButton.setOnClickListener {
            googlePayButton.cardElevation = 0f
            Handler().postDelayed(Runnable { googlePayButton.cardElevation = 5f }, 80)
            val appPackageName = "com.google.android.apps.nbu.paisa.user"
            val pm: PackageManager = applicationContext.packageManager
            val appStart = pm.getLaunchIntentForPackage(appPackageName)
            if (null != appStart) {
                applicationContext.startActivity(appStart)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Install GooglePay to use this Payment Mode",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
