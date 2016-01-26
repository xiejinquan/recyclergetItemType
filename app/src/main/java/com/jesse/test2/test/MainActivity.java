package com.jesse.test2.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jesse.recyclerview.JesseHolder;
import com.jesse.test2.FootView;
import com.jesse.test2.HeadFootAdapter;
import com.jesse.test2.HeadView;
import com.jesse.test2.ItemView;
import com.jesse.test2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        assignViews();
        iniRecyclerview();
    }

    private HeadFootAdapter<HeadView,ItemView,FootView> mAdapter;
    private void iniRecyclerview() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(mAdapter = new HeadFootAdapter<HeadView, ItemView, FootView>(this, R.layout.item_head, mHeadViews, R.layout.item_item, mItemViews, R.layout.item_foot, mFootViews) {
            @Override
            public void onBindHeaderViewHolder(JesseHolder holder, int position) {
                TextView tv = holder.getView(R.id.tv_head);
                tv.setText(mHeadViews.get(position).getName());
            }

            @Override
            public void onBindItemViewHolder(JesseHolder holder, int position) {
                TextView tv = holder.getView(R.id.tv_item);
                tv.setText(mItemViews.get(position).getName());
            }

            @Override
            public void onBindFooterViewHolder(JesseHolder holder, int position) {
                TextView tv = holder.getView(R.id.tv_foot);
                tv.setText(mFootViews.get(position).getName());
            }
        });

        /** 头部item单击事件 */
        mAdapter.setOnHeadClickListener(new HeadFootAdapter.OnHeadClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "head click", Toast.LENGTH_SHORT).show();
            }
        });

        /** 中间item单击事件 */
        mAdapter.setOnItemClickListener(new HeadFootAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "item_item click", Toast.LENGTH_SHORT).show();
            }
        });

        /** 底部item单击事件 */
        mAdapter.setOnFootClickListener(new HeadFootAdapter.OnFootClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "foot click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** 头部数据 */
    private List<HeadView> mHeadViews;
    /** 内容数据 */
    private List<ItemView> mItemViews;
    /** 底部数据 */
    private List<FootView> mFootViews;
    /**初始化数据*/
    private void initDatas() {
        mHeadViews=new ArrayList<>();
        mItemViews=new ArrayList<>();
        mFootViews=new ArrayList<>();
        for (int i=0;i<10;i++){
            mHeadViews.add(new HeadView("headview"+i));
        }
        for (int i=0;i<28;i++){
            mItemViews.add(new ItemView("itemview"+i));
        }
        for (int i=0;i<15;i++){
            mFootViews.add(new FootView("footview"+i));
        }
    }

    private Context mContext=this;
    private RecyclerView mRecyclerview;
    private void assignViews() {
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    }

}
