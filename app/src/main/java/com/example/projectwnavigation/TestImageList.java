package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import models.TestEntity;

public class TestImageList extends AppCompatActivity {
    GridView gridView;
    ArrayList<TestEntity> list;
    Test_image_list_adapter adapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_list);
    }
}
