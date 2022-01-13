package com.example.qrscanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import db.DBHelper
import db.DBHelperI
import db.QrClasses.time.QrResultDialog
import db.QrResultDatabase
import me.dm7.barcodescanner.zxing.ZXingScannerView
                                         //هنا نرث من كلاس الريزولت
class ScannerFragment : Fragment(),ZXingScannerView.ResultHandler {

    companion object {
        fun newInstance() : ScannerFragment{
            return ScannerFragment()
        }
    }
var imageViewFlash:ImageView? = null
var frameLayoutCam:FrameLayout? = null
 private lateinit var mView:View // سوينا متغير فيو و ساويناه ب الفيو حقت الفراقميت
 private lateinit var scannerView:ZXingScannerView // متغير سكانر
 private lateinit var resultDialog: QrResultDialog
 private lateinit var dbHelperI: DBHelperI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_qrscanner, container, false)
        connectVs(mView)
        init()
        initViews()
        flashHandle()
        return mView.rootView
    }

private fun init() {
 dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(requireContext())!! )
 }

 private fun initViews() {
    scannerInstall()
    setResultDialog()
}

private fun setResultDialog() { //حطينا الكاتسم ديالوق في الفراقمت
         resultDialog = QrResultDialog(requireContext())
         resultDialog.setOnDismissListener(object: QrResultDialog.QrDismissListener{
             override fun onDismiss() {
                 scannerView.resumeCameraPreview(this@ScannerFragment)
             }
         })
}

                                             private fun flashHandle() { //صوره الفلاش
imageViewFlash?.setOnClickListener {
    if(it.isSelected){
        flashOff()// لو تم الضغط على صورة الفلاش
    }else flashOn() // لو الصورة الفلاش لم يتم الضغط عليها
}
}
  private fun flashOff() { // لو م تم اختيار صورة الفلاش طفي الفلاش
      imageViewFlash?.isSelected = false
      scannerView.flash = false
}

private fun flashOn() {//لو تم اختيار صورة الفلاش شغلي الفلاش
     imageViewFlash?.isSelected = true
     scannerView.flash = true
 }
 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
super.onViewCreated(view, savedInstanceState)
   connectVs(view)
 }

private fun connectVs(view: View){
    imageViewFlash = view.findViewById(R.id.flash)
    frameLayoutCam = view.findViewById(R.id.containerCamera)
}

private fun scannerInstall(){
scannerView = ZXingScannerView(requireContext()) //ربطنا السكانر ب الكونتست حق الفراقمت
scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(),android.R.color.transparent)) //لون الخلفية في السكانر شفاف
scannerView.setBorderColor(ContextCompat.getColor(requireContext(),R.color.lightgreen))//الوان السكانر
scannerView.setLaserColor(ContextCompat.getColor(requireContext(),R.color.design_default_color_primary_dark))
scannerView.setBorderStrokeWidth(10)//عرض خط البوردر
scannerView.setAutoFocus(true)// يسوي اوتو فوكس
scannerView.setSquareViewFinder(true)//يدور على شكل مربع
scannerView.setResultHandler (this)    // هنا نقوله الفراقمت ذي راح تستقبل النتيجة حق السكان
frameLayoutCam?.addView(scannerView) //نحط الكاميرا في الفريم
startCamForScan()
}

private fun startCamForScan(){
    scannerView.startCamera() //]داله نبدا الكاميرا
}

override fun onResume() {
super.onResume()
scannerView.setResultHandler (this) // نسجل النتيجة
scannerView.startCamera()//الكاميرا تبقى شغاله في الريزوم
}

override fun onPause() {
super.onPause()
scannerView.stopCamera()//نوقف الكاميرا اذا طلع المستخدم من التطبيق
}

            //هنا نحدد كيف نعرض الريزولت
override fun handleResult(rawResult: Result?) {
onQrResult(rawResult!!.text)

    }

private fun onQrResult(text: String?) {
      if (text.isNullOrEmpty()){
          Toast.makeText(requireContext(),"لا يوجد نتيجة!!",Toast.LENGTH_SHORT).show()
      }else{
          saveToDatabase(text)
      }
 }

private fun saveToDatabase(result: String) {
  val insertedId =  dbHelperI.insertQrResult(result)
    val qrResult = dbHelperI.getQrResult(insertedId)
      resultDialog.show(qrResult)
}
 }