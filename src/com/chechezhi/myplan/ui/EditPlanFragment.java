package com.chechezhi.myplan.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.chechezhi.myplan.R;
import com.chechezhi.myplan.db.DBHelper;
import com.chechezhi.myplan.db.DBManager;

public class EditPlanFragment extends DialogFragment {
    private EditText mPlanContent;
    private int mId;
    private CheckBox mFinished;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mId = getArguments().getInt("KEY_DATABASE_ID");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View whole = inflater.inflate(R.layout.fragment_edit_plan, null);

        Cursor cursor = DBManager.getInstance(getActivity()).query(mId);
        cursor.moveToNext();
        String title = cursor.getString(cursor.getColumnIndex(DBHelper.DB_COLUMN_TITLE));
        int finished = cursor.getInt(cursor.getColumnIndex(DBHelper.DB_COLUMN_FINISHED));
        final long createdDate = cursor.getLong(cursor.getColumnIndex(DBHelper.DB_COLUMN_CREATE_DATE));
        cursor.close();

        mFinished = (CheckBox) whole.findViewById(R.id.cb_finished);
        if (finished > 0) {
            mFinished.setChecked(true);
        } else {
            mFinished.setChecked(false);
        }

        mPlanContent = (EditText) whole.findViewById(R.id.add_plan_edit);
        mPlanContent.setText(title);

        whole.findViewById(R.id.btn_modify).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBManager dbManager = DBManager.getInstance(getActivity());
                String content = mPlanContent.getText().toString();

                DBManager.Record r = new DBManager.Record();
                r.content = content;
                r.createDate = createdDate;
                r.modificationDate = System.currentTimeMillis();
                r.description = "";
                if (mFinished.isChecked()) {
                    r.finished = 1;
                } else {
                    r.finished = 0;
                }
                r.title = content;
                long result = dbManager.update(mId, r);
                dismiss();
                if (result > 0) {
                    Toast.makeText(getActivity(), R.string.added, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.add_failed, Toast.LENGTH_SHORT).show();
                }
                ((MainActivity) getActivity()).notifyDataChange();
            }
        });
        whole.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        whole.findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (DBManager.getInstance(getActivity()).delete(mId) > 0) {
                    Toast.makeText(getActivity(), R.string.deleted, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.delete_failed, Toast.LENGTH_SHORT).show();
                }
                ((MainActivity) getActivity()).notifyDataChange();
            }
        });
        getDialog().setTitle(R.string.edit_plan);
        return whole;
    }

}
