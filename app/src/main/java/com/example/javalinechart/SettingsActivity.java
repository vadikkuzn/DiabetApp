package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SettingsActivity extends AppCompatActivity {
    CardView editUserButton, exportTable;
    MyDB myDB;
    ArrayList<String> sugar_id,date, time, blood,food,insulin,lantus,comment;
    String monthYear ="";
    ImageView female,male;
    TextView textFIO,text2FIO,check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        myDB = new MyDB(SettingsActivity.this);

        editUserButton = findViewById(R.id.changeUser);
        text2FIO = findViewById(R.id.text2FIO);
        textFIO = findViewById(R.id.textFIO);
        female = findViewById(R.id.image_female);
        male = findViewById(R.id.image_male);
        check = findViewById(R.id.check);
        exportTable = findViewById(R.id.exportTable);
        female.setVisibility(View.INVISIBLE);
        male.setVisibility(View.INVISIBLE);

        getUserInformation();

        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, EditUserActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });


        exportTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate1();
            }

        });

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
                //Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                //SettingsActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView calculatorButton = findViewById(R.id.imageView2);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, CalculatorActivity.class);
                SettingsActivity.this.startActivity(myIntent);
                finish();
            }
        });


        ImageView alarmButton = findViewById(R.id.imageView3);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, AlarmActivity.class);
                SettingsActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton = findViewById(R.id.imageButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, AddSugarActivity.class);
                SettingsActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton1 = findViewById(R.id.imageView5);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, AddSugarActivity.class);
                SettingsActivity.this.startActivity(myIntent);
                finish();
            }
        });

        // end menu




        check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {



                storeDataInArrays();

                //String folderName = "Import Excel"; //check.getText().toString().trim()
                String folderName = check.getText().toString().trim();
                String fileName = folderName + System.currentTimeMillis() + ".xls";
                String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;



                try {

                    String filename = "/data/data/com.example.javalinechart/databases/"+check.getText().toString().trim()+".xls";
                    HSSFWorkbook workbook = new HSSFWorkbook();

                    HSSFSheet sheet = workbook.createSheet(String.valueOf(Calendar.MONTH));

                    HSSFRow rowhead = sheet.createRow((short) 0);

                    rowhead.createCell(0).setCellValue("id");
                    rowhead.createCell(1).setCellValue("Дата");
                    rowhead.createCell(2).setCellValue("Время");
                    rowhead.createCell(3).setCellValue("Сахар");
                    rowhead.createCell(4).setCellValue("Еда");
                    rowhead.createCell(5).setCellValue("Короткий инсулин");
                    rowhead.createCell(6).setCellValue("Длинный инсулин");
                    rowhead.createCell(7).setCellValue("Комментарий");



                    //Тут i==0 должно быть, а row i просто
                    for(int i=0;i<date.size();i++) {
                        HSSFRow row = sheet.createRow((short) i+1);

                        row.createCell(0).setCellValue(String.valueOf(sugar_id.get(i)));
                        row.createCell(1).setCellValue(String.valueOf(date.get(i)));
                        row.createCell(2).setCellValue(String.valueOf(time.get(i)));
                        row.createCell(3).setCellValue(String.valueOf(blood.get(i)));
                        row.createCell(4).setCellValue(String.valueOf(food.get(i)));
                        row.createCell(5).setCellValue(String.valueOf(insulin.get(i)));
                        row.createCell(6).setCellValue(String.valueOf(lantus.get(i)));
                        row.createCell(7).setCellValue(String.valueOf(comment.get(i)));
                    }


                    FileOutputStream fileOut = new FileOutputStream(filename);
                    workbook.write(fileOut);
//closing the Stream
                    fileOut.close();
//closing the workbook
                    workbook.close();
//prints the message on the console
                    System.out.println("Excel file has been generated successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }


    void storeDataInArrays() {


        sugar_id = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        blood = new ArrayList<>();
        food = new ArrayList<>();
        insulin = new ArrayList<>();
        lantus = new ArrayList<>();
        comment = new ArrayList<>();


        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("M yyyy");

        SimpleDateFormat dateFormatToExcel = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat exportFormatToExcel = new SimpleDateFormat("dd-MM-yyyy");


        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        } else {
            //запись данных в ArrayList
            while (cursor.moveToNext()) {
                String stringData = "";

                try {
                    Date date1 = dateBDformat.parse(cursor.getString(1));
                    stringData = dateFormat.format(date1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println(stringData);
                System.out.println(check.getText());

                System.out.println("STRING DATA "+ stringData);
                System.out.println("CHECK "+ check.getText() );

                if(stringData.equals(check.getText().toString().trim())){


                    Date date1 = null;
                    try {
                        date1 = dateBDformat.parse(cursor.getString(1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String strDate = dateFormatToExcel.format(date1.getTime());

                    System.out.println("1");

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

            //здесь отсортировать //sort //метод пузырька
            for (int i = 0; i < date.size(); i++) {
                for (int j = date.size() - 1; j > i; j--) {
                    if (Long.parseLong(date.get(j)) < Long.parseLong(date.get(j - 1))) {

                        String n = date.get(j);
                        String n2 = sugar_id.get(j);
                        String n3 = time.get(j);
                        String n4 = blood.get(j);
                        String n5 = food.get(j);
                        String n6 = insulin.get(j);
                        String n7 = lantus.get(j);
                        String n8 = comment.get(j);

                        date.set(j, date.get(j - 1));
                        sugar_id.set(j, sugar_id.get(j - 1));
                        time.set(j, time.get(j - 1));
                        blood.set(j, blood.get(j - 1));
                        food.set(j, food.get(j - 1));
                        insulin.set(j, insulin.get(j - 1));
                        lantus.set(j, lantus.get(j - 1));
                        comment.set(j, comment.get(j - 1));

                        date.set(j - 1, n);
                        sugar_id.set(j - 1, n2);
                        time.set(j - 1, n3);
                        blood.set(j - 1, n4);
                        food.set(j - 1, n5);
                        insulin.set(j - 1, n6);
                        lantus.set(j - 1, n7);
                        comment.set(j - 1, n8);

                    }
                }
            }

            //переформатирование даты в dd-mm-yyyy вместо yyyy-mm-dd
            Date data = null;
            for (int i = 0; i < date.size(); i++) {
                try {
                     data = dateFormatToExcel.parse(date.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                date.set(i,exportFormatToExcel.format(data));
            }

        }
    }





        //ДАТА И ВРЕМЯ
        Calendar dateAndTime = Calendar.getInstance();


        void setDate1() {


            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(SettingsActivity.this,
                    new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            monthYear = String.valueOf(selectedMonth + 1) + " " + String.valueOf(selectedYear);
                            check.setText(monthYear);
                        }
                    }, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH));



            builder.setMinYear(1960)
                    .setActivatedYear(dateAndTime.get(Calendar.YEAR))
                    .setActivatedMonth(dateAndTime.get(Calendar.MONTH))
                    .setMaxYear(2030)
                    .setMinMonth(Calendar.JANUARY)
                    .setTitle("Выберите месяц")
                    .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                    .build().show();


        }


        void getUserInformation(){

            String name ="";
            String lastname = "";
            String age ="";
            String gender ="";
            String diabetType ="";


            myDB = new MyDB(SettingsActivity.this);
            Cursor cursor = myDB.readAllUser();
            if(cursor.getCount() == 0){
                Toast.makeText(this,"Нет пользователя",Toast.LENGTH_SHORT).show();
                //Закинуть на страницу авторизации
            }else {
                while (cursor.moveToNext()){
                    name = cursor.getString(1);
                    lastname = cursor.getString(2);
                    age = cursor.getString(3);
                    gender = cursor.getString(4);
                    diabetType = cursor.getString(5);
                }
            }

            System.out.println(gender);
            if(gender.equals("M")){
                System.out.println("11111111");
                male.setVisibility(View.VISIBLE);
                female.setVisibility(View.INVISIBLE);
            }if(gender.equals("Ж")){
                female.setVisibility(View.VISIBLE);
                male.setVisibility(View.INVISIBLE);
            }

            textFIO.setText(String.valueOf(name +" "+ lastname));
            text2FIO.setText(diabetType+" Тип диабета, "+age+" лет, Пол: "+gender);


        }

}