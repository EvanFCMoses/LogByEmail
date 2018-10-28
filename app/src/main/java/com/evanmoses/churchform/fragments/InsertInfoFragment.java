package com.evanmoses.churchform.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.evanmoses.churchform.R;
import com.evanmoses.churchform.activities.MainActivity;
import com.evanmoses.churchform.dao.DayReportDao;
import com.evanmoses.churchform.objects.DayReport;

/**
 * Created by Evan on 4/26/2017.
 *
 */

public class InsertInfoFragment extends DialogFragment
{
    DayReportDao dayReportDao;

    View v;
    static public InsertInfoFragment newInstance() {
        InsertInfoFragment f = new InsertInfoFragment();

        // Supply num input as an argument.
        /*Bundle args = new Bundle();
        args.putInt("num", num);*/
        f.setArguments(null);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dayReportDao = new DayReportDao(getActivity().getBaseContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.insert_info_fragment, container, false);


        Button button = (Button)v.findViewById(R.id.add_day_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v=getView();
                String date = ((EditText)v.findViewById(R.id.entering_date)).getText().toString();
                String place = ((EditText)v.findViewById(R.id.entering_place_of_labor)).getText().toString();
                String information = ((EditText)v.findViewById(R.id.entering_daily_ministry_information)).getText().toString();
                String mileage = ((TextView)v.findViewById(R.id.entering_mileage)).getText().toString();
                ((MainActivity)getActivity()).insert_row(date, place, information, mileage);
                dismiss();
            }
        });

        return v;
    }
}
