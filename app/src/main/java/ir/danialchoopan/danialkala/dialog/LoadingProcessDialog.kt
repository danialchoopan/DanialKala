package ir.danialchoopan.danialkala.dialog

import android.app.AlertDialog
import android.content.Context
import ir.danialchoopan.danialkala.R

class LoadingProcessDialog(val m_context: Context) : AlertDialog.Builder(m_context) {
    init {
        setCancelable(false)
        setView(R.layout.dialog_loading)
    }
}