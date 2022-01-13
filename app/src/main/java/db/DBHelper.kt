package db

import db.QrClasses.QrResult
import java.util.*

class DBHelper(var qrResultDatabase: QrResultDatabase) : DBHelperI  {
    override fun insertQrResult(result: String): Int {
        val time = Calendar.getInstance()
        val resultType = "TEXT"
        val qrResult = QrResult(result = result, resultType = resultType, calender = time, fav = false)
        return qrResultDatabase.getQrDao().insertQrResult(qrResult).toInt()
    }

    override fun getQrResult(id: Int): QrResult {
     return  qrResultDatabase.getQrDao().getQrResultId(id)
    }

    override fun addToFav(id: Int): Int {
       return qrResultDatabase.getQrDao().addToFav(id)
    }

    override fun removeFromFav(id: Int): Int {
        return  qrResultDatabase.getQrDao().RemoveFav(id)
    }

}