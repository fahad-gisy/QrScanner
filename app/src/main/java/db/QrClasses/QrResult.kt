package db.QrClasses

import androidx.room.*
import db.QrClasses.time.DataTimeConverters
import java.util.*

@Entity //طاوله بيانات الي بنحفظها في التطبيق
@TypeConverters(DataTimeConverters::class)
data class QrResult (

    @PrimaryKey(autoGenerate = true)
    val id:Int? =null, //اي دي عشوائي للبيانات

    @ColumnInfo(name = "Result")
    val result:String?,

    @ColumnInfo(name = "Result_type")
    val resultType:String?,

    @ColumnInfo(name = "Fav")
    val fav:Boolean?,

    @ColumnInfo(name = "time")
    val calender:Calendar

        )
