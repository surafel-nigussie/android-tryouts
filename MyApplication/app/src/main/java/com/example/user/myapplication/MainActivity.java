package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Issues");
//        toolbar.setSubtitle("Alamata Town");
//
//        TextView tv_header = (TextView) findViewById(R.id.scroll_item_header_id);
//        tv_header.setText("What is Lorem?");
//
//        TextView tv_detail = (TextView) findViewById(R.id.scroll_item_detail_id);
//        tv_detail.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");

    }
}
