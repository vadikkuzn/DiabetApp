 package com.example.javalinechart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.w3c.dom.Text;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.mvdw.recyclerviewmergeadapter.adapter.RecyclerViewMergeAdapter;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LineChart mChart;
    MyDB myDB;
    ArrayList<String> sugar_id,date, time, blood, food, insulin, lantus, comment;
    CustomAdapter customAdapter;
    CardView morningButton, dayButton, eveningButton;
    TextView morningText, dayText,eveningText, numberDate, weekDate,yearDate;
    int flag = 0;
    CustomSpaceAdapter customAdapterSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChart = findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        myDB = new MyDB(MainActivity.this);

        //проверка есть ли пользователь вообще
        Cursor cursor1 = myDB.readAllUser();
        if(cursor1.getCount() == 0){
            Intent myIntent = new Intent(MainActivity.this, SignUpActivity.class);
            MainActivity.this.startActivity(myIntent);
        }

        berforeStart();

        morningButton = findViewById(R.id.buttonMorning);
        dayButton = findViewById(R.id.buttonDay);
        eveningButton = findViewById(R.id.buttonEvening);

        morningButton.setOnClickListener(this);
        eveningButton.setOnClickListener(this);
        dayButton.setOnClickListener(this);
        morningButton.callOnClick();

        ImageView backButton = findViewById(R.id.imageView9);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ImageView settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


