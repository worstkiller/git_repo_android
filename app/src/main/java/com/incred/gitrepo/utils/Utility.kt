package com.incred.gitrepo.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

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
                if (count < args.size && args.size > 1) {
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
        fun getDateInFormatForComment(stringDate: String): String {
            val fromFormat = SimpleDateFormat(DATE_FORMAT_TIME_COMMENT, Locale.getDefault())
            val date = fromFormat.parse(stringDate)
            return DateUtils.getRelativeTimeSpanString(date.time).toString()
        }
    }
}