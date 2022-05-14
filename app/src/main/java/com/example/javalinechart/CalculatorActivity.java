package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    CardView sugar, food,morningButton, dayButton, eveningButton;
    TextView morningText, dayText,eveningText, descriptionAnswer,calculatorAnswer,sugarNumber,foodNumber;
    MyDB myDB;
    ArrayList<String> dayList, morningList, eveningList, sensivityList;
    Double morningValue = 1.0,dayValue=1.0,eveningValue=1.0, sensivityValue=1.0,a;
    boolean flag1 = false; //это кнопки большие - сахар и еда


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        sugar = findViewById(R.id.sugarCard);
        food = findViewById(R.id.foodCard);


        morningButton = findViewById(R.id.buttonMorning);
        dayButton = findViewById(R.id.buttonDay);
        eveningButton = findViewById(R.id.buttonEvening);

        storeDataInArrays(); //чтобы до нажатий всех уже были данные

        sugar.setOnClickListener(this);
        food.setOnClickListener(this);
        morningButton.setOnClickListener(this);
        eveningButton.setOnClickListener(this);
        dayButton.setOnClickListener(this);

        dayButton.callOnClick();
        sugar.callOnClick();

        RulerValuePicker rulerValuePicker = findViewById(R.id.ruler_picker);
        TextView textRulerPicker = findViewById(R.id.textView);


        rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                updateText();

            }


            @Override
            public void onIntermediateValueChange(int selectedValue) {
                sugarNumber = findViewById(R.id.sugarText);
                foodNumber = findViewById(R.id.foodText);

                float valueRulerPicker = Float.valueOf(rulerValuePicker.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                textRulerPicker.setText(String.valueOf(valueRulerPicker));

                if(flag1==false){
                    sugarNumber.setText(String.valueOf(valueRulerPicker));


                }else {
                    foodNumber.setText(String.valueOf(valueRulerPicker));
                }


            }
        });


        ImageView backButton = findViewById(R.id.imageBack);
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
                //Intent myIntent = new Intent(CalculatorActivity.this, MainActivity.class);
                //CalculatorActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView alarmButton = findViewById(R.id.imageView3);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CalculatorActivity.this, AlarmActivity.class);
                CalculatorActivity.this.startActivity(myIntent);
                finish();
            }
        });


        ImageView settingButton = findViewById(R.id.imageView4);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CalculatorActivity.this, SettingsActivity.class);
                CalculatorActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton = findViewById(R.id.imageButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CalculatorActivity.this, AddSugarActivity.class);
                CalculatorActivity.this.startActivity(myIntent);
                finish();
            }
        });

        ImageView addButton1 = findViewById(R.id.imageView5);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CalculatorActivity.this, AddSugarActivity.class);
                CalculatorActivity.this.startActivity(myIntent);
                finish();
            }
        });

        // end menu


    }




    @Override
    public void onClick(View v) {
        sugar = findViewById(R.id.sugarCard);
        food = findViewById(R.id.foodCard);
        sugarNumber = findViewById(R.id.sugarText);
        foodNumber = findViewById(R.id.foodText);
        descriptionAnswer = findViewById(R.id.descriptionAnswer);

        calculatorAnswer=findViewById(R.id.calculatorAnswer);
        descriptionAnswer = findViewById(R.id.descriptionAnswer);
        descriptionAnswer.setText(morningValue+ " "+dayValue+" "+eveningValue);

        morningButton = findViewById(R.id.buttonMorning);
        dayButton = findViewById(R.id.buttonDay);
        eveningButton = findViewById(R.id.buttonEvening);

        morningText = findViewById(R.id.textMorning);
        dayText = findViewById(R.id.textDay);
        eveningText =  findViewById(R.id.textEvening);

        int flag = 0;


        if (v==food){


            sugarNumber.setTextColor(Color.BLACK);
            sugar.setCardBackgroundColor(Color.WHITE);

            foodNumber.setTextColor(Color.WHITE);
            food.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            flag1 = true;

        } if(v == sugar){


            sugarNumber.setTextColor(Color.WHITE);
            sugar.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));

            foodNumber.setTextColor(Color.BLACK);
            food.setCardBackgroundColor(Color.WHITE);
            flag1 = false;

        }
        if(v == morningButton){
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            //morningText.setTextColor(Color.BLACK);
            dayButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.white));

            flag = 1;


        } if(v == dayButton){

            dayButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.white));

            flag = 2;

        } if(v == eveningButton){
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            dayButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            flag = 3;
        }

        //double a;

        if(flag ==1){ a =morningValue; }
        else if(flag==2){ a= dayValue; }
        else { a = eveningValue; }


        updateText();


    }

    void storeDataInArrays(){
        dayList = new ArrayList<>();
        eveningList = new ArrayList<>();
        morningList = new ArrayList<>();
        sensivityList = new ArrayList<>();

        myDB = new MyDB(CalculatorActivity.this);
        Cursor cursor = myDB.readAllUser();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"Зарегистрируйте пользователя",Toast.LENGTH_SHORT).show();
            //Закинуть на страницу авторизации
        }else {
            while (cursor.moveToNext()){
                morningList.add(cursor.getString(8));
                dayList.add(cursor.getString(9));
                eveningList.add(cursor.getString(10));
                sensivityList.add(cursor.getString(11));
            }
        }

        morningValue = Double.valueOf(morningList.get(0));
        dayValue = Double.valueOf(dayList.get(0));
        eveningValue = Double.valueOf(eveningList.get(0));
        sensivityValue = Double.valueOf(sensivityList.get(0));

    }

    void updateText(){

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        if((Double.valueOf(sugarNumber.getText().toString()) - 5.5) >= 0){
            Double answer = (Double.valueOf(sugarNumber.getText().toString()) - 5.5) * sensivityValue +a * Double.valueOf(foodNumber.getText().toString());
            String result = decimalFormat.format(answer);
            calculatorAnswer.setText(result);

            descriptionAnswer.setText("("+sugarNumber.getText().toString() +"- 5.5) * "+sensivityValue +"+ "+ a +" * "+foodNumber.getText().toString());

        } else {
            Double answer = a * Double.valueOf(foodNumber.getText().toString()) -
                    Math.abs(Double.valueOf(sugarNumber.getText().toString()) - 5.5) * sensivityValue;
            String result = decimalFormat.format(answer);
            calculatorAnswer.setText(result);

            descriptionAnswer.setText(
                    a +" * "+ foodNumber.getText().toString()+" - |" +sugarNumber.getText().toString() +"- 5.5 | *"+ sensivityValue);

        }

    }

}