//menu


        ImageView calculatorButton = findViewById(R.id.imageView2);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CalculatorActivity.class);
                MainActivity.this.startActivity(myIntent);
                //finish();
            }
        });


        ImageView settingButton = findViewById(R.id.settingsButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
                //finish();
            }
        });

        ImageView addButton = findViewById(R.id.imageButton2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddSugarActivity.class);
                MainActivity.this.startActivity(myIntent);
                //finish();
            }
        });

        ImageView addButton1 = findViewById(R.id.imageView5);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddSugarActivity.class);
                MainActivity.this.startActivity(myIntent);
                //finish();
            }
        });

        ImageView alarmButton = findViewById(R.id.imageView3);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AlarmActivity.class);
                MainActivity.this.startActivity(myIntent);
                //finish();
            }
        });

        // end menu

        setInitialDateTime();
        //renderData();

    }



    @Override
    public void onClick(View v) {

        morningButton = findViewById(R.id.buttonMorning);
        dayButton = findViewById(R.id.buttonDay);
        eveningButton = findViewById(R.id.buttonEvening);

        morningText = findViewById(R.id.textMorning);
        dayText = findViewById(R.id.textDay);
        eveningText =  findViewById(R.id.textEvening);




        if(v == morningButton){
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            //morningText.setTextColor(Color.BLACK);
            dayButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));

            flag = 1;
            storeDataInArrays();
            renderData();

        } if(v == dayButton){

            dayButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));

            flag = 2;
            storeDataInArraysWeekAndMonth();
            renderData();


        } if(v == eveningButton){
            eveningButton.setCardBackgroundColor(getResources().getColor(R.color.white));
            morningButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            dayButton.setCardBackgroundColor(getResources().getColor(R.color.lightgreen));
            flag = 3;

            storeDataInArraysWeekAndMonth();
            renderData();
        }
        mChart.notifyDataSetChanged();
        mChart.invalidate();

    }

    //refresh page automaticly after changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }



    void storeDataInArrays(){

        sugar_id = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        blood = new ArrayList<>();
        food = new ArrayList<>();
        insulin = new ArrayList<>();
        lantus = new ArrayList<>();
        comment = new ArrayList<>();


        numberDate = findViewById(R.id.dateText);
        yearDate = findViewById(R.id.yearText);

        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");


        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"Нет данных",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                String stringData = "";

                try {
                    Date date1 = dateBDformat.parse(cursor.getString(1));
                    stringData = dateFormat.format(date1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (stringData.equals(numberDate.getText()+" "+ yearDate.getText())) {

                    sugar_id.add(cursor.getString(0));
                    date.add(cursor.getString(1));
                    time.add(cursor.getString(2));
                    blood.add(cursor.getString(3));
                    food.add(cursor.getString(4));
                    insulin.add(cursor.getString(5));
                    lantus.add(cursor.getString(6));
                    comment.add(cursor.getString(7));

                }
            }


        }

    }


    void storeDataInArraysWeekAndMonth() {

        sugar_id = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        blood = new ArrayList<>();
        food = new ArrayList<>();
        insulin = new ArrayList<>();
        lantus = new ArrayList<>();
        comment = new ArrayList<>();


        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");


        Cursor cursor1 = myDB.readAllData();
        if (cursor1.getCount() == 0) {
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        } else {

            int week;
            if(flag == 2){
                week = 7;
            }else week=30;


            for (int j = 0; j < week; j++) {
                date.add(String.valueOf(dateAndTime.getTime()));
                blood.add(String.valueOf(0));
                food.add(String.valueOf(0));
                insulin.add(String.valueOf(0));

                //System.out.println(date.get(j));
                dateAndTime.add(Calendar.  DATE, -1);
            }
            dateAndTime.add(Calendar.DATE, week);




            for (int j = 0; j < date.size(); j++) {
                Double allBlood = 0.0, allFood = 0.0, allInsuline = 0.0;
                Cursor cursor = myDB.readAllData();

                String dat = "";
                int i=0;
                while (cursor.moveToNext()) {
                    String stringData = "";
                    String stringData2 = "";


                    try {
                        Date date1 = dateBDformat.parse(cursor.getString(1));
                        stringData = dateFormat.format(date1.getTime());
                       //System.out.println("1 "+stringData);

                        Date date2 = dateBDformat.parse(date.get(j));
                        stringData2 = dateFormat.format(date2.getTime());
                        //m.out.println("2 "+stringData2);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (stringData.equals(stringData2)) {

                        //System.out.println("GGHGH");
                        //date.add(cursor.getString(1));

                        allBlood += Double.parseDouble(cursor.getString(3));
                        //System.out.println(allBlood);
                        allFood += Double.parseDouble(cursor.getString(4));
                        allInsuline += Double.parseDouble(cursor.getString(5));
                        i++;

                    }
                }

               // System.out.println("BLOOD 1 "+allBlood);
                if(allBlood!=0) {
                    allBlood = allBlood / i;
                    allFood = allFood / i;
                    allInsuline = allInsuline / i;
                }
                //System.out.println(allBlood);

                //System.out.println("BLOOD2 "+allBlood);

                blood.add(j,String.valueOf(allBlood));
                food.add(j,String.valueOf(allFood));
                insulin.add(j,String.valueOf(allInsuline));

            }


        }
    }


    public void renderData() {

        Legend legend = mChart.getLegend(); // квадратик тот снизу
        legend.setEnabled(false);
        mChart.getDescription().setEnabled(false); //пояснение в правом нижнем углу


        //тут про 24 часа графика или последний замер = конец графика
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawGridLines(false); // GRID LINE OFF
        xAxis.setLabelCount(5); // ЭТО СКОЛЬКО ШТУК НА ОСИ Х ПОКАЗЫВАТЬ
        xAxis.setTextSize(14);
        Typeface tf = ResourcesCompat.getFont(this, R.font.poppins_medium);
        xAxis.setTypeface(tf);





        if(flag==1) {
            xAxis.setValueFormatter(new BarChartXAxisValueFormatter());
            //xAxis.setAxisMaximum(24f);
            //xAxis.setAxisMinimum(0f);
            xAxis.setLabelCount(5);
        } else if(flag == 2 || flag == 3){
            //xAxis.setLabelCount(2);
            //xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new ChartDayAndMonthValueFormatter());

        }



        LimitLine ll1 = new LimitLine(10f, "Maximum Limit");
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);

        LimitLine ll2 = new LimitLine(3.5f, "Minimum Limit");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();

        //leftAxis.setEnabled(false);


        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(20f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f); //это сетка
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(false); //GRID LINE OFF
        leftAxis.setTextSize(14);
        leftAxis.setTypeface(tf);
        //leftAxis.setLabelCount(5);

        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(false); //ось Y значений справа - убрать


        if(flag==1) {
            leftAxis.setLabelCount(5);
            setData();
        } else if(flag == 2 || flag == 3){
            xAxis.setLabelCount(6);
            setDataWeekAndMonth();

        }
    }


    private void setData() {

        numberDate = findViewById(R.id.dateText);
        yearDate = findViewById(R.id.yearText);

        ArrayList<Entry> values = new ArrayList<>();

        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");


        for (int i = 0; i < date.size(); i++) {
            String stringData = "";

            try {
                    Date date1 = dateBDformat.parse(date.get(i));
                   stringData = dateFormat.format(date1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            if (stringData.equals(numberDate.getText()+" "+ yearDate.getText())) {
                values.add(new Entry(Float.parseFloat(time.get(i).replace(":",".")),Float.parseFloat(blood.get(i))));
            }

        }



        drawChart(values);
    }


    private void setDataWeekAndMonth() {


        ArrayList<Entry> values = new ArrayList<>();

        SimpleDateFormat dateBDformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


        for (int i = 0; i < date.size(); i++) {
            String stringData = "";
            long ms = 0;
            try {
                //Date date1 = dateBDformat.parse(date.get(i));
                //stringData = dateFormat.format(date1.getTime());

                Date date1 = dateBDformat.parse(date.get(i));
                stringData = dateFormat.format(date1.getTime());
                Date d = dateFormat.parse(stringData);
                ms = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //values.add(new Entry(Float.parseFloat(stringData),Float.parseFloat(blood.get(i))));
            values.add(new Entry(TimeUnit.MILLISECONDS.toDays((long)ms), Float.parseFloat(blood.get(i))));
            //System.out.println("X "+stringData);
            //System.out.println("Y "+Float.parseFloat(blood.get(i)));

        }

        drawChart(values);
    }



    void drawChart(ArrayList<Entry> values){
        values.sort(Comparator.comparing(Entry::getX));


        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Сахар");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER); //именно плавная линия

            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 0f, 0f); // ТУТ УЖЕ САМ ГРАФИК ИМЕННО ПАЛКИ МЕЖДУ ЗНАЧНИЙ
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.GREEN);
            set1.setLineWidth(3f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            set1.setDrawValues(false);
            //set1.setHighlightEnabled(true);
            set1.setDrawHighlightIndicators(false);
            set1.setDrawCircles(false);



            if (Utils.getSDKInt() >= 10) {
                //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
                //set1.setFillColor(Color.BLACK);
                set1.setFillAlpha(55);

            } else {
                set1.setFillColor(Color.GREEN);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            mChart.setExtraOffsets(0,0,20,12);
            mChart.animateY(700, Easing.EaseInOutQuart);
            mChart.setData(data);
            //mChart.xAxis.setValueFormatter(new LineChartXAxisValueFormatter());
        }
    }




    static class BarChartXAxisValueFormatter extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {

            // Convert float value to date string
            // Convert from days back to milliseconds to format time  to show to the user
            long emissionsMilliSince1970Time = TimeUnit.HOURS.toMillis((long)value); // ТУТА МЕНЯЕШЬ НА ДЕНЬ, МЕСЯЦ, ЭТО РЕЖИМЫ ДЛЯ ОСИ Х
            // Show time in local version
            Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("H:mm");

            return dateTimeFormat.format(timeMilliseconds);


        }
    }

    static class ChartDayAndMonthValueFormatter extends IndexAxisValueFormatter {

        @Override
        public String getFormattedValue(float value) {

            long emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis((long)value); // ТУТА МЕНЯЕШЬ НА ДЕНЬ, МЕСЯЦ, ЭТО РЕЖИМЫ ДЛЯ ОСИ Х
            Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd");

            return dateTimeFormat.format(timeMilliseconds);


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



    //ДАТА И ВРЕМЯ
    //ДАТА И ВРЕМЯ ДО НАЖАТИЯ УСТАНАВЛИВАЕТСЯ В OVERRIDE В НАЧАЛЕ
    Calendar dateAndTime=Calendar.getInstance();


    public void setDate(View v) {
        new DatePickerDialog(MainActivity.this,
                d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {


        numberDate = findViewById(R.id.dateText);
        weekDate = findViewById(R.id.dayText);
        yearDate = findViewById(R.id.yearText);


        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        numberDate.setText(dateFormat.format(dateAndTime.getTime()));

        weekDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_WEEKDAY));


        yearDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                dateAndTime.get(Calendar.YEAR)|
                        dateAndTime.get(Calendar.MONTH)));



        if(flag == 1) {
            storeDataInArrays();
        } else storeDataInArraysWeekAndMonth();
        renderData();


        RecyclerView recycleView = findViewById(R.id.recycleView);

        customAdapter = new CustomAdapter(MainActivity.this,this,sugar_id,date,time,blood,food,insulin,lantus,comment);
        //recycleView.setAdapter(customAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        customAdapterSpace = new CustomSpaceAdapter(MainActivity.this,this);
        //recycleView.setAdapter(customAdapterSpace);
        recycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RecyclerViewMergeAdapter mergeAdapter = new RecyclerViewMergeAdapter();
        mergeAdapter.addAdapter(customAdapter);
        mergeAdapter.addAdapter(customAdapterSpace);

        recycleView.setAdapter(mergeAdapter);
        //обновить график
        mChart.notifyDataSetChanged();
        mChart.invalidate();


    }






}
