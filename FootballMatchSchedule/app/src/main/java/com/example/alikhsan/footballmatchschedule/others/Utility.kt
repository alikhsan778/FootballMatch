package com.example.alikhsan.footballmatchschedule.others

import android.app.ProgressDialog
import java.text.SimpleDateFormat
import java.util.*


class Utility {

    companion object {
        fun getDateConvert(mDate: String?, mType: Int?): String {
            val cDate: String
            val nDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val nDate = nDateFormat.parse(mDate)
            if (mType == 1) {
                val rDateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
                cDate = rDateFormat.format(nDate)
            } else {
                val rDateFormat = SimpleDateFormat("yyyy/MM/dd",Locale.US)
                cDate  = rDateFormat.format(nDate)
            }
            return cDate

        }

        fun openProgressDialog(mProgressDialog:ProgressDialog,nMessage:String?){
            mProgressDialog.setMessage(nMessage)
            mProgressDialog.isIndeterminate = false
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }




    }


}