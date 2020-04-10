package com.example.spellandrtimer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.security.auth.login.LoginException;


public class RiotApiConnet extends AppCompatActivity {

    ProgressBar progress;
    Summoner summoner;
    String name;
    String platform;
    StringBuilder sb;
    CurrentData[] teamA; //teamID : 100
    CurrentData[] teamB; //teamID : 200
    int TeamID = 0;
    Boolean error = false;
    Boolean isExsist = false;
    int errorcode = 0;

    private class ApiTask extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setIndeterminate(true);
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            ApiConfig config = new ApiConfig().setKey("RGAPI-68ed4055-e38a-46fe-9f04-8dc40482dc3c");
            RiotApi api = new RiotApi(config);

            try {
                switch (platform) {
                    case "KR":  summoner = api.getSummonerByName(Platform.KR, name);
                                break;
                    case "NA":  summoner = api.getSummonerByName(Platform.NA, name);
                                break;
                    case "BR":  summoner = api.getSummonerByName(Platform.BR, name);
                                break;
                    case "EUNE":  summoner = api.getSummonerByName(Platform.EUNE, name);
                                break;
                    case "EUW":  summoner = api.getSummonerByName(Platform.EUW, name);
                                break;
                    case "JP":  summoner = api.getSummonerByName(Platform.JP, name);
                                break;
                    case "LAN":  summoner = api.getSummonerByName(Platform.LAN, name);
                                break;
                    case "LAS":  summoner = api.getSummonerByName(Platform.LAS, name);
                                break;
                    case "OCE":  summoner = api.getSummonerByName(Platform.OCE, name);
                                break;
                    case "RU":  summoner = api.getSummonerByName(Platform.RU, name);
                                break;
                    case "TR":  summoner = api.getSummonerByName(Platform.TR, name);
                                break;

                }
                summoner = api.getSummonerByName(Platform.KR, name);
                String summonerID = summoner.getId();
                Log.e("id", ""+summonerID);
                String api_key = "RGAPI-68ed4055-e38a-46fe-9f04-8dc40482dc3c";
                String api_url = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerID + "?api_key=" + api_key;
                try {
                    URL url = new URL(api_url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    int code = con.getResponseCode();
                    BufferedReader br;
                    if(code == 200) {
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        sb = new StringBuilder();

                        String line;

                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        con.disconnect();
                        JsonParsing(sb.toString());
                    } else {
                        Log.e("Error~!", ""+code);
                        errorcode = code;
                        //에러 발생
                        error = true;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (RiotApiException e) {
                Log.e("Error", ""+"no exist");
                isExsist = true;
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.setIndeterminate(false);

            Intent intent = new Intent(RiotApiConnet.this, MainActivity.class);

            if(error) {
                if(errorcode == 400) {
                    Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(errorcode == 404) {
                    Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
                Intent mintent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mintent);
                finish();
            }
            else if(isExsist) {
                Toast.makeText(getApplicationContext(), "존재하는 소환사 이름이 아닙니다.", Toast.LENGTH_SHORT).show();
                Intent mintent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mintent);
                finish();
            }
            else {
                if(teamA[0].getTeamid() == TeamID) {
                    intent.putExtra("data", teamB);
                }
                else {
                    intent.putExtra("data", teamA);
                }

                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riot_api_connet);

        progress = findViewById(R.id.progress);
        teamA = new CurrentData[5];
        teamB = new CurrentData[5];

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        platform = intent.getStringExtra("platform");

        ApiTask task = new ApiTask();
        task.execute();
    }

    private void JsonParsing(String s) {

        int teamAnum = 0;
        int teamBnum = 0;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray participantsArray = jsonObject.getJSONArray("participants");

            for (int i = 0; i < participantsArray.length(); i++) {
                JSONObject current = participantsArray.getJSONObject(i);

                if((int) current.getLong("teamId") == 100) {

                    teamA[teamAnum] = new CurrentData();

                    teamA[teamAnum].setChampionid(current.getLong("championId"));
                    teamA[teamAnum].setName(current.getString("summonerName"));
                    teamA[teamAnum].setSpell1id(current.getLong("spell1Id"));
                    teamA[teamAnum].setSpell2id(current.getLong("spell2Id"));
                    teamA[teamAnum].setTeamid(current.getLong("teamId"));

                    JSONObject rune = current.getJSONObject("perks");

                    JSONArray runeid = rune.getJSONArray("perkIds");

                    teamA[teamAnum].setPerkid1(runeid.getLong(0));
                    teamA[teamAnum].setPerkid2(runeid.getLong(1));
                    teamA[teamAnum].setPerkid3(runeid.getLong(2));
                    teamA[teamAnum].setPerkid4(runeid.getLong(3));
                    teamA[teamAnum].setPerkid5(runeid.getLong(4));
                    teamA[teamAnum].setPerkid6(runeid.getLong(5));
                    teamA[teamAnum].setPerkid7(runeid.getLong(6));
                    teamA[teamAnum].setPerkid8(runeid.getLong(7));
                    teamA[teamAnum].setPerkid9(runeid.getLong(8));

                    teamAnum++;
                } else {

                    teamB[teamBnum] = new CurrentData();

                    teamB[teamBnum].setChampionid(current.getLong("championId"));
                    teamB[teamBnum].setName(current.getString("summonerName"));
                    teamB[teamBnum].setSpell1id(current.getLong("spell1Id"));
                    teamB[teamBnum].setSpell2id(current.getLong("spell2Id"));
                    teamB[teamBnum].setTeamid(current.getLong("teamId"));

                    JSONObject rune = current.getJSONObject("perks");

                    JSONArray runeid = rune.getJSONArray("perkIds");

                    teamB[teamBnum].setPerkid1(runeid.getLong(0));
                    teamB[teamBnum].setPerkid2(runeid.getLong(1));
                    teamB[teamBnum].setPerkid3(runeid.getLong(2));
                    teamB[teamBnum].setPerkid4(runeid.getLong(3));
                    teamB[teamBnum].setPerkid5(runeid.getLong(4));
                    teamB[teamBnum].setPerkid6(runeid.getLong(5));
                    teamB[teamBnum].setPerkid7(runeid.getLong(6));
                    teamB[teamBnum].setPerkid8(runeid.getLong(7));
                    teamB[teamBnum].setPerkid9(runeid.getLong(8));

                    teamBnum++;
                }

                if(current.getString("summonerName").equals(name) ||
                        current.getString("summonerName").toLowerCase().equals(name.toLowerCase())) {
                    TeamID = (int) current.getLong("teamId");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
