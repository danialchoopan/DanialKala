package ir.danialchoopan.danialkala.ui.searchProduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import ir.danialchoopan.danialkala.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    val SPEECH_SEARCH_REQUEST_CODE = 221
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_txt_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start > 5) {
                    search_imgBtnClose.visibility = View.VISIBLE
                    search_imgBtnMic.visibility = View.GONE
                } else {
                    search_imgBtnClose.visibility = View.GONE
                    search_imgBtnMic.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end text change listener

        search_imgBtnClose.setOnClickListener {
            search_txt_text.setText("")
        }//end img btn close

        //setup speak search
        search_imgBtnMic.setOnClickListener {
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).also { intent ->
                intent.putExtra(
                    RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    applicationContext.packageName
                )
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1000)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa")
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "لطفا صحبت کنید ...")
                startActivityForResult(intent, SPEECH_SEARCH_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_SEARCH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val result_speech_data = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            search_txt_text.setText(result_speech_data?.get(0)?.toString() ?: "error")
        }
    }
}