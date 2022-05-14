package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.TotalCaptureResult;
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
import android.widget.Toast;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateSugarActivity extends AppCompatActivity {

    Drawable ic_insulin,ic_lantus,ic_food,ic_comment;
    ImageButton food,insulin,lantus,comment;

    boolean fdflag = false;
    boolean inslflag = false;
    boolean lantusflag = false;
    boolean cmmntflag = false;

    String id = "";
    String blood="0";
    String fd="0";
    String insl ="0";
    String lnts="0";
    String cmmnt = "";
    String date = "";
    String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sugar);

        TextView textNumbPicker = findViewById(R.id.textView32);
        TextView textRulerPicker = findViewById(R.id.text2View2);
        NumberPicker numberPickerSecond = findViewById(R.id.numberPickerSecond2);
        NumberPicker numberPickerFirst = findViewById(R.id.numberPickerFirst2);
        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker2);
        ImageButton addSugar = findViewById(R.id.addSugarButton2);
        TextView timeDate = findViewById(R.id.timeText2);
        TextView numberDate = findViewById(R.id.dateText2);

        //передача всех данных
        getAndSetIntentData();

        textNumbPicker.setText(blood);
        textRulerPicker.setText(fd);

        ImageView backButton = findViewById(R.id.imageView9);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berforeFinish();
                finish();
            }
        });



        //Update SUGAR (NOT ADD SUGAR. ADDSUGAR = name of button)
        addSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //А только потом это
                MyDB myDB = new MyDB(UpdateSugarActivity.this);
                myDB.updateData(id,
                        String.valueOf(dateAndTime.getTime()),
                        String.valueOf(timeDate.getText()),
                        textNumbPicker.getText().toString().trim(),
                        fd,insl,lnts,cmmnt);

                Intent myIntent = new Intent(UpdateSugarActivity.this, MainActivity.class);
                UpdateSugarActivity.this.startActivity(myIntent);
                finish();
            }
        });



        // Установка даты и времени
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");


        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date date1 = dateBDformat.parse(date);
            numberDate.setText(dateFormat.format(date1.getTime()));

            timeDate.setText(timeFormat.format(date1.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Button deleteButton = findViewById(R.id.deleteButton2);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });



        numberPickerFirst.setMaxValue(9);
        numberPickerFirst.setMinValue(0);
        numberPickerFirst.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // блокируем появление клавиатуры

        numberPickerSecond.setMaxValue(30);
        numberPickerSecond.setMinValue(1);
        numberPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        numberPickerFirst.setOnValueChangedListener((picker, oldVal, newVal) ->
                textNumbPicker.setText(String.valueOf(numberPickerSecond.getValue()) +"."+String.valueOf(newVal)));

        numberPickerSecond.setOnValueChangedListener((picker, oldVal, newVal) ->
                textNumbPicker.setText(String.valueOf(newVal)+"."+String.valueOf(numberPickerFirst.getValue())));
        


        EditText commentText = findViewById(R.id.commentText2);
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cmmnt = String.valueOf(s);

            }
        });



        //TextView textView13 = findViewById(R.id.textView13);
        rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                if (fdflag==true) {
                    //int valueForDB = rulerValuePicker.getCurrentValue();
                    //textView13.setText("fd"+String.valueOf(valueRulerPicker));
                    fd = String.valueOf(valueRulerPicker);
                }if(inslflag==true){
                    //int valueForDB = rulerValuePicker.getCurrentValue();
                    //textView13.setText("insl"+String.valueOf(valueRulerPicker));
                    insl = String.valueOf(valueRulerPicker);
                } if(lantusflag==true){
                    //int valueForDB = rulerValuePicker.getCurrentValue();
                    //textView13.setText("lnts"+String.valueOf(valueRulerPicker));
                    lnts = String.valueOf(valueRulerPicker);
                }
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {

                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                textRulerPicker.setText(String.valueOf(valueRulerPicker));

            }
        });

        commentText.setVisibility(View.INVISIBLE);

        ImageButton food = (ImageButton) findViewById(R.id.food2);
        food.callOnClick();
    }



    public void fd(View v){

        fdflag = true;
        inslflag = false;
        lantusflag = false;
        cmmntflag = false;

        food = (ImageButton) findViewById(R.id.food2);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);


        insulin = (ImageButton) findViewById(R.id.insulin2);
        lantus = (ImageButton) findViewById(R.id.lantus2);
        comment = (ImageButton) findViewById(R.id.comment2);


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

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker2);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.text2View2);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText2);
        commentText.setVisibility(View.INVISIBLE);


    }

    public void insln(View v){

        fdflag = false;
        inslflag = true;
        lantusflag = false;
        cmmntflag = false;

        insulin = (ImageButton) findViewById(R.id.insulin2);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);


        food = (ImageButton) findViewById(R.id.food2);
        lantus = (ImageButton) findViewById(R.id.lantus2);
        comment = (ImageButton) findViewById(R.id.comment2);


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

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker2);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.text2View2);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText2);
        commentText.setVisibility(View.INVISIBLE);

    }

    public void lnts(View v){

        fdflag = false;
        inslflag = false;
        lantusflag = true;
        cmmntflag = false;

        lantus = (ImageButton) findViewById(R.id.lantus2);


        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        insulin = (ImageButton) findViewById(R.id.insulin2);
        food = (ImageButton) findViewById(R.id.food2);
        comment = (ImageButton) findViewById(R.id.comment2);


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

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker2);
        rulerValuePicker.setVisibility(View.VISIBLE);
        TextView number = findViewById(R.id.text2View2);
        number.setVisibility(View.VISIBLE);
        EditText commentText = findViewById(R.id.commentText2);
        commentText.setVisibility(View.INVISIBLE);

    }

    public void cmmnt(View v){
        comment = (ImageButton) findViewById(R.id.comment2);

        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        insulin = (ImageButton) findViewById(R.id.insulin2);
        food = (ImageButton) findViewById(R.id.food2);
        lantus = (ImageButton) findViewById(R.id.lantus2);


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


        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker2);
        rulerValuePicker.setVisibility(View.INVISIBLE);
        TextView number = findViewById(R.id.text2View2);
        number.setVisibility(View.INVISIBLE);
        EditText commentText = findViewById(R.id.commentText2);
        commentText.setVisibility(View.VISIBLE);

    }

    public void berforeFinish(){


        ic_insulin = ContextCompat.getDrawable(this,R.drawable.ic_insulin);
        ic_food = ContextCompat.getDrawable(this,R.drawable.ic_food);
        ic_lantus = ContextCompat.getDrawable(this,R.drawable.ic_lantus);
        ic_comment = ContextCompat.getDrawable(this,R.drawable.ic_comment);

        ic_insulin.setTint(getResources().getColor(R.color.black));
        ic_food.setTint(getResources().getColor(R.color.black));
        ic_lantus.setTint(getResources().getColor(R.color.black));
        ic_comment.setTint(getResources().getColor(R.color.black));

    }


    //ИЗМЕНЕНИЕ RULEPICKER ПРИ НАЖАТИИ КНОПОК

    public void rulerpicker(String btn){
        RulerValuePicker rulerpicker = findViewById(R.id.ruler_picker2);
        ImageButton food1 = findViewById(R.id.food);
        switch (btn){
            case "fd":
                rulerpicker.setMinMaxValue(0,20);
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
        new DatePickerDialog(UpdateSugarActivity.this,
                d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(UpdateSugarActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        TextView numberDate = findViewById(R.id.dateText2);
        TextView weekDate = findViewById(R.id.dayText2);
        TextView yearDate = findViewById(R.id.yearText2);

        TextView timeDate = findViewById(R.id.timeText2);


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






    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")){

            //getting data from intent

            id = getIntent().getStringExtra("id");
            blood = getIntent().getStringExtra("blood");
            fd = getIntent().getStringExtra("food");
            insl = getIntent().getStringExtra("insulin");
            lnts = getIntent().getStringExtra("lantus");
            cmmnt = getIntent().getStringExtra("cmmnt");
            time = getIntent().getStringExtra("time");
            date = getIntent().getStringExtra("data");

            ///Это удали
            //TextView textView13 = findViewById(R.id.textView13);
            //textView13.setText( date+" "+time+" "+blood+" "+fd+" "+insl+" "+lnts+" "+cmmnt);


            //setting intent data
            //textNumbPicker.getText().toString().trim();


        }else {
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить " + blood + " ?");
        builder.setMessage("Вы точно хотите удалить " + blood + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDB myDB = new MyDB(UpdateSugarActivity.this);
                myDB.deleteOneRow(id);
                berforeFinish();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
