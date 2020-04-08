package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText edt_id;
    Button btn_next;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_id = findViewById(R.id.edt_id);
        btn_next = findViewById(R.id.btn_next);
        textView = findViewById(R.id.textview);

        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edt_id.getText().toString();

                if(name.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "소환사명을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, RiotApiConnet.class);
                    intent.putExtra("name", edt_id.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

