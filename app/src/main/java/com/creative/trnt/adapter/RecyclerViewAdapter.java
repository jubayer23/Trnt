package com.creative.trnt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creative.trnt.R;
import com.creative.trnt.model.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int GRID = 0;
    public static final int LIST = 1;
    private int listStyle = 0;
    private Context mContext;
    private List<Movie> moviesList;

    public RecyclerViewAdapter(List<Movie> paramList, Context paramContext) {
        this.moviesList = paramList;
        this.mContext = paramContext;
    }

    public int getItemCount() {
        return this.moviesList.size();
    }

    @Override
    public int getItemViewType(int paramInt) {
        return this.listStyle;
    }


    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
        Movie localHeavyMovie =  this.moviesList.get(paramInt);



        if (paramViewHolder.getItemViewType() == 0) {
            ((GridViewHolder) paramViewHolder).tv_movie_name.setText(localHeavyMovie.getTitleEnglish());
            ((GridViewHolder) paramViewHolder).img_cover.setImageURI(localHeavyMovie.getMediumCoverImage());
            return;
        }
        ((ListViewHolder) paramViewHolder).tv_movie_name.setText(localHeavyMovie.getTitleEnglish());
        ((ListViewHolder) paramViewHolder).img_cover.setImageURI(localHeavyMovie.getMediumCoverImage());
        if(localHeavyMovie.getGenres() != null && !localHeavyMovie.getGenres().isEmpty()){
            ((ListViewHolder) paramViewHolder).tv_genre.setText(localHeavyMovie.getGenres().get(0));
        }else{
            ((ListViewHolder) paramViewHolder).tv_genre.setText("No genre");
        }

        ((ListViewHolder) paramViewHolder).tv_rating.setText("Rating : " +String.valueOf(localHeavyMovie.getRating()));
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        if (paramInt == 0) {
            return new GridViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_grid_item, null));
        }
       return new ListViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_list_item, null));
    }

    public void removeAllAndNotify() {
        int i = this.moviesList.size();
        this.moviesList.clear();
        notifyItemRangeRemoved(0, i);
    }

    public void setListStyle(int paramInt) {
        this.listStyle = paramInt;
    }

    public class GridViewHolder
            extends RecyclerView.ViewHolder {
        public SimpleDraweeView img_cover;
        public TextView tv_movie_name;

        public GridViewHolder(View paramView) {
            super(paramView);
            this.tv_movie_name = ((TextView) paramView.findViewById(R.id.tv_movie_name));
            this.img_cover = ((SimpleDraweeView) paramView.findViewById(R.id.img_cover));
        }
    }

    public class ListViewHolder
            extends RecyclerView.ViewHolder {
        public TextView tv_movie_name;
        public SimpleDraweeView img_cover;
        public TextView tv_genre;
        public TextView tv_rating;

        public ListViewHolder(View paramView) {
            super(paramView);
            this.tv_movie_name = ((TextView) paramView.findViewById(R.id.tv_movie_name));
            this.img_cover = ((SimpleDraweeView) paramView.findViewById(R.id.img_cover));
            this.tv_genre = ((TextView) paramView.findViewById(R.id.tv_genre));
            this.tv_rating = ((TextView) paramView.findViewById(R.id.tv_rating));
        }
    }
}


/* Location:           E:\APK\dex2jar\classes-dex2jar.jar
 * Qualified Name:     vinitm.yts.Views.InfiniteScrollRecyclerView.Adapter.RecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */