package com.example.user.touremate;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.DataBase.DataBaseSource;
import com.example.user.touremate.DataBase.LoginPojoClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private EditText UserNameET;
    private EditText PasswordET;
    private TextView Register;
    private TextView user;
    private TextView pass;
    private Button login;
    private FrameLayout frameLayout;
    private DataBaseSource dataBaseSource;
    private boolean loadAlrearylogin;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferencesOne;
    private  SharedPreferences.Editor editorOne;
    private boolean Contaiatologin ;
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    private LoginPojoClass loginPojoClass;
    private TextView ForgetPassword;
    private ArrayList<LoginPojoClass>loginPojoClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializitionView();

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreferencesOne = getSharedPreferences("UserIDAsForeignKey",MODE_PRIVATE);
        editorOne = sharedPreferencesOne.edit();



        boolean finish = getIntent().getBooleanExtra("finish", false);
        if(finish){
            Contaiatologin=false;
            editor.clear();
            editor.commit();
            editor.putBoolean("autologin",Contaiatologin);
            editor.commit();
        }

        Contaiatologin = sharedPreferences.getBoolean("autologin",false);

       if(Contaiatologin){
           Toast.makeText(this, "Already Loged in", Toast.LENGTH_SHORT).show();
           startActivity(new Intent(MainActivity.this, ChooseActivity.class));
        }
            dataBaseSource = new DataBaseSource(this);
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                  //  user.setEnabled(false);
                    UserNameET.setEnabled(false);
                    PasswordET.setEnabled(false);
                    Register.setEnabled(false);
                 //   pass.setEnabled(false);
                    login.setEnabled(false);


                    RegistrationFragment fragment = new RegistrationFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(R.anim.from_right, R.anim.form_left, R.anim.from_right, R.anim.form_left);
                    ft.addToBackStack(null);
                    Register.setEnabled(false);
                    ft.add(R.id.fragmentContainer, fragment, "Register").commit();
                }
            });



        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(MainActivity.this,ForgetPasswordActivity.class));

                }
        });

    }


    private void InitializitionView() {
        UserNameET = findViewById(R.id.login_useraNameID);
        PasswordET = findViewById(R.id.login_userPasswordID);
        Register = findViewById(R.id.create_new_oneID);
        ForgetPassword = findViewById(R.id.forgetpassword);
        login = findViewById(R.id.button);
        frameLayout = findViewById(R.id.fragmentContainer);

        Animation myanimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        UserNameET.startAnimation(myanimation);
        PasswordET.startAnimation(myanimation);
        Register.startAnimation(myanimation);
        ForgetPassword.startAnimation(myanimation);

        login.startAnimation(myanimation);
        relativeLayout = findViewById(R.id.mymainactivityId);
//        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();
    }

    public void Login(View view) {


        String Username = UserNameET.getText().toString();
        String UserPassword = PasswordET.getText().toString();

        Contaiatologin = true;
        editor.clear();
        editor.commit();
        editor.putBoolean("autologin",Contaiatologin);
        editor.commit();

        if((UserNameET.getText().toString().length() == 0) || (PasswordET.getText().toString().length()==0)){

            if(TextUtils.isEmpty(Username)) {
                UserNameET.setError("Must fill up ");
            }
            if(TextUtils.isEmpty(UserPassword)){
                PasswordET.setError("Must fill up");
        }
            Toast.makeText(this, "Must be fill up", Toast.LENGTH_SHORT).show();
        }else if(UserNameET.getText().toString().length() == 0){
            if(TextUtils.isEmpty(Username)) {
                UserNameET.setError("Must fill up ");
            }
        }else if(PasswordET.getText().toString().length()==0) {
            if (TextUtils.isEmpty(Username)) {
                PasswordET.setError("Must fill up");
            }
        }

        else{
            boolean status = dataBaseSource.MatchUserANDPassword(Username,UserPassword);
            int ID  = dataBaseSource.Id;
            // Toast.makeText(this, "ID"+ID, Toast.LENGTH_SHORT).show();
             editorOne.clear();
             editorOne.commit();
             editorOne.putInt("IDD",ID);
             editorOne.commit();
            if(status){
                startActivity(new Intent(this,ChooseActivity.class));
            }
            else {
                Toast.makeText(this, "Faild", Toast.LENGTH_SHORT).show();
            }
        }


       // Toast.makeText(this, "Idd "+ID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(String name) {
       // Toast.makeText(this, "mainActivity"+name, Toast.LENGTH_SHORT).show();
//        user.setEnabled(true);
//        UserNameET.setEnabled(true);
//        PasswordET.setEnabled(true);
//        Register.setEnabled(true);
//        pass.setEnabled(true);
//        login.setEnabled(true);
    }


}


