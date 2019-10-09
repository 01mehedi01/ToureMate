package com.example.user.touremate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touremate.Classes.Sliderclass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ChooseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private RelativeLayout relativeLayout;
    private boolean islogedin = true;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int flag = 0;
    private ImageView textView;
    private Uri fileeee;

    private static final int CONTENT_REQUEST=1337;
    private File output=null;
    private static final int REQUEST_CAPTURE_IMAGE = 100;


    private TextView ShowWelcome;
    private  int chooseUserID;
    private  ImageButton Camera;
    private  ImageButton videos;
    private   Bitmap extrass;
    private AdView mAdView;
    private CircleIndicator indicator;


    private MainActivity mainActivity;
    private Sliderclass adapter;

    private CardView CreateEventcardview,WeatherCardView,DirectionCardview,CameraCardview;


    private ViewPager viewpager;
    private String ImagePath ;
    String imageFilePath;
    File photoFile = null;
    static final int CAPTURE_IMAGE_REQUEST = 1;


    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "VLEMONN";




    private static final String TAG = "CapturePicture";
    static final int REQUEST_PICTURE_CAPTURE = 1;
    private ImageView image;
    private String pictureFilePath;
    private String deviceIdentifier;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        InitializeView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getIntent().getExtras() != null) {

            Bundle p = getIntent().getExtras();
            chooseUserID = p.getInt("UserID");
        }




        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,5000);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            Toast.makeText(this, ""+imgFile, Toast.LENGTH_SHORT).show();
            if(imgFile.exists())            {
              addToGallery();
            }
        }
    }



    private void InitializeView() {



//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-9177620541638751/4045046868");


        mainActivity = new MainActivity();

        CreateEventcardview = findViewById(R.id.createvent);
        WeatherCardView = findViewById(R.id.weather);
        DirectionCardview = findViewById(R.id.direction);
        CameraCardview = findViewById(R.id.camera);

        CreateEventcardview.setOnClickListener(this);
        WeatherCardView.setOnClickListener(this);
        DirectionCardview.setOnClickListener(this);
        CameraCardview.setOnClickListener(this);

        adapter = new Sliderclass(this);

        viewpager =  findViewById(R.id.viewpagerid);


        indicator = findViewById(R.id.indicator);


//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


    }


    private void sendTakePictureIntent() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.user.touremate.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }else{
                Toast.makeText(mainActivity, "Nulll", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addToGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
        Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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
                islogedin = false;
                Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_setting_ID:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
   // @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.direction) {
            startActivity(new Intent(ChooseActivity.this,DirectionActivity.class));

        } else if (id == R.id.nrearestPlace) {

        }else if (id == R.id.about) {
            startActivity(new Intent(ChooseActivity.this,AboutUSActivity.class));

        }else if (id == R.id.termscondition) {
            startActivity(new Intent(ChooseActivity.this,TermsAndConditionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()== R.id.createvent){
            startActivity(new Intent(ChooseActivity.this,CreateATourePlanActivity.class));


        }else if(v.getId()==R.id.weather){
            startActivity(new Intent(ChooseActivity.this,WeatherActivity.class));
        }else if(v.getId()==R.id.direction){

            startActivity(new Intent(ChooseActivity.this,DirectionActivity.class));
        }else if(v.getId()==R.id.camera){

            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(ChooseActivity.this,CameraActivity.class));

            sendTakePictureIntent();


        }



    }




    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String imageFileName = "ZOFTINO_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */storageDir      /* directory */);

        pictureFilePath = image.getAbsolutePath();
        return image;
    }
    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                   sendTakePictureIntent();
            }

        }else{
            Toast.makeText(this, "Request error", Toast.LENGTH_SHORT).show();
        }
    }
    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            ChooseActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewpager.getCurrentItem() == 0){
                       // fadeOutTransformation.transformPage(viewpager,0);
//                        viewpager.startAnimation(AnimationUtils.loadAnimation(ChooseActivity.this,
//                                R.anim.fade_in));

                       // Animation an = myAnimationContainer.getRandomAnimation();
                        viewpager.setCurrentItem(1);
                    }else if(viewpager.getCurrentItem() == 1){
                      //  fadeOutTransformation.transformPage(viewpager,1);
//                        viewpager.startAnimation(AnimationUtils.loadAnimation(ChooseActivity.this,
//                                R.anim.fade_in));
                        viewpager.setCurrentItem(2);
                    }else if(viewpager.getCurrentItem() == 2){
                        //fadeOutTransformation.transformPage(viewpager,1);
//                        viewpager.startAnimation(AnimationUtils.loadAnimation(ChooseActivity.this,
//                                R.anim.fade_in));
                        viewpager.setCurrentItem(3);
                    }
                    else if(viewpager.getCurrentItem() == 3){
                       // fadeOutTransformation.transformPage(viewpager,0);
//                        viewpager.startAnimation(AnimationUtils.loadAnimation(ChooseActivity.this,
//                                R.anim.fade_in));
                        viewpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}