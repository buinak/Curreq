package com.buinak.curreq.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW_PORTRAIT = 4;
    public static final int ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW_LANDSCAPE = 6;

    public static final int ADD_SCREEN_AMOUNT_OF_ROWS_PER_SCREEN = 4;

    public static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    public static final String SETTINGS_PREFERENCES = "settings";

    public static final List<String> PERMITTED_CODES = Arrays.asList("AUD", "BGN", "BRL", "CAD", "CNY", "CHF", "CZK", "DKK", "EUR", "GBP", "HRK",
            "HUF", "INR", "JPY", "KRW", "NOK", "PLN", "RON", "RUB", "SEK", "USD");

    public static final String TOO_FREQUENT_UPDATES_STRING = "Updates more frequently than daily are not allowed!";
    public static final String UPDATE_SUCCESSFUL_STRING = "Update successful!";

    private Constants() {
    }
}
