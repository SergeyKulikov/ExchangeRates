package com.mycoloruniverse.exchangerates.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyRates {
    private String Date;
    private String PreviousDate;
    private String PreviousURL;
    private String Timestamp;

    private Map<String, CurrencyRate> Valute;

    // List<CurrencyRate> rates = new ArrayList<>();
    // JsonObject Valute;

    public DailyRates() {
    }

    public DailyRates(String date, String previousDate, String previousURL, String timestamp) {
        Date = date;
        PreviousDate = previousDate;
        PreviousURL = previousURL;
        Timestamp = timestamp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPreviousDate() {
        return PreviousDate;
    }

    public void setPreviousDate(String previousDate) {
        PreviousDate = previousDate;
    }

    public String getPreviousURL() {
        return PreviousURL;
    }

    public void setPreviousURL(String previousURL) {
        PreviousURL = previousURL;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public Map<String, CurrencyRate> getValute() {
        return Valute;
    }

    public void setValute(Map<String, CurrencyRate> valute) {
        Valute = valute;
    }
}
