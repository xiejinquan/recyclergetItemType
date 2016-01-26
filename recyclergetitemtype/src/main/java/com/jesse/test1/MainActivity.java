package com.jesse.test1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext=this;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        iniRecyclerview();
    }

    private List<HeadView> headViews;
    private List<ItemView> itemViews;
    private List<FootView> footViews;
    private void initDatas() {
        headViews=new ArrayList<>();
        itemViews=new ArrayList<>();
        footViews=new ArrayList<>();
        for (int i=0;i<10;i++){
            headViews.add(new HeadView("headview"+i));
        }
        for (int i=0;i<8;i++){
            itemViews.add(new ItemView("itemview"+i));
        }
        for (int i=0;i<5;i++){
            footViews.add(new FootView("footview"+i));
        }
    }

    private void iniRecyclerview() {
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(new HeadFootAdapter<HeadViewHolder, FootViewHolder, ItemViewHolder>() {
            @Override
            public HeadViewHolder onCreateHeaderViewHolder(ViewGroup parent, int position) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item2, parent, false);
                return new HeadViewHolder (view);
            }

            @Override
            public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int position) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
                return new ItemViewHolder (view);
            }

            @Override
            public FootViewHolder onCreateFooterViewHolder(ViewGroup parent, int position) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item2, parent, false);
                return new FootViewHolder (view);
            }

            @Override
            public void onBindHeaderViewHolder(HeadViewHolder holder,int position) {
                holder.tv.setTextSize(30);
                holder.tv.setText(headViews.get(position).getName());
            }

            @Override
            public void onBindItemViewHolder(ItemViewHolder holder, int position) {
                holder.tv.setText(itemViews.get(position).getName());
            }

            @Override
            public void onBindFooterViewHolder(FootViewHolder holder, int position) {
                holder.tv.setTextSize(30);
                holder.tv.setText(footViews.get(position).getName());
            }

            @Override
            public int getItemViewCount() {
                return itemViews==null?0:itemViews.size();
            }

            @Override
            public int getHeadViewCount() {
                return headViews==null?0:headViews.size();
            }

            @Override
            public int getFootViewCount() {
                return footViews==null?0:footViews.size();
            }
        });
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }


    class HeadViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public HeadViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public FootViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
