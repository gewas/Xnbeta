package com.icer.cnbeta.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.ui.ContentActivity;
import com.icer.cnbeta.util.TextViewUtil;
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
        mContext.startActivity(intent);
    }

    public String getLastSid() {
        if (mData.size() == 0)
            return null;
        else
            return mData.get(mData.size() - 1).sid;
    }

    private class ViewHolder {
        private ImageView thumb;
        private TextView info;
        private TextView title;
        private TextView summary;

        private void initView(View view) {
            thumb = (ImageView) view.findViewById(R.id.item_thumb_iv);
            info = (TextView) view.findViewById(R.id.item_info_tv);
            title = (TextView) view.findViewById(R.id.item_title_tv);
            summary = (TextView) view.findViewById(R.id.item_summary_tv);
        }

        private void fillInData(Latest bean) {
            loadImage(bean);
            info.setText(R.string.item_info);//reset to initial state is important
            TextViewUtil.setSubColorText(mContext, info, null, R.color.color_333333, bean.comments, bean.score, bean.score_story);
            title.setText(bean.title);
            summary.setText(bean.summary);
        }

        private void loadImage(final Latest bean) {
            thumb.setImageResource(R.drawable.ic_launcher);
            thumb.setTag(bean.thumb);
            int maxWidth = ((BaseActivity) mContext).dp2pxInt(80);
            int maxHeight = ((BaseActivity) mContext).dp2pxInt(80);
            RequestManager.getInstance().cancelRequest(thumb.hashCode());
            RequestManager.getInstance().requestImage(bean.thumb, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    if (thumb.getTag().equals(bean.thumb))
                        thumb.setImageBitmap(bitmap);
                }
            }, maxWidth, maxHeight, ImageView.ScaleType.FIT_XY, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ((BaseActivity) mContext).showToast(AppConstants.HINT_LOADING_FAILED);
                }
            }, thumb.hashCode());
        }
    }
}
