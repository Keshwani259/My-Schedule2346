package com.example.my_schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.events.Event;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    Button save_button;
   // Event om;
    TextView Time, TextViewCal;
    EditText contacteditext, NameEdittext, Event1, Event2, Event3;
    //EditText NameEdittext;
  //  int mYear, mMonth, mDay, mHour, mMinutee;
    int t2Hour, t2Minute;
    DataBase dataBase;
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
        initDatePicker();
        contacteditext = findViewById(R.id.contactEDittext);
        NameEdittext = findViewById(R.id.NameEdtext);
        TextViewCal = findViewById(R.id.textView5);
        TextViewCal.setText(getTodaysDate());
        Time = findViewById(R.id.textView4);
        Event1 = findViewById(R.id.event1);
        Event2 = findViewById(R.id.event2);
        Event3 = findViewById(R.id.event3);
        save_button = findViewById(R.id.button2);
        dataBase = new DataBase(this);

       // databaseUsers = FirebaseDatabase.getInstance().getReference();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { InsertData(); }
        });




     //   // databas = FirebaseDatabase.getInstance().getReference().child("Add");

//        BroadcastReceiver myLocalBroadcastReciever = (context, intent) -> {
//            String result = intent.getStringExtra("result");
//            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        };


//        //Whatsapp number exist or not
//        private void handleActionWHATSAPP(String message, String count, String[] mobile_number ){
//            try {
//                PackageManager packageManager = getApplicationContext().getPackageManager();
//                if(mobile_number.length!=0){
//                    for(int j = 0; j < mobile_number.length; j++){
//                        for(int i = 0; i<Integer.parseInt(count.toString()); i++){
//                            String number = mobile_number[j];
//                            String url = "https://api.whatsapp.com?phone="+number+"&text="+ URLEncoder.encode(message, "UTF-8");
//                            Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
//                            whatappIntent.setPackage("com.whatsapp");
//                            whatappIntent.setData(Uri.parse(url));
//                            whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            if(whatappIntent.resolveActivity(packageManager)!=null) {
//                                getApplicationContext().startActivity(whatappIntent);
//                                Thread.sleep(10000);
//                                sendBroadcastMessage("Result: " + number);
//                            }else {
//                                sendBroadcastMessage("Result: WhatsApp not installed");
//                            }
//                        }
//                    }
//                }
//            }catch (Exception e){
//                sendBroadcastMessage("Result: "+e.toString());
//            }
//        }


//        Intent intent = getIntent();
//        String s = intent.getStringExtra("key");
//        Intent inte = new Intent(this, MainActivity2.class);
//        save_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databas = FirebaseDatabase.getInstance().getReference().child("/");
//                Modalclass om = new Modalclass(NameEdittext.getText().toString(),
//                        contacteditext.getText().toString(),
//                        TextViewCal.getText().toString(),
//                        textView.getText().toString(),
//                        Event1.getText().toString(),
//                        Event2.getText().toString(),
//                        Event3.getText().toString());
//                databas.push().setValue(om).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(MainActivity3.this, "Saved Changes", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//                startActivity(inte);
//                finish();
//            }
//        });


        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity3.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                String time = t2Hour + ":" + t2Minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa");
                                    Time.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2Hour, t2Minute);
                timePickerDialog.show();

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

       // String id = databaseUsers.push().getKey();
//        boolean checkInsertData = DB.insertUserData(name, phoneNumber, calendar, time, e1, e2, e3);
//        if (checkInsertData == true){
//            Toast.makeText(this, "Inserted Data", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(this, "User data insertion failed", Toast.LENGTH_SHORT).show();
//        }

        //Modalclass user = new Modalclass(name, phoneNumber,calendar, time, e1, e2, e3);
        //databaseUsers.child("users").child(id).setValue(user)
          //      .addOnCompleteListener(new OnCompleteListener<Void>() {
            //        @Override
              //      public void onComplete(@NonNull Task<Void> task) {
                //        if (task.isSuccessful()){
                  //          Toast.makeText(MainActivity3.this, "User Details Inserted", Toast.LENGTH_SHORT).show();
                    //    }
                   // }
                //});

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String Date = makeDateString(dayOfMonth, month, year);
                TextViewCal.setText(Date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + "/" + getMonthFormat(month) + "/" + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "March";
        if(month == 4)
            return "April";
        if(month == 5)
            return "May";
        if(month == 6)
            return "June";
        if(month == 7)
            return "July";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sept";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //Default should never happen
        return "Jan";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
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
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());
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








    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        //button = findViewById(R.id.button);



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String d= et1.getText().toString();
//                String fd= et3.getText().toString();

//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//
//        });
//
//
//
//
//
//    }
//
//
//
//
//
//}