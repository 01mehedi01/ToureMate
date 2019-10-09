package com.example.user.touremate.Weather;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.touremate.R;
import com.example.user.touremate.WeatherActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForcastWeather extends Fragment {

    private RecyclerView recyclerView;
    private CurrentWeatherService service;
    private ForCustWeatherResponse forcastWeatherResponse;
    private ArrayList<ForcastDetails> forcastDetailsArray = new ArrayList<>();
    private ForcastDetails forcastDetails;
    private WeatherAdapter forcastAdapter;
    private Calendar calendar;

    private String iconString, statusString, dayString, tempString, minTString, maxTString, sunRiseString, sunSetString;
    public ForcastWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout forca this fragment
        View view = inflater.inflate(R.layout.fragment_forcast_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        calendar = Calendar.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CurrentWeatherService.class);
        @SuppressLint("DefaultLocale") String endUrl = String.format("forecast?lat=%f&lon=%f&units=%s&appid=%s",
                WeatherActivity.latitude,
                WeatherActivity.longitude,
                WeatherActivity.units,
                getString(R.string.weather_api_key));
        Call<ForCustWeatherResponse> call = service.getForcustWeather(endUrl);
        call.enqueue(new Callback<ForCustWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForCustWeatherResponse> call, @NonNull Response<ForCustWeatherResponse> response) {
                if(response.code() == 200){
                    forcastWeatherResponse = response.body();
                    ArrayList<ForcastDetails> details = new ArrayList<>();
                    for( int i = 0; i < forcastWeatherResponse.getList().size(); i++) {
                        iconString = forcastWeatherResponse.getList().get(i).getWeather().get(0).getIcon();

                        statusString = forcastWeatherResponse.getList().get(i).getWeather().get(0).getDescription();

                        long unix_day = forcastWeatherResponse.getList().get(i).getDt();
                        Date date = new Date(unix_day*1000L);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMd, hha");
                        SimpleDateFormat df2 = new SimpleDateFormat("hha");
                        String todayTime = df2.format(date.getTime());

                        SimpleDateFormat dfMy = new SimpleDateFormat("d");
                        int weatherDate = Integer.parseInt(dfMy.format(date.getTime()));
                        int sysDate = Integer.parseInt(dfMy.format(calendar.getTime()));
                        if( weatherDate == sysDate){
                            dayString = "Today, "+todayTime;
                        }
                        else if( (weatherDate-1) == sysDate){
                            dayString = "Tomorrow, "+todayTime;
                        }
                        else {
                            dayString = df.format(date.getTime());
                        }

                        tempString = String.valueOf(forcastWeatherResponse.getList().get(i).getMain().getTemp().intValue());

                        minTString = String.valueOf(forcastWeatherResponse.getList().get(i).getMain().getTempMin().intValue());

                        maxTString = String.valueOf(forcastWeatherResponse.getList().get(i).getMain().getTempMax().intValue());

                        sunRiseString = String.valueOf(forcastWeatherResponse.getList().get(i).getMain().getHumidity().intValue());

                        sunSetString = String.valueOf(forcastWeatherResponse.getList().get(i).getMain().getPressure().intValue());

                        forcastDetails = new ForcastDetails(iconString,statusString,dayString,tempString,minTString,maxTString,sunRiseString,sunSetString);
                        details.add(forcastDetails);
                    }
                    forcastDetailsArray = details;
                    forcastAdapter = new WeatherAdapter(getActivity().getApplicationContext(),forcastDetailsArray);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(llm);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(forcastAdapter);

                }
            }



            @Override
            public void onFailure(@NonNull Call<ForCustWeatherResponse> call, @NonNull Throwable t) {
            }
        });
        return view;
    }

}
