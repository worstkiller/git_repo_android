package com.incred.gitrepo.widget

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.incred.gitrepo.R

class ProgressDialog(context: Context) : AlertDialog(context) {

    private var tvMessage: TextView
    private var progressView: ProgressBar

    private val view: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.widget_progress_view, null, false)
    }

    init {
        progressView = view.findViewById(R.id.pbProgress)
        tvMessage = view.findViewById(R.id.tvMessages)

        //default message
        tvMessage.text = context.getString(R.string.progress_default_msg)

        setView(view)

        progressView.visibility = View.VISIBLE

        setCancelable(false)
    }

    /**
     * set the message for progress
     * @param messsag
     */
    fun setMessages(messsag: String) {
        tvMessage.text = messsag
    }

}