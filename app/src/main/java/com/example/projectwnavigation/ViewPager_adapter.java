package com.example.projectwnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPager_adapter extends PagerAdapter {
    Context context;
    int images[] = {R.drawable.slide1, R.drawable.slide2};
    int titles[] = {R.string.first_slide_title, R.string.second_slide_title};
    int desc[] = {R.string.first_slide_desc, R.string.second_slide_desc};
    LayoutInflater lInflater;

    public ViewPager_adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.on_boarding_slide1, container, false);
        ImageView image = view.findViewById(R.id.slide1_image);
        TextView title= view.findViewById(R.id.slide1_title);
        TextView txtdesc= view.findViewById(R.id.slide1_text);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        txtdesc.setText(desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((RelativeLayout)object);
    }
}
