package com.daniel.JoyRent.House;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.utils.ImageLoaderUtils;


import java.util.List;


public class HousesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<HousesBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public HousesAdapter(Context context) {
        this.mContext = context;
    }

    public void setmDate(List<HousesBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
/*
这里开始填充item_houses 列表里的数据
 */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_houses, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);                                 //没加载到的话显示正在记载
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }
// onBindViewHolder 负责将每个子项holder绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {//判断其左边对象是否为其右边类的实例
            // ItemViewHolder extends RecyclerView.ViewHolder 绑定数据和扩展

            HousesBean Houses = mData.get(position);
            if(Houses == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(Houses.getHouseName());
            ((ItemViewHolder) holder).mDesc.setText(Houses.getDescription());
            ((ItemViewHolder) holder).tvPrice.setText(Houses.getRentPrice());
            ((ItemViewHolder) holder).way.setText(Houses.getWay());

//            Uri uri = Uri.parse(Houses.getImgsrc());
//            ((ItemViewHolder) holder).mHousesImg.setImageURI(uri);
          ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mHousesImg, Houses.getImage());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public HousesBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    /*
    接收布局传来的id，这样方便绑定数据和扩展 ItemViewHolder给  onBindViewHolder


     */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public TextView tvPrice;
        public TextView way;
        public ImageView mHousesImg;



        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            way = (TextView) v.findViewById(R.id.way);
            mHousesImg = (ImageView) v.findViewById(R.id.ivHouses);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {

                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

}
