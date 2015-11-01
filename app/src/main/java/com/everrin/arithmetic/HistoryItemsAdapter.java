package com.everrin.arithmetic;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by everrin on 10/11/2015.
 */
public class HistoryItemsAdapter extends BaseAdapter {

    private static final String TAG = HistoryItemsAdapter.class.getSimpleName();
    private ArrayList<SimpleFormula> mlist;
    private static LayoutInflater mInflater;

    public HistoryItemsAdapter(LayoutInflater iflt, ArrayList<SimpleFormula> list)
    {
        mInflater = iflt;
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null)
        {
            vi = mInflater.inflate(R.layout.one_list_item_layout, null);
        }
        TextView tv = (TextView) vi.findViewById(R.id.one_list_item_textView);

        SimpleFormula item = mlist.get(position);
        tv.setText("  " + item.toInputString());

        //tv.setBackgroundColor(Color.argb(0x0FF, 0x094, 0x04, 0x08D));

        if(item.checkResult())
        {
            tv.setTextColor(Color.BLUE);
        }else
        {
            tv.setTextColor(Color.RED);
        }

        return vi;
    }
}
