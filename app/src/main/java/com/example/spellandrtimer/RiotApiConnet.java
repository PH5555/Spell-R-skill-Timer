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


public class RiotApiConnet extends AppCompatActivity {

    ProgressBar progress;
    Summoner summoner;
    String name;

    private class ApiTask extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setIndeterminate(true);
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            ApiConfig config = new ApiConfig().setKey("RGAPI-0628f47c-f087-4f16-9876-9fbc97bd28b9");
            RiotApi api = new RiotApi(config);

            try {
                summoner = api.getSummonerByName(Platform.KR, name);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }
            /*
            System.out.println("Name: " + summoner.getName());
            System.out.println("Summoner ID: " + summoner.getId());
            System.out.println("Account ID: " + summoner.getAccountId());
            System.out.println("PUUID: " + summoner.getPuuid());
            System.out.println("Summoner Level: " + summoner.getSummonerLevel());
            System.out.println("Profile Icon ID: " + summoner.getProfileIconId());*/


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progress.setIndeterminate(false);

            Intent intent = new Intent(RiotApiConnet.this, MainActivity.class);
           // intent.putExtra("Name", summoner.getName());
            intent.putExtra("Name", summoner.getName());
            intent.putExtra("SummonerID", summoner.getId());
            intent.putExtra("AccountID", summoner.getAccountId());
            intent.putExtra("Level", summoner.getSummonerLevel());

            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riot_api_connet);

        progress = findViewById(R.id.progress);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        ApiTask task = new ApiTask();
        task.execute();
    }
}
