package com.example.user.touremate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.EventAdaptar;
import com.example.user.touremate.DataBase.EventProjoClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class CreateEnentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    private Calendar calendar;
    private  int year,month,day;
    private String enentstartDataeOnDevice;
    private String enentReturntDataeOnDevice;
    private SharedPreferences sharedPreferencesOne;
    private  SharedPreferences.Editor editorOne;
    private  int createtoureUserID;
    private ArrayList<EventProjoClass>eventProjoClasses = new ArrayList<>();
    private  EventProjoClass eventProjoClass;
    private DataBaseSource dataBaseSource;
    private EventAdaptar eventAdaptar ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEnentFragment() {
        // Required empty public constructor
    }


    public static CreateEnentFragment newInstance(String param1, String param2) {
        CreateEnentFragment fragment = new CreateEnentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_create_enent, container, false);
        eventAdaptar = new EventAdaptar(context,eventProjoClasses);
        dataBaseSource= new DataBaseSource(context);
        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        sharedPreferencesOne = this.getActivity().getSharedPreferences("UserIDAsForeignKey",Context.MODE_PRIVATE);
        editorOne = sharedPreferencesOne.edit();
        createtoureUserID = sharedPreferencesOne.getInt("IDD",0);
      //  initilizeView(v);
        final TextInputLayout EventName = v.findViewById(R.id.create_event_EventName);
        final TextView title = v.findViewById(R.id.create_event_titel);
        final TextInputLayout EventBudget = v.findViewById(R.id.create_event_EventBudget);
        final EditText Returndate = v.findViewById(R.id.create_event_Eventreturn);
        Button Create = v.findViewById(R.id.create_event_Button_create);


        Animation myanimation = AnimationUtils.loadAnimation(context,R.anim.mytransition);
        EventName.startAnimation(myanimation);
        title.startAnimation(myanimation);
        EventBudget.startAnimation(myanimation);
        Returndate.startAnimation(myanimation);
        Create.startAnimation(myanimation);

///////////////////////select Start Date /////////////////////////////////////

           Date c = Calendar.getInstance().getTime();
           SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
           enentstartDataeOnDevice = df.format(c);


        //////////////////////////////  Select Return date ////////////////////////////////

        final DatePickerDialog.OnDateSetListener Returndatedatelistenerrr = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                enentReturntDataeOnDevice = sdf.format(calendar.getTime());
                Returndate.setText(enentReturntDataeOnDevice);

            }
        };
        Returndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog ReturndatedatePickerDialogg = new DatePickerDialog(context,
                        Returndatedatelistenerrr,year,month,day);
                ReturndatedatePickerDialogg.show();
            }

        });

        ///////////////////////////////////  Button //////////////////////////////////////////////


        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enentName = EventName.getEditText().getText().toString().trim();
                String enentBudget = EventBudget.getEditText().getText().toString().trim();
                String enentreturnDate = Returndate.getText().toString();


                try{
                    double EstimateBudget = Double.parseDouble(enentBudget);
                    eventProjoClass = new EventProjoClass(createtoureUserID,enentName,enentstartDataeOnDevice,enentreturnDate,EstimateBudget);
                    boolean status = dataBaseSource.InsertEventInfo(eventProjoClass);
                    if(status){
                        eventProjoClasses.add(eventProjoClass);
                        eventAdaptar.AddImageList(eventProjoClasses);
                        Toast.makeText(context, "Insert succesfull", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getActivity(),CreateATourePlanActivity.class));
                    }else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(context, "Budget Must be Integer", Toast.LENGTH_SHORT).show();
                }

                mListener.onFragmentInteraction("Mehedi");


            }
        });
        return v;
    }




    public void onButtonPressed(String Name) {
        if (mListener != null) {
            mListener.onFragmentInteraction(Name);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String name);
    }
}
