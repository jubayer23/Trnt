package com.creative.trnt;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creative.trnt.appdata.AppConstant;
import com.creative.trnt.appdata.AppController;
import com.creative.trnt.model.Movie;
import com.creative.trnt.model.Torrent;
import com.creative.trnt.utils.TorrentDownloader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Movie movie;

    private SimpleDraweeView img_cover;

    private TextView tv_genre, tv_movie_name, tv_rate, tv_runtime, tv_sev_size, tv_sev_seed, tv_sev_leech,
            tv_one_eight_size, tv_one_eight_seed, tv_one_eight_leech;

    FloatingTextButton btn_sev_magnet, btn_sev_download, btn_one_eight_magnet, btn_one_eight_download;


    LinearLayout ll_container_genre;

    FloatingActionButton fab_add_to_wishlist, fab_share_link;

    android.support.design.widget.FloatingActionButton  fab_youtube;

    private static final int KEY_FILE_TYPE_SEVENT_TWENTY = 0;
    private static final int KEY_FILE_TYPE_ONE_EIGHT = 1;

    private static final String TAG_ADD_TO_WISHLIST = "Add to wishlist";
    private static final String TAG_REMOVE_FROM_WISHLIST = "Remove from wishlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movie = AppController.getInstance().getGson().fromJson(getIntent().getExtras().getString(AppConstant.KEY_EXTRA_MOVIE_JSON), Movie.class);


        init();

        updateUi();

        updateFloatingActionButtonStatus();

        initClickListener();

    }


    private void init() {

        img_cover = (SimpleDraweeView) findViewById(R.id.img_cover);

        tv_genre = (TextView) findViewById(R.id.tv_genre);
        tv_movie_name = (TextView) findViewById(R.id.tv_movie_name);
        tv_rate = (TextView) findViewById(R.id.tv_rate);
        tv_runtime = (TextView) findViewById(R.id.tv_runtime);
        tv_sev_size = (TextView) findViewById(R.id.tv_sev_size);
        tv_sev_seed = (TextView) findViewById(R.id.tv_sev_seed);
        tv_sev_leech = (TextView) findViewById(R.id.tv_sev_leech);

        tv_one_eight_size = (TextView) findViewById(R.id.tv_one_eight_size);
        tv_one_eight_seed = (TextView) findViewById(R.id.tv_one_eight_seed);
        tv_one_eight_leech = (TextView) findViewById(R.id.tv_one_eight_leech);

        ll_container_genre = (LinearLayout) findViewById(R.id.ll_container_genre);
        ll_container_genre.removeAllViews();

        btn_sev_download = (FloatingTextButton) findViewById(R.id.btn_sev_download);
        btn_sev_magnet = (FloatingTextButton) findViewById(R.id.btn_sev_magnet);
        btn_one_eight_download = (FloatingTextButton) findViewById(R.id.btn_one_eight_download);
        btn_one_eight_magnet = (FloatingTextButton) findViewById(R.id.btn_one_eight_magnet);

        fab_add_to_wishlist = (FloatingActionButton) findViewById(R.id.fab_add_to_wishlist);
        fab_add_to_wishlist.setOnClickListener(this);
        fab_share_link = (FloatingActionButton) findViewById(R.id.fab_share_link);
        fab_share_link.setOnClickListener(this);

        fab_youtube = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_youtube);
        fab_youtube.setOnClickListener(this);

    }


    private void updateUi() {

        tv_movie_name.setText(movie.getTitle());

        if (movie.getMediumCoverImage() != null)
            img_cover.setImageURI(movie.getMediumCoverImage());

        ArrayList<String> genres = (ArrayList<String>) movie.getGenres();


        for (int i = 0; i < genres.size(); i++) {
            TextView tv_genre = (TextView) getLayoutInflater().inflate(R.layout.template_tv, null);
            tv_genre.setText(genres.get(i));
            ll_container_genre.addView(tv_genre);
        }


        tv_rate.setText(String.valueOf(movie.getRating()));
        tv_runtime.setText(String.valueOf(movie.getRuntime()) + " mins");

        ArrayList<Torrent> torrents = (ArrayList<Torrent>) movie.getTorrents();
        int counter = 0;
        for (Torrent torrent : torrents) {

            if (counter == 0) {
                tv_sev_size.setText(torrent.getSize());
                tv_sev_seed.setText(String.valueOf(torrent.getSeeds()));
                tv_sev_leech.setText(String.valueOf(torrent.getPeers()));
            } else if (counter == 1) {
                tv_one_eight_size.setText(torrent.getSize());
                tv_one_eight_seed.setText(String.valueOf(torrent.getSeeds()));
                tv_one_eight_leech.setText(String.valueOf(torrent.getPeers()));
            }
            counter++;
        }

    }


    private void updateFloatingActionButtonStatus(){

        fab_add_to_wishlist.setLabelText(TAG_ADD_TO_WISHLIST);

        List<Movie> wishListMovies = AppController.getInstance().getPrefManger().getWishListMovies();

        for(Movie wishListMovie: wishListMovies){

            if(String.valueOf(wishListMovie.getId()).equals(String.valueOf( movie.getId()))){
                fab_add_to_wishlist.setImageResource(R.drawable.ic_favorite_white);
                fab_add_to_wishlist.setLabelText(TAG_REMOVE_FROM_WISHLIST);
                break;
            }
        }
    }

    private void initClickListener() {
        btn_sev_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadTorrent(KEY_FILE_TYPE_SEVENT_TWENTY);
            }
        });
        btn_one_eight_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadTorrent(KEY_FILE_TYPE_ONE_EIGHT);
            }
        });
    }


    private void downloadTorrent(int file_type) {

        String movieName;
        if (movie.getTitleEnglish() != null) {
            movieName = movie.getTitleEnglish();
        } else {
            movieName = movie.getTitle();
        }
        Torrent torrent = movie.getTorrents().get(file_type);
        try {
            String mUrl = generateMagneticUrl(torrent.getHash(), movieName);
            new TorrentDownloader().openMagneturi(mUrl, DetailsActivity.this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.fab_add_to_wishlist) {

            List<Movie> wishListMovies = AppController.getInstance().getPrefManger().getWishListMovies();


            if(fab_add_to_wishlist.getLabelText().equalsIgnoreCase(TAG_REMOVE_FROM_WISHLIST)){
                for(Movie wishListMovie: wishListMovies){
                    if(wishListMovie.getId() == movie.getId()){
                        wishListMovies.remove(wishListMovie);
                        break;
                    }
                }
                fab_add_to_wishlist.setLabelText(TAG_ADD_TO_WISHLIST);
                fab_add_to_wishlist.setImageResource(R.drawable.ic_favorite_border_white);
            }else{
                wishListMovies.add(movie);
                fab_add_to_wishlist.setLabelText(TAG_REMOVE_FROM_WISHLIST);
                fab_add_to_wishlist.setImageResource(R.drawable.ic_favorite_white);
            }
            AppController.getInstance().getPrefManger().setWishListMovies(wishListMovies);
        }

        if (id == R.id.fab_youtube) {
            if(movie.getYtTrailerCode() != null  && !movie.getYtTrailerCode().isEmpty()){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + movie.getYtTrailerCode()));
                startActivity(intent);
            }
        }
    }


    public String generateMagneticUrl(String hash, String movieName) throws UnsupportedEncodingException {
        //magnet:?xt=urn:btih:TORRENT_HASH&dn=Url+Encoded+Movie+Name&tr=http://track.one:1234/announce&tr=udp://track.two:80

        String baseString = "magnet:?xt=urn:btih:" + hash + "&dn=";
        String encodedMovieName = URLEncoder.encode(movieName, "utf-8").replace("+", "%20");
        baseString += encodedMovieName;
        Log.e("TAG", "after mName encode = " + baseString);

        String tracker1 = "udp://open.demonii.com:1337/announce";
        String tracker2 = "udp://tracker.openbittorrent.com:80";
        String tracker3 = "udp://tracker.coppersurfer.tk:6969";
        String tracker4 = "udp://glotorrents.pw:6969/announce";
        String tracker5 = "udp://tracker.opentrackr.org:1337/announce";
        String tracker6 = "udp://torrent.gresille.org:80/announce";
        String tracker7 = "udp://p4p.arenabg.com:1337";
        String tracker8 = "udp://tracker.leechers-paradise.org:6969";

        String[] trackerArray = {tracker1, tracker2, tracker3, tracker4, tracker5, tracker6, tracker7, tracker8};
        String trackers = "";


        for (String trackerItem : trackerArray) {
            trackers += "&tr=" + URLEncoder.encode(trackerItem, "utf-8").replace("+", "%20");
        }
        Log.e("TAG", "after tracker encode = " + trackers);
        Log.e("TAG", "final magnetic url  = " + baseString + trackers);

        return baseString + trackers;

    }
}
