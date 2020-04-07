package com.example.spellandrtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CurrentData[] data;
    CurrentData[] champLiine = new CurrentData[5]; //0 : 탑, 1 : 정글, 2 : 미드, 3 : 원딜, 4 : 서폿
    ImageView img_spell1_t,img_spell1_j,img_spell1_m,img_spell1_a,img_spell1_s,img_spell2_t,img_spell2_j,img_spell2_m,img_spell2_a,img_spell2_s;
    LinearLayout top_spell1, top_spell2, jungle_spell1, jungle_spell2, mid_spell1, mid_spell2, ad_spell1, ad_spell2, sup_spell1, sup_spell2;
    TextView txt_tspell1, txt_tspell2, txt_jspell1, txt_jspell2, txt_mspell1, txt_mspell2, txt_aspell1, txt_aspell2, txt_sspell1, txt_sspell2;
    Boolean[] checkTime = new Boolean[10];
    Boolean[] checkItem = new Boolean[5]; //아이오니아 아이템 클릭 시 true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        top_spell1 = findViewById(R.id.top_spell1);
        top_spell2 = findViewById(R.id.top_spell2);
        txt_tspell1 = findViewById(R.id.txt_tspell1);
        txt_tspell2 = findViewById(R.id.txt_tspell2);
        jungle_spell1 = findViewById(R.id.jungle_spell1);
        jungle_spell2 = findViewById(R.id.jungle_spell2);
        txt_jspell1 = findViewById(R.id.txt_jspell1);
        txt_jspell2 = findViewById(R.id.txt_jspell2);
        mid_spell1 = findViewById(R.id.mid_spell1);
        mid_spell2 = findViewById(R.id.mid_spell2);
        txt_mspell1 = findViewById(R.id.txt_mspell1);
        txt_mspell2 = findViewById(R.id.txt_mspell2);
        ad_spell1 = findViewById(R.id.ad_spell1);
        ad_spell2 = findViewById(R.id.ad_spell2);
        txt_aspell1 = findViewById(R.id.txt_aspell1);
        txt_aspell2 = findViewById(R.id.txt_aspell2);
        sup_spell1 = findViewById(R.id.sup_spell1);
        sup_spell2 = findViewById(R.id.sup_spell2);
        txt_sspell1 = findViewById(R.id.txt_sspell1);
        txt_sspell2 = findViewById(R.id.txt_sspell2);

        //TODO : 아이템 선택 가능하게 하기

        for (int i = 0; i < 10; i++) {
            checkTime[i] = false;
        }

        Intent intent = getIntent();
        data = (CurrentData[]) intent.getSerializableExtra("data");
        setChampLine(data);

        setImage(img_spell1_t,img_spell2_t,champLiine[0]);
        setImage(img_spell1_j,img_spell2_j,champLiine[1]);
        setImage(img_spell1_m,img_spell2_m,champLiine[2]);
        setImage(img_spell1_a,img_spell2_a,champLiine[3]);
        setImage(img_spell1_s,img_spell2_s,champLiine[4]);

        img_spell1_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[0]) {
                    checkTime[0] = false;
                    setCoolDown(ad_spell1, txt_aspell1, (int) champLiine[3].getSpell1id(), 0, champLiine[3]);
                }
                else {
                    checkTime[0] = true;
                    setCoolDown(ad_spell1, txt_aspell1, (int) champLiine[3].getSpell1id(), 0,champLiine[3]);
                }
            }
        });

        img_spell2_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[1]) {
                    checkTime[1] = false;
                    setCoolDown(ad_spell2, txt_aspell2, (int) champLiine[3].getSpell2id(), 1, champLiine[3]);
                }
                else {
                    checkTime[1] = true;
                    setCoolDown(ad_spell2, txt_aspell2, (int) champLiine[3].getSpell2id(), 1, champLiine[3]);
                }
            }
        });

        img_spell1_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[2]) {
                    checkTime[2] = false;
                    setCoolDown(sup_spell1, txt_sspell1, (int) champLiine[4].getSpell1id(), 2, champLiine[4]);
                }
                else {
                    checkTime[2] = true;
                    setCoolDown(sup_spell1, txt_sspell1, (int) champLiine[4].getSpell1id(), 2, champLiine[4]);
                }
            }
        });

        img_spell2_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[3]) {
                    checkTime[3] = false;
                    setCoolDown(sup_spell2, txt_sspell2, (int) champLiine[4].getSpell2id(), 3, champLiine[4]);
                }
                else {
                    checkTime[3] = true;
                    setCoolDown(sup_spell2, txt_sspell2, (int) champLiine[4].getSpell2id(), 3, champLiine[4]);
                }
            }
        });

        img_spell1_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[4]) {
                    checkTime[4] = false;
                    setCoolDown(mid_spell1, txt_mspell1, (int) champLiine[2].getSpell1id(), 4, champLiine[2]);
                }
                else {
                    checkTime[4] = true;
                    setCoolDown(mid_spell1, txt_mspell1, (int) champLiine[2].getSpell1id(), 4, champLiine[2]);
                }
            }
        });

        img_spell2_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[5]) {
                    checkTime[5] = false;
                    setCoolDown(mid_spell2, txt_mspell2, (int) champLiine[2].getSpell2id(), 5, champLiine[2]);
                }
                else {
                    checkTime[5] = true;
                    setCoolDown(mid_spell2, txt_mspell2, (int) champLiine[2].getSpell2id(), 5, champLiine[2]);
                }
            }
        });

        img_spell1_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[6]) {
                    checkTime[6] = false;
                    setCoolDown(jungle_spell1, txt_jspell1, (int) champLiine[1].getSpell1id(), 6, champLiine[1]);
                }
                else {
                    checkTime[6] = true;
                    setCoolDown(jungle_spell1, txt_jspell1, (int) champLiine[1].getSpell1id(), 6 ,champLiine[1]);
                }
            }
        });

        img_spell2_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[7]) {
                    checkTime[7] = false;
                    setCoolDown(jungle_spell2, txt_jspell2, (int) champLiine[1].getSpell2id(), 7, champLiine[1]);
                }
                else {
                    checkTime[7] = true;
                    setCoolDown(jungle_spell2, txt_jspell2, (int) champLiine[1].getSpell2id(), 7, champLiine[1]);
                }
            }
        });

        img_spell1_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[8]) {
                    checkTime[8] = false;
                    setCoolDown(top_spell1, txt_tspell1, (int) champLiine[0].getSpell1id(), 8, champLiine[0]);
                }
                else {
                    checkTime[8] = true;
                    setCoolDown(top_spell1, txt_tspell1, (int) champLiine[0].getSpell1id(), 8, champLiine[0]);
                }
            }
        });

        img_spell2_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[9]) {
                    checkTime[9] = false;
                    setCoolDown(top_spell2, txt_tspell2, (int) champLiine[0].getSpell2id(), 9, champLiine[0]);
                }
                else {
                    checkTime[9] = true;
                    setCoolDown(top_spell2, txt_tspell2, (int) champLiine[0].getSpell2id(), 9, champLiine[0]);
                }
            }
        });

    }

    void setImage(ImageView spell1, ImageView spell2, CurrentData mdata) {

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


    }


    void setCoolDown(final LinearLayout setvisible, TextView text, int spell, final int check, CurrentData data) {

        //함수 인자 boolean item 추가
        //true 시 10% 쿨감

        //TODO : 아이템 적용해서 쿨타임 줄이기

        boolean isTrue = false;

        long[] rune = new long[]{data.getPerkid1(), data.getPerkid2(),data.getPerkid3(),data.getPerkid4(),data.getPerkid5(),data.getPerkid6(),data.getPerkid7(),data.getPerkid8(),data.getPerkid9()};

        for (int i = 0; i < 9; i++) {
            if(rune[i] == 8347) {
                isTrue = true;
                break;
            }
        }

        final TextView cooldownTxt = text;
        final int i = check;

        if(spell != 0) {
            SpellCoolDownList cool = new SpellCoolDownList();

            long time = cool.getCool(spell);

            if(isTrue) {
                time = (long) (time * 0.95);
            }

            CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long l) {
                    if(checkTime[i]) {
                        setvisible.setVisibility(View.VISIBLE);
                        cooldownTxt.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
                    }
                    else {
                        setvisible.setVisibility(View.GONE);
                        cancel();

                        //TODO : 소리나오게 하기
                    }
                }

                @Override
                public void onFinish() {
                    checkTime[check] = false;
                    setvisible.setVisibility(View.GONE);
                }
            }.start();
        }

    }

    void setChampLine(CurrentData data[]) {

        int[] pass = new int[5];
        for (int i = 0; i < 5; i++) {
            pass[i] = -1;
        }

        for (int i = 0; i < 5; i++) { // 강타 정글로 정하기
            if(data[i].getSpell1id() == 11 || data[i].getSpell2id() == 11)  {
                champLiine[1] = data[i];
                pass[i] = i;
                break;
            }
        }

        ChampionLineData championLineData = new ChampionLineData();
        for (int i = 0; i < 5; i++) { //원딜 결정
            if(championLineData.getLine((int)data[i].getChampionid()) == 4 && i != pass[0]) {
                champLiine[3] = data[i];
                pass[i] = i;
                break;
            }
        }

        for (int i = 0; i < 5; i++) { //서폿 결정
            if(championLineData.getLine((int)data[i].getChampionid()) == 5 && i != pass[0] && i != pass[1]) {
                champLiine[4] = data[i];
                pass[i] = i;
                break;
            }
        }

        for (int i = 0; i < 5; i++) { //탑 결정
            if(championLineData.getLine((int)data[i].getChampionid()) == 1 && i != pass[0] && i != pass[1] && i != pass[2]) {
                champLiine[0] = data[i];
                pass[i] = i;
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            if(champLiine[i] == null) {
                for (int j = 0; j < 5; j++) {
                    if (pass[j] == -1 && champLiine[i] == null) {
                        champLiine[i] = data[j];
                        pass[j] = i;
                    }
                }
            }
        }
    }
}
