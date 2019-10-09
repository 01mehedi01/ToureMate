package com.example.user.touremate.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.touremate.DataBase.EventProjoClass;
import com.example.user.touremate.MainActivity;
import com.example.user.touremate.R;
import com.example.user.touremate.ShowEventActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventUpdate extends Fragment {
    private Calendar calendar;
    private  int year,month,day;
  private EditText CostName;
  private EditText CostAmount;
  private EditText CostDetails;
  private String CostDate;
  private Button CreateCost;
  private Context context;
  private EventProjoClass eventProjoClass;
  private int AutoByCreateAPlan;
    public EventUpdate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_event_update, container, false);
        initilizationView(v);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        String currentdate = sdf.format(date);

        ShowEventActivity mainActivity = new ShowEventActivity();
        AutoByCreateAPlan = mainActivity.AutoIncrement;
        Toast.makeText(context, "AutoByCreateAPlan \t"+AutoByCreateAPlan, Toast.LENGTH_SHORT).show();
        CreateCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String costname = CostName.getText().toString();
                String costamount = CostAmount.getText().toString();
                String costdetails = CostDetails.getText().toString();
                eventProjoClass = new EventProjoClass();
            }
        });

        return v;
    }

    private void initilizationView(View v) {
        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        CostName = v.findViewById(R.id.Create_cost_Name);
        CostAmount = v.findViewById(R.id.Create_cost_Amount);
        CostDetails = v.findViewById(R.id.Create_cost_AmountDetails);
        CreateCost = v.findViewById(R.id.Create_cost_Button);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
