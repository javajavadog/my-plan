package com.chechezhi.myplan.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chechezhi.myplan.R;
import com.chechezhi.myplan.db.DBHelper;
import com.chechezhi.myplan.db.DBManager;

public class TodoFragment extends Fragment {
    private TodoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View whole = inflater.inflate(R.layout.fragment_todo, null);

        Cursor c = DBManager.getInstance(getActivity()).query();
        mAdapter = new TodoAdapter(getActivity(), c, true);

        ListView todoList = (ListView) whole.findViewById(R.id.todo_list);
        todoList.setAdapter(mAdapter);
        return whole;
    }
    
    public void notifyListChange(){
        mAdapter.notifyDataSetChanged();
    }

    static class TodoAdapter extends CursorAdapter {

        public TodoAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
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
            
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.DB_COLUMN_TITLE));
            int createDate = cursor.getInt(cursor.getColumnIndex(DBHelper.DB_COLUMN_CREATE_DATE));
            
            h.title.setText(title);
            h.subTitle02.setText(String.valueOf(createDate));
        }
        
        static class Holder {
            TextView title;
            TextView subTitle01;
            TextView subTitle02;
        }
    }

}
