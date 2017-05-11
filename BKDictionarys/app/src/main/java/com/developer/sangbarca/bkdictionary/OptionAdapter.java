package com.developer.sangbarca.bkdictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SANG BARCA on 4/28/2017.
 */

public class OptionAdapter extends BaseAdapter{
    public Context context;
    public int layout;
    public List<Option> listOption;

    public OptionAdapter(Context context, int layout, List<Option> listOption) {
        this.context = context;
        this.layout = layout;
        this.listOption = listOption;
    }

    @Override
    public int getCount() {
        return listOption.size();
    }

    @Override
    public Object getItem(int position) {
        return listOption.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgHinh;
        TextView txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView  = convertView;
        ViewHolder holder = new ViewHolder();

        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout, null);

            holder.imgHinh = (ImageView) rowView.findViewById(R.id.imageViewIcon);
            holder.txtName = (TextView) rowView.findViewById(R.id.textViewName);

            rowView.setTag(holder);
        }else {
            holder = (ViewHolder) rowView.getTag();
        }

        Option items = listOption.get(position);
        holder.imgHinh.setImageResource(items.hinh);
        holder.txtName.setText(items.name);

        return rowView;
    }
}
