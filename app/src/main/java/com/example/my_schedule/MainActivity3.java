package com.example.my_schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.DataBase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    Button save_button;
   // Event om;
    TextView Time, TextViewCal;
    EditText contacteditext, NameEdittext, Event1, Event2, Event3;
    //EditText NameEdittext;
    int mYear, mMo, mDy, mHur, mMinutee;
    int t2Ho, t2Minutem;
    DataBase dataBase;
    private String[]  PERMUISSION;




    private DatePickerDialog.OnDateSetListener dateSeListenr;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;



    Calendar caldeen;
     Calendar myCalendar;

    //DatabaseHandler DB;

    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int CONTACT_PICK_CODE = 2;
   // DatabaseReference databas;
  // DatabaseReference databaseUsers;
    private BroadcastReceiver context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        myCalendar = Calendar.getInstance();
        contacteditext = findViewById(R.id.contactEDittext);
        NameEdittext = findViewById(R.id.NameEdtext);
        TextViewCal = findViewById(R.id.textView5);
        Time = findViewById(R.id.textView4);
        Event1 = findViewById(R.id.event1);
        Event2 = findViewById(R.id.event2);
        Event3 = findViewById(R.id.event3);
        save_button = findViewById(R.id.button2);
        dataBase = new com.example.myapplication.DataBase(this);
        Calendar mCalendar ;
        mCalendar = Calendar.getInstance();

        PERMUISSION = new String[]{
                Manifest.permission.SEND_SMS
        };



        // databaseUsers = FirebaseDatabase.getInstance().getReference();
        caldeen = Calendar.getInstance();
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { InsertData();
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                caldeen.set(Calendar.MONTH, mMo);
//                caldeen.set(Calendar.YEAR, mYear);
//                caldeen.set(Calendar.DAY_OF_MONTH, mDy);
                Date date= new Date();
                Calendar cal_aLarm = Calendar.getInstance();




                Calendar cal_now =  Calendar.getInstance();
                cal_now.setTime(date);
                cal_aLarm.setTime(date);
                cal_aLarm.set(Calendar.MONTH, mMo);
                cal_aLarm.set(Calendar.YEAR, mYear);
                cal_aLarm.set(Calendar.DAY_OF_MONTH, mDy);
                cal_aLarm.set(Calendar.HOUR_OF_DAY, t2Ho);
                cal_aLarm.set(Calendar.MINUTE, t2Minutem);

                if (!hasPermissio(MainActivity3.this, PERMUISSION)){
                    ActivityCompat.requestPermissions(MainActivity3.this, PERMUISSION, CONTACT_PERMISSION_CODE);
                }
                int fd = (int) Calendar.getInstance().getTimeInMillis();
                Intent i =  new Intent(MainActivity3.this, BroadCastReciever.class);
                i.putExtra("MyMessage",Event3.getText().toString());
                i.putExtra("phone", contacteditext.getText().toString());
                PendingIntent pendingInte =  PendingIntent.getBroadcast(MainActivity3.this, fd, i , PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_aLarm.getTimeInMillis(), pendingInte);

            }
        });





        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity3.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Ho = hourOfDay;
                                t2Minutem = minute;
                                String time = hourOfDay + ":" + minute;
                                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                mCalendar.set(Calendar.MINUTE, minute);
                                mCalendar.set(Calendar.SECOND, 0);
                                Time.setText(time);
                            }
                        }, mHur, mMinutee, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(mHur, mMinutee);
                timePickerDialog.show();
            }

        });
TextViewCal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear+1);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mMo = monthOfYear;
                mYear = year;
                mDy = dayOfMonth;

                TextViewCal.setText(dayOfMonth+" "+monthOfYear +" "+year);
            }
        };


        DatePickerDialog datePickerDialog=  new DatePickerDialog(MainActivity3.this,R.style.Theme_MySchedule, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
});



        NameEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkContactPermission()) {
                    //Permission Granted, pick contact
                    pickContactIntent();
                } else {
                    //Permission not granted, request
                    requestContactPermission();
                }
            }
        });
    }

    private  void  InsertData(){
        String phoneNumber  = contacteditext.getText().toString();
        String name  = NameEdittext.getText().toString();
        String calendar = TextViewCal.getText().toString();
        String time  = Time.getText().toString();
        String e1 = Event1.getText().toString();
        String e2 = Event2.getText().toString();
        String e3 = Event3.getText().toString();

        Boolean dd= dataBase.insertData(phoneNumber, name, calendar, time, e1, e2, e3);
        if(dd==true){
            Toast.makeText(MainActivity3.this,"Inserted Data",Toast.LENGTH_SHORT ).show();
        }
        else {
            Toast.makeText(MainActivity3.this,"User data insertion failed",Toast.LENGTH_SHORT ).show();


        }


    }

    public   boolean hasPermissio(Context context  , String... PERMISSSSION){
        if(context != null &&PERMISSSSION !=null){
            for (String permission : PERMISSSSION){
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }

            }
        }
        return true;
    }


    private boolean checkContactPermission() {
        //check if contact permission was granted or not
        boolean result = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED
        );
        return result; //true if permission granted, false if not
    }

    private void requestContactPermission() {
        //permissions to request
        String[] permission = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permission, CONTACT_PERMISSION_CODE);
    }


    private void pickContactIntent() {
        //Intent to pick contact
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
// App check

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //handle permission request result
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted, can pick a contact now
                pickContactIntent();
            } else {
                //Permission denied
                Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
            }
            if ( grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted, can pick a contact now
                Toast.makeText(this, "PErmission GR", Toast.LENGTH_LONG).show();
            } else {
                //Permission denied
                Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //handle intent results
        if (resultCode == RESULT_OK) {
            //calls when a user click a contact from list
            if (requestCode == CONTACT_PICK_CODE) {
                TextView contactTv;
                Cursor cursor1, cursor2;
                //get data from intent
                Uri uri = data.getData();
                cursor1 = getContentResolver().query(uri, null, null, null, null);
                if (cursor1.moveToFirst()) {
//get contact details
                    @SuppressLint("Range") String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    @SuppressLint("Range") String contactThumbnail = ((Cursor) cursor1).getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    @SuppressLint("Range") String idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);
                    NameEdittext.setText(contactName);
                    contacteditext.setText("\nName: " + contactName);
                    if (idResultHold == 1) {
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null
                        );
                        //a contact may have multiple phone numbers
                        while (cursor2.moveToNext()) {
// get phone number
                            @SuppressLint("Range") String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            // set details

                            contacteditext.setText("\nPhone: " + contactNumber);
                            //before setting image, check if have or not
//        if(contactThumbnail != null){
//        thumbnailIv.setImageURI(Uri.parse(contactThumbnail));
//        }
//        else{
//        thumbnailIv.setImageResource(R.drawable.ic_person);
//        }
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        } else {
            //calls when user clicks back button I don't pick contact
        }
        boolean dddd;

    }


}







//
//
//
//
//}