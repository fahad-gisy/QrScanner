package com.example.qrscanner

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
private const val CAMERA_REQUEST_CODE = 101 // نسوي كود للكاميرا او اي شي نريد السماح له3
class Splash_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkPermissionNow()
        },3000)


    }
    private fun checkPermissionNow(){ //نطلب من التطبيق يسال عن السماح للمستخدم ب استخدام الكاميرا1
      if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
          goToMainAct()
      }else{
          requestThePermission()
      }

    }
    private fun requestThePermission(){//نحدد قائمة للتبيطقات التي نريد السوال عن السماح عنها 2
       ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
           CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {//4نرث هذه الداله لو الكود صحيح نحط الاكشن هنا
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_REQUEST_CODE ){//لو الكود ريكوست يساوي كود الكاميرا يعني يرجع ترو6
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //5 لو تمت الموافقة
                goToMainAct()
            } else if(isUserPermanentlyDenied()){//لو المستخدم رفض بشكل دائم7
                  showGoToSettingDialog()

            }else requestThePermission()
        }
    }

    private fun showGoToSettingDialog(){//هنا المستخدم يختار الموافقة او لا
        AlertDialog.Builder(this)
            .setTitle("الموفقة على الكاميرا")
            .setMessage("التطبيق يحتاج الوصول الى الكاميرا لكي يتم مسح الكود")
            .setPositiveButton("موافق؟"){dialog, which ->
             goToAppSetting() // لو المستخدم وافق نروح اعادادات التطبيق
            }
            .setNegativeButton("لا"){dialog,which ->
                Toast.makeText(this,"لكي بتستطيع مسح الكود يجب عليك الموافة على استخدام الكاميرا",Toast.LENGTH_SHORT).show()
                finish()// لو رفض المستخدم نعرض الرسالة ذي وننهي العملية
            }.show()
    }

    private fun goToAppSetting(){// الدالة هاذي تودينا للعدادات التطبيق
     var intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",packageName,null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

private fun isUserPermanentlyDenied():Boolean{

return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//8لو نسخة النظام مارشميلو او الي بعدة
    shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not() // نلغي الطلب 9
} else {
     return false
}

}
    private fun goToMainAct(){
                //عند الموافقة نروح للهوم
            startActivity(Intent(this,MainActivity::class.java))
           finish()
    }

    override fun onRestart() {
        super.onRestart()
        checkPermissionNow()
    }

}