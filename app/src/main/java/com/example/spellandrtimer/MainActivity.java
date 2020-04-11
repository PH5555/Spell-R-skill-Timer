package com.example.spellandrtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
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
    ImageView img_top, img_jungle, img_mid, img_ad, img_sup;
    LinearLayout rune_top, shoe_top,rune_jungle, shoe_jungle,rune_mid, shoe_mid,rune_ad, shoe_ad,rune_sup, shoe_sup;
    ImageView t_shoe, j_shoe, m_shoe, a_shoe, s_shoe;
    Boolean[] checkTime = new Boolean[10];
    Boolean[] checkItem = new Boolean[5]; //아이오니아 아이템 클릭 시 true


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("종료하시겠습니까?");
        alert.setMessage("종료하시면 메인화면으로 돌아갑니다.")
             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                     startActivity(intent);
                 }
             })
             .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.cancel(); //TODO:광고삽입
                 }
             });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

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
        img_top = findViewById(R.id.img_top);
        img_jungle = findViewById(R.id.img_jungle);
        img_mid = findViewById(R.id.img_mid);
        img_ad = findViewById(R.id.img_ad);
        img_sup = findViewById(R.id.img_sup);
        rune_top = findViewById(R.id.rune_top);
        shoe_top = findViewById(R.id.shoe_top);
        rune_jungle = findViewById(R.id.rune_jungle);
        shoe_jungle = findViewById(R.id.shoe_jungle);
        rune_mid = findViewById(R.id.rune_mid);
        shoe_mid = findViewById(R.id.shoe_mid);
        rune_ad = findViewById(R.id.rune_ad);
        shoe_ad = findViewById(R.id.shoe_ad);
        rune_sup = findViewById(R.id.rune_sup);
        shoe_sup = findViewById(R.id.shoe_sup);
        t_shoe = findViewById(R.id.t_shoe);
        j_shoe = findViewById(R.id.j_shoe);
        m_shoe = findViewById(R.id.m_shoe);
        a_shoe = findViewById(R.id.a_shoe);
        s_shoe = findViewById(R.id.s_shoe);

        for (int i = 0; i < 10; i++) {
            checkTime[i] = false;
        }
        for (int i = 0; i < 5; i++) {
            checkItem[i] = false;
        }

        Intent intent = getIntent();
        data = (CurrentData[]) intent.getSerializableExtra("data");
        setChampLine(data);

        setImage(img_spell1_t,img_spell2_t, img_top, rune_top, champLiine[0]);
        setImage(img_spell1_j,img_spell2_j, img_jungle, rune_jungle, champLiine[1]);
        setImage(img_spell1_m,img_spell2_m, img_mid, rune_mid, champLiine[2]);
        setImage(img_spell1_a,img_spell2_a, img_ad, rune_ad, champLiine[3]);
        setImage(img_spell1_s,img_spell2_s, img_sup, rune_sup, champLiine[4]);

        img_spell1_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[0]) {
                    checkTime[0] = false;
                    setCoolDown(ad_spell1, txt_aspell1, (int) champLiine[3].getSpell1id(), 0, champLiine[3], checkItem[3]);
                }
                else {
                    checkTime[0] = true;
                    setCoolDown(ad_spell1, txt_aspell1, (int) champLiine[3].getSpell1id(), 0,champLiine[3], checkItem[3]);
                }
            }
        });

        img_spell2_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[1]) {
                    checkTime[1] = false;
                    setCoolDown(ad_spell2, txt_aspell2, (int) champLiine[3].getSpell2id(), 1, champLiine[3], checkItem[3]);
                }
                else {
                    checkTime[1] = true;
                    setCoolDown(ad_spell2, txt_aspell2, (int) champLiine[3].getSpell2id(), 1, champLiine[3], checkItem[3]);
                }
            }
        });

        img_spell1_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[2]) {
                    checkTime[2] = false;
                    setCoolDown(sup_spell1, txt_sspell1, (int) champLiine[4].getSpell1id(), 2, champLiine[4], checkItem[4]);
                }
                else {
                    checkTime[2] = true;
                    setCoolDown(sup_spell1, txt_sspell1, (int) champLiine[4].getSpell1id(), 2, champLiine[4], checkItem[4]);
                }
            }
        });

        img_spell2_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[3]) {
                    checkTime[3] = false;
                    setCoolDown(sup_spell2, txt_sspell2, (int) champLiine[4].getSpell2id(), 3, champLiine[4], checkItem[4]);
                }
                else {
                    checkTime[3] = true;
                    setCoolDown(sup_spell2, txt_sspell2, (int) champLiine[4].getSpell2id(), 3, champLiine[4], checkItem[4]);
                }
            }
        });

        img_spell1_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[4]) {
                    checkTime[4] = false;
                    setCoolDown(mid_spell1, txt_mspell1, (int) champLiine[2].getSpell1id(), 4, champLiine[2], checkItem[2]);
                }
                else {
                    checkTime[4] = true;
                    setCoolDown(mid_spell1, txt_mspell1, (int) champLiine[2].getSpell1id(), 4, champLiine[2], checkItem[2]);
                }
            }
        });

        img_spell2_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[5]) {
                    checkTime[5] = false;
                    setCoolDown(mid_spell2, txt_mspell2, (int) champLiine[2].getSpell2id(), 5, champLiine[2], checkItem[2]);
                }
                else {
                    checkTime[5] = true;
                    setCoolDown(mid_spell2, txt_mspell2, (int) champLiine[2].getSpell2id(), 5, champLiine[2], checkItem[2]);
                }
            }
        });

        img_spell1_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[6]) {
                    checkTime[6] = false;
                    setCoolDown(jungle_spell1, txt_jspell1, (int) champLiine[1].getSpell1id(), 6, champLiine[1], checkItem[1]);
                }
                else {
                    checkTime[6] = true;
                    setCoolDown(jungle_spell1, txt_jspell1, (int) champLiine[1].getSpell1id(), 6 ,champLiine[1], checkItem[1]);
                }
            }
        });

        img_spell2_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[7]) {
                    checkTime[7] = false;
                    setCoolDown(jungle_spell2, txt_jspell2, (int) champLiine[1].getSpell2id(), 7, champLiine[1], checkItem[1]);
                }
                else {
                    checkTime[7] = true;
                    setCoolDown(jungle_spell2, txt_jspell2, (int) champLiine[1].getSpell2id(), 7, champLiine[1], checkItem[1]);
                }
            }
        });

        img_spell1_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[8]) {
                    checkTime[8] = false;
                    setCoolDown(top_spell1, txt_tspell1, (int) champLiine[0].getSpell1id(), 8, champLiine[0], checkItem[0]);
                }
                else {
                    checkTime[8] = true;
                    setCoolDown(top_spell1, txt_tspell1, (int) champLiine[0].getSpell1id(), 8, champLiine[0], checkItem[0]);
                }
            }
        });

        img_spell2_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[9]) {
                    checkTime[9] = false;
                    setCoolDown(top_spell2, txt_tspell2, (int) champLiine[0].getSpell2id(), 9, champLiine[0], checkItem[0]);
                }
                else {
                    checkTime[9] = true;
                    setCoolDown(top_spell2, txt_tspell2, (int) champLiine[0].getSpell2id(), 9, champLiine[0], checkItem[0]);
                }
            }
        });

        t_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkItem[0] == true) {
                    checkItem[0] = false;
                    shoe_top.setVisibility(View.VISIBLE);
                }
                else {
                    checkItem[0] = true;
                    shoe_top.setVisibility(View.GONE);
                }
            }
        });

        j_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkItem[1] == true) {
                    checkItem[1] = false;
                    shoe_jungle.setVisibility(View.VISIBLE);
                }
                else {
                    checkItem[1] = true;
                    shoe_jungle.setVisibility(View.GONE);
                }
            }
        });

        m_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkItem[2] == true) {
                    checkItem[2] = false;
                    shoe_mid.setVisibility(View.VISIBLE);
                }
                else {
                    checkItem[2] = true;
                    shoe_mid.setVisibility(View.GONE);
                }
            }
        });

        a_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkItem[3] == true) {
                    checkItem[3] = false;
                    shoe_ad.setVisibility(View.VISIBLE);
                }
                else {
                    checkItem[3] = true;
                    shoe_ad.setVisibility(View.GONE);
                }
            }
        });

        s_shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkItem[4] == true) {
                    checkItem[4] = false;
                    shoe_sup.setVisibility(View.VISIBLE);
                }
                else {
                    checkItem[4] = true;
                    shoe_sup.setVisibility(View.GONE);
                }
            }
        });



    }

    void setImage(ImageView spell1, ImageView spell2, ImageView champ, LinearLayout rune, CurrentData mdata) {

        long mspell1 = mdata.getSpell1id();
        long mspell2 = mdata.getSpell2id();
        long mcham = mdata.getChampionid();

        SpellList spellList = new SpellList();
        ChampionList championList = new ChampionList();

        long[] runes = new long[]{mdata.getPerkid1(),mdata.getPerkid2(),mdata.getPerkid3(),mdata.getPerkid4(),mdata.getPerkid5(),mdata.getPerkid6(),mdata.getPerkid7(),mdata.getPerkid8(),mdata.getPerkid9()};

        for (int i = 0; i < 9; i++) {
            if(runes[i] == 8347) {
                rune.setVisibility(View.GONE);
                break;
            }
        }

        String resspell1 = "@drawable/" + spellList.getSpell((int) mspell1);
        String resspell2 = "@drawable/" + spellList.getSpell((int) mspell2);
        String rescham = "@drawable/" + championList.getName((int) mcham);
        String packName = this.getPackageName();

        spell1.setImageResource(getResources().getIdentifier(resspell1,"drawable", packName));
        spell2.setImageResource(getResources().getIdentifier(resspell2,"drawable", packName));
        champ.setImageResource(getResources().getIdentifier(rescham,"drawable", packName));


    }

    void setCoolDown(final LinearLayout setvisible, TextView text, int spell, final int check, CurrentData data, Boolean item) {

        //함수 인자 boolean item 추가
        //true 시 10% 쿨감

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

            if(isTrue && !item) {
                time = (long) (time * 0.95);
            }
            else if(isTrue && item) {
                time = (long) (time * 0.85);
            }
            else if(!isTrue && item) {
                time = (long) (time * 0.90);
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
