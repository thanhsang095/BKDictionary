package com.developer.sangbarca.bkdictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SANG BARCA on 5/5/2017.
 */

public class SearchReultAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<SearchResuls> searchResulsList;
    ArrayList<SearchResuls> arrayFiltter;

    public SearchReultAdapter(Context context, int layout, List<SearchResuls> searchResulsList) {
        this.context = context;
        this.layout = layout;
        this.searchResulsList = searchResulsList;
        this.arrayFiltter = new ArrayList<>();
        this.arrayFiltter.addAll(searchResulsList);
    }

    @Override
    public int getCount() {
        return searchResulsList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResulsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgIconRecent;
        TextView txtNameResult;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = new ViewHolder();

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout, null);

            holder.imgIconRecent = (ImageView) rowView.findViewById(R.id.imageViewIconRecent);
            holder.txtNameResult = (TextView) rowView.findViewById(R.id.textViewNameSearch);

            rowView.setTag(holder);
        }else {
            holder = (ViewHolder) rowView.getTag();
        }

        SearchResuls searchResuls = searchResulsList.get(position);
        holder.imgIconRecent.setImageResource(searchResuls.imgIcon);
        holder.txtNameResult.setText(searchResuls.name);


        return rowView;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchResulsList.clear();
        if (charText.length() == 0) {
            searchResulsList.addAll(arrayFiltter);
        }
        else
        {
            for (SearchResuls sear : arrayFiltter)
            {
                if (sear.name.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    searchResulsList.add(sear);
                }
            }
        }
        notifyDataSetChanged();
    }
}
