package com.example.donemodevi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
fun isNetworkAvailable(application: Application): Boolean {
    val connectivityManager = application.getSystemService(
        Application.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
            else ->     false
        }
    }
    // For below 29 api
    else {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({


            if(isNetworkAvailable(application) == true){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }, 3000)
            }else{
                Toast.makeText(getApplicationContext(),"İnternet Bağlantınızı Kontol Ediniz...",Toast.LENGTH_LONG).show();
                finish()
            }
        },10)
    }
}