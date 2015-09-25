package com.icer.cnbeta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icer.cnbeta.R;
import com.icer.cnbeta.volley.entity.Latest;

import java.util.ArrayList;

public class LatestListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Latest> mData;

    public LatestListAdapter(Context context, ArrayList<Latest> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_latest, parent, false);
            holder = new ViewHolder();
            holder.initView(convertView);
            convertView.setTag(holder);
        }
        if (holder == null)
            holder = (ViewHolder) convertView.getTag();
        holder.fillInData(mData.get(position));
        return convertView;
    }

    public void refreshData(ArrayList<Latest> list) {
        //»±…Ÿsid≈–∂œ
        notifyDataSetChanged();
    }

    public void addData(ArrayList<Latest> list) {
        //»±…Ÿsid≈–∂œ
        mData.addAll(list);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView title;
        private TextView summary;

        private void initView(View view) {
            title = (TextView) view.findViewById(R.id.item_title_tv);
            summary = (TextView) view.findViewById(R.id.item_summary_tv);
        }

        private void fillInData(Latest bean) {
            title.setText(bean.title);
            summary.setText(bean.summary);
        }
    }
}
