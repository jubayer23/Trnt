package com.creative.trnt.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.creative.trnt.R;
import com.creative.trnt.model.Movie;

import java.util.List;

/**
 * Created by comsol on 9/10/2015.
 */
public class IconGridAdapter extends BaseAdapter {

    private List<Movie> displayedplaces;
    private List<Movie> originalplaces;
    private LayoutInflater inflater;
    @SuppressWarnings("unused")
    private Activity activity;

    public IconGridAdapter(Activity activity,List<Movie> venues) {
        this.activity = activity;
        this.displayedplaces = venues;
        this.originalplaces = venues;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return displayedplaces.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_grid_item, parent, false);
            viewHolder = new ViewHolder();
            //viewHolder.img_thumbnail = (ImageView) convertView
            //        .findViewById(R.id.img_thumbnail);
            //viewHolder.tv_name = (TextView) convertView
            //        .findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie venue = displayedplaces.get(position);

       // viewHolder.img_icon.setImageResource(activity.getResources().getIdentifier(
       //         levelParams[0], "drawable", activity.getPackageName()));
        viewHolder.tv_name.setText(venue.getTitleEnglish());

        return convertView;
    }

    private static class ViewHolder {

        private ImageView img_thumbnail;
        private TextView tv_name;
        // private TextView destination;
    }


}
