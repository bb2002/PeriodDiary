package kr.saintdev.pdiary.views.fragments.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragmn_main_diary.*
import kr.saintdev.pdiary.R
import kr.saintdev.pdiary.libs.data.DiaryObject
import kr.saintdev.pdiary.libs.func.DateFunction
import kr.saintdev.pdiary.views.adapter.DiaryAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Copyright (c) 2015 - 2019 Saint software All rights reserved.
 * Dialog Fragment 11.14 2018
 * 메인화면 일기 프레그먼트를 담당 합니다.
 */
class DiaryFragment : Fragment() {
    private lateinit var v: View

    private lateinit var listView: ListView
    private lateinit var yearSelector: Spinner
    private lateinit var monthSelector: Spinner

    private lateinit var adapter: DiaryAdapter
    private lateinit var yearAdapter: ArrayAdapter<Int>
    private lateinit var monthAdapter: ArrayAdapter<Int>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.v = inflater.inflate(R.layout.fragmn_main_diary, container, false)

        // find view
        this.listView = this.v.findViewById(R.id.diary_list)
        this.yearSelector = this.v.findViewById(R.id.diary_selector_year)
        this.monthSelector = this.v.findViewById(R.id.diary_selector_month)

        // init listview
        this.adapter = DiaryAdapter()
        this.listView.adapter = this.adapter

        // DB 에서 일기 목록을 가져온다.
        // this.adapter.refreshItems()

        // init spinner
        yearAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item)
        monthAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item)
        yearAdapter.addAll(DateFunction.getAllYears())
        monthAdapter.addAll(DateFunction.getAllMonths())
        this.yearSelector.adapter = yearAdapter
        this.monthSelector.adapter = monthAdapter

        return this.v
    }
}