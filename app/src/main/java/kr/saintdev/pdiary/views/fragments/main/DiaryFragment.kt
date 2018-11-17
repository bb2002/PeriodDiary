package kr.saintdev.pdiary.views.fragments.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragmn_main_diary.*
import kr.saintdev.pdiary.R
import kr.saintdev.pdiary.libs.data.DiaryObject
import kr.saintdev.pdiary.libs.func.DateFunction
import kr.saintdev.pdiary.modules.db.DBM
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM
import kr.saintdev.pdiary.views.activitys.ReadDiaryActivity
import kr.saintdev.pdiary.views.activitys.WriteDiaryActivity
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
    private lateinit var diaryWrite: FloatingActionButton
    private lateinit var getAllDiaries: Button

    private lateinit var adapter: DiaryAdapter
    private lateinit var yearAdapter: ArrayAdapter<Int>
    private lateinit var monthAdapter: ArrayAdapter<Int>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.v = inflater.inflate(R.layout.fragmn_main_diary, container, false)

        // find view
        this.listView = this.v.findViewById(R.id.diary_list)
        this.yearSelector = this.v.findViewById(R.id.diary_selector_year)
        this.monthSelector = this.v.findViewById(R.id.diary_selector_month)
        this.diaryWrite = this.v.findViewById(R.id.diary_create_new)
        this.getAllDiaries = this.v.findViewById(R.id.diary_selector_all)

        // inti adapter
        this.adapter = DiaryAdapter()
        this.listView.adapter = this.adapter

        this.yearSelector.onItemSelectedListener = dateChangeListener
        this.monthSelector.onItemSelectedListener = dateChangeListener
        this.listView.onItemClickListener = onDiarySelectedListener

        // init spinner
        yearAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item)
        monthAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item)
        yearAdapter.addAll(DateFunction.getAllYears())
        monthAdapter.addAll(DateFunction.getAllMonths())
        this.yearSelector.adapter = yearAdapter
        this.monthSelector.adapter = monthAdapter

        // init listener
        diaryWrite.setOnClickListener { startActivity(Intent(context!!, WriteDiaryActivity::class.java)) }
        this.getAllDiaries.setOnClickListener { refreshDiaries() }

        return this.v
    }

    override fun onResume() {
        super.onResume()

        // 오늘을 기본값으로 설정
        refreshDiariesToday()
    }

    private fun refreshDiaries() {
        // DB 에서 일기 목록을 가져온다.
        val items = DiaryDBM.getAllDiaries(DBM.getDB(context!!))
        this.adapter.refreshItems(items)
        this.adapter.notifyDataSetChanged()
    }

    private fun refreshDiariesToday() {
        // DB 에서 오늘 일기를 가져온다.
        val cal = Calendar.getInstance()
        this.yearSelector.setSelection(cal.get(Calendar.YEAR) - 1970)
        this.monthSelector.setSelection(cal.get(Calendar.MONTH))

        val year = yearAdapter.getItem(yearSelector.selectedItemPosition)
        val month = monthAdapter.getItem(monthSelector.selectedItemPosition)

        val items = DiaryDBM.getDayOfDiaries(DBM.getDB(context!!), year, month)
        adapter.refreshItems(items)
        adapter.notifyDataSetChanged()
    }

    /**
     * 날짜가 변경되었을 경우
     */
    private val dateChangeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val year = yearAdapter.getItem(yearSelector.selectedItemPosition)
            val month = monthAdapter.getItem(monthSelector.selectedItemPosition)

            if(year != null && month != null) {
                val items = DiaryDBM.getDayOfDiaries(DBM.getDB(context!!), year, month)
                adapter.refreshItems(items)
                adapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * 글이 선택되었을 경우
     */
    private val onDiarySelectedListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        val uniqid = adapter.getItem(position).uniqid
        val intent = Intent(context, ReadDiaryActivity::class.java)
        intent.putExtra("uniqid", uniqid)       // 글 고유 번호를 추가 한다.

        startActivity(intent)
    }
}