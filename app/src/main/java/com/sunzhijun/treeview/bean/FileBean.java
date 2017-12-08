package com.sunzhijun.treeview.bean;

import com.sunzhijun.treeview.utils.annotation.TreeNodeId;
import com.sunzhijun.treeview.utils.annotation.TreeNodeLabel;
import com.sunzhijun.treeview.utils.annotation.TreeNodePid;

/**
 * Created by sunzhijun on 2017/12/8.
 */

public class FileBean {
    //自己的id
    @TreeNodeId
    private int id;
    //父级id
    @TreeNodePid
    private int pId;
    //标题
    @TreeNodeLabel
    private String label;


    //描述
    private String desc;

    //...


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
