package com.dongdong.spellandrtimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
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
    ImageView glide_top,glide_jungle,glide_mid,glide_ad,glide_sup,glide_trune,glide_jrune,glide_mrune,glide_arune,glide_srune;
    Switch vib;
    Button help;
    Boolean checkvib = false;
    Boolean[] checkTime = new Boolean[10];
    Boolean[] checkItem = new Boolean[5]; //아이오니아 아이템 클릭 시 true
    boolean[] checkThread = new boolean[10];
    boolean[] checkThreadisrunnig = new boolean[20];
    private AdView adBanner;
    private InterstitialAd adFull;
    CountDownTimer count1,count1_shoe,count2,count2_shoe,count3,count3_shoe,count4,count4_shoe,count5,count5_shoe,count6,count6_shoe,count7,count7_shoe,count8,count8_shoe,count9,count9_shoe,count10,count10_shoe;
    SoundPlayer soundPlayer;

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

                     if(adFull.isLoaded()) {
                         adFull.show();
                     }
                     else {
                         Log.e("error", "error");
                     }
                     finish();
                 }
             })
             .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.cancel();
                 }
             });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(checkThreadisrunnig[0]) {
            count1_shoe.cancel();
        }
        if(checkThreadisrunnig[1]) {
            count1.cancel();
        }
        if(checkThreadisrunnig[2]) {
            count2_shoe.cancel();
        }
        if(checkThreadisrunnig[3]) {
            count2.cancel();
        }
        if(checkThreadisrunnig[4]) {
            count3_shoe.cancel();
        }
        if(checkThreadisrunnig[5]) {
            count3.cancel();
        }
        if(checkThreadisrunnig[6]) {
            count4_shoe.cancel();
        }
        if(checkThreadisrunnig[7]) {
            count4.cancel();
        }
        if(checkThreadisrunnig[8]) {
            count5_shoe.cancel();
        }
        if(checkThreadisrunnig[9]) {
            count5.cancel();
        }
        if(checkThreadisrunnig[10]) {
            count6_shoe.cancel();
        }
        if(checkThreadisrunnig[11]) {
            count6.cancel();
        }
        if(checkThreadisrunnig[12]) {
            count7_shoe.cancel();
        }
        if(checkThreadisrunnig[13]) {
            count7.cancel();
        }
        if(checkThreadisrunnig[14]) {
            count8_shoe.cancel();
        }
        if(checkThreadisrunnig[15]) {
            count8.cancel();
        }
        if(checkThreadisrunnig[16]) {
            count9_shoe.cancel();
        }
        if(checkThreadisrunnig[17]) {
            count9.cancel();
        }
        if(checkThreadisrunnig[18]) {
            count10_shoe.cancel();
        }
        if(checkThreadisrunnig[19]) {
            count10.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
        glide_top = findViewById(R.id.glide_top);
        glide_jungle = findViewById(R.id.glide_jungle);
        glide_mid = findViewById(R.id.glide_mid);
        glide_ad = findViewById(R.id.glide_ad);
        glide_sup = findViewById(R.id.glide_sup);
        glide_trune = findViewById(R.id.glide_trune);
        glide_jrune = findViewById(R.id.glide_jrune);
        glide_mrune = findViewById(R.id.glide_mrune);
        glide_arune = findViewById(R.id.glide_arune);
        glide_srune = findViewById(R.id.glide_srune);
        vib = findViewById(R.id.vib);
        help = findViewById(R.id.btn_help);

        MobileAds.initialize(this, "ca-app-pub-5875725796538353/6130209488"); //배너광고 아이디 입력
        adBanner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adBanner.loadAd(adRequest);

        adFull = new InterstitialAd(this); //전면광고 아이디 입력
        adFull.setAdUnitId("ca-app-pub-5875725796538353/1999392785");
        adFull.loadAd(new AdRequest.Builder().build());

        Glide.with(this).load(R.drawable.top).into(glide_top);
        Glide.with(this).load(R.drawable.jungle).into(glide_jungle);
        Glide.with(this).load(R.drawable.mid).into(glide_mid);
        Glide.with(this).load(R.drawable.bottom).into(glide_ad);
        Glide.with(this).load(R.drawable.support).into(glide_sup);
        Glide.with(this).load(R.drawable.rune).into(glide_trune);
        Glide.with(this).load(R.drawable.rune).into(glide_jrune);
        Glide.with(this).load(R.drawable.rune).into(glide_mrune);
        Glide.with(this).load(R.drawable.rune).into(glide_arune);
        Glide.with(this).load(R.drawable.rune).into(glide_srune);
        Glide.with(this).load(R.drawable.shoe).into(t_shoe);
        Glide.with(this).load(R.drawable.shoe).into(j_shoe);
        Glide.with(this).load(R.drawable.shoe).into(m_shoe);
        Glide.with(this).load(R.drawable.shoe).into(a_shoe);
        Glide.with(this).load(R.drawable.shoe).into(s_shoe);

        for (int i = 0; i < 10; i++) {
            checkTime[i] = false;
            checkThread[i] = false;
        }
        for (int i = 0; i < 5; i++) {
            checkItem[i] = false;
        }
        for (int i = 0; i < 20; i++) {
            checkThreadisrunnig[i] = false;
        }

        Intent intent = getIntent();
        data = (CurrentData[]) intent.getSerializableExtra("data");
        setChampLine(data);
        soundPlayer.initSound(getApplicationContext());

        setImage(img_spell1_t,img_spell2_t, img_top, rune_top, champLiine[0]);
        setImage(img_spell1_j,img_spell2_j, img_jungle, rune_jungle, champLiine[1]);
        setImage(img_spell1_m,img_spell2_m, img_mid, rune_mid, champLiine[2]);
        setImage(img_spell1_a,img_spell2_a, img_ad, rune_ad, champLiine[3]);
        setImage(img_spell1_s,img_spell2_s, img_sup, rune_sup, champLiine[4]);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("소리를 켰습니까?");
        alert.setMessage("소리를 키시면 알림음을 들을 수 있습니다.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

//        swap.setOnClickListener(new View.OnClickListener() { 라인스왑
//            @Override
//            public void onClick(View view) {
//                CustomDialog customDialog = new CustomDialog(MainActivity.this);
//                customDialog.setDialogListner(new CustomDialog.CustomDialogListener() {
//                    @Override
//                    public void getData(int line1, int line2) {
//                        CurrentData temp;
//                        temp = champLiine[line1];
//                        champLiine[line1] = champLiine[line2];
//                        champLiine[line2] = temp;
//
//                        setImage(img_spell1_t,img_spell2_t, img_top, rune_top, champLiine[0]);
//                        setImage(img_spell1_j,img_spell2_j, img_jungle, rune_jungle, champLiine[1]);
//                        setImage(img_spell1_m,img_spell2_m, img_mid, rune_mid, champLiine[2]);
//                        setImage(img_spell1_a,img_spell2_a, img_ad, rune_ad, champLiine[3]);
//                        setImage(img_spell1_s,img_spell2_s, img_sup, rune_sup, champLiine[4]);
//
//                    }
//
//                    @Override
//                    public void onNegativeClicked() {
//
//                    }
//                });
//                customDialog.show();
//            }
//        });

        count1 = new CountDownTimer(setCoolDown((int) champLiine[3].getSpell1id(), champLiine[3], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_aspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[3].getSpell1id(), 3);
                ad_spell1.setVisibility(View.GONE);
            }
        };

        count1_shoe = new CountDownTimer(setCoolDown((int) champLiine[3].getSpell1id(), champLiine[3], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_aspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[3].getSpell1id(), 3);
                ad_spell1.setVisibility(View.GONE);
            }
        };

        count2 = new CountDownTimer(setCoolDown((int) champLiine[3].getSpell2id(), champLiine[3], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_aspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[3].getSpell2id(), 3);
                ad_spell2.setVisibility(View.GONE);
            }
        };

        count2_shoe = new CountDownTimer(setCoolDown((int) champLiine[3].getSpell2id(), champLiine[3], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_aspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[3].getSpell2id(), 3);
                ad_spell2.setVisibility(View.GONE);
            }
        };

        count3 = new CountDownTimer(setCoolDown((int) champLiine[4].getSpell1id(), champLiine[4], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_sspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[4].getSpell1id(), 4);
                sup_spell1.setVisibility(View.GONE);
            }
        };

        count3_shoe = new CountDownTimer(setCoolDown((int) champLiine[4].getSpell1id(), champLiine[4], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_sspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[4].getSpell1id(), 4);
                sup_spell1.setVisibility(View.GONE);
            }
        };

        count4 = new CountDownTimer(setCoolDown((int) champLiine[4].getSpell2id(), champLiine[4], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_sspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[4].getSpell2id(), 4);
                sup_spell2.setVisibility(View.GONE);
            }
        };

        count4_shoe = new CountDownTimer(setCoolDown((int) champLiine[4].getSpell2id(), champLiine[4], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_sspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[4].getSpell2id(), 4);
                sup_spell2.setVisibility(View.GONE);
            }
        };

        count5 = new CountDownTimer(setCoolDown((int) champLiine[2].getSpell1id(), champLiine[2], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_mspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[2].getSpell1id(), 2);
                mid_spell1.setVisibility(View.GONE);
            }
        };

        count5_shoe = new CountDownTimer(setCoolDown((int) champLiine[2].getSpell1id(), champLiine[2], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_mspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[2].getSpell1id(), 2);
                mid_spell1.setVisibility(View.GONE);
            }
        };

        count6 = new CountDownTimer(setCoolDown((int) champLiine[2].getSpell2id(), champLiine[2], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_mspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[2].getSpell2id(), 2);
                mid_spell2.setVisibility(View.GONE);
            }
        };

        count6_shoe = new CountDownTimer(setCoolDown((int) champLiine[2].getSpell2id(), champLiine[2], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_mspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[2].getSpell2id(), 2);
                mid_spell2.setVisibility(View.GONE);
            }
        };

        count7 = new CountDownTimer(setCoolDown((int) champLiine[1].getSpell1id(), champLiine[1], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_jspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[1].getSpell1id(), 1);
                jungle_spell1.setVisibility(View.GONE);
            }
        };

        count7_shoe = new CountDownTimer(setCoolDown((int) champLiine[1].getSpell1id(), champLiine[1], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_jspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[1].getSpell1id(), 1);
                jungle_spell1.setVisibility(View.GONE);
            }
        };

        count8 = new CountDownTimer(setCoolDown((int) champLiine[1].getSpell2id(), champLiine[1], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_jspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[1].getSpell2id(), 1);
                jungle_spell2.setVisibility(View.GONE);
            }
        };

        count8_shoe = new CountDownTimer(setCoolDown((int) champLiine[1].getSpell2id(), champLiine[1], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_jspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[1].getSpell2id(), 1);
                jungle_spell2.setVisibility(View.GONE);
            }
        };

        count9 = new CountDownTimer(setCoolDown((int) champLiine[0].getSpell1id(), champLiine[0], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_tspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[0].getSpell1id(), 0);
                top_spell1.setVisibility(View.GONE);
            }
        };

        count9_shoe = new CountDownTimer(setCoolDown((int) champLiine[0].getSpell1id(), champLiine[0], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_tspell1.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[0].getSpell1id(), 0);
                top_spell1.setVisibility(View.GONE);
            }
        };

        count10 = new CountDownTimer(setCoolDown((int) champLiine[0].getSpell2id(), champLiine[0], false), 1000) {
            @Override
            public void onTick(long l) {
                txt_tspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[0].getSpell2id(), 0);
                top_spell2.setVisibility(View.GONE);
            }
        };

        count10_shoe = new CountDownTimer(setCoolDown((int) champLiine[0].getSpell2id(), champLiine[0], true), 1000) {
            @Override
            public void onTick(long l) {
                txt_tspell2.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
            }

            @Override
            public void onFinish() {
                soundMake(checkvib, (int) champLiine[0].getSpell2id(), 0);
                top_spell2.setVisibility(View.GONE);
            }
        };

        img_spell1_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[0]) {
                    checkTime[0] = false;
                    ad_spell1.setVisibility(View.GONE);
                    if(checkThread[0]) {
                        checkThreadisrunnig[0] = false;
                        count1_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[1] = false;
                        count1.cancel();
                    }
                }
                else {
                    checkTime[0] = true;
                    ad_spell1.setVisibility(View.VISIBLE);

                    if(checkItem[3]) {
                        checkThreadisrunnig[0] = true;
                        checkThread[0] = true;
                        count1_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[1] = true;
                        checkThread[0] = false;
                        count1.start();
                    }
                }
            }
        });

        img_spell2_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[1]) {
                    checkTime[1] = false;
                    ad_spell2.setVisibility(View.GONE);
                    if(checkThread[1]) {
                        checkThreadisrunnig[2] = false;
                        count2_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[3] = false;
                        count2.cancel();
                    }
                }
                else {
                    checkTime[1] = true;
                    ad_spell2.setVisibility(View.VISIBLE);
                    if(checkItem[3]) {
                        checkThreadisrunnig[2] = true;
                        checkThread[1] = true;
                        count2_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[3] = true;
                        checkThread[1] = false;
                        count2.start();
                    }
                }
            }
        });

        img_spell1_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[2]) {
                    checkTime[2] = false;
                    sup_spell1.setVisibility(View.GONE);
                    if(checkThread[2]) {
                        checkThreadisrunnig[4] = false;
                        count3_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[5] = false;
                        count3.cancel();
                    }
                }
                else {
                    checkTime[2] = true;
                    sup_spell1.setVisibility(View.VISIBLE);

                    if(checkItem[4]) {
                        checkThreadisrunnig[4] = true;
                        checkThread[2] = true;
                        count3_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[5] = true;
                        checkThread[2] = false;
                        count3.start();
                    }
                }
            }
        });

        img_spell2_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[3]) {
                    checkTime[3] = false;
                    sup_spell2.setVisibility(View.GONE);
                    if(checkThread[3]) {
                        checkThreadisrunnig[6] = false;
                        count4_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[7] = false;
                        count4.cancel();
                    }
                }
                else {
                    checkTime[3] = true;
                    sup_spell2.setVisibility(View.VISIBLE);
                    if(checkItem[4]) {
                        checkThreadisrunnig[6] = true;
                        checkThread[3] = true;
                        count4_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[7] = true;
                        checkThread[3] = false;
                        count4.start();
                    }
                }
            }
        });

        img_spell1_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[4]) {
                    checkTime[4] = false;
                    mid_spell1.setVisibility(View.GONE);
                    if(checkThread[4]) {
                        checkThreadisrunnig[8] = false;
                        count5_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[9] = false;
                        count5.cancel();
                    }
                }
                else {
                    checkTime[4] = true;
                    mid_spell1.setVisibility(View.VISIBLE);
                    if(checkItem[2]) {
                        checkThreadisrunnig[8] = true;
                        checkThread[4] = true;
                        count5_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[9] = true;
                        checkThread[4] = false;
                        count5.start();
                    }
                }
            }
        });

        img_spell2_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[5]) {
                    checkTime[5] = false;
                    mid_spell2.setVisibility(View.GONE);
                    if(checkThread[5]) {
                        checkThreadisrunnig[10] = false;
                        count6_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[11] = false;
                        count6.cancel();
                    }
                }
                else {
                    checkTime[5] = true;
                    mid_spell2.setVisibility(View.VISIBLE);
                    if(checkItem[2]) {
                        checkThreadisrunnig[10] = true;
                        checkThread[5] = true;
                        count6_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[11] = true;
                        checkThread[5] = false;
                        count6.start();
                    }
                }
            }
        });

        img_spell1_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[6]) {
                    checkTime[6] = false;
                    jungle_spell1.setVisibility(View.GONE);

                    if(checkThread[6]) {
                        checkThreadisrunnig[12] = false;
                        count7_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[13] = false;
                        count7.cancel();
                    }
                }
                else {
                    checkTime[6] = true;
                    jungle_spell1.setVisibility(View.VISIBLE);
                    if(checkItem[1]) {
                        checkThreadisrunnig[12] = true;
                        checkThread[6] = true;
                        count7_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[13] = true;
                        checkThread[6] = false;
                        count7.start();
                    }
                }
            }
        });

        img_spell2_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[7]) {
                    checkTime[7] = false;
                    jungle_spell2.setVisibility(View.GONE);
                    if(checkThread[7]) {
                        checkThreadisrunnig[14] = false;
                        count8_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[15] = false;
                        count8.cancel();
                    }
                }
                else {
                    checkTime[7] = true;
                    jungle_spell2.setVisibility(View.VISIBLE);
                    if(checkItem[1]){
                        checkThreadisrunnig[14] = true;
                        checkThread[7] = true;
                        count8_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[15] = true;
                        checkThread[7] = false;
                        count8.start();
                    }
                }
            }
        });

        img_spell1_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[8]) {
                    checkTime[8] = false;
                    top_spell1.setVisibility(View.GONE);
                    if(checkThread[8]) {
                        checkThreadisrunnig[16] = false;
                        count9_shoe.cancel();
                    }
                    else{
                        checkThreadisrunnig[17] = false;
                        count9.cancel();
                    }
                }
                else {
                    checkTime[8] = true;
                    top_spell1.setVisibility(View.VISIBLE);
                    if(checkItem[0]) {
                        checkThreadisrunnig[16] = true;
                        checkThread[8] = true;
                        count9_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[17] = true;
                        checkThread[8] = false;
                        count9.start();
                    }
                }
            }
        });

        img_spell2_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTime[9]) {
                    checkTime[9] = false;
                    top_spell2.setVisibility(View.GONE);
                    if(checkThread[9]) {
                        checkThreadisrunnig[18] = false;
                        count10_shoe.cancel();
                    }
                    else {
                        checkThreadisrunnig[19] = false;
                        count10.cancel();
                    }
                }
                else {
                    checkTime[9] = true;
                    top_spell2.setVisibility(View.VISIBLE);
                    if(checkItem[0]) {
                        checkThreadisrunnig[18] = true;
                        checkThread[9] = true;
                        count10_shoe.start();
                    }
                    else {
                        checkThreadisrunnig[19] = true;
                        checkThread[9] = false;
                        count10.start();
                    }
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

        vib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //진동 스위치 버튼 체크
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkvib = true;
                }else {
                    checkvib = false;
                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentDialog overlay = new FragmentDialog();
                overlay.show(fm, "FragmentDialog");
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
            else{
                rune.setVisibility(View.VISIBLE);
            }
        }

        String resspell1 = "@drawable/" + spellList.getSpell((int) mspell1);
        String resspell2 = "@drawable/" + spellList.getSpell((int) mspell2);
        String rescham = "@drawable/" + championList.getName((int) mcham);
        String packName = this.getPackageName();

        Glide.with(this).load(getResources().getIdentifier(resspell1,"drawable", packName)).into(spell1);
        Glide.with(this).load(getResources().getIdentifier(resspell2,"drawable", packName)).into(spell2);
        Glide.with(this).load(getResources().getIdentifier(rescham,"drawable", packName)).into(champ);
    }

    long setCoolDown(int spell, CurrentData data, Boolean item) {

        boolean isTrue = false;
        long time;

        long[] rune = new long[]{data.getPerkid1(), data.getPerkid2(),data.getPerkid3(),data.getPerkid4(),data.getPerkid5(),data.getPerkid6(),data.getPerkid7(),data.getPerkid8(),data.getPerkid9()};

        for (int i = 0; i < 9; i++) {
            if(rune[i] == 8347) {
                isTrue = true;
                break;
            }
        }

        SpellCoolDownList cool = new SpellCoolDownList();

        time = cool.getCool(spell);

        if(isTrue && !item) {
            time = (long) (time * 0.95);
        }
        else if(isTrue && item) {
            time = (long) (time * 0.85);
        }
        else if(!isTrue && item) {
            time = (long) (time * 0.90);
        }

        return time;
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
            if(championLineData.getLine((int)data[i].getChampionid()) == 4 && pass[i] == -1) {
                champLiine[3] = data[i];
                pass[i] = i;
                break;
            }
        }

        for (int i = 0; i < 5; i++) { //서폿 결정
            if(championLineData.getLine((int)data[i].getChampionid()) == 5 && pass[i] == -1) {
                champLiine[4] = data[i];
                pass[i] = i;
                break;
            }
        }

        for (int i = 0; i < 5; i++) { //탑 결정
            if(championLineData.getLine((int)data[i].getChampionid()) == 1 && pass[i] == -1) {
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

    void soundMake(Boolean check, int spell, int line) {
        if(check) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
        }
        else {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            soundPlayer.play(getLineSound(line));
            soundPlayer.play(getSpellSound(spell));
        }
    }

    int getSpellSound(int spell) {
        switch (spell) {
            case 21 : return (R.raw.barrier);
            case 1 : return(R.raw.cleanse);
            case 14 : return (R.raw.ignite);
            case 3 : return (R.raw.exhaust);
            case 4 : return (R.raw.flash);
            case 6 : return (R.raw.ghost);
            case 7 : return (R.raw.heal);
            case 13 : return (R.raw.clarity);
            case 11 : return (R.raw.smite);
            case 32 : return (R.raw.mark);
            case 12 : return (R.raw.teleport);
        }
        return 0;
    }

    int getLineSound(int line) {
        switch (line) {
            case 0 : return (R.raw.top);
            case 1 : return (R.raw.jungle);
            case 2 : return (R.raw.mid);
            case 3 : return (R.raw.ad);
            case 4 : return (R.raw.support);
        }
        return 0;
    }

}
