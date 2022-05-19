package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import me.mvdw.recyclerviewmergeadapter.adapter.RecyclerViewMergeAdapter;

public class AlarmActivity extends AppCompatActivity {

    MyDB myDB;
    ArrayList<String> sugar_id,date, time, blood, food, insulin, lantus, comment;
    CustomAdapter customAdapter;
    CustomCharapterAdapter customAdapter1;
    CustomSpaceAdapter customAdapterSpace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        berforeStart();

        myDB = new MyDB(AlarmActivity.this);

        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat exportFormat = new SimpleDateFormat("dd-MM-yyyy");

        TreeSet<String> uniqueData = new TreeSet<>();
        ArrayList<String> sortedData;


        RecyclerView recycleView = findViewById(R.id.recycleView22);

        ImageView backButton = findViewById(R.id.imageBack2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        //menu

        ImageView homeButton = findViewById(R.id.imageView);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(AlarmActivity.this, MainActivity.class);
                //AlarmActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView calculatorButton = findViewById(R.id.imageView2);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AlarmActivity.this, CalculatorActivity.class);
                AlarmActivity.this.startActivity(myIntent);
                finish();
            }
        });


        ImageView settingButton = findViewById(R.id.imageView4);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AlarmActivity.this, SettingsActivity.class);
                AlarmActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton = findViewById(R.id.imageButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AlarmActivity.this, AddSugarActivity.class);
                AlarmActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton1 = findViewById(R.id.imageView5);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AlarmActivity.this, AddSugarActivity.class);
                AlarmActivity.this.startActivity(myIntent);
                finish();
            }
        });

        // end menu


        Cursor cursor1 = myDB.readAllData();
        if(cursor1.getCount() == 0){
            Toast.makeText(this,"Нет данных",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor1.moveToNext()) {
                String stringData = "";

                try {
                    Date date1 = dateBDformat.parse(cursor1.getString(1));
                    stringData = exportFormat.format(date1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                uniqueData.add(stringData);
            }
            sortedData = new ArrayList<>(uniqueData);

            /*
            for(int i=0;i<sortedData.size();i++) {
                System.out.println(sortedData.get(i));
            }
            */

            RecyclerViewMergeAdapter mergeAdapter = new RecyclerViewMergeAdapter();


            for(int i=0;i<sortedData.size();i++) {
                sugar_id = new ArrayList<>();
                date = new ArrayList<>();
                time = new ArrayList<>();
                blood = new ArrayList<>();
                food = new ArrayList<>();
                insulin = new ArrayList<>();
                lantus = new ArrayList<>();
                comment = new ArrayList<>();


                customAdapter1 = new CustomCharapterAdapter(AlarmActivity.this,this,sortedData.get(i));
                recycleView.setAdapter(customAdapter1);
                recycleView.setLayoutManager(new LinearLayoutManager(AlarmActivity.this));

                Cursor cursor = myDB.readAllData();
                while (cursor.moveToNext()) {
                    String stringData = "", stringData1="";

                    try {
                        Date date1 = dateBDformat.parse(cursor.getString(1));
                        stringData = shortDateFormat.format(date1.getTime());

                        Date date11 = exportFormat.parse(sortedData.get(i));
                        stringData1 = shortDateFormat.format(date11.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if( stringData1.equals(stringData)) {

                        Date date1 = null;
                        try {
                            date1 = dateBDformat.parse(cursor.getString(1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String strDate = longDateFormat.format(date1.getTime());

                        sugar_id.add(cursor.getString(0));
                        date.add(String.valueOf(strDate));
                        time.add(cursor.getString(2));
                        blood.add(cursor.getString(3));
                        food.add(cursor.getString(4));
                        insulin.add(cursor.getString(5));
                        lantus.add(cursor.getString(6));
                        comment.add(cursor.getString(7));

                    }
                }




                for (int u = 0; u < date.size()-1; u++) {
                    for (int j = date.size() - 1; j > u; j--) {
                        if (Long.parseLong(date.get(j-1)) < Long.parseLong(date.get(j))) {


                            String n = date.get(j-1);
                            String n2 = sugar_id.get(j-1);
                            String n3 = time.get(j-1);
                            String n4 = blood.get(j-1);
                            String n5 = food.get(j-1);
                            String n6 = insulin.get(j-1);
                            String n7 = lantus.get(j-1);
                            String n8 = comment.get(j-1);

                            date.set(j-1, date.get(j));
                            sugar_id.set(j-1, sugar_id.get(j));
                            time.set(j-1, time.get(j));
                            blood.set(j-1, blood.get(j));
                            food.set(j-1, food.get(j));
                            insulin.set(j-1, insulin.get(j));
                            lantus.set(j-1, lantus.get(j));
                            comment.set(j-1, comment.get(j));

                            date.set(j, n);
                            sugar_id.set(j , n2);
                            time.set(j , n3);
                            blood.set(j , n4);
                            food.set(j , n5);
                            insulin.set(j , n6);
                            lantus.set(j , n7);
                            comment.set(j, n8);

                        }
                    }
                }






                customAdapter = new CustomAdapter(AlarmActivity.this,this,sugar_id,date,time,blood,food,insulin,lantus,comment);
                recycleView.setAdapter(customAdapter);
                recycleView.setLayoutManager(new LinearLayoutManager(AlarmActivity.this));

                customAdapterSpace = new CustomSpaceAdapter(AlarmActivity.this,this);
                recycleView.setAdapter(customAdapterSpace);
                recycleView.setLayoutManager(new LinearLayoutManager(AlarmActivity.this));


                mergeAdapter.addAdapter(customAdapter1);
                mergeAdapter.addAdapter(customAdapter);


            }

            mergeAdapter.addAdapter(customAdapterSpace);
            recycleView.setAdapter(mergeAdapter);
        }





    }


    public void berforeStart(){


        Drawable ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        Drawable ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        Drawable ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        Drawable ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        ic_insulin.setTint(getResources().getColor(R.color.black));
        ic_food.setTint(getResources().getColor(R.color.black));
        ic_lantus.setTint(getResources().getColor(R.color.black));
        ic_comment.setTint(getResources().getColor(R.color.black));

    }


}



