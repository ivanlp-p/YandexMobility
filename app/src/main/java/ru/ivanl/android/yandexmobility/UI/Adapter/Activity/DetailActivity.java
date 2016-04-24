package ru.ivanl.android.yandexmobility.UI.Adapter.Activity;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.Logic.LogicArtist;
import ru.ivanl.android.yandexmobility.R;

public class DetailActivity extends AppCompatActivity{

    @Inject
    ArtistHelper artistHelper;
    @Inject
    LogicArtist logicArtist;

    private static final String INTENT_NAME ="Get artist";

    private Long artistId;
    private ActionBar actionBar;
    private ImageView cover;
    private TextView genre;
    private TextView albums;
    private TextView tracks;
    private TextView description;
    private TextView link;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ((ForYandexAplication) getApplication()).getAppComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cover = (ImageView) findViewById(R.id.toolbar_image);
        genre = (TextView) findViewById(R.id.detail_genre);
        albums = (TextView) findViewById(R.id.detail_albums);
        tracks = (TextView) findViewById(R.id.detail_tracks);
        description = (TextView) findViewById(R.id.detail_description);
        artistId = getIntent().getLongExtra(INTENT_NAME, -1);
        link = (TextView) findViewById(R.id.detail_link);

        Cursor cursor = artistHelper.query(artistId);


        String url = artistHelper.getBigCover(cursor);
        Picasso.with(this).load(url).into(cover);

        genre.setText(logicArtist.buildGenreString(cursor));
        albums.setText(artistHelper.getAlbums(cursor).toString());
        tracks.setText(artistHelper.getTracks(cursor).toString());
        description.setText(artistHelper.getDescription(cursor));
        link.setText(artistHelper.getLink(cursor));
        setTitle(artistHelper.getName(cursor));

    }

    public static Intent prepareIntent(Context context, long id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_NAME, id);
        return intent;
    }
}
