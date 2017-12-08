package com.sunzhijun.treeview.utils;

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
        return null;
    }
}
