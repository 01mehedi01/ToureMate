package com.example.user.touremate.Classes;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.user.touremate.ChooseActivity;
import com.example.user.touremate.R;

public class Sliderclass extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.travel,R.drawable.travel1,R.drawable.travel2,R.drawable.travel3};

    public Sliderclass(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.single_slide_picture,null);
        ImageView imageView = view.findViewById(R.id.iv_auto_image_slider);

        imageView.setImageResource(images[position]);
        ViewPager vp = (ViewPager) container;
        vp.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.mytransition));
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
