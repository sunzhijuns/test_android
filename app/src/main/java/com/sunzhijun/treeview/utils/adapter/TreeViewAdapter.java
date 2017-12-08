package com.sunzhijun.treeview.utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sunzhijun.treeview.utils.Node;
import com.sunzhijun.treeview.utils.TreeHelper;

import java.util.List;

/**
 * Created by sunzhijun on 2017/12/8.
 */

public abstract class TreeViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<Node> mAllNodes;
    protected List<Node> mVisibleNodes;
    protected LayoutInflater mInflater;
    protected ListView mTree;


    public interface OnTreeNodeClickListener{
        void onClick(Node node, int i);
    }

    private OnTreeNodeClickListener mListener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener mListener) {
        this.mListener = mListener;
    }

    public TreeViewAdapter(ListView tree, Context context,
                           List<T> datas,
                           int defaultExpandLevel)
            throws IllegalAccessException
    {

        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        Log.v("DATAS","数据为：" + String.valueOf(datas) );
        mAllNodes = TreeHelper.getSortedNodes(datas,defaultExpandLevel);
        mVisibleNodes=TreeHelper.filterVisibleNodes(mAllNodes);


        mTree = tree;
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expandOrCollapse(i);

                if (mListener != null){
                    mListener.onClick(mVisibleNodes.get(i),i);
                }
            }
        });

    }

    /**
     * 点击收缩或展开
     * @param i
     */
    private void expandOrCollapse(int i) {
        Node n = mVisibleNodes.get(i);
        if (n!=null){
            if (n.isLeaf())
                return;
            n.setExpand(!n.isExpand());
            mVisibleNodes=TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return mVisibleNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node = mVisibleNodes.get(i);
        view = getView(node, i, view , viewGroup);
        view.setPadding(node.getLevel() * 30, 3,3,3);
        return view;
    }


    public abstract View getView(Node node, int i, View view, ViewGroup viewGroup);

}
