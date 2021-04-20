package ir.danialchoopan.danialkala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //check connection to internet
        VolleySingleTon.getInstance(this@SplashActivity).addToRequestQueue(
            StringRequest(
                Request.Method.GET,
                EndPoints.baseUrl,
                //success
                { response ->
                    //open home activity
                    Intent(
                        this@SplashActivity,
                        HomeActivity::class.java
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
}