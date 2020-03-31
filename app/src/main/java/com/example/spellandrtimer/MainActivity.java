package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    CurrentData[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        data = (CurrentData[]) intent.getSerializableExtra("data");
        for (int i = 0; i < 5; i++) {
            Log.e("check", ""+data[i].getName());
        }
    }


}
