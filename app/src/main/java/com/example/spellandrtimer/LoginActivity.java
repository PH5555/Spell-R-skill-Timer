package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText edt_id;
    Button btn_next;
    TextView textView;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    ArrayList arrayList = new ArrayList<>();
    String platform;
    final int UNCONNECTED = 3;
    private AdView adBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_id = findViewById(R.id.edt_id);
        btn_next = findViewById(R.id.btn_next);
        textView = findViewById(R.id.textview);
        spinner = findViewById(R.id.spinner);

        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        arrayList.add("KR");
        arrayList.add("JP");
        arrayList.add("EUW");
        arrayList.add("EUNE");
        arrayList.add("BR");
        arrayList.add("LAN");
        arrayList.add("LAS");
        arrayList.add("NA");
        arrayList.add("OCE");
        arrayList.add("RU");
        arrayList.add("TR");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(arrayAdapter);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        adBanner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adBanner.loadAd(adRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                platform = (String) arrayList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(InternetCheck()) {
                    String name = edt_id.getText().toString();

                    if(name.getBytes().length <= 0) {
                        Toast.makeText(getApplicationContext(), "소환사명을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, RiotApiConnet.class);
                        intent.putExtra("name", edt_id.getText().toString());
                        intent.putExtra("platform", platform);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentDialog overlay = new FragmentDialog();
                overlay.show(fm, "FragmentDialog");
            }
        });
    }

    public boolean InternetCheck() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == UNCONNECTED) {
            Toast.makeText(this, "인터넷 연결을 확인해주세요!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}

