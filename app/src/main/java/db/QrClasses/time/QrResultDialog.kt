package db.QrClasses.time

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.qrscanner.R
import db.DBHelper
import db.DBHelperI
import db.QrClasses.QrResult
import db.QrResultDatabase

class QrResultDialog (var context: Context){
    private lateinit var dialog:Dialog
    private var qrResult: QrResult? = null
    private var onDismissListener : PopupWindow.OnDismissListener? = null
    private lateinit var dbHelperI:DBHelperI

    init {
        init()
        initDialog()
    }

    private fun init() {
        dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(context)!!)
    }


    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.qr_custom_result)
        dialog.setCancelable(false)
        onClicksEvents()
    }

    fun setOnDismissListener(onDismissListener: PopupWindow.OnDismissListener){
         this.onDismissListener = onDismissListener
    }

    fun show(qrResult: QrResult){
        this.qrResult = qrResult
        dialog.findViewById<TextView>(R.id.calenderTime).text = qrResult.calender.toFormatDisplay()
        dialog.findViewById<TextView>(R.id.qrResultTv).text = qrResult.result
        dialog.findViewById<CardView>(R.id.fav).isSelected = qrResult.fav == true
        dialog.show()

    }

    private fun onClicksEvents() {
        dialog.findViewById<CardView>(R.id.fav).setOnClickListener {

            if(dialog.findViewById<CardView>(R.id.fav).isSelected){
              removeFromFav()
            }else{
                addToFav()
            }

        }

        dialog.findViewById<CardView>(R.id.share).setOnClickListener {

             shareTheResult()
        }

        dialog.findViewById<CardView>(R.id.copy).setOnClickListener {

            copyTheResult()

        }

        dialog.findViewById<ImageView>(R.id.quitbotton).setOnClickListener {
             onDismissListener?.onDismiss()
            dialog.dismiss()
        }

    }

    private fun addToFav() {
        dialog.findViewById<CardView>(R.id.fav).isSelected = true
        dbHelperI.addToFav(qrResult?.id!!)
        Toast.makeText(context,"تمت الاضافة الى المفضلة",Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFav() {
        dialog.findViewById<CardView>(R.id.fav).isSelected = false
        dbHelperI.removeFromFav(qrResult?.id!!)
        Toast.makeText(context,"تمت الازالة من المفضلة",Toast.LENGTH_SHORT).show()
    }

    private fun shareTheResult() { // نشارك نص النتيجة
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(Intent.EXTRA_TEXT,dialog.findViewById<TextView>(R.id.qrResultTv).text.toString())
        context.startActivity(txtIntent)
    }

    private fun copyTheResult() { //نسخ النتيجة للجهاز
        val clipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager // نوصل للوحه المحفوظات
        val clip = ClipData.newPlainText("QrScannerResult",dialog.findViewById<TextView>(R.id.qrResultTv).text)
        clipBoard.text = clip.getItemAt(0).text
        Toast.makeText(context,"تم النسخ",Toast.LENGTH_SHORT).show()
    }
  interface QrDismissListener : PopupWindow.OnDismissListener {
       override fun onDismiss()
  }
}