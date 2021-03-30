package com.mycoloruniverse.exchangerates.model;

import com.google.gson.JsonObject;

public class CurrencyRate {
    private String ID;
    private String NumCode;
    private String CharCode;
    private double Nominal;
    private String Name;
    private double Value;
    private double Previous;

    public CurrencyRate(String ID, String numCode, String charCode, double nominal, String name, double value, double previous) {
        this.ID = ID;
        this.NumCode = numCode;
        this.CharCode = charCode;
        this.Nominal = nominal;
        this.Name = name;
        this.Value = value;
        this.Previous = previous;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumCode() {
        return NumCode;
    }

    public void setNumCode(String numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public double getNominal() {
        return Nominal;
    }

    public void setNominal(double nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public double getPrevious() {
        return Previous;
    }

    public void setPrevious(double previous) {
        Previous = previous;
    }

}
