package com.example.user.touremate.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 1/4/2018.
 */

public interface CurrentWeatherService {

  @GET("")
  Call<CurrentWeatherResponse> getCurrentWeather(@Url String endUrl);
  @GET("")
  Call<ForCustWeatherResponse> getForcustWeather(@Url String forcustUrl);

}
