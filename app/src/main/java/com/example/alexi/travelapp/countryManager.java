package com.example.alexi.travelapp;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class countryManager {
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException {


        //test cases
        System.out.print(readFile(getFilePath(), StandardCharsets.UTF_8));
        System.out.print("\n\n\n");

        String[] countries = getCountries();

        //test country recognition
        System.out.print("number of countries: " + countries.length + "\ncountries: \n");
        for(int i = 0; i < countries.length; i++){
            System.out.print(countries[i] + "\n");
        }

        //initialize country class for testing
        country Tanzania = new country("Tanzania");
        System.out.print("\n\n" + Tanzania.getWarnings());


    }

    //Convert text file to String
    @RequiresApi(api = Build.VERSION_CODES.O)
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    //return countries in String array
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String[] getCountries() throws IOException{
        String data = readFile(getFilePath(), StandardCharsets.UTF_8);
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
                //scan for country name until end symbol is recognised
                int scanNum = 0;
                while(data.charAt(i + scanNum) != '|'){
                    scanNum += 1;
                    //add character to country name
                    if(data.charAt(i + scanNum) != '|')
                        countries[countryIndex] = countries[countryIndex] + data.charAt(i + scanNum);
                }
                countryIndex += 1;
            }
        }
        return countries;
    }

    static String getFilePath(){return "C:\\Users\\iaind\\Downloads\\TravelApp-master\\app\\database";}
}
