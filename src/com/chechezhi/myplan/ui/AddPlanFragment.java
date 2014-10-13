package com.chechezhi.myplan.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.chechezhi.myplan.R;
import com.chechezhi.myplan.db.DBManager;

public class AddPlanFragment extends DialogFragment {
    private EditText mPlanContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View whole = inflater.inflate(R.layout.fragment_add_plan, null);
        mPlanContent = (EditText) whole.findViewById(R.id.add_plan_edit);
        whole.findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBManager dbManager = DBManager.getInstance(getActivity());
                String content = mPlanContent.getText().toString();

                DBManager.Record r = new DBManager.Record();
                r.content = content;
                r.createDate = (int) (System.currentTimeMillis() / 1000);
                r.description = "";
                r.finished = 0;
                r.modificationDate = -1;
                r.title = content;
                long result = dbManager.add(r);
                dismiss();
                if (result > 0) {
                    Toast.makeText(getActivity(), R.string.added, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.add_failed, Toast.LENGTH_SHORT).show();
                }
                ((MainActivity)getActivity()).notifyDataChange();
            }
        });
        whole.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().setTitle(R.string.add_plan);
        return whole;
    }

}
