package com.creative.trnt;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.creative.trnt.adapter.RecyclerViewAdapter;
import com.creative.trnt.appdata.ApiUrl;
import com.creative.trnt.appdata.AppConstant;
import com.creative.trnt.appdata.AppController;
import com.creative.trnt.eventListener.EndlessRecyclerViewScrollListener;
import com.creative.trnt.eventListener.RecyclerItemClickListener;
import com.creative.trnt.model.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends BaseActivity {

    private static final int KEY_GRID_VIEW = 0;
    private static final int KEY_LIST_VIEW = 1;
    private static int KEY_CURRENT_VIEW = KEY_GRID_VIEW;
    // private GridView gridView;
    private RecyclerView recyclerView;
    /// private IconGridAdapter iconGridAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;

    List<Movie> movieList = new ArrayList<>();

    LinearLayoutManager listLayoutManager;
    GridLayoutManager gridLayoutManager;

    private FloatingActionButton fabTopToTheList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        initToolbar(true);

        init();

        initAdapter();

        initRecyclerViewOnItemClickListener();

        updateUi();
    }


    private void init() {

        // gridView = (GridView) view.findViewById(R.id.gridview_latestmovie);

        fabTopToTheList = (FloatingActionButton) findViewById(R.id.fabTopToTheList);
        fabTopToTheList.setVisibility(View.GONE);
        fabTopToTheList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recyclerView.scrollToPosition(0);
                recyclerView.smoothScrollToPosition(0);
                //recyclerView.setScrollY(0);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    private void initAdapter() {
        // iconGridAdapter = new IconGridAdapter(this, movieList);
        //gridView.setAdapter(iconGridAdapter);

        listLayoutManager = new LinearLayoutManager(this);
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final int numberOfColumns = 2;
        gridLayoutManager = new GridLayoutManager(this,numberOfColumns);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(movieList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void initRecyclerViewOnItemClickListener() {
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Movie movie = movieList.get(position);
                        String movieResponse = AppController.getInstance().getGson().toJson(movie);
                        Intent intent = new Intent(WishListActivity.this, DetailsActivity.class);
                        intent.putExtra(AppConstant.KEY_EXTRA_MOVIE_JSON,movieResponse);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void updateUi(){
        movieList.addAll(AppController.getInstance().getPrefManger().getWishListMovies());

        recyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                onBackPressed();
                break;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
