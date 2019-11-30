package com.example.wake.ethiocinemamovie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.wake.ethiocinemamovie.R;


public class SlideShowAdapter extends PagerAdapter {

    int[] images = {
            R.drawable.slide,
            R.drawable.slide,
            R.drawable.slide
    };
    Context ctx;
    LayoutInflater inflater;

    public SlideShowAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_slideshow_layout, container, false);
        ImageView imageView = view.findViewById(R.id.slide_image);

        Glide.with(ctx).load(images[position]).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
