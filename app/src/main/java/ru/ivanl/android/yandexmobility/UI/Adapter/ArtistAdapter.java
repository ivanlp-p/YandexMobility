package ru.ivanl.android.yandexmobility.UI.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.Logic.LogicArtist;
import ru.ivanl.android.yandexmobility.R;
import ru.ivanl.android.yandexmobility.UI.Adapter.Activity.DetailActivity;

public class ArtistAdapter extends CursorAdapter<ArtistAdapter.ArtistHolder> {

    @Inject
    ArtistHelper artistHelper;

    @Inject
    LogicArtist logicArtist;

    Activity activity;

    public ArtistAdapter(Activity activity, Cursor cursor) {
        super(activity, cursor);
        ((ForYandexAplication) activity.getApplicationContext()).getAppComponent().inject(this);

        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistHolder viewHolder, Cursor cursor) {

        viewHolder.id = artistHelper.getId(cursor);
        viewHolder.artist.setText(artistHelper.getName(cursor));
        viewHolder.genres.setText(logicArtist.buildGenreString(cursor));
        viewHolder.albNTrk.setText(logicArtist.buildAlbumsAndTracksString(cursor));

        String url = artistHelper.getSmallCover(cursor);
        Picasso.with(activity).load(url).fit().into(viewHolder.cover);
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_view_item,
                parent,
                false
        );

        return new ArtistHolder(view);

    }

    public class ArtistHolder extends RecyclerView.ViewHolder {

        long id;
        public final TextView artist;
        public final TextView genres;
        public final TextView albNTrk;
        public final ImageView cover;

        public ArtistHolder(View itemView) {
            super(itemView);

            cover = (ImageView) itemView.findViewById(R.id.recyclerview_cover);
            artist = (TextView) itemView.findViewById(R.id.recyclerview_name);
            genres = (TextView) itemView.findViewById(R.id.recyclerview_genres);
            albNTrk = (TextView) itemView.findViewById(R.id.recyclerview_albums_and_tracks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailActivity.prepareIntent(activity, id);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
