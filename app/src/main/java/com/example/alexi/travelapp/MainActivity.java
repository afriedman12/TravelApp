package com.example.alexi.travelapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (findViewById(R.id.my_toolbar));
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    LinearLayout countryInfo = findViewById(R.id.country_info);
                    countryInfo.removeAllViews();
                    Country c = Country.getCountry(s, getAssets());
                    displayCountryInfo(c);
                } catch(IOException e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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

    public void displayCountryInfo(Country c) {
        LinearLayout countryInfo = findViewById(R.id.country_info);

        if (c == null) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText("This country is not in our database.");
            tv.setBackgroundColor(getResources().getColor(R.color.gray));
            countryInfo.addView(tv);
        } else {
            List<String> vaccines = c.getVaccinations();
            List<String> warnings = c.getWarnings();
            List<String> alerts = c.getAlerts();
            for (int i = 0; i < vaccines.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(vaccines.get(i));
                tv.setBackgroundColor(getResources().getColor(R.color.red));
                countryInfo.addView(tv);
            }
            for (int i = 0; i < warnings.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(warnings.get(i));
                tv.setBackgroundColor(getResources().getColor(R.color.yellow));
                countryInfo.addView(tv);
            }
            for (int i = 0; i < alerts.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(alerts.get(i));
                tv.setBackgroundColor(getResources().getColor(R.color.green));
                countryInfo.addView(tv);
            }
        }
    }
}
