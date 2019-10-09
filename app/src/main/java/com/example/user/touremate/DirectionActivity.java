package com.example.user.touremate;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.touremate.Direction.DirectionResponse;
import com.example.user.touremate.Direction.DirectionService;
import com.example.user.touremate.Direction.GeofenceTransitionService;
import com.example.user.touremate.Direction.MyItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnPolylineClickListener {
    private boolean islogedin = true;
    private GoogleMap map;
    public  static final String BaseUrl = "https://maps.googleapis.com/maps/api/";
    private ClusterManager<MyItem> clusterManager;
    private List<MyItem> items = new ArrayList<>();
    private FusedLocationProviderClient client;
    private Location lastLocation;
    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;
    private  DirectionService directionService;
    private int noofSteps=0;
    private Button ChangeDirectionBtn;
    private Button InstructionBtn;
    private Button btnGeofence;
    private String[]instructions;
    private int rootCount = 0;
    private LatLng Destination;
    private double lat,lng;
    private int dirCount = 0;
    private Button btnNextRoute;
    private Button btnDirection;
    private Button btnInstruction;
    private GeofencingClient clientgeo;
    private ArrayList<Geofence> geofences;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_direction);


        Toolbar toolbar = findViewById(R.id.derectiontoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectionActivity.super.onBackPressed();
            }
        });





        btnInstruction = findViewById(R.id.btnInstruction_Id);
        btnNextRoute = findViewById(R.id.btnnextRoute_Id);
        btnDirection = findViewById(R.id.btnDirection_Id);
        btnGeofence=findViewById(R.id.btnGeofence_Id);
        client = LocationServices.getFusedLocationProviderClient(this);
        geoDataClient = Places.getGeoDataClient(this);
        placeDetectionClient = Places.getPlaceDetectionClient(this);
        getLastLocation();

        geofences = new ArrayList<>();
        pendingIntent = null;
        clientgeo = LocationServices.getGeofencingClient(DirectionActivity.this);

        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(true);
        options.compassEnabled(true);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapcontainer, mapFragment);
        transaction.commit();
        mapFragment.getMapAsync(this);

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create()
                ).build();
        directionService = retrofit.create(DirectionService.class);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    lastLocation = task.getResult();
                    lat = lastLocation.getLatitude();
                    lng = lastLocation.getLongitude();
                    LatLng latLng = new LatLng(lat, lng);
                    map.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 17));

                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        btnDirection.setEnabled(true);
        map.setOnPolylineClickListener(this);
        clusterManager = new ClusterManager<MyItem>(this, map);
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                btnGeofence.setEnabled(true);
                Destination = latLng;
                /*items.add(new MyItem(latLng));
                clusterManager.addItems(items);
                clusterManager.cluster();*/
                map.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                dirCount++;
            }
        });


    }

    private void getDirection() {

        String key = getString(R.string.google_direction_api);
        String origin = String.valueOf(lat+","+lng);
        String destination = String.valueOf(Destination.latitude+","+Destination.longitude);
        String UrlString = String.format("directions/json?origin=%s&destination=%s&alternatives=true&key=%s",origin,destination,key);
        Toast.makeText(this, "Destination =>\t"+Destination.latitude+","+Destination.longitude, Toast.LENGTH_SHORT).show();
        Call<DirectionResponse> directionResponseCall = directionService.getDirection(UrlString);

        directionResponseCall.enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {

                if(response.code()==200){

                    btnInstruction.setEnabled(true);
                    btnNextRoute.setEnabled(true);
                    map.clear();

                    DirectionResponse directionResponse = response.body();

                    List<DirectionResponse.Step> steps = directionResponse.getRoutes().get(noofSteps).getLegs().get(0).getSteps();
                    rootCount = directionResponse.getRoutes().size();

                    LatLng latLng = new LatLng(steps.get(0).getStartLocation().getLat()
                            , steps.get(0).getStartLocation().getLng());
                    map.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    map.addMarker(new MarkerOptions().position(Destination)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    instructions = new String[steps.size()];


                    for(int i=0;i<steps.size();i++){

                       /* LatLng start = new LatLng(steps.get(i).getStartLocation().getLat(),steps.get(i).getStartLocation().getLng());
                        LatLng end = new LatLng(steps.get(i).getEndLocation().getLat(),steps.get(i).getEndLocation().getLng());*/
                        double startlat = steps.get(i).getStartLocation().getLat();
                        double startlon = steps.get(i).getStartLocation().getLng();

                        double endlat = steps.get(i).getEndLocation().getLat();
                        double endlon = steps.get(i).getEndLocation().getLng();
                        String instruction = String.valueOf(Html.fromHtml(steps.get(i).getHtmlInstructions()));
                        instructions[i] = instruction;

                        Polyline polyline = map.addPolyline(new PolylineOptions()
                                .add(new LatLng(startlat, startlon))
                                .add(new LatLng(endlat, endlon))
                                .clickable(true));


                        polyline.setTag(instruction);
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void FindLocation(MenuItem item) {
        showPlace();
    }

    private void showPlace() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 2);
            return;
        }
        placeDetectionClient.getCurrentPlace(null).addOnCompleteListener(this,new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {

                if(task.isSuccessful() && task.getResult() != null){
                    PlaceLikelihoodBufferResponse response = task.getResult();
                    int count = response.getCount();
                    String[] Name = new String[count];
                    String[] address = new String[count];
                    LatLng[] latLngs = new LatLng[count];

                    for(int i=0;i<count;i++){

                        PlaceLikelihood placeLikelihood = response.get(i);

                        Name[i] = (String) placeLikelihood.getPlace().getName();
                        address[i] = (String) placeLikelihood.getPlace().getAddress();
                        latLngs[i] = placeLikelihood.getPlace().getLatLng();
                        items.add(new MyItem(latLngs[i],Name[i],address[i]));
                    }
                    clusterManager.addItems(items);
                    clusterManager.cluster();
                    response.release();
                    OpenDialog(Name,address,latLngs);
                }
            }

        }).addOnFailureListener(this,new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DirectionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OpenDialog(final String[] name, final String[] address, final LatLng[] latLngs) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LatLng latL = latLngs[which];
                String Nam = name[which];
                String Addre = address[which];
                map.addMarker(new MarkerOptions().position(latL).title(Nam).snippet(Addre));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latL, 20));
            }
        };
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Take a Place")
                .setItems(name,listener)
                .show();

    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        Toast.makeText(this, polyline.getTag().toString(), Toast.LENGTH_SHORT).show();
    }




    public void showInstuction(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(instructions,null)
                .show();
    }

    public void showDirection(View view) {

        if (dirCount != 0) {
            getDirection();
        } else {
            Toast.makeText(this, "select your destination first", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextRoute(View view) {
        if(noofSteps<rootCount){
            getDirection();
            noofSteps++;
        }
        if(noofSteps==rootCount){
            noofSteps = 0;
        }
    }

    public void addGeofence(View view) {

        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("be sure to add geofence this area");
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.geofence_img);
        dialog.setView(imageView);

        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Geofence geofence1 = new Geofence.Builder()
                        .setRequestId("DBBL Bhobon karwan bazar")
                        .setCircularRegion(Destination.latitude, Destination.longitude, 200)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                        .setExpirationDuration(2 * 60 * 60 * 1000)
                        .build();
                geofences.add(geofence1);
                if (ActivityCompat.checkSelfPermission(DirectionActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    permission();
                }
                clientgeo.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
            }
            public void permission() {
                ActivityCompat.requestPermissions(DirectionActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }

            private GeofencingRequest getGeofencingRequest(){
                GeofencingRequest.Builder builder=new GeofencingRequest.Builder();
                builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
                builder.addGeofences(geofences);
                return builder.build();
            }
            private PendingIntent getGeofencePendingIntent(){
                if(pendingIntent != null){
                    return pendingIntent;
                }else {
                    Intent intent=new Intent(DirectionActivity.this, GeofenceTransitionService.class);
                    pendingIntent=PendingIntent.getService(DirectionActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    return pendingIntent;
                }
            }
        });
        dialog.setNegativeButton("close",null);
        dialog.setCancelable(false);
        dialog.show();
    }

}

