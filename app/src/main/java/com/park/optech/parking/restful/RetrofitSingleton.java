package com.park.optech.parking.restful;



import android.annotation.SuppressLint;
import android.content.Context;

import com.park.optech.parking.BuildConfig;
import com.park.optech.parking.sharedpref.MySharedPref;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {


    private static Retrofit mInstance;
    private static OkHttpClient.Builder okHttpClientBuilder;
    private static HttpLoggingInterceptor loggingInterceptor;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Retrofit getInstance(Context context) {
        RetrofitSingleton.context=context;
        if (mInstance == null) {


            okHttpClientBuilder = new OkHttpClient.Builder();  /// I must use OkHttpClient.Builder to add the log interceptor to the request
            loggingInterceptor = new HttpLoggingInterceptor(); /// I must use HttpLoggingInterceptor to could identify log configuration
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); /// add the log level body, header or ... etc

            if(BuildConfig.DEBUG){  // only enable log in depug mode to still secure my requests like password ..
                okHttpClientBuilder.addInterceptor(loggingInterceptor);
            }

            mInstance = new Retrofit.Builder()
                    .baseUrl(MySharedPref.getData(context, "restful_api_url", ""))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return mInstance;
    }
}
