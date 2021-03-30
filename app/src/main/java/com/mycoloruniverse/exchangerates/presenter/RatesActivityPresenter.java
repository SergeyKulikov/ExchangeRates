package com.mycoloruniverse.exchangerates.presenter;

import android.util.Log;

import com.mycoloruniverse.exchangerates.model.ApiUtils;
import com.mycoloruniverse.exchangerates.model.Country;
import com.mycoloruniverse.exchangerates.model.CountryCurrencyInfo;
import com.mycoloruniverse.exchangerates.model.CurrencyRate;
import com.mycoloruniverse.exchangerates.model.DailyRates;
import com.mycoloruniverse.exchangerates.view.RatesActivity;
import com.mycoloruniverse.exchangerates.view.RatesAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RatesActivityPresenter {
    private RatesActivity ratesView;

    public RatesActivityPresenter(RatesActivity view) {
        ratesView = view;
        connect();
    }

    Disposable gettingRate;

    public interface PresenterInterface {
        RatesAdapter getAdapter();
    }

    private void connect() {
        gettingRate = getRateFromWeb();
    }

    Disposable getRateFromWeb() {
        Flowable<DailyRates> call = ApiUtils.getDailyRatesInterface().getDailyRates();

        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyRates -> {
                    if (ratesView.getAdapter() != null) {
                        // Добавляем базовую валюту, для этого курса
                        // 810 код = RUR (рубль РФ 1991-1998) - не используем, Android определяет его как устаревщий
                        // 643 код = RUB (рубль РФ)
                        dailyRates.getValute().put("RUB", new CurrencyRate("R01", "643", "RUB", 1, "Российский рубль",1,1 ));

                        // Проблема неверной валюты почему-то касается и белорусского рубля.
                        // В российском справочнике используется его старый буквеный код
                        dailyRates.getValute().get("BYN").setCharCode("BYR");
                        dailyRates.getValute().put("BYN", dailyRates.getValute().get("BYN"));

                        /*
                        dailyRates.getValute().get("TJR").setCharCode("TJS");
                        dailyRates.getValute().put("TJS", dailyRates.getValute().get("TJS"));
*/
                        ratesView.getAdapter().setRates(dailyRates);
                        ratesView.getAdapter().notifyDataSetChanged();

                        ratesView.update(dailyRates);
                    }
                }, throwable -> {
                    Log.e(getClass().getSimpleName(), "");
                });
    }

    public ArrayList<Country> getCountryInfo() {
        ArrayList<Country> countryList = new ArrayList<>(CountryCurrencyInfo.getInstance().getCountryMap().values());
        Collections.sort(countryList, (Country, t1) -> (Country.getName().compareToIgnoreCase(t1.getName())));
        return countryList;
    }

}
