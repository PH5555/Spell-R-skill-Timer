package com.example.spellandrtimer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

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


public class RiotApiConnet extends AppCompatActivity {

    ProgressBar progress;
    Summoner summoner;
    String name;
    StringBuilder sb;
    CurrentData[] data;

    private class ApiTask extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setIndeterminate(true);
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            ApiConfig config = new ApiConfig().setKey("RGAPI-e5bb440a-9c9a-41f3-a81f-55d12dcefbe1");
            RiotApi api = new RiotApi(config);

            try {
                summoner = api.getSummonerByName(Platform.KR, name);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }

            String summonerID = summoner.getId();
            String api_key = "RGAPI-e5bb440a-9c9a-41f3-a81f-55d12dcefbe1";
            String api_url = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerID + "?api_key=" + api_key;
            try {
                URL url = new URL(api_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int code = con.getResponseCode();
                BufferedReader br;
                if(code == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }
                sb = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                con.disconnect();
                JsonParsing(sb.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.setIndeterminate(false);

            Intent intent = new Intent(RiotApiConnet.this, MainActivity.class);
            intent.putExtra("data", data);

            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riot_api_connet);

        progress = findViewById(R.id.progress);
        data = new CurrentData[10];

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        ApiTask task = new ApiTask();
        task.execute();
    }

    private void JsonParsing(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray participantsArray = jsonObject.getJSONArray("participants");
            for (int i = 0; i < participantsArray.length(); i++) {
                JSONObject current = participantsArray.getJSONObject(i);

                data[i] = new CurrentData();

                data[i].setChampionid(current.getLong("championId"));
                data[i].setName(current.getString("summonerName"));
                data[i].setSpell1id(current.getLong("spell1Id"));
                data[i].setSpell2id(current.getLong("spell2Id"));
                data[i].setTeamid(current.getLong("teamId"));

                JSONObject rune = current.getJSONObject("perks");

                data[i].setPerkstyle(rune.getLong("perkIds"));
                data[i].setPerksubstyle(rune.getLong("perkSubStyle"));

                JSONArray runeid = rune.getJSONArray("perkIds");

                data[i].setPerkid1(runeid.getLong(0));
                data[i].setPerkid2(runeid.getLong(1));
                data[i].setPerkid3(runeid.getLong(2));
                data[i].setPerkid4(runeid.getLong(3));
                data[i].setPerkid5(runeid.getLong(4));
                data[i].setPerkid6(runeid.getLong(5));
                data[i].setPerkid7(runeid.getLong(6));
                data[i].setPerkid8(runeid.getLong(7));
                data[i].setPerkid9(runeid.getLong(8));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
