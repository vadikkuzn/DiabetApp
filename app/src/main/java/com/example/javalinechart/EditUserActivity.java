package com.example.javalinechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.card.MaterialCardView;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.warkiz.widget.IndicatorSeekBar;

public class EditUserActivity extends AppCompatActivity {
    private MaterialCardView cardView;
    //private IndicatorSeekBar seekBar, seekBar1, seekBar2;
    private RadioGroup gender, toglgl;
    private RulerValuePicker morningValue, day, evening, sensivity;
    private String id = "1", genderText = "M", diabet = "I", heightText = null, weighText = null;
    private LinearLayout layout, layout1, layout2, layout3, layout4, layout5, layout6, layout7, check, circle, circle2;
    private TextView morning, morning1, morning2, declarative,declarative1, text3, text4, textView5;
    private ConstraintLayout seekbar, seekbar1, seekbar2;
    private EditText name, lastName, age, height, weight;
    private RadioButton quantity1, quantity2, quantity3, quantity4;
    private ConstraintLayout constraintLayout6, constraintLayout5, constraintLayout, constraintLayout7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        textView5 = findViewById(R.id.text5);
        gender = findViewById(R.id.toggle);
        cardView = findViewById(R.id.cardView2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);
        circle = findViewById(R.id.circle);

        morningValue = findViewById(R.id.ruler_picker_utro);
        day = findViewById(R.id.ruler_picker_day);
        evening = findViewById(R.id.ruler_picker_evening);
        sensivity = findViewById(R.id.ruler_picker_sensivity);

        TextView textRulerPicker = findViewById(R.id.textView_utro);
        TextView textRulerPickerDay = findViewById(R.id.textView_day);
        TextView textRulerPickerEvening = findViewById(R.id.textView_evening);
        TextView textRulerPickerSensivity = findViewById(R.id.textView_sensivity);

        constraintLayout6 = findViewById(R.id.constraintLayout6);
        constraintLayout5 = findViewById(R.id.constraintLayout5);
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout7 = findViewById(R.id.constraintLayout7);

        //seekbar = findViewById(R.id.seekbar);
        //seekbar1 = findViewById(R.id.seekbar3);
        //seekbar2 = findViewById(R.id.seekbar2);

