package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button spell1, spell2;
    private SpellDialog spellDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spell1 = findViewById(R.id.btn_spell1);
        spell2 = findViewById(R.id.btn_spell2);

        spell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spellDialog = new SpellDialog(MainActivity.this);
                spellDialog.callDialog();
            }
        });

        spell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spellDialog = new SpellDialog(MainActivity.this);
                spellDialog.callDialog();
            }
        });
    }


}
