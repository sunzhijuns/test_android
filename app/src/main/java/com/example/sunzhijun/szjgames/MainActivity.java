package com.example.sunzhijun.szjgames;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sunzhijun.treeview.bean.FileBean;
import com.sunzhijun.treeview.utils.Node;
import com.sunzhijun.treeview.utils.adapter.SimpleTreeViewAdapter;
import com.sunzhijun.treeview.utils.adapter.TreeViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mTree;
    private SimpleTreeViewAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTree =(ListView) findViewById(R.id.id_tree_list);
        initDatas();
        try{
            mAdapter = new SimpleTreeViewAdapter<FileBean>(mTree,MainActivity.this,mDatas,1);
            mTree.setAdapter(mAdapter);
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }

        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnTreeNodeClickListener(new TreeViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int i) {
                if (node.isLeaf()){
                    Toast.makeText(MainActivity.this,node.getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                DialogFragment
                final EditText et = new EditText(MainActivity.this);
                final int position = pos;
                new AlertDialog.Builder(MainActivity.this).setTitle("Add Node")
                        .setView(et).setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAdapter.addExtraNode(position,et.getText().toString());
                    }
                }).setNegativeButton("Cancel", null).show();
                return true;
            }
        });
    }

    private void initDatas() {
        mDatas  = new ArrayList<FileBean>();
        FileBean bean = new FileBean(100,0,"根目录1");
        mDatas.add(bean);
        for (int i = 1; i < 5; i++) {
            bean = new FileBean(100 + i,100,"子目录1-" + i);
            mDatas.add(bean);
        }
        bean = new FileBean(200,0,"根目录2");
        mDatas.add(bean);
        for (int i = 1; i < 5; i++) {
            bean = new FileBean(200+i,200,"子目录2-" + i);
            mDatas.add(bean);
            for (int j = 1; j < 5; j++) {
                bean = new FileBean(2000 + i + 100 +j,200 + i,"子目录2-" + i + "-" + j);
                mDatas.add(bean);
            }
        }

        bean = new FileBean(300,0,"根目录3");
        mDatas.add(bean);
        bean = new FileBean(3000,300,"子目录3-1");
        mDatas.add(bean);
        bean = new FileBean(3001,3000,"子目录3-1-1");
        mDatas.add(bean);
        bean = new FileBean(3002,3000,"子目录3-1-2");
        mDatas.add(bean);
        bean = new FileBean(3003,3000,"子目录3-1-3");
        mDatas.add(bean);
        bean = new FileBean(302,300,"子目录3-2");
        mDatas.add(bean);
        bean = new FileBean(303,300,"子目录3-3");
        mDatas.add(bean);

    }
}
