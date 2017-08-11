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

    public int getItemViewType(int paramInt) {
        return this.listStyle;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
        Movie localHeavyMovie = (Movie) this.moviesList.get(paramInt);


        ((GridViewHolder) paramViewHolder).title.setText(localHeavyMovie.getTitleEnglish());
        ((GridViewHolder) paramViewHolder).image.setImageURI(localHeavyMovie.getMediumCoverImage());
        if (paramViewHolder.getItemViewType() == 0) {
           // ((GridViewHolder) paramViewHolder).title.setText(localHeavyMovie.c() + " (" + localHeavyMovie.f() + ")");
            //j.a(this.mContext).a(localHeavyMovie.s(), ((GridViewHolder) paramViewHolder).image);
            return;
        }
        //((ListViewHolder) paramViewHolder).title.setText(localHeavyMovie.c() + " (" + localHeavyMovie.f() + ")");
       // ((ListViewHolder) paramViewHolder).rating.setText(localHeavyMovie.g() + "");
       // ((ListViewHolder) paramViewHolder).genres.setText(TextUtils.join(", ", localHeavyMovie.i()));
       // j.a(this.mContext).a(localHeavyMovie.s(), ((ListViewHolder) paramViewHolder).image);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        //if (paramInt == 0) {
            return new GridViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_grid_item, null));
        //}
      //  return new ListViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_grid_item, null));
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
        public SimpleDraweeView image;
        public TextView title;

        public GridViewHolder(View paramView) {
            super(paramView);
            this.title = ((TextView) paramView.findViewById(R.id.title));
            this.image = ((SimpleDraweeView) paramView.findViewById(R.id.my_image_view));
        }
    }

    public class ListViewHolder
            extends RecyclerView.ViewHolder {
        public TextView genres;
        public ImageView image;
        public TextView rating;
        public TextView title;

        public ListViewHolder(View paramView) {
            super(paramView);
            //this.title = ((TextView) paramView.findViewById(2131558504));
            //this.image = ((ImageView) paramView.findViewById(2131558620));
           // this.rating = ((TextView) paramView.findViewById(2131558621));
            //this.genres = ((TextView) paramView.findViewById(2131558571));
        }
    }
}


/* Location:           E:\APK\dex2jar\classes-dex2jar.jar
 * Qualified Name:     vinitm.yts.Views.InfiniteScrollRecyclerView.Adapter.RecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */