package kr.saintdev.pdiary

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.saintdev.pdiary.views.fragments.main.CalenderFragment
import kr.saintdev.pdiary.views.fragments.main.DiaryFragment
import kr.saintdev.pdiary.views.fragments.main.MemoFragment

/**
 * Copyright (c) 2015 - 2019 Saint software All rights reserved.
 * Main Activity 11.14 2018
 * 프레그먼트와 인터랙션 한다.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Bottom Menu Click listener
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.main_nav_diary -> {
                replaceFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.main_nav_memo -> {
                replaceFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.main_nav_calender -> {
                replaceFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Fragment mapping.
     */
    private val fragmentArray = arrayOf(
        DiaryFragment(), MemoFragment(), CalenderFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(0)
    }

    /**
     * 11.14 2018
     * Main 의 Container 를 교체 한다.
     */
    private fun replaceFragment(idx: Int) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.main_container, fragmentArray[idx])
        trans.addToBackStack(null)
        trans.commit()
    }
}
