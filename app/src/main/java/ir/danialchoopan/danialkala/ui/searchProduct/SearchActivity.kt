package ir.danialchoopan.danialkala.ui.searchProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import ir.danialchoopan.danialkala.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_txt_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start > 5) {
                    search_imgBtnClose.visibility = View.VISIBLE
                } else {
                    search_imgBtnClose.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end text change listener

        search_imgBtnClose.setOnClickListener {
            search_txt_text.setText("")
        }//end img btn close
    }
}