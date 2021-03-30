package com.mycoloruniverse.exchangerates.model;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class ApiUtils {
    private final static String CBR_URN = "daily_json.js";
    private final static String CBR_URL = "https://www.cbr-xml-daily.ru";

    static IDailyRates sCliqueDBApiInterface;

    public static IDailyRates getDailyRatesInterface() {

        if (sCliqueDBApiInterface == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CBR_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            sCliqueDBApiInterface = retrofit.create(IDailyRates.class);
        }

        return sCliqueDBApiInterface;
    }

    public interface IDailyRates {
            // @GET("/{file}")
            // Observable<DailyRates> getDailyRates(@Path("file") String file);

            @GET(CBR_URN)
            Flowable<DailyRates> getDailyRates();
    }
}
