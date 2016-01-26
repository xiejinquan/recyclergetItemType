package com.jesse.test2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jesse.recyclerview.JesseHolder;

import java.util.List;

/**
 * Author：Jesse
 * Creation time：2016/1/25 00:17
 * Contact me：774202859@qq.com
 */
public  abstract class HeadFootAdapter<HT,IT,FT> extends RecyclerView.Adapter<JesseHolder> {
    /** 头部类型 */
    private final int TYPE_HEAD = 0;
    /** 中间类型 */
    private final int TYPE_ITEM = 1;
    /** 底部类型 */
    private final int TYPE_FOOT = 2;

    private Context mContext;
    private int mHeadLayoutdID;
    private List<HT> mHeadDatas;
    private int mItemLayoutID;
    private List<IT> mItemDatas;
    private int mFootLayoutID;
    private List<FT> mFootDatas;

    /** 只显示中间item */
    public HeadFootAdapter(Context mContext, int mItemLayoutID, List<IT> mItemDatas) {
        this(mContext,0,null,mItemLayoutID,mItemDatas,0,null);
    }

    /** 显示头部item和中间item isfoot没意义，只是区别显示在头部还是底部*/
    public HeadFootAdapter(Context mContext, int mHeadLayoutdID, List<HT> mHeadDatas, int mItemLayoutID, List<IT> mItemDatas,Boolean isfoot) {
        this(mContext,mHeadLayoutdID,mHeadDatas,mItemLayoutID,mItemDatas,0,null);
    }

    /** 显示中间item和底部item */
    public HeadFootAdapter(Context mContext, int mItemLayoutID, List<IT> mItemDatas, int mFootLayoutID, List<FT> mFootDatas) {
        this(mContext,0,null,mItemLayoutID,mItemDatas,mFootLayoutID,mFootDatas);
    }

    /**
     * @param mContext
     * @param mHeadLayoutdID 头部布局
     * @param mHeadDatas     头部数据
     * @param mItemLayoutID 中间布局
     * @param mItemDatas    中间数据
     * @param mFootLayoutID 底部布局
     * @param mFootDatas    底部数据
     */
    public HeadFootAdapter(Context mContext, int mHeadLayoutdID, List<HT> mHeadDatas, int mItemLayoutID, List<IT> mItemDatas, int mFootLayoutID, List<FT> mFootDatas) {
        this.mContext = mContext;
        this.mHeadLayoutdID = mHeadLayoutdID;
        this.mHeadDatas = mHeadDatas;
        this.mItemLayoutID = mItemLayoutID;
        this.mItemDatas = mItemDatas;
        this.mFootLayoutID = mFootLayoutID;
        this.mFootDatas = mFootDatas;
    }

    /** 对头部item的子布局操作 */
    public abstract void onBindHeaderViewHolder(JesseHolder holder, int position);
    /** 对中间item的子布局操作 */
    public abstract void onBindItemViewHolder(JesseHolder holder, int position);
    /** 对底部item的子布局操作 */
    public abstract void onBindFooterViewHolder(JesseHolder holder, int position);

    /** 头部item单击事件 */
    public interface OnHeadClickListener {
        void onItemClick(View view, int position);
    }
    private OnHeadClickListener mOnHeadClickListener;
    public void setOnHeadClickListener(OnHeadClickListener mOnHeadClickListener) {
        this.mOnHeadClickListener = mOnHeadClickListener;
    }

    /** 中间item单击事件 */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /** 底部item单击事件 */
    public interface OnFootClickListener {
        void onItemClick(View view, int position);
    }
    private OnFootClickListener mOnFootClickListener;
    public void setOnFootClickListener(OnFootClickListener mOnFootClickListener) {
        this.mOnFootClickListener = mOnFootClickListener;
    }

    /** 这里viewType=getItemViewType(),也就是重写getItemViewType()返回viewType，根据viewType来创建不同的ViewHolder */
    @Override
    public JesseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View headview = LayoutInflater.from(mContext).inflate(mHeadLayoutdID, parent, false);
                return new JesseHolder(headview);
            case 1:
                View itemview = LayoutInflater.from(mContext).inflate(mItemLayoutID, parent, false);
                return new JesseHolder(itemview);
            case 2:
            default:
                View footView = LayoutInflater.from(mContext).inflate(mFootLayoutID, parent, false);
                return new JesseHolder(footView);
        }
    }

    /** 根据ViewType绑定数据  这里重新计算了position，使每个部分的position都从0开始 */
    @Override
    public void onBindViewHolder(final JesseHolder holder, int position) {
        int headCount = getHeadViewCount();
        int itemViewCount = getItemViewCount();
        int itemType = getItemViewType(position);
        switch (itemType){
            case 0:
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnHeadClickListener != null) {
                            int layoutPosition = holder.getLayoutPosition();
                            mOnHeadClickListener.onItemClick(holder.getItemView(), layoutPosition);
                        }
                    }
                });
            onBindHeaderViewHolder(holder,position);
            break;
            case 1:
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            int layoutPosition = holder.getLayoutPosition();
                            mOnItemClickListener.onItemClick(holder.getItemView(), layoutPosition);
                        }
                    }
                });
            onBindItemViewHolder(holder,position-headCount);
            break;
            case 2:
            default:
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnFootClickListener != null) {
                            int layoutPosition = holder.getLayoutPosition();
                            mOnFootClickListener.onItemClick(holder.getItemView(), layoutPosition);
                        }
                    }
                });
            onBindFooterViewHolder(holder,position-itemViewCount-headCount);
            break;
        }

    }

    /**
     *adapter会调用这个方法来获取item的总数
     *总数为上中下三部分数量加起来
     *不需要重写，标志为final
     *返回所有View的数量
     */
    @Override
    public final int getItemCount(){
        return  getHeadViewCount() + getFootViewCount() + getItemViewCount();
    }

    /** 返回头部View的数量 */
    public int getHeadViewCount(){
        return mHeadDatas==null?0:mHeadDatas.size();
    }

    /** 返回底部View的数量 */
    public int getFootViewCount(){
        return mFootDatas==null?0:mFootDatas.size();
    }


    /** 返回中间View的数量 */
    public int getItemViewCount(){
        return mItemDatas==null?0:mItemDatas.size();
    }

    /** 重点，根据position来判断item的类型 adapter会将此方法的返回值传入onCreateViewHolder */
    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        int headCount = getHeadViewCount();
        int itemCount = getItemViewCount();
        if(position<headCount){
            return TYPE_HEAD;
        }
        if(position>headCount+itemCount-1){
            return TYPE_FOOT;
        }
        return TYPE_ITEM;
    }

}