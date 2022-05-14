           package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSugarActivity extends AppCompatActivity {

    Drawable ic_insulin,ic_lantus,ic_food,ic_comment;
    ImageButton food,insulin,lantus,comment;

    boolean fdflag = false;
    boolean inslflag = false;
    boolean lantusflag = false;
    boolean cmmntflag = false;

    String fd="0";
    String insl ="0";
    String lnts="0";
    String cmmnt = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sugar);


        //TextView timeText = findViewById(R.id.timeText);
        TextView textNumbPicker = findViewById(R.id.textView3);
        TextView textRulerPicker = findViewById(R.id.textView);
        NumberPicker numberPickerSecond = findViewById(R.id.numberPickerSecond);
        NumberPicker numberPickerFirst = findViewById(R.id.numberPickerFirst);
        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        TextView timeDate = findViewById(R.id.timeText);




        numberPickerFirst.setMaxValue(9);
        numberPickerFirst.setMinValue(0);
        //numberPickerFirst.setTextSize();
        numberPickerFirst.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // блокируем появление клавиатуры

        numberPickerSecond.setMaxValue(30);
        numberPickerSecond.setMinValue(1);
        numberPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        // Установка даты и времени
        setInitialDateTime();




        numberPickerFirst.setOnValueChangedListener((picker, oldVal, newVal) ->
                textNumbPicker.setText(String.valueOf(numberPickerSecond.getValue()) +"."+String.valueOf(newVal)));

        numberPickerSecond.setOnValueChangedListener((picker, oldVal, newVal) ->
                textNumbPicker.setText(String.valueOf(newVal)+"."+String.valueOf(numberPickerFirst.getValue())));


        float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
        valueRulerPicker = valueRulerPicker/2;


        textRulerPicker.setText(String.valueOf(valueRulerPicker));



        ImageView backButton = findViewById(R.id.imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //Добавление данных в БД
        ImageButton addSugarButton = findViewById(R.id.addSugarButton);
        addSugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDB mydb = new MyDB(AddSugarActivity.this);

                mydb.addSugar(String.valueOf(dateAndTime.getTime()),
                        String.valueOf(timeDate.getText()),
                        Double.valueOf(textNumbPicker.getText().toString().trim()),
                        Double.valueOf(fd.trim()),
                        Double.valueOf(insl.trim()),
                        Double.valueOf(lnts.trim()),
                        cmmnt);


                Intent myIntent = new Intent(AddSugarActivity.this, MainActivity.class);
                AddSugarActivity.this.startActivity(myIntent);
                finish();
            }

        });


        EditText commentText = findViewById(R.id.commentText);
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                cmmnt = String.valueOf(s);
            }
        });




        rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                if (fdflag==true) {
                    fd = String.valueOf(valueRulerPicker);
                }if(inslflag==true){
                    insl = String.valueOf(valueRulerPicker);
                } if(lantusflag==true){
                    //int valueForDB = rulerValuePicker.getCurrentValue();
                    lnts = String.valueOf(valueRulerPicker);
                }
            }


            @Override
            public void onIntermediateValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                //textRulerPicker.setText(String.valueOf(rulerValuePicker.getCurrentValue()));
                textRulerPicker.setText(String.valueOf(valueRulerPicker));

            }
        });

        commentText.setVisibility(View.INVISIBLE);
        ImageButton food = (ImageButton) findViewById(R.id.food);
        food.callOnClick();

    }



    public void fd(View v){

        fdflag = true;
        inslflag = false;
        lantusflag = false;
        cmmntflag = false;

        food = (ImageButton) findViewById(R.id.food);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);


        insulin = (ImageButton) findViewById(R.id.insulin);
        lantus = (ImageButton) findViewById(R.id.lantus);
        comment = (ImageButton) findViewById(R.id.comment);


        ic_food.setTint(getResources().getColor(R.color.white));

        food.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_food));
        food.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button_black));

        insulin.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_insulin.setTint(getResources().getColor(R.color.black));
        insulin.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_insulin));

        lantus.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_lantus.setTint(getResources().getColor(R.color.black));
        lantus.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lantus));

        comment.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_comment.setTint(getResources().getColor(R.color.black));
        comment.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_comment));

        rulerpicker("fd");

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.textView);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText);
        commentText.setVisibility(View.INVISIBLE);


    }

    public void insln(View v){

        fdflag = false;
        inslflag = true;
        lantusflag = false;
        cmmntflag = false;

        insulin = (ImageButton) findViewById(R.id.insulin);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);


        food = (ImageButton) findViewById(R.id.food);
        lantus = (ImageButton) findViewById(R.id.lantus);
        comment = (ImageButton) findViewById(R.id.comment);


        ic_insulin.setTint(getResources().getColor(R.color.white));

        insulin.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_insulin));
        insulin.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button_black));

        food.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_food.setTint(getResources().getColor(R.color.black));
        food.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_food));

        lantus.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_lantus.setTint(getResources().getColor(R.color.black));
        lantus.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lantus));

        comment.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_comment.setTint(getResources().getColor(R.color.black));
        comment.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_comment));

        rulerpicker("insln");

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.textView);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText);
        commentText.setVisibility(View.INVISIBLE);

    }

    public void lnts(View v){

        fdflag = false;
        inslflag = false;
        lantusflag = true;
        cmmntflag = false;

        lantus = (ImageButton) findViewById(R.id.lantus);


        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        insulin = (ImageButton) findViewById(R.id.insulin);
        food = (ImageButton) findViewById(R.id.food);
        comment = (ImageButton) findViewById(R.id.comment);


        ic_lantus.setTint(getResources().getColor(R.color.white));

        lantus.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lantus));
        lantus.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button_black));

        food.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_food.setTint(getResources().getColor(R.color.black));
        food.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_food));

        insulin.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_insulin.setTint(getResources().getColor(R.color.black));
        insulin.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_insulin));

        comment.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_comment.setTint(getResources().getColor(R.color.black));
        comment.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_comment));

        rulerpicker("lnts");

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.textView);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText);
        commentText.setVisibility(View.INVISIBLE);

    }

    public void cmmnt(View v){
        comment = (ImageButton) findViewById(R.id.comment);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        insulin = (ImageButton) findViewById(R.id.insulin);
        food = (ImageButton) findViewById(R.id.food);
        lantus = (ImageButton) findViewById(R.id.lantus);


        ic_comment.setTint(getResources().getColor(R.color.white));

        comment.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_comment));
        comment.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button_black));

        food.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_food.setTint(getResources().getColor(R.color.black));
        food.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_food));

        insulin.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_insulin.setTint(getResources().getColor(R.color.black));
        insulin.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_insulin));

        lantus.setBackground(ContextCompat.getDrawable(this, R.drawable.style_button));
        ic_lantus.setTint(getResources().getColor(R.color.black));
        lantus.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lantus));

        //rulerpicker("cmmnt");

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        rulerValuePicker.setVisibility(View.INVISIBLE);
        TextView number = findViewById(R.id.textView);
        number.setVisibility(View.INVISIBLE);
        EditText commentText = findViewById(R.id.commentText);
        commentText.setVisibility(View.VISIBLE);

    }


    //ИЗМЕНЕНИЕ RULEPICKER ПРИ НАЖАТИИ КНОПОК

    public void rulerpicker(String btn){
        RulerValuePicker rulerpicker = findViewById(R.id.ruler_picker);

        switch (btn){
            case "fd":
                rulerpicker.setMinMaxValue(0,50);
                break;

            case "insln":
                rulerpicker.setMinMaxValue(0,100);
                break;

            case "lnts":
                rulerpicker.setMinMaxValue(0,120);
                break;

        }
    }





    //ДАТА И ВРЕМЯ
    //ДАТА И ВРЕМЯ ДО НАЖАТИЯ УСТАНАВЛИВАЕТСЯ В OVERRIDE В НАЧАЛЕ
    Calendar dateAndTime=Calendar.getInstance();


    public void setDate(View v) {
        new DatePickerDialog(AddSugarActivity.this,
                 d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(AddSugarActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        TextView numberDate = findViewById(R.id.dateText);
        TextView weekDate = findViewById(R.id.dayText);
        TextView yearDate = findViewById(R.id.yearText);

        TextView timeDate = findViewById(R.id.timeText);


        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeDate.setText(timeFormat.format(dateAndTime.getTime()));


        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        numberDate.setText(dateFormat.format(dateAndTime.getTime()));

        weekDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_WEEKDAY));


        yearDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                dateAndTime.get(Calendar.YEAR)|
                dateAndTime.get(Calendar.MONTH)));

    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };





}