package com.example.alexi.travelapp;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by alexi on 1/15/2018.
 */

public class Country {
    private static String[] countryNames;
    private static HashMap<String, Country> countryHashMap;
    private static boolean initialized = false;

    private String name;
    private ArrayList<String> warnings = new ArrayList<>();
    private ArrayList<String> alerts = new ArrayList<>();
    private ArrayList<String> vaccinations = new ArrayList<>();

    private Country(String name, BufferedReader reader) throws IOException{
        createCountry(name, reader);
    }

    private static void initialize(AssetManager assets) throws IOException{
        countryNames = getCountryNames(new BufferedReader(new InputStreamReader(assets.open("database.txt"))));
        countryHashMap = new HashMap<String, Country>();
        for(int i = 0; i < countryNames.length; i++){
            BufferedReader countryReader = new BufferedReader(new InputStreamReader(assets.open("database.txt")));
            Country c = new Country(countryNames[i], countryReader);
            countryReader.close();
            countryHashMap.put(countryNames[i].toLowerCase(), c);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String[] getCountryNames(BufferedReader reader) throws IOException {
        String data = readFile(reader);
        int numCountries = 0;

        //find number of countries
        for(int i = 0; i < data.length(); i++){if(data.charAt(i) == '#')numCountries += 1;}

        String[] countries = new String[numCountries];

        //set 'countries' array to "" (null)
        for(int i = 0; i < countries.length; i++){
            countries[i] = "";
        }

        int countryIndex = 0;//current array index to be modified

        for(int i = 0; i < data.length(); i++){
            if(data.charAt(i) == '#'){
                //scan for Country name until end symbol is recognised
                int scanNum = 0;
                while(data.charAt(i + scanNum) != '|'){
                    scanNum += 1;
                    //add character to Country name
                    if(data.charAt(i + scanNum) != '|')
                        countries[countryIndex] = countries[countryIndex] + data.charAt(i + scanNum);
                }
                countryIndex += 1;
            }
        }
        return countries;
    }

    //Convert text file to String
    private static String readFile(BufferedReader reader) throws IOException {
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private void createCountry(String name, BufferedReader reader) throws IOException{
        String data = readFile(reader);
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

        reader.close();
    }

    //returns Country if it exists, null otherwise
    public static Country getCountry(String name, AssetManager assets) throws IOException{
        if(!initialized){
            initialize(assets);
            initialized = true;
        }
        return countryHashMap.get(name);
    }

    public ArrayList<String> getWarnings(){ return warnings;}
    public ArrayList<String> getAlerts(){return alerts;}
    public ArrayList<String> getVaccinations(){ return vaccinations;}
}
