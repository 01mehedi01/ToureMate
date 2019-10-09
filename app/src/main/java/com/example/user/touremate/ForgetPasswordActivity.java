package com.example.user.touremate;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextInputLayout UserPhoneNumber;
    private ArrayList<LoginPojoClass>loginPojoClasses;
    private DataBaseSource dataBaseSource;
    private Button forgetbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);


        initilizeView();

        Toolbar toolbar = findViewById(R.id.forgetpasstol);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.super.onBackPressed();
            }
        });

    }

    private void initilizeView() {

        UserPhoneNumber = findViewById(R.id.forget_PhoneNumber);
        forgetbtn = findViewById(R.id.forgetpassbtn);
        dataBaseSource = new DataBaseSource(this);

        Animation myanimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        UserPhoneNumber.startAnimation(myanimation);
        forgetbtn.startAnimation(myanimation);

    }

    public void SearchByphone(View view) {
        String phone = UserPhoneNumber.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            UserPhoneNumber.setError("Required");
        } else {
        }

        loginPojoClasses = new ArrayList<>();
        loginPojoClasses =   dataBaseSource.ShowUserAndPassByPhoneNumber(phone);


        if(loginPojoClasses.size() !=0 ){

            String username = loginPojoClasses.get(0).getUserName();
            String pasword = loginPojoClasses.get(0).getUserPassword();



            AlertDialog.Builder  dailog  = new AlertDialog.Builder(this);

                LayoutInflater inflater = LayoutInflater.from(this);
                final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.forgetpassword_alart_dailog,null,false);

                final TextView ShowUserName= ll.findViewById(R.id.alart_show_user_name);
                final TextView TitleUser= ll.findViewById(R.id.alart_username_for_hide);
                final TextView TitlePass= ll.findViewById(R.id.alart_password_for_hide);
                final TextView ShowPassword = ll.findViewById(R.id.alart_show_password);



                TitleUser.setEnabled(false);
                TitlePass.setEnabled(false);

                dailog.setView(ll);


             TitleUser.setEnabled(true);
             TitlePass.setEnabled(true);

             ShowUserName.setText(username);
             ShowPassword.setText(pasword);


            dailog.setNegativeButton("Close",null);
            dailog.setView(ll);
            dailog.show();



        }else{
            Toast.makeText(ForgetPasswordActivity.this, "Phone number is not Valid", Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem logoutItem = menu.findItem(R.id.manu_logout_ID);
//
//        return super.onPrepareOptionsMenu(menu);
//    }


}
