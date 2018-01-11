package com.example.alexi.travelapp;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country {
    private String name;

    //private List<String> warnings = ArrayList<String>("example");
    private ArrayList<String> warnings = new ArrayList<>();
    private ArrayList<String> alerts = new ArrayList<>();
    private ArrayList<String> vaccinations = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Country(String name) throws IOException{
        String data = CountryManager.readFile(CountryManager.getFilePath(), StandardCharsets.UTF_8);
        this.name = name;

        //getWarnings().add("example");

        System.out.print("\n\nLOG(WARNING INDEX)(MIN): " + data.indexOf("#" + name) + "\n");
        System.out.print("\n\nLOG(WARNING INDEX)(MAX): " + data.indexOf("\\" + name) + "\n");

        //add warnings
        for(int i = 0; i < data.indexOf("\\" + name)/*database closing statement(\)*/; i++){
            if(data.indexOf("#" + name) < i) {
                if (data.charAt(i) == '-') {
                    int scanNum = 0;
                    StringBuilder warning = new StringBuilder();
                    //scan characters for warning
                    while (data.charAt(i + scanNum + 1) != '|') {
                        scanNum += 1;
                        //add next character in warning
                        warning.append(data.charAt(i + scanNum));
                    }
                    System.out.print("\n\nWARNING: " + warning);
                    warnings.add(warning.toString());
                }
            }
        }

        //add alerts
        for(int i = 0; i < data.indexOf("\\" + name)/*database closing statement(\)*/; i++){
            if(data.indexOf("#" + name) < i) {
                if (data.charAt(i) == '~') {
                    int scanNum = 0;
                    StringBuilder alert = new StringBuilder();
                    //scan characters for warning
                    while (data.charAt(i + scanNum + 1) != '|') {
                        scanNum += 1;
                        //add next character in warning
                        alert.append(data.charAt(i + scanNum));
                    }
                    System.out.print("\n\nAlert: " + alert);
                    alerts.add(alert.toString());
                }
            }
        }

        //add vaccinations
        for(int i = 0; i < data.indexOf("\\" + name)/*database closing statement(\)*/; i++){
            if(data.indexOf("#" + name) < i) {
                if (data.charAt(i) == '*') {
                    int scanNum = 0;
                    StringBuilder vaccination = new StringBuilder();
                    //scan characters for warning
                    while (data.charAt(i + scanNum + 1) != '|') {
                        scanNum += 1;
                        //add next character in warning
                        vaccination.append(data.charAt(i + scanNum));
                    }
                    System.out.print("\n\nVaccination: " + vaccination);
                    vaccinations.add(vaccination.toString());
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isCountry(String name, String data, int index){
        boolean isCountry = false;
        StringBuilder tempName = new StringBuilder();
        for(int i = index; i < data.length(); i++){
            if(data.charAt(i) == '#'){
                //scan for Country name until end symbol is recognised
                int scanNum = 0;
                while(data.charAt(i + scanNum) != '|'){
                    scanNum += 1;
                    //add character to Country name
                    if(data.charAt(i + scanNum) != '|')//if statement possibly not needed due to while loop requirement
                        tempName.append(data.charAt(i + scanNum));
                }
            }
        }
        if(Objects.equals(tempName.toString(), name))isCountry = true;
        return isCountry;
    }


    public List<String> getWarnings(){
        if(warnings.isEmpty()){
            ArrayList isNull = new ArrayList<>();
            isNull.add("null");
            return isNull;
        }
        return warnings;
    }
    public List<String> getAlerts(){
        if(alerts.isEmpty()){
            ArrayList isNull = new ArrayList<>();
            isNull.add("null");
            return isNull;
        }
        return alerts;
    }
    public List<String> getVaccinations(){
        if(vaccinations.isEmpty()){
            ArrayList isNull = new ArrayList<>();
            isNull.add("null");
            return isNull;
        }
        return vaccinations;
    }
}
