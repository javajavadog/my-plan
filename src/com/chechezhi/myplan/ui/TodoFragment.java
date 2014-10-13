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
import com.chechezhi.myplan.ui.TodoFragment.TodoAdapter.Holder;

public class TodoFragment extends Fragment {
    private TodoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View whole = inflater.inflate(R.layout.fragment_todo, null);

        Cursor c = DBManager.getInstance(getActivity()).query();
        
        mAdapter = new TodoAdapter(getActivity(), c, true);

        ListView todoList = (ListView) whole.findViewById(R.id.todo_list);
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
        Cursor c = DBManager.getInstance(getActivity()).query();
        mAdapter.changeCursor(c);
        mAdapter.notifyDataSetChanged();
    }

    static class TodoAdapter extends CursorAdapter {
        private SimpleDateFormat mFormat;

        public TodoAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            mFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View whole = LayoutInflater.from(context).inflate(R.layout.row_todo, null);
            TextView title = (TextView) whole.findViewById(R.id.row_todo_title);
            TextView subTitle02 = (TextView) whole.findViewById(R.id.row_todo_subtitle02);
            
            Holder h = new Holder();
            h.title = title;
            h.subTitle02 = subTitle02;
            
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
            h.subTitle02.setText(createdTime);
            
        }
        
        static class Holder {
            int databaseId;
            TextView title;
            TextView subTitle01;
            TextView subTitle02;
        }
    }

}
