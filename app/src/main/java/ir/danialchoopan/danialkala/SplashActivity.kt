package ir.danialchoopan.danialkala

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setLocale("fa")

        //check connection to internet
        VolleySingleTon.getInstance(this@SplashActivity).addToRequestQueue(
            StringRequest(
                Request.Method.GET,
                EndPoints.home,
                //success
                { response ->
                    //open home activity
                    Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    ).also { intentOpenHomeActivity ->
                        startActivity(intentOpenHomeActivity)
                        finish()
                    }
                },
                //error
                { errorResponse ->
                    Snackbar.make(
                        constraintLayoutSpalshScreen,
                        R.string.errorConnectionSnackBar,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.tryAgain) {

                    }
                })
            //end request
        )
        //end volley single ton
    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, SplashActivity::class.java)
//        finish()
//        startActivity(refresh)
    }
}