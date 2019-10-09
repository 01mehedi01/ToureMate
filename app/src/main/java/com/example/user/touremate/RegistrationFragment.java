package com.example.user.touremate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.LoginPojoClass;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  String Name = "Develop By mehedi";
    private TextInputLayout UserName;
    private TextInputLayout UserPhoneNumber;
    private EditText UserPassword;
    private EditText UserConfromPassword;
    private Button RegistrationDButton;
    private LoginPojoClass loginPojoClass;
    private DataBaseSource dataBaseSource;
    private Context context;
    private TextView ForgetPassword;
    private ArrayList<LoginPojoClass>loginPojoClasses;
    private LinearLayout linearLayout;
    private TextView Usertitle;
    private TextView passTitle;
    private TextView PhoneTitle;
    private TextView ConforPassTitle;
    private TextView WelcomeTitle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        initializeViewinFragment(v);

         dataBaseSource = new DataBaseSource(context);
         loginPojoClass = new LoginPojoClass();
         loginPojoClasses = new ArrayList<>();


        RegistrationDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = UserName.getEditText().getText().toString().trim();
                String phone = UserPhoneNumber.getEditText().getText().toString().trim();
                String pass = UserPassword.getText().toString();
                String Confpass = UserConfromPassword.getText().toString();

                if(UserName.getEditText().getText().toString().trim().length()!=0 &&  UserPhoneNumber.getEditText().getText().toString().trim().length()!=0 &&UserPassword.getText().toString().length()!=0
                        && UserConfromPassword.getText().toString().length()!=0) {
                    if(pass.equals(Confpass)){

                        loginPojoClass = new LoginPojoClass(username,Confpass,phone);

                        boolean status =  dataBaseSource.InsertLoginValue(loginPojoClass);

                        if(status){
                            Toast.makeText(context, "Register SuccessFull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }else {
                            Toast.makeText(context, "register Fail ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, "Password Doesnot match ", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    if (TextUtils.isEmpty(username)) {
                        UserName.setError("Requird");
                    }
                    if (TextUtils.isEmpty(phone)) {
                        UserPhoneNumber.setError("If you forget password then Phone No is reuqird ");
                    }
                    if (TextUtils.isEmpty(pass)) {
                        UserPassword.setError("Requird");
                    }
                    if (TextUtils.isEmpty(Confpass)) {
                        UserConfromPassword.setError("Requird");
                    }

                }





            }

        });





        return v;
    }

    private void initializeViewinFragment(View v) {
        UserName = v.findViewById(R.id.registration_Useraname);
        UserPhoneNumber = v.findViewById(R.id.registration_PhoneNumber);
        UserPassword = v.findViewById(R.id.registration_Password);
        UserConfromPassword = v.findViewById(R.id.registration_Conform_Password);
        RegistrationDButton = v.findViewById(R.id.Registration_button);
       // ForgetPassword = v.findViewById(R.id.forgetpassword);
        linearLayout = v.findViewById(R.id.registerfragment);
      //  Usertitle = v.findViewById(R.id.registration_userTitle);
      //  PhoneTitle = v.findViewById(R.id.registration_PhoneTitle);
       // ConforPassTitle = v.findViewById(R.id.registration_ConformPasswordTitle);
       // passTitle = v.findViewById(R.id.registration_PasswordTitle);
        WelcomeTitle = v.findViewById(R.id.registration_WelcomeTitle);

        Animation myanimation = AnimationUtils.loadAnimation(context,R.anim.mytransition);
        UserName.startAnimation(myanimation);
        UserPhoneNumber.startAnimation(myanimation);
        UserPassword.startAnimation(myanimation);
        UserConfromPassword.startAnimation(myanimation);
        RegistrationDButton.startAnimation(myanimation);
       // ForgetPassword.startAnimation(myanimation);
//        Usertitle.startAnimation(myanimation);
      //  PhoneTitle.startAnimation(myanimation);
     //   ConforPassTitle.startAnimation(myanimation);
     //   passTitle.startAnimation(myanimation);
        WelcomeTitle.startAnimation(myanimation);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String name) {
        if (mListener != null) {
            mListener.onFragmentInteraction(name);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }


    @Override
    public void onStop() {
        super.onStop();
//        mListener.onFragmentInteraction(Name);
       // Toast.makeText(context, "onStop", Toast.LENGTH_SHORT).show();
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String name);

    }
}

