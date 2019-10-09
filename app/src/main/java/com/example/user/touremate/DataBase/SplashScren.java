package com.example.user.touremate.DataBase;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.example.user.touremate.R;
import com.example.user.touremate.ShowEventActivity;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by User on 4/14/2018.
 */

public class SplashScren extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setLogoSplash(R.mipmap.ic_launcher_round);
        configSplash.setAnimLogoSplashDuration(2000);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);


        configSplash.setOriginalHeight(400);
        configSplash.setOriginalWidth(400);
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3);
        configSplash.setPathSplashStrokeColor(R.color.colorAccent);
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.colourWhite);


        configSplash.setTitleSplash("Developed by Mehedi ");
        configSplash.setTitleTextColor(R.color.colourWhite);
        configSplash.setTitleTextSize(20f);
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashScren.this,ShowEventActivity.class));
    }
}
