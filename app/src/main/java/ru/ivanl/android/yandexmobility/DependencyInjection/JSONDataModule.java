package ru.ivanl.android.yandexmobility.DependencyInjection;


import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.ivanl.android.yandexmobility.JSONData.YandexServ;

@Module
public class JSONDataModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(YandexServ.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(YandexServ.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(YandexServ.TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    GsonBuilder provideGSON() {
        return new GsonBuilder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(GsonBuilder gsonBuilder) {
        return new Retrofit.Builder()
                .baseUrl(YandexServ.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
    }

    @Singleton
    @Provides
    YandexServ provideYandexService(Retrofit retrofit) {
        return retrofit.create(YandexServ.class);
    }
}