        toglgl = findViewById(R.id.toggl);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);


        morning = findViewById(R.id.utro);
        morning1 = findViewById(R.id.utro1);
        morning2 = findViewById(R.id.utro2);
        declarative = findViewById(R.id.decelerate);
        declarative1 = findViewById(R.id.decelerate1);
        check = findViewById(R.id.check);
        circle2 = findViewById(R.id.circle2);

        quantity1 = findViewById(R.id.search);
        quantity2 = findViewById(R.id.offer);
        quantity3 = findViewById(R.id.search1);
        quantity4 = findViewById(R.id.offerr);

        heightText = height.getText().toString().trim();
        weighText = weight.getText().toString().trim();

        //appDatabase = AlarmDatabase.getDatabase(this);

        //String indicator = String.valueOf(seekBar.getProgress());

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.search) {
                    Log.d("TAG", "onCheckedChanged: ");
                    genderText = quantity1.getText().toString().trim();
                } else if (i == R.id.offer) {
                    Log.d("TAG", "onCheckedChanged: ");
                    genderText = quantity2.getText().toString().trim();
                }
            }
        });

        toglgl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.search1) {
                    Log.d("TAG", "onCheckedChanged: ");
                    diabet = quantity3.getText().toString().trim();
                } else if (i == R.id.offerr) {
                    Log.d("TAG", "onCheckedChanged: ");
                    diabet = quantity4.getText().toString().trim();
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String heightText = "";
                String weightText = "";

                if (textView5.getText().toString().equals("Продолжить")) {
                    if (name.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getBaseContext(), "Введите имя", Toast.LENGTH_SHORT).show();
                    } else if (lastName.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Введи фамилию", Toast.LENGTH_SHORT).show();
                    } else if (age.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getBaseContext(), "Введите возраст", Toast.LENGTH_SHORT).show();
                    } else {
                        textView5.setText("Сохранить");
                        setInvisible();
                        show();
                    }

                }

                else if (textView5.getText().toString().equals("Сохранить")) {
                    textView5.setText("Сохранить");

                    if (height.getText().toString().trim().isEmpty()){
                        height.setText("0");}
                    if (weight.getText().toString().trim().isEmpty()){
                        weight.setText("0");
                    }

                    //Добавление данных в БД
                    MyDB mydb = new MyDB(EditUserActivity.this);

                    mydb.updateUser("1",
                            String.valueOf(name.getText()),
                            String.valueOf(lastName.getText()),
                            Integer.valueOf(age.getText().toString().trim()),
                            String.valueOf(genderText),
                            String.valueOf(diabet),
                            Integer.valueOf(height.getText().toString().trim()),
                            Integer.valueOf(weight.getText().toString().trim()),
                            Double.valueOf(textRulerPicker.getText().toString()),
                            Double.valueOf(textRulerPickerDay.getText().toString()),
                            Double.valueOf(textRulerPickerEvening.getText().toString()),
                            Double.valueOf(textRulerPickerSensivity.getText().toString()));

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();

                }



            }
        });


        morningValue.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(morningValue.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(morningValue.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                //textRulerPicker.setText(String.valueOf(rulerValuePicker.getCurrentValue()));
                textRulerPicker.setText(String.valueOf(valueRulerPicker));
            }
        });



        day.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(day.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(day.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                //textRulerPicker.setText(String.valueOf(rulerValuePicker.getCurrentValue()));
                textRulerPickerDay.setText(String.valueOf(valueRulerPicker));
            }
        });



        evening.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(evening.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(evening.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;

                //textRulerPicker.setText(String.valueOf(rulerValuePicker.getCurrentValue()));
                textRulerPickerEvening.setText(String.valueOf(valueRulerPicker));
            }
        });


        sensivity.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(sensivity.getCurrentValue());
                valueRulerPicker = valueRulerPicker/2;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                float valueRulerPicker = Float.valueOf(sensivity.getCurrentValue());
                valueRulerPicker = valueRulerPicker/10;

                //textRulerPicker.setText(String.valueOf(rulerValuePicker.getCurrentValue()));
                textRulerPickerSensivity.setText(String.valueOf(valueRulerPicker));
            }
        });


    }


    private void show() {
        morning.setVisibility(View.VISIBLE);
        morning1.setVisibility(View.VISIBLE);
        morning2.setVisibility(View.VISIBLE);
        constraintLayout6.setVisibility(View.VISIBLE);
        constraintLayout5.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        constraintLayout7.setVisibility(View.VISIBLE);
        declarative.setVisibility(View.VISIBLE);
        declarative1.setVisibility(View.VISIBLE);
        check.setVisibility(View.VISIBLE);
        circle2.setVisibility(View.VISIBLE);
    }

    private void setInvisible() {

        layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        layout5.setVisibility(View.INVISIBLE);
        layout6.setVisibility(View.INVISIBLE);
        layout7.setVisibility(View.INVISIBLE);
        text4.setVisibility(View.INVISIBLE);
        text3.setVisibility(View.INVISIBLE);
        circle.setVisibility(View.INVISIBLE);
        circle2.setVisibility(View.VISIBLE);
    }

    private void setVisible() {
        textView5.setText("Продолжить");
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.VISIBLE);
        layout4.setVisibility(View.VISIBLE);
        layout5.setVisibility(View.VISIBLE);
        layout6.setVisibility(View.VISIBLE);
        layout7.setVisibility(View.VISIBLE);
        text4.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        circle.setVisibility(View.VISIBLE);
        circle2.setVisibility(View.VISIBLE);
    }

    private void setInVis() {
        morning.setVisibility(View.INVISIBLE);
        morning1.setVisibility(View.INVISIBLE);
        morning2.setVisibility(View.INVISIBLE);
        constraintLayout6.setVisibility(View.INVISIBLE);
        constraintLayout5.setVisibility(View.INVISIBLE);
        constraintLayout7.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
        declarative.setVisibility(View.INVISIBLE);
        declarative1.setVisibility(View.INVISIBLE);
        check.setVisibility(View.INVISIBLE);
        circle2.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setVisible();
            setInVis();
            return false;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }



}