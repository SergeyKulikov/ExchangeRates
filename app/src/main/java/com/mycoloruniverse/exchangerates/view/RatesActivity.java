package com.mycoloruniverse.exchangerates.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycoloruniverse.exchangerates.R;
import com.mycoloruniverse.exchangerates.model.Country;
import com.mycoloruniverse.exchangerates.model.DailyRates;
import com.mycoloruniverse.exchangerates.presenter.RatesActivityPresenter;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RatesActivity extends AppCompatActivity implements RatesActivityPresenter.PresenterInterface {
    private RatesAdapter ratesAdapter;
    private ArrayList<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        final RatesActivityPresenter presenter = new RatesActivityPresenter(this);
        ratesAdapter = new RatesAdapter();

        final RecyclerView rvCurrencyRatesList = findViewById(R.id.rvCurrencyRatesList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                RecyclerView.VERTICAL);

        rvCurrencyRatesList.setLayoutManager(new LinearLayoutManager(this));
        rvCurrencyRatesList.setAdapter(ratesAdapter);
        rvCurrencyRatesList.setClickable(true);
        rvCurrencyRatesList.setLongClickable(true);
        rvCurrencyRatesList.setOnCreateContextMenuListener(this); // необходимо для контекстного меню для RecycledView
        rvCurrencyRatesList.addItemDecoration(dividerItemDecoration);

    }

    @NotNull
    private ArrayList<Country> setCountryInfo(RatesActivityPresenter presenter) {
        return presenter.getCountryInfo();
    }

    @Override
    public RatesAdapter getAdapter() {
        return ratesAdapter;
    }

    @SuppressLint("NewApi")
    public void update(DailyRates dailyRates) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(String.format("%s - %s",
                strDateToShortFormat(dailyRates.getTimestamp()),
                strDateToShortFormat(dailyRates.getDate()))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rates_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                findViewById(R.id.filter_layout).setVisibility(View.VISIBLE);
                return true;
            default:
                return false;
        }
    }

    private String strDateToShortFormat(String strDate) {
        // "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        return dateFormatNew.format(date);
    }
}