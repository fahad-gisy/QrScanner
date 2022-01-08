package com.example.qrscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
var bottomNav:BottomNavigationView? = null
var viewPager:ViewPager? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectVs()
        setPagerAdapter()
        bottomNav()
        viewPagerListener()

    }
private fun connectVs (){
    viewPager = findViewById(R.id.containerApp)
    bottomNav = findViewById(R.id.bottomNav)
}
    private fun setPagerAdapter(){
        viewPager?.adapter = ViewPagerAdapter(supportFragmentManager)
    }
 private fun bottomNav(){
    bottomNav?.setOnItemSelectedListener {
        when(it.itemId){
            R.id.qrScan -> viewPager?.currentItem = 0
            R.id.history -> viewPager?.currentItem = 1
            R.id.fav -> viewPager?.currentItem = 2

        }
       true
    }
 }
    private fun viewPagerListener(){
        viewPager?.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }
            override fun onPageSelected(position: Int) {
              when(position){
                  0-> bottomNav?.selectedItemId = R.id.qrScan
                  1-> bottomNav?.selectedItemId = R.id.history
                  2-> bottomNav?.selectedItemId = R.id.fav
                  else -> bottomNav?.selectedItemId = R.id.qrScan
              }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }
}