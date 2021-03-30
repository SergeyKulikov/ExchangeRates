package com.mycoloruniverse.exchangerates.model;

public class Country {
    private String DisplayName;
    private String id;
    private String flag;
    private String currency_code;

    public Country(String displayName, String id, String currency_code) {
        this.DisplayName = displayName;
        this.id = id;
        this.currency_code = currency_code;
        this.flag = CountryFlags.getInstance().getFlag(id);
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public String getName() {
        return getDisplayName();
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimbol() {
        return CountryFlags.getInstance().getFlag(getId());
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
