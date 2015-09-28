package com.icer.cnbeta.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icer.cnbeta.R;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.ui.ContentActivity;
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

    public boolean refreshData(ArrayList<Latest> list) {
        boolean res = false;
        if (mData.size() == 0) {
            addData(list);
            res = true;
        } else {
            int count = list.size();
            for (int i = 0; i < count; i++) {
                if (list.get(0).sid.compareTo(mData.get(i).sid) > 0) {
                    mData.add(i, list.remove(0));
                    res = true;
                } else
                    break;
            }
            notifyDataSetChanged();
        }
        return res;
    }

    public void addData(ArrayList<Latest> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, ContentActivity.class);
        intent.putExtra(AppConstants.SID, mData.get(position).sid);
        intent.putExtra(AppConstants.TITLE, mData.get(position).title);
        intent.putExtra(AppConstants.PUBTIME, mData.get(position).pubtime);
        intent.putExtra(AppConstants.SUMMARY, mData.get(position).summary);
        mContext.startActivity(intent);
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
