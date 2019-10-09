package com.example.user.touremate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.touremate.DataBase.CostAdaptar;
import com.example.user.touremate.DataBase.CostProjoClass;
import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.EventProjoClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateaCost extends AppCompatActivity {
    private Calendar calendar;
    private  int year,month,day;
    private TextInputLayout CostName;
    private TextInputLayout CostAmount;
    private TextInputLayout CostDetails;
    private String CostDate;
    private Button CreateCost;
    private Context context;
    private int AutoinBYCreateTour;
    private CostProjoClass costProjoClass;
    private ArrayList<CostProjoClass>costProjoClasses = new ArrayList<>();
    private int AutoByCreateAPlan;
    private  String currentdate;
    private DataBaseSource dataBaseSource;
    private CostAdaptar costAdaptar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPreferencesOne;
    private  SharedPreferences.Editor editorOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createa_cost);

        initilizationView();

        Toolbar toolbar = findViewById(R.id.costtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateaCost.super.onBackPressed();
            }
        });
        costAdaptar = new CostAdaptar(this,costProjoClasses);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        currentdate = sdf.format(date);


       // Toast.makeText(this, "AutoinBYCreateTour => "+AutoinBYCreateTour, Toast.LENGTH_SHORT).show();



        CreateCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String costname = null;
                double costamount = 0.0 ;
                String costdetails =null;*/
                try {


                    String  costname = CostName.getEditText().getText().toString().trim();
                    double costamount = Double.parseDouble(CostAmount.getEditText().getText().toString().trim());
                    String costdetails = CostDetails.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(costname)){
                    CostName.setError("Requird");
                }else if(TextUtils.isEmpty(CostAmount.getEditText().getText().toString().trim())){
                    CostAmount.setError("Requird");
                }else {
                    costProjoClass = new CostProjoClass(AutoinBYCreateTour, costname, costamount, costdetails, currentdate);

                    boolean status = dataBaseSource.InsertCost(costProjoClass);
                    if (status) {

                        Toast.makeText(CreateaCost.this, "Create  SuccessFull ", Toast.LENGTH_SHORT).show();
                        // costProjoClasses.add(costProjoClass);
                        //costAdaptar.CostaNotf(costProjoClasses);
                        Intent intent = new Intent(CreateaCost.this, ShowEventActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CreateaCost.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                }catch (Exception ex){
                    Toast.makeText(CreateaCost.this, "Amount Must be Integer", Toast.LENGTH_SHORT).show();
                }

                }


        });

    }

    private void initilizationView() {
        dataBaseSource = new DataBaseSource(this);

//        sharedPreferencesOne =   getSharedPreferences("UserIDAsForeignKey",Context.MODE_PRIVATE);
//        editorOne = sharedPreferencesOne.edit();
//        AutoinBYCreateTour = sharedPreferencesOne.getInt("IDD",0);


        sharedPreferences = getSharedPreferences("AutoByCretePlan",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        AutoinBYCreateTour = sharedPreferences.getInt("auttto",0);

        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        CostName = findViewById(R.id.Create_cost_Name);
        CostAmount = findViewById(R.id.Create_cost_Amount);
        CostDetails = findViewById(R.id.Create_cost_AmountDetails);
        CreateCost = findViewById(R.id.Create_cost_Button);

    }
}
