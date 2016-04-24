package ru.ivanl.android.yandexmobility;

import android.app.Application;

import ru.ivanl.android.yandexmobility.DependencyInjection.AppComponent;
import ru.ivanl.android.yandexmobility.DependencyInjection.AppModule;
import ru.ivanl.android.yandexmobility.DependencyInjection.DBModule;
import ru.ivanl.android.yandexmobility.DependencyInjection.JSONDataModule;

public class ForYandexAplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dBModule(new DBModule())
                .rESTModule(new JSONDataModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
