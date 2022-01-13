package db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import db.QrClasses.QrResult


@Dao
interface QrResultDao {//نختار من الداتا و نعرضها بشكل الي سوينا له سكان اخر شي راح يطلع اول شي في الليست
    @Query("SELECT * FROM QrResult ORDER BY time DESC")
 fun getAllScannedResult():List<QrResult>

          //هنا نختار المفضله واحد يعني ترو او صحيح
 @Query("SELECT * FROM QrResult WHERE fav = 1")
 fun getAllFavResult() : List<QrResult>
      //نمسح كل النتائج
 @Query("DELETE FROM QrResult")
     fun deleteAllScannedResult()

     //نسمح كل النتائج المفضلة
 @Query("DELETE FROM QrResult WHERE fav = 1")
 fun deleteAllFavResult()

 @Insert (onConflict = OnConflictStrategy.REPLACE)  // لو فيه تعارض بين اي دي الداتا يسوي ري بليس
 fun insertQrResult(qrResult: QrResult) : Long // ادخال طاوله الداتا بيس

 @Query("SELECT * FROM QrResult where id = :id") //داله ترجع اي دي الداتا عشان لو نبي نشوفها ب اي ديها
 fun getQrResultId(id:Int) :QrResult

 @Query("UPDATE QrResult SET  Fav = 1  WHERE id = :id")
 fun addToFav(id:Int) :Int

 //اضافة و حذف المفضلة عن طريق الاي دي

 @Query("UPDATE QrResult SET  Fav = 0  WHERE id = :id")
 fun RemoveFav(id:Int) :Int


}