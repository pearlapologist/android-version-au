package com.example.projectwnavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import models.MyDataProvider;
import models.TestEntity;

public class Test_image_list_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    MyDataProvider provider;
    private ArrayList<TestEntity> arrayList;

    public Test_image_list_adapter(Context context, int layout, ArrayList<TestEntity> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
        this.provider  = new MyDataProvider(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolderTestImage holder = new MyViewHolderTestImage();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtTitle = row.findViewById(R.id.test_image_items_title);
            holder.imageView = row.findViewById(R.id.test_image_items_photo);

            row.setTag(holder);
        } else {
            holder = (MyViewHolderTestImage) row.getTag();
         }
        TestEntity entity = arrayList.get(position);

         holder.txtTitle.setText(entity.getName());
        holder.imageView.setImageBitmap(provider.decodeByteToBitmap(entity.getImage()));

        return row;
    }




    private class MyViewHolderTestImage {
        ImageView imageView;
        TextView txtTitle;
    }
}
