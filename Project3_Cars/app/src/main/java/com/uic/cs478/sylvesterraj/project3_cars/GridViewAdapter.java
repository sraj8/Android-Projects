package com.uic.cs478.sylvesterraj.project3_cars;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class GridViewAdapter extends BaseAdapter {
    private static final int PADDING = 8;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private Context mContext;          // This will have to be passed to the ImageView
    private List<Integer> thumbIds;   // Adapter must store AdapterView's items
    private List<Integer> imgIds;   //full size images
    private List<Integer> labelIds; // labels of the grid images
    private List<String> urls;  //urls associated with the images
    private List<Integer> dealers;// dealers of cars

    static class ViewHolder{
        private ImageView img;
        private TextView name;
    }

    // Save the list of image IDs and the context
    public GridViewAdapter(Context c, ArrayList<Integer> thumbIdsCars, List<Integer> imgIdsCars,List<Integer> ids, ArrayList<String> urls, ArrayList<Integer>dealers) {
        mContext = c;
        this.thumbIds = thumbIdsCars;
        this.imgIds = imgIdsCars;
        this.labelIds = ids;
        this.urls = urls;
        this.dealers = dealers;
    }

    // Now the methods inherited from abstract superclass BaseAdapter

    // Return the number of items in the Adapter
    @Override
    public int getCount() {
        return thumbIds.size();
    }

    // Return the image item at position
    @Override
    public Object getItem(int position) {
        return imgIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imgIds.get(position);
    }

    // ViewHolder to hold images and texts - image and caption
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.grid_image,null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.grid_img);
            holder.name = (TextView) convertView.findViewById(R.id.grid_label);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(thumbIds.get(position));
        holder.name.setText(labelIds.get(position));

        return convertView;
    }
}
