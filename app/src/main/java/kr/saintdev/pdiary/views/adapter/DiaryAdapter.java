package kr.saintdev.pdiary.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.func.CalendarFunc;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM;

public class DiaryAdapter extends BaseAdapter {
    private ArrayList<DiaryObject> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DiaryObject getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, final ViewGroup p) {
        LayoutInflater inflater = (LayoutInflater) p.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_dialog, p, false);

        TextView dateView = v.findViewById(R.id.item_dialog_date);
        TextView titleView = v.findViewById(R.id.item_dialog_title);
        ImageView removeButton = v.findViewById(R.id.item_dialog_remove);
        DiaryObject item = items.get(pos);

        dateView.setText(CalendarFunc.makeMMDD(item.getDate()));
        titleView.setText(item.getQuestion());
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(p.getContext(), pos);
            }
        });

        return v;
    }

    private void removeItem(Context context, int idx) {
        DiaryDBM.removeDiary(DBM.getDB(context), items.get(idx).getUniqid());
        items.remove(idx);
        notifyDataSetChanged();
    }

    public void refreshItems(ArrayList<DiaryObject> items) {
        this.items = items;
    }
}
