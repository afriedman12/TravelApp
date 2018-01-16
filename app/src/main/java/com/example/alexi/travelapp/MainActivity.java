package com.example.alexi.travelapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (findViewById(R.id.my_toolbar));
        setSupportActionBar(myToolbar);

        try {
            Country c = Country.getCountry("Tanzania", getAssets());
            displayCountryInfo(c);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        /*SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_show_menu:
                LinearLayout tripList = findViewById(R.id.tripLayout);
                tripList.setVisibility(tripList.isShown() ? View.GONE:View.VISIBLE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayCountryInfo(Country c){
        List<String> vaccines = c.getVaccinations();
        List<String> warnings = c.getWarnings();
        List<String> alerts = c.getAlerts();

        LinearLayout countryInfo = findViewById(R.id.country_info);

        for(int i = 0; i < vaccines.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(vaccines.get(i));
            tv.setBackgroundColor(getResources().getColor(R.color.red));
            countryInfo.addView(tv);
        }
        for(int i = 0; i < warnings.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(warnings.get(i));
            tv.setBackgroundColor(getResources().getColor(R.color.yellow));
            countryInfo.addView(tv);
        }
        for(int i = 0; i < alerts.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(alerts.get(i));
            tv.setBackgroundColor(getResources().getColor(R.color.green));
            countryInfo.addView(tv);
        }
    }
}
