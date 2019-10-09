package com.example.user.touremate.DataBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.R;

import java.util.ArrayList;

/**
 * Created by User on 4/6/2018.
 */

public class EventAdaptar extends RecyclerView.Adapter<EventAdaptar.EventViewHolder>  {
    private Context context;
    private ArrayList<EventProjoClass> eventProjoClasses;

    public EventAdaptar(Context context, ArrayList<EventProjoClass> eventProjoClasses) {
        this.context = context;
        this.eventProjoClasses = eventProjoClasses;
    }

    public EventAdaptar() {
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v =  inflater.inflate(R.layout.event_card_view_item,parent,false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
    holder.NameET.setText(eventProjoClasses.get(position).getEventName());
    holder.BudgetET.setText(String.valueOf(eventProjoClasses.get(position).getEstimateBudget()));
    holder.StartdateET.setText(eventProjoClasses.get(position).getStartDate());
    }

    @Override
    public int getItemCount() {
        return eventProjoClasses.size();
    }




    public class EventViewHolder extends RecyclerView.ViewHolder{
     TextView NameET;
     TextView BudgetET;
     TextView StartdateET;
    public EventViewHolder(View itemView) {
        super(itemView);
        NameET = itemView.findViewById(R.id.card_event_name);
        BudgetET = itemView.findViewById(R.id.card_event_budget);
        StartdateET = itemView.findViewById(R.id.card_event_start_date);
    }
}

    public void AddImageList(ArrayList<EventProjoClass>projoClasses){
        this.eventProjoClasses = projoClasses;
        notifyDataSetChanged();

    }}
