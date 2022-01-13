package db

import db.QrClasses.QrResult

interface DBHelperI {

    fun insertQrResult(result : String) : Int

    fun getQrResult(id:Int) : QrResult

    fun addToFav(id: Int) :Int

    fun removeFromFav(id: Int):Int
}