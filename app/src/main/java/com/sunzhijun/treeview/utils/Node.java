package com.sunzhijun.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzhijun on 2017/12/8.
 */

public class Node {
    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    private int id;
    /**
     * 根节点的pid=0
     */
    private int pId = 0;
    private String name;
    /**
     * 树的深度
     */
    private int level;
    /**
     * 是否是展开
     */
    private boolean isExpand = false;
    private int icon;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    private Node parent;
    private List<Node> children = new ArrayList<Node>();


    /**
     * 是否是根节点
     * @return
     */
    public boolean isRoot(){
        return parent == null;
    }

    /**
     * 判断当前树节点的展开状态
     * @return
     */
    public boolean isParentExpand(){
        if (parent == null)
            return false;
        return parent.isExpand;
    }

    public boolean isLeaf(){
        return children.size()==0;
    }

    public int getLevel() {
        return parent == null ? 0 : parent.getLevel()+1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!isExpand){
            for (Node node : children){
                node.setExpand(false);
            }
        }
    }


}
