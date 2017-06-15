package com.evanmoses.churchform;

import static com.evanmoses.churchform.constants.MultiColumnListNames.FIRST_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.FOURTH_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.SECOND_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.THIRD_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DayRowListAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;
    public DayRowListAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.day_line_filled_out, null);

            txtFirst=(TextView) convertView.findViewById(R.id.finalized_date_of_work);
            txtSecond=(TextView) convertView.findViewById(R.id.finalized_place_of_labor);
            txtThird=(TextView) convertView.findViewById(R.id.finalized_ministry_information);
            txtFourth=(TextView) convertView.findViewById(R.id.finalized_mileage);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));

        return convertView;
    }

}
