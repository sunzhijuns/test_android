package com.sunzhijun.treeview.utils;

import android.util.Log;

import com.example.sunzhijun.szjgames.R;
import com.sunzhijun.treeview.utils.annotation.TreeNodeId;
import com.sunzhijun.treeview.utils.annotation.TreeNodeLabel;
import com.sunzhijun.treeview.utils.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzhijun on 2017/12/8.
 */

public class TreeHelper {
    /**
     * 将用户的数据转化为树形数据
     * @param datas
     * @param <T>
     * @return
     */
    public static<T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;
        for (T t:datas){
            int id = -1;
            int pid = -1;
            String label = null;
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    id = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);
                    pid = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }

            }
            node = new Node(id,pid,label);
            nodes.add(node);
        }

        //设置节点父子关系
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i+1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (n.getId() == m.getpId()){
                    n.getChildren().add(m);
                    m.setParent(n);
                }else if (n.getpId() == m.getId()){
                    n.setParent(m);
                    m.getChildren().add(n);
                }
            }
        }
        //
        for (Node n : nodes){
            setNodeIcon(node);
        }
        return nodes;
    }

    /**
     * 为node设置icon
     * @param n
     */
    private static void setNodeIcon(Node n){
        if (n.getChildren().size()>0){
            if (n.isExpand()){
                n.setIcon(R.drawable.tree_ex);
            }else{
                n.setIcon(R.drawable.tree_ec);
            }

        }else{
            n.setIcon(-1);
        }
    }

    public static<T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        List<Node> nodes = convertDatas2Nodes(datas);
        List<Node> rootNodes = getRootNodes(nodes);

        System.out.println(rootNodes);

        for (Node node :rootNodes){
            addNode(result, node, defaultExpandLevel, 1);
        }

        return result;
    }

    /**
     * 把一个节点的所有孩子节点都放入result
     * @param result
     * @param node
     * @param defaultExpandLevel
     * @param currentLevel
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel){
            node.setExpand(true);
        }
        if (node.isLeaf())
            return;

        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result,node.getChildren().get(i),defaultExpandLevel,currentLevel+1);
        }
    }

    /**
     * 过滤出可见节点
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes){
        List<Node> result = new ArrayList<Node>();
        for (Node node : nodes){
            if (node.isRoot() || node.isParentExpand()){
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 从所有节点中过滤出根节点
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {

        List<Node> root = new ArrayList<Node>();
        for (Node node :nodes){
            if (node.isRoot()){
                root.add(node);
            }
        }
        return root;
    }
}
