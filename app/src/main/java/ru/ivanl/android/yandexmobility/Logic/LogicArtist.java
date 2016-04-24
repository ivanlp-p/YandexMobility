package ru.ivanl.android.yandexmobility.Logic;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.ivanl.android.yandexmobility.DataBase.ArtistGenreHelper;
import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;

public class LogicArtist {

    public static final String DATA_UPDATED_ACTION = "DATA_UPDATED";
    private static final String DELIMITER_STRING = ", ";
    private static final int THUMB_WIDTH = 300;
    private final Context context;

    private ArtistHelper artistHelper;
    private ArtistGenreHelper artistGenreHelper;

    public LogicArtist(Context context, ArtistHelper artistHelper, ArtistGenreHelper artistGenreHelper) {
        this.context = context;
        this.artistHelper = artistHelper;
        this.artistGenreHelper = artistGenreHelper;
    }

    //String of genres
    public String buildGenreString(Cursor cursor) {
        StringBuilder builder = new StringBuilder();

        Long artistId = artistHelper.getId(cursor);
        List<String> genres = artistGenreHelper.getGenreNameByArtist(artistId);

        if (genres != null && !genres.isEmpty()) {
            for (int i = 0;i < genres.size(); i++) {
                builder.append(genres.get(i));
                if (i < genres.size() - 1) {
                    builder.append(DELIMITER_STRING);
                }
            }
        }

        return builder.toString();
    }

    //Load image
    public void loadOmageToView (Cursor cursor, ImageView view) {
        int wigth = view.getWidth();
        String url;

        if (wigth <= THUMB_WIDTH) {
            url = artistHelper.getSmallCover(cursor);
        } else {
            url = artistHelper.getBigCover(cursor);
        }

        Picasso.with(context).load(url).fit().into(view);
    }

    // Build tracks and albums string
    public String buildAlbumsAndTracksString(@NonNull Cursor cursor) {
        int albums = artistHelper.getAlbums(cursor);
        int tracks = artistHelper.getTracks(cursor);

        return String.valueOf(albums) + " " + declensionWordAlbum(albums) + ", " +
                tracks + " " + declensionWordTrack(tracks);
    }

    private String declensionWordAlbum(int number) {
        if (number > 10 && number < 15) {
            return "альбомов";
        } else {
            switch (number % 10) {
                case 1:
                    return "альбом";
                case 2:
                case 3:
                case 4:
                    return "альбомa";
                default:
                    return "альбомов";
            }
        }
    }

    private String declensionWordTrack(int number) {
        if (number > 10 && number < 15) {
            return "песен";
        } else {
            switch (number % 10) {
                case 1:
                    return "песня";
                case 2:
                case 3:
                case 4:
                    return "песни";
                default:
                    return "песен";
            }
        }
    }
}
