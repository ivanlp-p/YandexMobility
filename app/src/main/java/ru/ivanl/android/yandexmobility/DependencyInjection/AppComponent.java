package ru.ivanl.android.yandexmobility.DependencyInjection;


import javax.inject.Singleton;

import dagger.Component;
import ru.ivanl.android.yandexmobility.DataBase.ArtistGenreHelper;
import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.DataBase.DBHelper;
import ru.ivanl.android.yandexmobility.DataBase.DataService;
import ru.ivanl.android.yandexmobility.DataBase.GenreHelper;
import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.MainActivity;
import ru.ivanl.android.yandexmobility.UI.Adapter.Activity.DetailActivity;
import ru.ivanl.android.yandexmobility.UI.Adapter.ArtistAdapter;


@Singleton
@Component(modules={AppModule.class, DBModule.class, JSONDataModule.class})
public interface AppComponent {
    void inject(ForYandexAplication obj);
    void inject(MainActivity obj);
    void inject(DBHelper obj);
    void inject(ArtistAdapter obj);
    void inject(ArtistHelper obj);
    void inject(GenreHelper obj);
    void inject(ArtistGenreHelper obj);
    void inject(DetailActivity obj);
    void inject(DataService obj);

}
