package com.chechezhi.myplan.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chechezhi.myplan.R;
import com.chechezhi.myplan.db.DBHelper;
import com.chechezhi.myplan.db.DBManager;
import com.chechezhi.myplan.ui.FinishedFragment.FinishedAdapter.Holder;

public class FinishedFragment extends Fragment {

    private FinishedAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View whole = inflater.inflate(R.layout.fragment_finished, null);
        Cursor c = DBManager.getInstance(getActivity()).query(DBHelper.DB_COLUMN_FINISHED + " > 0");
        
        mAdapter = new FinishedAdapter(getActivity(), c, true);

        ListView todoList = (ListView) whole.findViewById(R.id.finished_list);
        todoList.setAdapter(mAdapter);
        todoList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Holder h = (Holder) view.getTag();
                EditPlanFragment f = new EditPlanFragment();
                Bundle b = new Bundle();
                b.putInt("KEY_DATABASE_ID", h.databaseId);
                f.setArguments(b);
                f.show(getActivity().getSupportFragmentManager(), "Edit Plan");
            }
        });
        return whole;
    }
    
    public void notifyListChange(){
        Cursor c = DBManager.getInstance(getActivity()).query(DBHelper.DB_COLUMN_FINISHED + " > 0");
        mAdapter.changeCursor(c);
        mAdapter.notifyDataSetChanged();
    }

    static class FinishedAdapter extends CursorAdapter {
        private SimpleDateFormat mFormat;

        public FinishedAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            mFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View whole = LayoutInflater.from(context).inflate(R.layout.row_finished, null);
            TextView title = (TextView) whole.findViewById(R.id.row_finished_title);
            TextView subTitle01 = (TextView) whole.findViewById(R.id.row_finished_subtitle01);
            TextView subTitle02 = (TextView) whole.findViewById(R.id.row_finished_subtitle02);
            TextView subTitle03 = (TextView) whole.findViewById(R.id.row_finished_subtitle03);
            TextView subTitle04 = (TextView) whole.findViewById(R.id.row_finished_subtitle04);
            
            Holder h = new Holder();
            h.title = title;
            h.subTitle01 = subTitle01;
            h.subTitle02 = subTitle02;
            h.subTitle03 = subTitle03;
            h.subTitle04 = subTitle04;
            
            whole.setTag(h);
            return whole;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Holder h = (Holder) view.getTag();
            
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.DB_COLUMN_TITLE));
            long createDate = cursor.getLong(cursor.getColumnIndex(DBHelper.DB_COLUMN_CREATE_DATE));
            Date d = new Date(createDate);
            String date = mFormat.format(d);
            
            h.databaseId = id;
            h.title.setText(title);
            String createdTime = context.getString(R.string.created_at) + " " + date;
            h.subTitle01.setText(createdTime);
            
            long modificationDate = cursor.getLong(cursor.getColumnIndex(DBHelper.DB_COLUMN_MODIFICATION_DATE));
            d = new Date(modificationDate);
            String finishedDate = mFormat.format(d);
            String finishedTime = context.getString(R.string.finished_at) + " " + finishedDate; 
            h.subTitle02.setText(finishedTime);
        }
        
        static class Holder {
            int databaseId;
            TextView title;
            TextView subTitle01;
            TextView subTitle02;
            TextView subTitle03;
            TextView subTitle04;
        }
    }

}
