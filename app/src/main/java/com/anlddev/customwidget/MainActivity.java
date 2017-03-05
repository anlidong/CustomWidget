package com.anlddev.customwidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.anlddev.customwidget.demo.StatusSwitchActivity;
import com.anlddev.customwidget.demo.fragmenttabhost.FragmentTabActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openStatusSwitch(View view) {
        Intent intent = new Intent(this, StatusSwitchActivity.class);
        startActivity(intent);
    }

    public void openFragmentTab(View view) {
        Intent intent = new Intent(this, FragmentTabActivity.class);
        startActivity(intent);
    }
}
