package kr.saintdev.pdiary.views.fragments.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.saintdev.pdiary.R

/**
 * Copyright (c) 2015 - 2019 Saint software All rights reserved.
 * Calander Fragment 11.14 2018
 * 메인화면 컨디션 달력 프레그먼트를 담당 합니다.
 */
class CalenderFragment : Fragment() {
    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.v = inflater.inflate(R.layout.fragmn_main_calender, container, false)
        return this.v
    }
}