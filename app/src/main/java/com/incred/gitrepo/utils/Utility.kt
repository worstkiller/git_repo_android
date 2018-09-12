package com.incred.gitrepo.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.view.View
import com.incred.gitrepo.model.GitRepo
import kotlin.collections.ArrayList
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager


/**
 * Created by incred on 11/9/18.
 */
class Utility {

    companion object {

        /**
         * get topic builder for search
         * @param args
         */
        fun getTopicBuilder(vararg args: String): String {
            val builder = StringBuilder()
            for ((count, value) in args.withIndex()) {
                builder.append("topic:")
                if (count < args.size - 1 && args.size > 1) {
                    builder.append(value).append("+")
                } else {
                    builder.append(value)
                }
            }
            return builder.toString()
        }

        /**
         * @param stringDate
         * date format "yyyy-MM-dd'T'HH:mm:ss'Z'"
         * converts to  = 2 days ago ex
         */
        fun getDateInFormatForRelativeTime(dateFormat: String, stringDate: String): String {
            val fromFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val date = fromFormat.parse(stringDate)
            return DateUtils.getRelativeTimeSpanString(date.time).toString()
        }


        /**
         * @param stringDate
         * date format "yyyy-MM-dd'T'HH:mm:ss'Z'"
         * converts to  = 2 days ago ex
         */
        fun getDateInFormatFor(dateFormat: String, requiredFormat: String, stringDate: String): String {
            val fromFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val date = fromFormat.parse(stringDate)
            val requiredFormat = SimpleDateFormat(requiredFormat, Locale.getDefault())
            return requiredFormat.format(date)
        }

        /**
         * gets a share intent in android
         * @param data
         */
        fun getShareIntent(data: String): Intent {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, data)
            sendIntent.type = "text/plain"
            return sendIntent
        }

        /**
         * matches the list against query and returns the list
         */
        fun getSortedResult(query: String, originalList: ArrayList<GitRepo>): ArrayList<GitRepo> {
            val updatedList = ArrayList<GitRepo>()
            for (item in originalList) {
                if (item.full_name!!.contains(query)) {
                    updatedList.add(item)
                }
            }
            return updatedList
        }

        /**
         * this hides the keyboard from the given view
         * @param context
         * @param view
         */
        fun hideKeyboardFrom(context: Context, view: View) {
            try {
                val manager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            } catch (exp: NullPointerException) {
                exp.printStackTrace()
            }
        }
    }
}