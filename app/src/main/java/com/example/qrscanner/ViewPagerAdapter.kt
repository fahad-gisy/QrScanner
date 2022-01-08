package com.example.qrscanner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class ViewPagerAdapter(var fm:FragmentManager): FragmentStatePagerAdapter (fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0 -> ScannerFragment.newInstance()
            1 -> HistoryFragment.newInstance()
            2 -> StarFavFragment.newInstance()
            else -> ScannerFragment.newInstance()
    }

}
    }