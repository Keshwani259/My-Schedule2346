package com.example.my_schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

  import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
 import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView recycleView;
    private ArrayList<String> phoneNumber, name, calendar, time, e1, e2, e3;
    com.example.myapplication.DataBase dataBase;

    //private MyAdapter adapter;
    DatabaseReference databaseReference;
    //  databas = FirebaseDatabase.getInstance().getReference("/");
    // databas = FirebaseDatabase.getInstance().getReference().child("Add");


    FloatingActionButton buttonf;


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(MainActivity2.this, MainActivity3.class));
//        finish();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dataBase= new com.example.myapplication.DataBase(this);
        name = new ArrayList<>();
        calendar = new ArrayList<>();
        e1 = new ArrayList<>();
        displayAllDat();
        buttonf = findViewById(R.id.fab);
        recycleView = findViewById(R.id.recycleview);
        adaptetr adaptetr = new adaptetr(this,name, calendar, e1);
        recycleView.setAdapter(adaptetr);
        recycleView.setLayoutManager(new LinearLayoutManager(this));




        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
// bekar names rakhna band kar

        buttonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);
            }
        });


    }


    void displayAllDat(){
        Cursor cuor= dataBase.readall();
        if(dataBase == null){
            Toast.makeText(this, "No ddata", Toast.LENGTH_LONG);

        }
        else{
            while(cuor.moveToNext()){
                name.add(cuor.getString(1));
                calendar.add(cuor.getString(4));
                e1.add(cuor.getString(2));

            }}}}



