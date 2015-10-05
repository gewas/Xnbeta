package com.icer.cnbeta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icer.cnbeta.R;
import com.icer.cnbeta.util.StringUtil;
import com.icer.cnbeta.util.TextViewUtil;
import com.icer.cnbeta.volley.entity.NewsComment;

import java.util.ArrayList;

/**
 * Created by icer on 2015/10/5.
 */
public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NewsComment> mData;

    public CommentAdapter(Context context, ArrayList<NewsComment> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
            holder = new ViewHolder();
            holder.initView(convertView);
            convertView.setTag(holder);
        }
        if (holder == null)
            holder = (ViewHolder) convertView.getTag();
        holder.fillData(position);
        return convertView;
    }

    public void addData(ArrayList<NewsComment> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView floorNum;
        private TextView time;
        private TextView username;
        private TextView reply;
        private TextView content;
        private TextView agree;
        private TextView against;

        private void initView(View view) {
            floorNum = (TextView) view.findViewById(R.id.comment_floor_num_tv);
            time = (TextView) view.findViewById(R.id.comment_time_tv);
            username = (TextView) view.findViewById(R.id.comment_username_tv);
            reply = (TextView) view.findViewById(R.id.comment_reply_floor_num_tv);
            content = (TextView) view.findViewById(R.id.comment_content_tv);
            agree = (TextView) view.findViewById(R.id.comment_agree_tv);
            against = (TextView) view.findViewById(R.id.comment_against_tv);
        }

        private void initData() {
            floorNum.setText(mContext.getString(R.string.comment_floor_num));
            time.setText(mContext.getString(R.string.comment_time));
            username.setText(mContext.getString(R.string.comment_username));
            reply.setText(mContext.getString(R.string.comment_reply_floor_num));
            agree.setText(mContext.getString(R.string.comment_agree));
            against.setText(mContext.getString(R.string.comment_against));
        }

        private void fillData(int position) {
            initData();
            NewsComment newsComment = mData.get(position);
            floorNum.setText(String.format(floorNum.getText().toString(), (position + 1) + ""));
            TextViewUtil.setTextAfterColon(mContext, time, newsComment.created_time, false);
            if (!StringUtil.isEmpty(newsComment.username))
                username.setText(newsComment.username);
            if (!StringUtil.isEmpty(newsComment.pid) && !newsComment.pid.equals("0")) {
                reply.setVisibility(View.VISIBLE);
                for (int i = 0; i < mData.size(); i++) {
                    NewsComment comment = mData.get(i);
                    if (comment.tid.equals(newsComment.pid)) {
                        reply.setText(String.format(reply.getText().toString(), (i + 1) + ""));
                        break;
                    }
                }
            } else
                reply.setVisibility(View.INVISIBLE);
            content.setText(newsComment.content);
            TextViewUtil.setSubColorText(mContext, agree, null, R.color.color_ccff5555, newsComment.support);
            TextViewUtil.setSubColorText(mContext, against, null, R.color.color_ccff5555, newsComment.against);
        }
    }
}
