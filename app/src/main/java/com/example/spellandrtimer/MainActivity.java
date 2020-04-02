package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    CurrentData[] data;
    ImageView img_spell1_t,img_spell1_j,img_spell1_m,img_spell1_a,img_spell1_s,img_spell2_t,img_spell2_j,img_spell2_m,img_spell2_a,img_spell2_s,img_r_t,img_r_j,img_r_m,img_r_a,img_r_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_r_a = findViewById(R.id.img_r_a);
        img_r_j = findViewById(R.id.img_r_j);
        img_r_m = findViewById(R.id.img_r_m);
        img_r_s = findViewById(R.id.img_r_s);
        img_r_t = findViewById(R.id.img_r_t);
        img_spell1_a = findViewById(R.id.img_spell1_a);
        img_spell1_j = findViewById(R.id.img_spell1_j);
        img_spell1_m = findViewById(R.id.img_spell1_m);
        img_spell1_s = findViewById(R.id.img_spell1_s);
        img_spell1_t = findViewById(R.id.img_spell1_t);
        img_spell2_a = findViewById(R.id.img_spell2_a);
        img_spell2_j = findViewById(R.id.img_spell2_j);
        img_spell2_m = findViewById(R.id.img_spell2_m);
        img_spell2_s = findViewById(R.id.img_spell2_s);
        img_spell2_t = findViewById(R.id.img_spell2_t);

        Intent intent = getIntent();
        data = (CurrentData[]) intent.getSerializableExtra("data");

        setImage(img_spell1_a,img_spell2_a,img_r_a,data[0]);
        setImage(img_spell1_j,img_spell2_j,img_r_j,data[1]);
        setImage(img_spell1_m,img_spell2_m,img_r_m,data[2]);
        setImage(img_spell1_s,img_spell2_s,img_r_s,data[3]);
        setImage(img_spell1_t,img_spell2_t,img_r_t,data[4]);

    }

    void setImage(ImageView spell1, ImageView spell2, ImageView r, CurrentData mdata) {

        long mspell1 = mdata.getSpell1id();
        long mspell2 = mdata.getSpell2id();
        long mcham = mdata.getChampionid();

        SpellList spellList = new SpellList();
        ChampionList championList = new ChampionList();

        String resspell1 = "@drawable/" + spellList.getSpell((int) mspell1);
        String resspell2 = "@drawable/" + spellList.getSpell((int) mspell2);
        String rescham = "@drawable/" + championList.getName((int) mcham);
        String packName = this.getPackageName();

        spell1.setImageResource(getResources().getIdentifier(resspell1,"drawable", packName));
        spell2.setImageResource(getResources().getIdentifier(resspell2,"drawable", packName));
        r.setImageResource(getResources().getIdentifier(rescham,"drawable", packName));


    }


}
