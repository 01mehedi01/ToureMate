package com.example.user.touremate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.EventAdaptar;
import com.example.user.touremate.DataBase.EventProjoClass;
import com.example.user.touremate.DataBase.RecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateATourePlanActivity extends AppCompatActivity implements CreateEnentFragment.OnFragmentInteractionListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  int createtoureUserID;
    private SharedPreferences sharedPreferencesOne;
    private  SharedPreferences.Editor editorOne;
    private Calendar calendar;
    private  int year,month,day;
    private String enentstartDataeOnDevice;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private ArrayList<EventProjoClass>eventProjoClasses;
    private EventAdaptar eventAdaptar;
    private DataBaseSource dataBaseSource;
    private EventProjoClass eventProjoClass;
    private int createUserID;
    private int AutoinBYCreateTour;
    private int eventID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atoure_plan);
        initializeview();


        Toolbar toolbar = findViewById(R.id.toureplantoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateATourePlanActivity.super.onBackPressed();
            }
        });

        eventProjoClasses = dataBaseSource.getAllInfo(createUserID);
        eventAdaptar = new EventAdaptar(this, eventProjoClasses);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(eventAdaptar);




        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(final View view, final int position) {

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String Name =  eventProjoClasses.get(position).getEventName();
                    String StartDate =  eventProjoClasses.get(position).getStartDate();
                    double Budget =  eventProjoClasses.get(position).getEstimateBudget();
                    String ReturnDate =  eventProjoClasses.get(position).getReturnDate();
                    int Auto = eventProjoClasses.get(position).getAutoincrementEvent();

//                editor.clear();
//                editor.commit();
                Intent intent=new Intent(CreateATourePlanActivity.this,ShowEventActivity.class);


                Bundle b = new Bundle();
                b.putString("mes",Name);
                b.putDouble("double",Budget);
                b.putString("mes1",StartDate);
                b.putString("return",ReturnDate);
                b.putInt("userlogin",Auto);


                intent.putExtras(b);

                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(CreateATourePlanActivity.this,
                        new android.support.v4.util.Pair<View,String>(view.findViewById(R.id.card_event_name),ShowEventActivity.VIEW_NAME_HEADER_TITLE));
                ActivityCompat.startActivity(CreateATourePlanActivity.this,intent,optionsCompat.toBundle());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // int eventId =   position+1;
                    //Toast.makeText(CreateATourePlanActivity.this, "Delete => "+position, Toast.LENGTH_SHORT).show();
                    eventID = eventProjoClasses.get(position).getAutoincrementEvent();

                   boolean sta = dataBaseSource.DELeteInfoDetailsBYForeignKey(eventID);
                   Toast.makeText(CreateATourePlanActivity.this, "Delete => "+createUserID, Toast.LENGTH_SHORT).show();
                   boolean status = dataBaseSource.DeleteInfo(eventID);

                if (status) {

                    Toast.makeText(CreateATourePlanActivity.this, "Delete  SuccessFull " , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateATourePlanActivity.this, CreateATourePlanActivity.class));
                } else {
                   // Toast.makeText(CreateATourePlanActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }


                    return true;
                }
            });

            }


        }));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){



            case R.id.manu_logout_ID:

                Intent intent = new Intent(CreateATourePlanActivity.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_setting_ID:
                break;

            case R.id.manu_home_page_ID:
                Intent intentt = new Intent(CreateATourePlanActivity.this, ChooseActivity.class);
                intentt.putExtra("finish", true);
                intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentt);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeview() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        frameLayout = findViewById(R.id.create_event_frameLayout);
        recyclerView = findViewById(R.id.create_aevent_recyclerview);
        eventProjoClasses = new ArrayList<>();
        dataBaseSource = new DataBaseSource(this);
        eventProjoClass = new EventProjoClass();


        sharedPreferencesOne =   getSharedPreferences("UserIDAsForeignKey",Context.MODE_PRIVATE);
        editorOne = sharedPreferencesOne.edit();
        createUserID = sharedPreferencesOne.getInt("IDD",0);

//        sharedPreferences = getSharedPreferences("AutoByCretePlan",MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//
//        AutoinBYCreateTour = sharedPreferences.getInt("auttto",0);

    }

    public void CreateaEvent(View view) {

        recyclerView.setEnabled(false);
        CreateEnentFragment fragment = new CreateEnentFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.from_right, R.anim.form_left, R.anim.from_right, R.anim.form_left);
        ft.addToBackStack(null);
        ft.add(R.id.create_event_frameLayout, fragment, "Register").commit();

    }

    @Override
    public void onFragmentInteraction(String name) {
          recyclerView.setEnabled(true);
    }





}
