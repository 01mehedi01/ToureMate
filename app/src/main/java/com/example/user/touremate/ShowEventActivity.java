package com.example.user.touremate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.DataBase.CostAdaptar;
import com.example.user.touremate.DataBase.CostProjoClass;
import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.EventAdaptar;
import com.example.user.touremate.DataBase.EventProjoClass;
import com.example.user.touremate.DataBase.RecyclerItemClickListener;
import com.example.user.touremate.Fragment.EventUpdate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ShowEventActivity extends AppCompatActivity {

      private TextView EventName;
      private Calendar calendar;
      private  int year,month,day;
      private boolean islogedin = true;
      private TextView lowerBudget;
      private TextView higherBudget;
      private TextView logerprogreeBar;
      private TextView dayLeft;
      private TextView StartingDate;
      private TextView ReturnDate;
      private ProgressBar progressBar;
      public int AutoIncrement;
      private  Date date =null;
      private LinearLayout OntuchBackGround;
      private DataBaseSource dataBaseSource;
      private String enentReturntDataeOnDevice;
      private String enentStarttDataeOnDevice;
      private CostProjoClass costProjoClass;
      private ArrayList<CostProjoClass>costProjoClasses;
      private CostAdaptar costAdaptar;
      private RecyclerView COSTRECYCLERVIEW;
      private  int ProgressAmount = 0;
      private  int CalculateProgressBar = 0;
      private  double budget = 0.0;
      private SharedPreferences sharedPreferences;
      private SharedPreferences.Editor editor;
      private int AutoByPLan;
      private EventProjoClass eventProjoClass;
      private ArrayList<EventProjoClass>eventProjoClasses = new ArrayList<>();
      private  String Name;
      private  String StartDate;
      private  String Returndate;
      private  double Budget;

    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_event);
        intializeView();




        ViewCompat.setTransitionName(EventName, VIEW_NAME_HEADER_TITLE);

        if (getIntent().getExtras() != null) {

            Bundle p = getIntent().getExtras();
           /* final String Nameee = p.getString("mes");
            String Startdate = p.getString("mes1");
            final String returndate = p.getString("return");
            budget = p.getDouble("double");
            final int EstimateBudget = (int) budget;*/
            AutoIncrement = p.getInt("userlogin");
        }

        //////////////////////////////////////////////////////////////


        sharedPreferences = getSharedPreferences("AutoByCretePlan",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(AutoIncrement!=0) {
            editor.putInt("auttto", AutoIncrement);
            editor.commit();
          //  Toast.makeText(this, "In AutoIncrement", Toast.LENGTH_SHORT).show();
        }

        AutoByPLan = sharedPreferences.getInt("auttto",0);
        if(AutoByPLan!=0){
            eventProjoClasses = dataBaseSource.getAllInfoByID(AutoByPLan);
            Name =  eventProjoClasses.get(0).getEventName();
            StartDate =  eventProjoClasses.get(0).getStartDate();
            Returndate =  eventProjoClasses.get(0).getReturnDate();
            Budget =  eventProjoClasses.get(0).getEstimateBudget();

        }


        //  Toast.makeText(this, "AutoByPLan \t"+ AutoByPLan, Toast.LENGTH_SHORT).show();

        costProjoClasses = dataBaseSource.getallInfoDetails(AutoByPLan);
        costAdaptar = new CostAdaptar(this, costProjoClasses);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        COSTRECYCLERVIEW.setLayoutManager(llm);
        COSTRECYCLERVIEW.setAdapter(costAdaptar);

        Date today = new Date();
        Date Enddate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Enddate = sdf.parse(Returndate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long Daysss = getDaysBetweenDates(today, Enddate);

        if(dataBaseSource.GetAmount!=0) {

            ProgressAmount = (int) dataBaseSource.GetAmount;
            CalculateProgressBar = (int) ((ProgressAmount/Budget)*100);

            if((ProgressAmount >= Budget)&& Daysss>0){

                AlertDialog.Builder dailog = new AlertDialog.Builder(ShowEventActivity.this);
                dailog.setTitle("Please Add Amount With you Budget");
                LayoutInflater inflater = LayoutInflater.from(ShowEventActivity.this);
                final LinearLayout lll = (LinearLayout) inflater.inflate(R.layout.add_budget, null, false);

                final EditText EventNamt = lll.findViewById(R.id.add_Amount_single);
                Button AddButton = lll.findViewById(R.id.add_Amount_single_BUTTON);
                dailog.setView(lll);
                AddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            double amountSigle = Double.parseDouble(EventNamt.getText().toString());
                            double addamount = amountSigle + Budget;


                            if (ProgressAmount < addamount) {

                                eventProjoClass = new EventProjoClass(AutoByPLan, addamount);
                                boolean status = dataBaseSource.UpdateInfoBudget(eventProjoClass);
                                progressBar.setProgress(CalculateProgressBar);
                                logerprogreeBar.setText(String.valueOf(CalculateProgressBar));
                                lowerBudget.setText(String.valueOf(ProgressAmount));

                                if (status) {
                                    Toast.makeText(ShowEventActivity.this, "Update  SuccessFull ", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(ShowEventActivity.this, ShowEventActivity.class));
                                } else {
                                    Toast.makeText(ShowEventActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            }catch(Exception ex){
                                Toast.makeText(ShowEventActivity.this, "Amount Must be integer", Toast.LENGTH_SHORT).show();
                            }


                    }
                });

                dailog.setNegativeButton("No", null);
                dailog.show();
            }else if(ProgressAmount <=Budget){
                progressBar.setProgress(CalculateProgressBar);
                logerprogreeBar.setText(String.valueOf(CalculateProgressBar));
                lowerBudget.setText(String.valueOf(ProgressAmount));
            }

        }
        /////////////////////////////////////////////////////



           // Toast.makeText(ShowEventActivity.this, "T=> \t " + Daysss, Toast.LENGTH_SHORT).show();

            EventName.setText(Name);
            StartingDate.setText(StartDate);
            ReturnDate.setText(Returndate);
            higherBudget.setText(String.valueOf(Budget));
            dayLeft.setText(String.valueOf(Daysss));
            OntuchBackGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    android.app.AlertDialog.Builder dailog = new android.app.AlertDialog.Builder(ShowEventActivity.this);
                    dailog.setTitle("Update Your Toure Plan");
                    LayoutInflater inflater = LayoutInflater.from(ShowEventActivity.this);
                    final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.event_update_xml, null, false);

                    final EditText EventNamt = ll.findViewById(R.id.Update_event_EventName);
                    final EditText EventBudget = ll.findViewById(R.id.Update_event_EventBudget);
                    final EditText EventReturn = ll.findViewById(R.id.Update_event_Eventreturn);
                    final EditText EventStert = ll.findViewById(R.id.Update_event_EventStart);
                    Button UpdateEvents = ll.findViewById(R.id.Update_event_Button_create);

                    EventNamt.setText(Name);
                    EventBudget.setText(String.valueOf(Budget));
                    EventReturn.setText(Returndate);
                    EventStert.setText(StartDate);
                    dailog.setView(ll);

                    final DatePickerDialog.OnDateSetListener Returndatedatelistenerrr = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            enentReturntDataeOnDevice = sdf.format(calendar.getTime());
                            EventReturn.setText(enentReturntDataeOnDevice);

                        }
                    };
                    EventReturn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog ReturndatedatePickerDialogg = new DatePickerDialog(ShowEventActivity.this,
                                    Returndatedatelistenerrr,year,month,day);
                            ReturndatedatePickerDialogg.show();
                        }

                    });

                    final DatePickerDialog.OnDateSetListener Returndatedateliste = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            enentStarttDataeOnDevice = sdf.format(calendar.getTime());
                            EventStert.setText(enentStarttDataeOnDevice);

                        }
                    };
                    EventStert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog Returndatedate= new DatePickerDialog(ShowEventActivity.this,
                                    Returndatedateliste,year,month,day);
                            Returndatedate.show();
                        }

                    });

                    UpdateEvents.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                String name = EventNamt.getText().toString();
                                double budg = Double.parseDouble(EventBudget.getText().toString());
                                String returndate = EventReturn.getText().toString();
                                String strtDate = EventStert.getText().toString();

                                if(TextUtils.isEmpty(name)){
                                    EventNamt.setError("requird");
                                }
                                if(TextUtils.isEmpty(EventBudget.getText().toString())){
                                    EventBudget.setError("requird");
                                }
                                if(TextUtils.isEmpty(returndate)){
                                    EventReturn.setError("requird");
                                }
                                if(TextUtils.isEmpty(strtDate)){
                                    EventStert.setError("requird");
                                }
                                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(strtDate)&& TextUtils.isEmpty(returndate)){
                                    EventStert.setError("requird");
                                    EventReturn.setError("requird");
                                    EventNamt.setError("requird");
                                }else if(TextUtils.isEmpty(name) && TextUtils.isEmpty(strtDate)){
                                    EventStert.setError("requird");
                                    EventNamt.setError("requird");
                                }else if(TextUtils.isEmpty(strtDate)&& TextUtils.isEmpty(returndate))
                                {
                                    EventStert.setError("requird");
                                    EventReturn.setError("requird");
                                }
                                else if(TextUtils.isEmpty(name) &&TextUtils.isEmpty(returndate)){
                                    EventReturn.setError("requird");
                                    EventNamt.setError("requird");
                                }else if(TextUtils.isEmpty(name)){
                                    EventNamt.setError("requird");
                                }else if(TextUtils.isEmpty(strtDate)){
                                    EventStert.setError("requird");
                                }else if(TextUtils.isEmpty(returndate)){
                                    EventReturn.setError("requird");
                                }
                                else{

                                    eventProjoClass = new EventProjoClass(AutoByPLan, name, strtDate, returndate, budg, 5);

                                    boolean status = dataBaseSource.UpdateInfo(eventProjoClass);

                                    if (status) {

                                        Toast.makeText(ShowEventActivity.this, "Update  SuccessFull ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ShowEventActivity.this, CreateATourePlanActivity.class));
                                    } else {
                                        Toast.makeText(ShowEventActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch (Exception ex){
                                Toast.makeText(ShowEventActivity.this, "Budget must be integer", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dailog.setNegativeButton("Close", null);

                    dailog.setView(ll);
                    dailog.show();
                }
            });

       COSTRECYCLERVIEW.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, final int position) {

               AlertDialog.Builder dailog = new AlertDialog.Builder(ShowEventActivity.this);
               dailog.setTitle("Delete?");

               final int DetelePosition = costProjoClasses.get(position).getCostAutoincrementID();

               dailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {


                       boolean status = dataBaseSource.DELeteInfoDetails(DetelePosition);
                       if(status) {

                        //   costProjoClasses.add(costProjoClass);
                         //  costAdaptar.CostaNotf(costProjoClasses);
                           Toast.makeText(ShowEventActivity.this, "Delete \t"+DetelePosition, Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(ShowEventActivity.this,ShowEventActivity.class));

                       } else {
                           Toast.makeText(ShowEventActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
               dailog.setNegativeButton("No", null);
               dailog.show();
           }
       }));
    }


    public static long getDaysBetweenDates(Date start, Date end) {

        long numberOfDays = 0;
        numberOfDays = getUnitBetweenDates(start, end, TimeUnit.DAYS);
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    private void intializeView() {
        eventProjoClass = new EventProjoClass();
        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        OntuchBackGround = findViewById(R.id.linearBackground);
        EventName = findViewById(R.id.show_event_name);
        COSTRECYCLERVIEW = findViewById(R.id.Cost_recycler_view);
        lowerBudget = findViewById(R.id.show_event_Calculate_amount);
        higherBudget = findViewById(R.id.show_event_estimateBudget);
        logerprogreeBar = findViewById(R.id.show_event_budget_mimiinimum_progress);
        dayLeft = findViewById(R.id.show_event_day_left);
        StartingDate = findViewById(R.id.show_event_stariing_date);
        ReturnDate = findViewById(R.id.show_event_ReturnDate);
        progressBar = findViewById(R.id.show_event_progressBar);
        dataBaseSource = new DataBaseSource(this);
    }

    public void WriteExpnese(View view) {
       /* Intent intent=new Intent(ShowEventActivity.this,CreateaCost.class);
        Bundle b = new Bundle();
        b.putInt("userlog",AutoIncrement);
        intent.putExtras(b);
        startActivity(intent);*/
       startActivity(new Intent(ShowEventActivity.this,CreateaCost.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return  true;

    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem loginItem = menu.findItem(R.id.manu_login_ID);
//        MenuItem logoutItem = menu.findItem(R.id.manu_logout_ID);
//        if(islogedin){
//            loginItem.setVisible(false);
//            logoutItem.setVisible(true);
//        }
//        else {
//            logoutItem.setVisible(false);
//            loginItem.setVisible(true);
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){



            case R.id.manu_logout_ID:

                Intent intent = new Intent(ShowEventActivity.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_setting_ID:
                break;
            case R.id.manu_home_page_ID:
                Intent intentt = new Intent(ShowEventActivity.this, ChooseActivity.class);
                intentt.putExtra("finish", true);
                intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentt);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}

