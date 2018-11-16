package kr.saintdev.pdiary.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import kr.saintdev.pdiary.R
import kr.saintdev.pdiary.libs.data.DiaryObject
import kr.saintdev.pdiary.libs.data.makeMMDD
import kr.saintdev.pdiary.modules.db.DBM
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM

class DiaryAdapter : BaseAdapter() {
    private var items: ArrayList<DiaryObject> = arrayListOf()       // Data container

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = convertView ?: inflater.inflate(R.layout.item_dialog, parent, false)

        val dateView = v.findViewById<TextView>(R.id.item_dialog_date)
        val titleView = v.findViewById<TextView>(R.id.item_dialog_title)
        val removeButton = v.findViewById<ImageButton>(R.id.item_dialog_remove)
        val item = items[position]

        dateView.text = item.date.makeMMDD()
        titleView.text = item.question
        removeButton.setOnClickListener {
            removeItem(parent.context, position)
        }

        return v
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size

    fun refreshItems(items: ArrayList<DiaryObject>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeItem(context: Context, idx: Int) {
        DiaryDBM.removeDiary(DBM.getDB(context), items[idx].uniqid)
        items.removeAt(idx)
        notifyDataSetChanged()
    }
}