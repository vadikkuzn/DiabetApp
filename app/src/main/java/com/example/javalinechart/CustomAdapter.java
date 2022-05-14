package com.example.javalinechart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList sugar_id, sugar_data, sugar_time, sugar_blood, sugar_food, sugar_insulin, sugar_lantus, sugar_comment;
    int position;

    CustomAdapter( Activity activity,Context context, ArrayList sugar_id, ArrayList sugar_data, ArrayList sugar_time,
                  ArrayList sugar_blood,ArrayList sugar_food,ArrayList sugar_insulin,ArrayList sugar_lantus,ArrayList sugar_comment){
        this.activity = activity;
        this.context = context;
        this.sugar_id = sugar_id;
        this.sugar_data = sugar_data;
        this.sugar_time = sugar_time;
        this.sugar_blood = sugar_blood;
        this.sugar_food = sugar_food;
        this.sugar_insulin = sugar_insulin;
        this.sugar_lantus = sugar_lantus;
        this.sugar_comment = sugar_comment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_data_list, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //this.position = position;

        //Это то, что находится в layout

        holder.sugar_time_txt.setText(String.valueOf(sugar_time.get(position)));
        holder.sugar_blood_txt.setText(String.valueOf(sugar_blood.get(position)));
        holder.sugar_food_txt.setText(String.valueOf(sugar_food.get(position)));
        holder.sugar_insulin_txt.setText(String.valueOf(sugar_insulin.get(position)));
        holder.sugar_lantus_txt.setText(String.valueOf(sugar_lantus.get(position)));





        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateSugarActivity.class);

                intent.putExtra("id",String.valueOf(sugar_id.get(position)));
                intent.putExtra("data",String.valueOf(sugar_data.get(position)));
                intent.putExtra("time",String.valueOf(sugar_time.get(position)));
                intent.putExtra("blood", String.valueOf(sugar_blood.get(position)));
                intent.putExtra("food", String.valueOf(sugar_food.get(position)));
                intent.putExtra("insulin", String.valueOf(sugar_insulin.get(position)));
                intent.putExtra("lantus", String.valueOf(sugar_lantus.get(position)));
                intent.putExtra("cmmnt", String.valueOf(sugar_comment.get(position)));

                //context.startActivity(intent);



                activity.startActivityForResult(intent, 1);
            }
        });




    }

    @Override
    public int getItemCount() {
        return sugar_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sugar_id_txt, sugar_data_txt, sugar_time_txt, sugar_blood_txt, sugar_food_txt,
                sugar_insulin_txt, sugar_lantus_txt, sugar_comment_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //sugar_id_txt = itemView.findViewById(R.id.sugar_id_txt);
            //sugar_data_txt = itemView.findViewById(R.id.sugar_data_txt);
            sugar_time_txt = itemView.findViewById(R.id.textView6);
            sugar_blood_txt = itemView.findViewById(R.id.textView3);
            sugar_food_txt = itemView.findViewById(R.id.textView2);
            sugar_insulin_txt = itemView.findViewById(R.id.textView5);
            sugar_lantus_txt = itemView.findViewById(R.id.textView4);

            //sugar_comment_txt = itemView.findViewById(R.id.book_pages_txt);

            mainLayout = itemView.findViewById(R.id.mainSugarLayout);
/*
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
 */
        }

    }


}