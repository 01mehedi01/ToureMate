package com.example.user.touremate.DataBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.user.touremate.R;

import java.util.ArrayList;

/**
 * Created by User on 4/10/2018.
 */

public class CostAdaptar extends RecyclerView.Adapter<CostAdaptar.CostViewHolder>{

    public CostAdaptar() {
    }

    private Context context;
    private ArrayList<CostProjoClass>costProjoClasses;
    private  int previousposition = 0;

    public CostAdaptar(Context context, ArrayList<CostProjoClass> costProjoClasses) {
        this.context = context;
        this.costProjoClasses = costProjoClasses;
    }

    @Override
    public CostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cost_show_single_row,parent,false);
        return new CostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CostViewHolder holder, int position) {
     holder.name.setText(costProjoClasses.get(position).getCostName());
     holder.amount.setText(String.valueOf(costProjoClasses.get(position).getCostAmount()));
     holder.date.setText(costProjoClasses.get(position).getCosteTime());
     holder.details.setText(costProjoClasses.get(position).getCostDetails());

        if(position > previousposition){

            AnimatonClass.animate(holder,true);
        }
        else{
            AnimatonClass.animate(holder,false);
        }
        previousposition = position;
    }

    @Override
    public int getItemCount() {
        return costProjoClasses.size();
    }

    public class CostViewHolder extends RecyclerView.ViewHolder{
         TextView name ;
         TextView amount ;
         TextView date ;
         TextView details ;
        public CostViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.adaptar_cost_name);
            amount = itemView.findViewById(R.id.adaptar_cost_amount);
            date = itemView.findViewById(R.id.adaptar_cost_Date);
            details = itemView.findViewById(R.id.adaptar_cost_details);
        }
    }


    public void CostaNotf(ArrayList<CostProjoClass>projoClasses){
        this.costProjoClasses=projoClasses;
        notifyDataSetChanged();

    }
}
