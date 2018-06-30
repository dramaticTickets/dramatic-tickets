package com.example.chen.dramatic_tickets.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/10/20.
 */

public abstract class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private int mLayoutId;
    private List<Map<String, Object>> mDatas;

    public MyAdapter(Context context, int layoutId, List<Map<String, Object>> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int ViewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        convert(holder, mDatas.get(position));

        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public abstract void convert(ViewHolder holder, Map<String, Object> stringObjectMap);

    static public class ViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;
        private View mConvertView;
        public ViewHolder(Context context, View itemView, ViewGroup parent) {
            super(itemView);
            mConvertView = itemView;
            mViews = new SparseArray<View>();
        }

        public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, parent);
            return holder;
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if(view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface  OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public int getmLayoutId() {
        return mLayoutId;
    }
}
