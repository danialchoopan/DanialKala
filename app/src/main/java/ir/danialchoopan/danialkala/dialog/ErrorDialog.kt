package ir.danialchoopan.danialkala.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import ir.danialchoopan.danialkala.R
import kotlinx.android.synthetic.main.alert_success.view.*

class ErrorDialog(val m_context: Context) : AlertDialog.Builder(m_context) {
    val layout = LayoutInflater.from(m_context).inflate(R.layout.alert_error, null)

    init {
        setView(layout)
    }
}