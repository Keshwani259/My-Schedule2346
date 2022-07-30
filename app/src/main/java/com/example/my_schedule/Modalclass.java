package com.example.my_schedule;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties

public class Modalclass {
    String Name;
    String PhoneNo;
    String Calendar;
    String Time;
    String E1;
    String E2;
    String E3;

    public Modalclass(){

    }

    public Modalclass(String name, String phoneNo, String calendar, String time, String e1, String e2, String e3) {
        Name = name;
        PhoneNo = phoneNo;
        Calendar = calendar;
        Time = time;
        E1 = e1;
        E2 = e2;
        E3 = e3;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getCalendar() {
        return Calendar;
    }

    public void setCalendar(String calendar) {
        Calendar = calendar;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getE1() {
        return E1;
    }

    public void setE1(String e1) {
        E1 = e1;
    }

    public String getE2() {
        return E2;
    }

    public void setE2(String e2) {
        E2 = e2;
    }

    public String getE3() {
        return E3;
    }

    public void setE3(String e3) {
        E3 = e3;
    }
}
