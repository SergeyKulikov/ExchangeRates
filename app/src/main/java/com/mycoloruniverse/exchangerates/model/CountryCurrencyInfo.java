package com.mycoloruniverse.exchangerates.model;

import android.annotation.SuppressLint;
import android.icu.util.Currency;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

public class CountryCurrencyInfo {
    // private static final CountryCurrencyInfo instance = new CountryCurrencyInfo();
    private static CountryCurrencyInfo instance;
    private final Map<String, Currency> currencyMap = new ArrayMap<>(); // Список валют
    private final HashMap<String, Country> countryMap = new HashMap<>(); // Список стран по коду страны
    private final Map<String, String> currencyOfCountryMap = new HashMap<>(); // Валюта страны по коду страны
                      // код варюты, коды стран
    private final Map<String, ArrayMap<String, Country>> countryListByCurrencyMap = new HashMap<>(); // Список стран по коду валюты

    public static CountryCurrencyInfo getInstance() {
        if (instance == null) {
            instance = new CountryCurrencyInfo();
        }
        return instance;
    }

    private CountryCurrencyInfo() {
        prepareMaps();
    }

    @SuppressLint("NewApi")
    private void prepareMaps() {
        Vector<String> stringBooleanMap = new Vector<>();
        ArrayMap<String, Country> countryList;  // 643, Россия
        Country country;

        currencyMap.clear();
        currencyOfCountryMap.clear();
        for (Locale locale : Locale.getAvailableLocales())
        {
            //  валюта территории
            Currency currency = Currency.getInstance(locale);
            if (currency == null) continue;

            // код валюты по коду страны
            if (currencyOfCountryMap.containsKey(locale.getCountry())) {
                currencyOfCountryMap.put(locale.getCountry(), currency.getCurrencyCode());
            }

            // Создаем объект страны
            country = new Country(
                    locale.getDisplayCountry(Locale.getDefault()), // название на языке устройства
                    locale.getCountry(), // код страны
                    currency.getCurrencyCode()); // код валюты страны

            // Проверяем была ли территория
            if (!stringBooleanMap.contains(country.getId())) {
                // Запоминаем, что территория уже была добавлена
                stringBooleanMap.add(country.getId());
                // Добавляем страну по коду страны
                countryMap.put(country.getId(), country);
            }

            // Валюта еще не добавлена
            if (!countryListByCurrencyMap.containsKey(currency.getCurrencyCode())) {
                countryList = new ArrayMap<>(); // Создаем новый список стран для валюты
            } else {
                // Валюта уже есть, то получаем умеющийся список стран для валюты
                countryList = countryListByCurrencyMap.get(currency.getCurrencyCode());
            }

            // Если страны в списке стран нет, то добавляем
            if (!countryList.containsKey(locale.getCountry())) {
                countryList.put(locale.getCountry(), country);
            }

            // кладем список стран обратно
            // countryListByCurrencyMap.put(locale.getCountry(), countryList);
            countryListByCurrencyMap.put(currency.getCurrencyCode(), countryList);

            if (currencyMap.containsKey(currency.getCurrencyCode())) continue;
            currencyMap.put(currency.getCurrencyCode(), currency);
        }
    }

    public Map<String, Currency> getCurrencyMap () {
        return currencyMap;
    }

    public Map<String, String> getCurrencyOfCountryMap() {
        return currencyOfCountryMap;
    }

    public HashMap<String, Country> getCountryMap() {
        return countryMap;
    }

    public Map<String, ArrayMap<String, Country>> getCountryListByCurrencyMap() {
        return countryListByCurrencyMap;
    }

    public String getCountryListStringByCurrency(String currencyCode, boolean showCountryFlags) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayMap<String, Country> map = getCountryListByCurrencyMap().get(currencyCode);

        if (map == null) return stringBuilder.toString();

        List<Country> countryList = new ArrayList<>(map.values());
        Collections.sort(countryList, (country1, country2) -> (country1.getName().compareToIgnoreCase(country2.getName())));

        if (countryList.size() > 0) {
            for (Country item : countryList) {

                stringBuilder.append((stringBuilder.toString().isEmpty()) ? "" : ", ");
                if (showCountryFlags) {
                    stringBuilder.append(item.getFlag());
                    stringBuilder.append(" ");
                }
                stringBuilder.append(item.getName());
             }
        }

        return stringBuilder.toString();
    }


}
