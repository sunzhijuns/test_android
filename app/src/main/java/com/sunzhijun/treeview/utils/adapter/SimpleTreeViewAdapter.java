package com.sunzhijun.treeview.utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.sunzhijun.szjgames.R;
import com.sunzhijun.treeview.utils.Node;
import com.sunzhijun.treeview.utils.TreeHelper;

import java.util.List;

/**
 * Created by sunzhijun on 2017/12/8.
 */

public class SimpleTreeViewAdapter<T> extends TreeViewAdapter <T>{
    public SimpleTreeViewAdapter(ListView tree, Context context, List datas, int defaultExpandLevel) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getView(Node node, int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view =  mInflater.inflate(R.layout.tree_view_item,viewGroup,false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) view.findViewById(R.id.id_tree_item_icon);
            holder.mText = (TextView) view.findViewById(R.id.id_tree_item_text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (node.getIcon() == -1){
            holder.mIcon.setVisibility(View.INVISIBLE);
        }else{
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }
        holder.mText.setText(node.getName());
        return view;
    }

    public void addExtraNode(int i, String s) {
        Log.v("TAG", String.format("数据 i = %d",i));
        Node node = mVisibleNodes.get(i);
        int indexOf = mAllNodes.indexOf(node);
        int id = (int)System.currentTimeMillis();
        Log.v("TAG", String.format("数据 id = %d , indexOf = %d",id,indexOf));
        Node extraNode = new Node(id,node.getId(),s);
        node.setExpand(true);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);
        mAllNodes.add(indexOf+1,extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();

    }

    private class ViewHolder{
        ImageView mIcon;
        TextView mText;
    }
}
