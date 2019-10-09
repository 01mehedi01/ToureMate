package com.example.user.touremate.DataBase;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by User on 4/14/2018.
 */

public class AnimatonClass {

    public static void animate(RecyclerView.ViewHolder Holder , boolean Down){
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(Holder.itemView,"translationY",Down==true?200:-200,0);
        animatorX.setDuration(1000);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(Holder.itemView,"translationX",-50,50,-30,30,-20,20,-5,5,0);
        animatorY.setDuration(1000);

        animatorSet.playTogether(animatorX,animatorY);
        animatorSet.start();
    }
}
