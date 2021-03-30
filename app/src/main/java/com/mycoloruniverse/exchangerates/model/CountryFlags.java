package com.mycoloruniverse.exchangerates.model;

/** Вместо создания кучи флагов ресурсах возьмем их из таблицы шрифтов OS Android */

public class CountryFlags {
    private int flagOffset = 0x1F1E6;
    private int asciiOffset = 0x41;

    private static CountryFlags instance;

    public static CountryFlags getInstance() {
        if (instance == null) {
            instance = new CountryFlags();
        }
        return instance;
    }

    public String getFlag(String country) {
        if (country.length() < 2) {
            return "";
        }
        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
    }
}
