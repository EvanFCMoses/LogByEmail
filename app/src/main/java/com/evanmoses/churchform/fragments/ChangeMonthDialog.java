package com.evanmoses.churchform.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.evanmoses.churchform.R;
import com.evanmoses.churchform.activities.MainActivity;

/**
 * Created by Evan on 5/7/2017.
 *
 * this is to pop up and allow the user to change the current month thye are looking at
 *
 */

public class ChangeMonthDialog extends DialogFragment {

    private View v;
    static public ChangeMonthDialog newInstance() {
        ChangeMonthDialog f = new ChangeMonthDialog();

        // Supply num input as an argument.
        /*Bundle args = new Bundle();
        args.putInt("num", num);*/
        f.setArguments(null);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.change_month_dialog, container, false);


        Spinner spinny = (Spinner) v.findViewById(R.id.change_month_spinner);
        String[] monthNames = getResources().getStringArray(R.array.month_names);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, monthNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinny.setAdapter(spinnerArrayAdapter);

        NumberPicker numbPick = (NumberPicker) v.findViewById(R.id.change_month_year_number_picker);
        numbPick.setMinValue(2016);
        numbPick.setMaxValue(2100);

        Button button = (Button)v.findViewById(R.id.change_month_dialog_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v=getView();
                int month = ((Spinner)v.findViewById(R.id.change_month_spinner)).getSelectedItemPosition();
                String monthName = getResources().getStringArray(R.array.month_names)[month];
                NumberPicker numbnum = (NumberPicker)v.findViewById(R.id.change_month_year_number_picker);
                String yearNumber = ""+numbnum.getValue();
                ((MainActivity)getActivity()).changeMonth(monthName,Integer.parseInt(yearNumber));
                dismiss();
            }
        });

        return v;
    }
}
