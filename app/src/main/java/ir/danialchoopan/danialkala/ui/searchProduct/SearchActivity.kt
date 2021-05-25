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
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.search.SearchProductRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.product.search.SearchProductVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.searchProduct.SearchProductDataModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    lateinit var searchProductVolley: SearchProductVolleyRequest
    val SPEECH_SEARCH_REQUEST_CODE = 221
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchProductVolley = SearchProductVolleyRequest(this@SearchActivity)
        //setup recycler
        val searchProductRecyclerViewAdapter = SearchProductRecyclerViewAdapter(this@SearchActivity)
        p_search_rcy_product.layoutManager = LinearLayoutManager(this@SearchActivity)
        p_search_rcy_product.adapter = searchProductRecyclerViewAdapter
        search_txt_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start > 3) {
                    search_imgBtnClose.visibility = View.VISIBLE
                    search_imgBtnMic.visibility = View.GONE
                    p_search_empty_progressBar.visibility = View.VISIBLE
                    searchProductVolley.query(search_txt_text.text.toString()) { success, productSearch ->
                        p_search_empty_progressBar.visibility = View.GONE
                        if (success) {
                            searchProductRecyclerViewAdapter.setData(productSearch)
                            if (productSearch.size == 0) {
                                p_search_empty.visibility = View.VISIBLE
                            } else {
                                p_search_empty.visibility = View.GONE
                            }
                        }
                    }
                } else {
                    searchProductRecyclerViewAdapter.setData(SearchProductDataModel())
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