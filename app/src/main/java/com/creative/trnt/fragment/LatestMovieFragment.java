package com.creative.trnt.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.trnt.R;
import com.creative.trnt.adapter.RecyclerViewAdapter;
import com.creative.trnt.alertbanner.AlertDialogForAnything;
import com.creative.trnt.appdata.ApiUrl;
import com.creative.trnt.appdata.AppController;
import com.creative.trnt.eventListener.EndlessRecyclerViewScrollListener;
import com.creative.trnt.model.Movie;
import com.creative.trnt.model.Movies;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubayer on 8/10/2017.
 */

public class LatestMovieFragment extends Fragment {

    private static final int  KEY_GRID_VIEW = 0;
    private static final int  KEY_LIST_VIEW = 1;
    private static  int  KEY_CURRENT_VIEW = KEY_GRID_VIEW;
    // private GridView gridView;
    private RecyclerView recyclerView;
    /// private IconGridAdapter iconGridAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;

    List<Movie> movieList = new ArrayList<>();

    private Gson gson;

    private EndlessRecyclerViewScrollListener gridScrollListener;

    private EndlessRecyclerViewScrollListener listScrollListener;

    LinearLayoutManager listLayoutManager;
    GridLayoutManager gridLayoutManager;

    private FloatingActionButton fabTopToTheList;

    public LatestMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latestmovie, container, false);
        init(view);

        initAdapter();

        return view;
        // Inflate the layout for this fragment
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        sendRequestToGetPlaceList(ApiUrl.getUrlForMovieList("50", null, null, null, null, null, "seeds", null,null),true);
    }

    private void init(View view) {

        gson = new Gson();

        // gridView = (GridView) view.findViewById(R.id.gridview_latestmovie);

        fabTopToTheList = (FloatingActionButton) view.findViewById(R.id.fabTopToTheList);
        fabTopToTheList.setVisibility(View.GONE);
        fabTopToTheList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recyclerView.scrollToPosition(0);
                recyclerView.smoothScrollToPosition(0);
                //recyclerView.setScrollY(0);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void initAdapter() {
        // iconGridAdapter = new IconGridAdapter(getActivity(), movieList);
        //gridView.setAdapter(iconGridAdapter);

        listLayoutManager = new LinearLayoutManager(getActivity());
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final int numberOfColumns = 3;
        gridLayoutManager = new GridLayoutManager(getActivity(),numberOfColumns);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(movieList, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);

        gridScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
               // loadNextDataFromApi(page);
                Log.d("DEBUG",String.valueOf(page));
                sendRequestToGetPlaceList(ApiUrl.getUrlForMovieList("50", String.valueOf(page), null, null, null, null, "seeds", null,null),false);
            }

            @Override
            public void showUpArrow() {
                if(fabTopToTheList.getVisibility() == View.GONE)
                    fabTopToTheList.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideUpArrow() {

                if(fabTopToTheList.getVisibility() == View.VISIBLE)
                    fabTopToTheList.setVisibility(View.GONE);
            }
        };

        listScrollListener = new EndlessRecyclerViewScrollListener(listLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                // loadNextDataFromApi(page);
                Log.d("DEBUG",String.valueOf(page));
                sendRequestToGetPlaceList(ApiUrl.getUrlForMovieList("50", String.valueOf(page), null, null, null, null, "seeds", null,null),false);
            }

            @Override
            public void showUpArrow() {

            }

            @Override
            public void hideUpArrow() {

            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(gridScrollListener);
    }


    public void sendRequestToGetPlaceList(String url,boolean need_to_show_progressbar) {

        Log.d("DEBUG", url);

        // TODO Auto-generated method stub
        // final ProgressBar progressBar = (ProgressBar)dialog_add_tag.findViewById(R.id.dialog_progressbar);
        //progressBar.setVisibility(View.VISIBLE);
        if(need_to_show_progressbar)showProgressDialog("loading..", true, false);

        final StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();


                        // progressBar.setVisibility(View.GONE);
                        Log.d("DEBUG", response);

                        Movies movies = gson.fromJson(response, Movies.class);
                        if (movies.getStatus().equals("ok")) {
                            movieList.addAll(movies.getData().getMovies());
                            recyclerViewAdapter.notifyDataSetChanged();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dismissProgressDialog();
                //progressBar.setVisibility(View.GONE);
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(),
                        "ERROR",
                        "Something went wrong!!",
                        false);

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        AppController.getInstance().addToRequestQueue(req);
    }


    public void toggleGridViewListView(){
        if(KEY_CURRENT_VIEW == KEY_GRID_VIEW){
            //recyclerView.setLayoutManager(gridLayoutManager);
           // recyclerView.addOnScrollListener(listScrollListener);
            //recyclerViewAdapter.setListStyle(RecyclerViewAdapter.LIST);
            //recyclerViewAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {

            case R.id.action_toggle_gridview_listview:


                if(KEY_CURRENT_VIEW == KEY_GRID_VIEW){
                    inputChangeCallback.changeMenuIcon(R.drawable.ic_view_comfy_black_24dp);
                    //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.history_converted));
                    KEY_CURRENT_VIEW = KEY_LIST_VIEW;
                    recyclerView.setLayoutManager(listLayoutManager);
                     recyclerView.addOnScrollListener(listScrollListener);
                    recyclerViewAdapter.setListStyle(RecyclerViewAdapter.LIST);
                    recyclerViewAdapter.notifyDataSetChanged();
                }else if(KEY_CURRENT_VIEW == KEY_LIST_VIEW){
                    inputChangeCallback.changeMenuIcon(R.drawable.ic_list_black_24dp);
                    KEY_CURRENT_VIEW = KEY_GRID_VIEW;
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.addOnScrollListener(gridScrollListener);
                    recyclerViewAdapter.setListStyle(RecyclerViewAdapter.GRID);
                    recyclerViewAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.action_search:
                // startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ydoodle.mymoneymanager")));
                // Toast.makeText(MainActivity.this,"Please publish your app on play store first!",Toast.LENGTH_LONG).show();
                break;
        }

        return false;
    }
    public interface fragmentActivityCommunicator {
        /*To change something in activty*/
         void changeMenuIcon(int id);

    }

    fragmentActivityCommunicator inputChangeCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            inputChangeCallback = (fragmentActivityCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onFragmentChangeListener");
        }
    }















    private ProgressDialog progressDialog;

    public void showProgressDialog(String message, boolean isIntermidiate, boolean isCancelable) {
       /**/
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.setIndeterminate(isIntermidiate);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}