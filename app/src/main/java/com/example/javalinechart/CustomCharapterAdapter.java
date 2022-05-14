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

public class CustomCharapterAdapter extends RecyclerView.Adapter<CustomCharapterAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private String name;

    CustomCharapterAdapter( Activity activity,Context context, String name){
        this.activity = activity;
        this.context = context;
        this.name = name;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.textsection, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //this.position = position;

        //Это то, что находится в layout

        holder.textChapter.setText(String.valueOf(name));






    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textChapter;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textChapter = itemView.findViewById(R.id.textChapter);

            mainLayout = itemView.findViewById(R.id.layoutChapter);

        }

    }


}