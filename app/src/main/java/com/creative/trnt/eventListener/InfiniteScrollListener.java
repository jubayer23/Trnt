package com.creative.trnt.eventListener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by comsol on 11-Aug-17.
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener{

    private int currentPage = 1;
    private boolean loading = true;
    RecyclerView.LayoutManager mLayoutManager;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 1;
    private int visibleThreshold = 30;

    private int bufferItemCount = 10;
    //private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;

    public InfiniteScrollListener(GridLayoutManager paramGridLayoutManager)
    {
        this.mLayoutManager = paramGridLayoutManager;
        this.visibleThreshold *= paramGridLayoutManager.getSpanCount();
    }

    public InfiniteScrollListener(LinearLayoutManager paramLinearLayoutManager)
    {
        this.mLayoutManager = paramLinearLayoutManager;
    }

    public InfiniteScrollListener(StaggeredGridLayoutManager paramStaggeredGridLayoutManager)
    {
        this.mLayoutManager = paramStaggeredGridLayoutManager;
        this.visibleThreshold *= paramStaggeredGridLayoutManager.getSpanCount();
    }

    public InfiniteScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    }

    public abstract void loadMore(int page, int totalItemsCount);


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }



    @Override
    public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true; }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }

        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int paramInt1, int paramInt2) {

        int i = this.mLayoutManager.getItemCount();
    }
}
