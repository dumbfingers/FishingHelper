package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by yaxi on 20/03/2014.
 */
public class CardStyleAdapter extends ArrayAdapter {

    private Context context;
    private int layout;
//    private Cursor cursor;
    private LayoutInflater inflater;
    private List list;

    private int reqWidth;
    private int reqHeight;
    private FishViewHolder holder = null;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public CardStyleAdapter(Context context, int layout, List list) {
        super(context, layout, list);
//        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
//        this.cursor = c;
        this.list = list;
        this.inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_action_picture)
                .showImageOnFail(R.drawable.ic_action_warning)
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .imageDownloader(new SQLiteImageLoader(context))
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {

            view = inflater.inflate(layout, parent, false);
            holder = new FishViewHolder();

            holder.title = (TextView) view.findViewById(R.id.textView_cell_title);
            holder.descr = (TextView) view.findViewById(R.id.textView_cell_descr);
            holder.img = (ImageView) view.findViewById(R.id.imageView_fishImg);

            view.setTag(holder);

        } else {
            holder = (FishViewHolder)view.getTag();
        }

        Fish fish = (Fish)list.get(position);
        holder.title.setText(fish.getFishName());

        imageLoader.displayImage("db://" + position, holder.img, options);

        // set for the selected colour
        view.setBackgroundResource(mSelectedItems.get(position) ?
                R.color.selected_overlay : android.R.color.transparent);

        return view;
    }



    static class FishViewHolder {
        TextView title;
        TextView descr;
        ImageView img;
    }


    public void toggleSelection(int position) {
        selectView(position, !mSelectedItems.get(position));
    }

    public void removeSelection() {
        mSelectedItems = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean selected) {
        if (selected == true)
            mSelectedItems.put(position, selected);
        else
            mSelectedItems.delete(position);

        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItems() {
        return mSelectedItems;
    }
}
