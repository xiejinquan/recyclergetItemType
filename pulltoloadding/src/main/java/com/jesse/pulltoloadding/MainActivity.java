package com.jesse.pulltoloadding;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jesse.recyclerview.JesseHolder;

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
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        mSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mSwiperefresh.isRefreshing()) {
                    //开启刷新
                    mSwiperefresh.setRefreshing(true);
                }
                Toast.makeText(mContext, "running", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        mSwiperefresh.setRefreshing(false);
                        mItemViews.add(0, new ItemView("新加的"));
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        //必须在setOnRefreshListener之后才起作用
        mSwiperefresh.setColorSchemeColors(R.color.red,R.color.orange,R.color.purple,R.color.green);
    }

    private int lastVisibleItem;
    private HeadFootAdapter<HeadView,ItemView,FootView> mAdapter;
    private void iniRecyclerview() {
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setAdapter(mAdapter = new HeadFootAdapter<HeadView, ItemView, FootView>(this, R.layout.item_item, mItemViews, R.layout.item_foot, mFootViews) {
            @Override
            public void onBindHeaderViewHolder(JesseHolder holder, int position) {
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

        /** mRecyclerview的滑动事件*/
        mRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    uploadding(new FootView("正在刷新......"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<5;i++){
                                mItemViews.add(new ItemView("新加的"+i));
                            }
                            uploadding(new FootView("上拉刷新......"));
                        }
                    },2000);
                }
            }

//            LayoutManger给我们提供了以下几个方法来让开发者方便的获取到屏幕上面的顶部item和顶部item相关的信息:
//            findFirstVisibleItemPosition()
//            findFirstCompletlyVisibleItemPosition()
//            findLastVisibleItemPosition()
//            findLastCompletlyVisibleItemPosition()
//            获取最后一个可见item的position
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    /** 内容数据 */
    private List<ItemView> mItemViews;
    /** 底部数据 */
    private List<FootView> mFootViews;
    /**初始化数据*/
    private void initDatas() {
        mItemViews=new ArrayList<>();
        mFootViews=new ArrayList<>();
        for (int i=0;i<28;i++){
            mItemViews.add(new ItemView("itemview"+i));
        }
        for (int i=0;i<1;i++){
            mFootViews.add(new FootView("上拉刷新......"));
        }
    }

    private Context mContext=this;
    private SwipeRefreshLayout mSwiperefresh;
    private RecyclerView mRecyclerview;
    private void assignViews() {
        mSwiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    }


    /** 底部item改变显示是否刷新完成 */
    public void uploadding(FootView footView){
        mFootViews.clear();
        mFootViews.add(footView);
        mAdapter.notifyDataSetChanged();
    }

}
