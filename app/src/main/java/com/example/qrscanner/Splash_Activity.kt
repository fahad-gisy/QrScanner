package com.example.qrscanner

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
private const val CAMERA_REQUEST_CODE = 101 // نسوي كود للكاميرا او اي شي نريد السماح له3
class Splash_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkPermissionNow()

    }
    private fun checkPermissionNow(){ //نطلب من التطبيق يسال عن السماح للمستخدم ب استخدام الكاميرا1
      if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

      }else{
          requestThePermission()
      }

    }
    private fun requestThePermission(){//نحدد قائمة للتبيطقات التي نريد السوال عن السماح عنها 2
       ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
           CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {//نرث هذه الداله لو الكود صحيح نحط الاكشن هنا
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_REQUEST_CODE ){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){ // لو تمت الموافقة
                goToMainAct()
            }
        }
    }

    private fun goToMainAct(){

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
    }


}