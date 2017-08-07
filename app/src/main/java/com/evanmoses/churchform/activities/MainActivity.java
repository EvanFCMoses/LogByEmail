package com.evanmoses.churchform.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.evanmoses.churchform.DayRowListAdapter;
import com.evanmoses.churchform.R;
import com.evanmoses.churchform.dao.DayReportDao;
import com.evanmoses.churchform.fragments.ChangeMonthDialog;
import com.evanmoses.churchform.fragments.InsertInfoFragment;
import com.evanmoses.churchform.objects.DayReport;

import java.util.ArrayList;
import java.util.HashMap;

import static com.evanmoses.churchform.constants.MultiColumnListNames.FIRST_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.FOURTH_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.SECOND_COLUMN;
import static com.evanmoses.churchform.constants.MultiColumnListNames.THIRD_COLUMN;

public class MainActivity extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private ArrayList<HashMap<String, String>> filled_list;
    private ArrayList<DayReport> dayReports;
    private DayRowListAdapter adapter;
    private DayReportDao dayReportDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        context = activity.getBaseContext();

        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        dayReportDao = new DayReportDao(context);

        String currentMonth = sharedPref.getString("CurrentMonth","January");
        String currentYear = sharedPref.getString("CurrentYear","2017");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentYear + " - " + currentMonth);
        setSupportActionBar(toolbar);
        ListView listView=(ListView)findViewById(R.id.main_list_view);
        filled_list = new ArrayList<>();
        dayReports = new ArrayList<>();

       adapter = new DayRowListAdapter(this, filled_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(MainActivity.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton add_new_button = (FloatingActionButton)findViewById(R.id.add_row_floating_button);
        add_new_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = InsertInfoFragment.newInstance();
                newFragment.show(ft, "dialog");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_change_month) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = ChangeMonthDialog.newInstance();
            newFragment.show(ft, "dialog");
        }else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populate_row(String date, String place, String information, String mileage){

        DayReport toAdd = new DayReport(Integer.parseInt(date),place, information, Integer.parseInt(mileage));
        dayReportDao.insert(toAdd);


        dayReports.add(toAdd);

        HashMap<String,String> temp=new HashMap<>();
        temp.put(FIRST_COLUMN, date);
        temp.put(SECOND_COLUMN, toAdd.location_concat);
        temp.put(THIRD_COLUMN, toAdd.information_concat);
        temp.put(FOURTH_COLUMN, mileage);
        filled_list.add(temp);

        adapter.notifyDataSetChanged();

    }

    public void changeMonth(String month, int currentYear){
        saveRecords();
        filled_list.clear();
        adapter.notifyDataSetChanged();
        loadRecords(month);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentYear + " - " + month);

        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CurrentMonth",month);
        editor.putString("CurrentYear",currentYear+"");
        editor.apply();

    }

    public void saveRecords(){
        for(int i = 0; i<dayReports.size(); i++){
            dayReportDao.insert(dayReports.get(i));
        }
    }

    public void loadRecords(String month){
        //TODO
        String selectionString;
        String[] selectionArgs;

        

        dayReports = dayReportDao.get(,)
    }

    public void showDialog() {
        DialogFragment newFragment = InsertInfoFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
}
