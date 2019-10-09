package com.example.user.touremate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TermsAndConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);


        Toolbar toolbar = findViewById(R.id.termtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsAndConditionActivity.super.onBackPressed();
            }
        });

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

                Intent intent = new Intent(TermsAndConditionActivity.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_setting_ID:
                break;

            case R.id.manu_home_page_ID:
                Intent intentt = new Intent(TermsAndConditionActivity.this, ChooseActivity.class);
                intentt.putExtra("finish", true);
                intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentt);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
