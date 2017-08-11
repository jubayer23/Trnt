package com.creative.trnt.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.trnt.R;
import com.creative.trnt.adapter.IconGridAdapter;
import com.creative.trnt.adapter.RecyclerViewAdapter;
import com.creative.trnt.alertbanner.AlertDialogForAnything;
import com.creative.trnt.appdata.ApiUrl;
import com.creative.trnt.appdata.AppConstant;
import com.creative.trnt.appdata.AppController;
import com.creative.trnt.model.Movie;
import com.creative.trnt.model.Movies;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubayer on 8/10/2017.
 */

public class LatestMovieFragment extends Fragment {

    // private GridView gridView;
    private RecyclerView recyclerView;
    /// private IconGridAdapter iconGridAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;

    List<Movie> movieList = new ArrayList<>();

    private Gson gson;

    public LatestMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        sendRequestToGetPlaceList(ApiUrl.getUrlForMovieList("50", null, null, null, null, null, "seeds", null));
    }

    private void init(View view) {

        gson = new Gson();

        // gridView = (GridView) view.findViewById(R.id.gridview_latestmovie);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void initAdapter() {
        // iconGridAdapter = new IconGridAdapter(getActivity(), movieList);
        //gridView.setAdapter(iconGridAdapter);

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerViewAdapter = new RecyclerViewAdapter(movieList, getActivity());
        //adapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    public void sendRequestToGetPlaceList(String url) {

        Log.d("DEBUG", url);

        // TODO Auto-generated method stub
        // final ProgressBar progressBar = (ProgressBar)dialog_add_tag.findViewById(R.id.dialog_progressbar);
        //progressBar.setVisibility(View.VISIBLE);
        showProgressDialog("loading..", true, false);

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