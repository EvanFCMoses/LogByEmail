package com.evanmoses.churchform.objects;

/**
 * Created by Evan on 4/30/2017.
 *
 * shall be used as the representation for a single day.
 */

public class DayReport {
    public int date;
    public int mileage;
    public String information;
    public String location;
    public String information_concat;
    public String location_concat;

    public DayReport(){
    }

    public DayReport(int date_entered, String place_entered, String information_entered, int mileage_entered){
        date = date_entered;
        mileage = mileage_entered;
        information= information_entered;
        location = place_entered;
        if(information.length()>20){
            information_concat = information.substring(0,20)+"...";
        }else{
            information_concat = information;
        }
        if(location.length()>7){
            location_concat = location.substring(0,10)+"...";
        }else{
            location_concat = location;
        }

    }



}
