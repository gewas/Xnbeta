package com.icer.cnbeta.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.icer.cnbeta.manager.FileManager;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.ui.ContentActivity;
import com.icer.cnbeta.volley.entity.NewsInfo;

import java.io.File;
import java.util.ArrayList;

public class CollectionListAdapter extends BaseAdapter {

    public static final String TAG = CollectionListAdapter.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Context mContext;
    private ArrayList<NewsInfo> mData;

    public CollectionListAdapter(Context context, ArrayList<NewsInfo> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collection, parent, false);
            holder = new ViewHolder();
            holder.initView(convertView);
            convertView.setTag(holder);
        }
        if (holder == null)
            holder = (ViewHolder) convertView.getTag();
        holder.fillInData(mData.get(position));
        return convertView;
    }

    public void addData(ArrayList<NewsInfo> list) {
        if (list != null && !list.isEmpty()) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void onItemClick(int position) {
        NewsInfo newsInfo = mData.get(position);
        Intent intent = new Intent(mContext, ContentActivity.class);
        intent.putExtra(AppConstants.SID, newsInfo.sid);
        intent.putExtra(AppConstants.TITLE, newsInfo.title);
        intent.putExtra(AppConstants.PUBTIME, newsInfo.pubtime);
        intent.putExtra(AppConstants.POSITION, position);
        ((BaseActivity) mContext).goToActivity(intent);
    }

    public void delItemAtPosition(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    public String getLastSid() {
        if (mData.size() == 0)
            return null;
        else
            return mData.get(mData.size() - 1).sid;
    }

    private class ViewHolder {
        private ImageView thumb;
        private TextView title;
        private TextView summary;

        private void initView(View view) {
            thumb = (ImageView) view.findViewById(R.id.item_thumb_iv);
            title = (TextView) view.findViewById(R.id.item_title_tv);
            summary = (TextView) view.findViewById(R.id.item_summary_tv);
        }

        private void fillInData(NewsInfo bean) {
            loadImage(bean);
            title.setText(bean.title);
            summary.setText(bean.summary);
        }

        private void loadImage(final NewsInfo bean) {
            thumb.setImageResource(R.drawable.ic_launcher);
            File file = FileManager.getInstance().findFileWithinInternalStorage(mContext, bean.thumb.hashCode() + "");
            if (file != null) {
                ((BaseActivity) mContext).logI(TAG, "Loading image from internal storage: " + file.getAbsolutePath());
                thumb.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                thumb.setTag(bean.thumb);
                int maxWidth = ((BaseActivity) mContext).dp2pxInt(80);
                int maxHeight = ((BaseActivity) mContext).dp2pxInt(80);
                RequestManager.getInstance().cancelRequest(thumb.hashCode());
                RequestManager.getInstance().requestImage(bean.thumb, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        FileManager.getInstance().saveBitmap2InternalStorage(mContext, bean.thumb, bitmap);
                        if (!((BaseActivity) mContext).isDestroyed() && thumb.getTag().equals(bean.thumb))
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
}
