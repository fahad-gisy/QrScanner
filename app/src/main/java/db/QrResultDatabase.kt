package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import db.QrClasses.QrResult
     //كلاس الداتا بيس نمرر له كلاس البيانات و النسخة و اذا نبي نستخرج الداتا للجهاز او لا
@Database(entities = [QrResult::class], version = 1, exportSchema = false)
abstract class QrResultDatabase : RoomDatabase(){
    abstract fun getQrDao(): QrResultDao

    companion object{ // هنا بنينا الداتا بيس داخل التطبيق
        private const val DB_NAME = "QrResultDatabase"
        private var qrResultDatabase:QrResultDatabase? = null

        fun getAppDatabase(context: Context): QrResultDatabase?{
            if (qrResultDatabase == null){
                qrResultDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    QrResultDatabase::class.java,
                    DB_NAME
                ).allowMainThreadQueries().build()
            }
            return qrResultDatabase
        }

    }
}