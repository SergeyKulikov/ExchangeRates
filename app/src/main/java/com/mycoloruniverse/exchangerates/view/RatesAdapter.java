package com.mycoloruniverse.exchangerates.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.Currency;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.mycoloruniverse.exchangerates.R;
import com.mycoloruniverse.exchangerates.model.Country;
import com.mycoloruniverse.exchangerates.model.CountryCurrencyInfo;
import com.mycoloruniverse.exchangerates.model.CurrencyRate;
import com.mycoloruniverse.exchangerates.model.DailyRates;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.RatesViewHolder> {
    private final List<CurrencyRate> dailyRates = new ArrayList<>();
    private final CountryCurrencyInfo countryCurrencyInfo = CountryCurrencyInfo.getInstance();

    @NonNull
    @Override
    public RatesAdapter.RatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.valute_item_layout, parent, false);

        return new RatesAdapter.RatesViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RatesAdapter.RatesViewHolder holder, int position) {
        CurrencyRate currencyRate = dailyRates.get(position);

        holder.tvID.setText(currencyRate.getID());
        holder.tvNumCode.setText(currencyRate.getNumCode());
        holder.tvCharCode.setText(currencyRate.getCharCode());
        holder.tvNominal.setText(String.format(Locale.getDefault(), "%d", (int)currencyRate.getNominal()));
        // holder.tvName.setText(dailyRates.getRates().get(position).getName());
        holder.tvValue.setText(String.format(Locale.getDefault(), "%.4f", currencyRate.getValue()));
        holder.tvPrevious.setText(String.format(Locale.getDefault(), "%.4f", currencyRate.getPrevious()));

        Currency currency = countryCurrencyInfo.getCurrencyMap().get(currencyRate.getCharCode());



        //Currency currency = Currency.getInstance(currencyRate.getCharCode());

        if (currency == null) { // для валюты, которая не связана со странами
            // Log.e("TAG", "Не найдена валюта: "+currencyRate.getCharCode());
            currency = Currency.getInstance(currencyRate.getCharCode());

            // Log.e("TAG", "Не найдена валюта: "+currencyRate.getCharCode()+" : "+locale.getDisplayCountry(Locale.getDefault()));
        }

        String currencyCode = currency.getCurrencyCode();
        String displayName = currency.getDisplayName();
        String symbol = currency.getSymbol();

        if (displayName.equals(currencyCode)) { // проблема белорусского рубля
            holder.tvName.setText(currencyRate.getName());
        } else {
            holder.tvName.setText(displayName);
        }
        holder.tvCountryList.setText(
                countryCurrencyInfo.getCountryListStringByCurrency(
                                currencyRate.getCharCode(),
                                true
                        )
        );
    }

    @Override
    public int getItemCount() {
        return dailyRates.size();
    }

    public void setRates(DailyRates dailyRates) {
        if (dailyRates != null) {
            this.dailyRates.clear();
            this.dailyRates.addAll(dailyRates.getValute().values());
        }
    }

    static class RatesViewHolder extends ViewHolder {
        private final TextView tvID;
        private final TextView tvNumCode;
        private final TextView tvCharCode;
        private final TextView tvNominal;
        private final TextView tvName;
        private final TextView tvValue;
        private final TextView tvPrevious;
        private final TextView tvCountryList;

        public RatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvNumCode = itemView.findViewById(R.id.tvNumCode);
            tvCharCode = itemView.findViewById(R.id.tvCharCode);
            tvNominal = itemView.findViewById(R.id.tvNominal);
            tvName = itemView.findViewById(R.id.tvName);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvPrevious = itemView.findViewById(R.id.tvPrevious);
            tvCountryList = itemView.findViewById(R.id.tvCountryList);
        }
    }
}
