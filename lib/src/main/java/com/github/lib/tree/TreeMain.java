package com.github.lib.tree;

import com.github.lib.bean.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) 2021 Tencent. All rights reserved.
 * 类功能描述:
 *
 * @author flynnjiao
 * @date 2021/3/17
 */
public class TreeMain {
    private static int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static void main(String[] args) {
        createBinTree();
        TreeNode root = nodeList.get(0);
        depth(root);
    }

    /*输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
    示例 1: 给定二叉树 [3,9,20,null,null,15,7]
    
        3
    
       / \
    
      9  20
    
        /  \
    
       15   7
    
    返回 true 。
    深度就是根节点到当前节点经过的边个数
    层数就是当前节点在第几层，跟节点为第一层，所以层数=深度+1*/
    private static int depth(TreeNode node) {
        System.out.println("value:" + (node == null ? -1 : node.val));
        if (node == null) return 0;
        int left = depth(node.left);
        System.out.println("left:" + left);
        int right = depth(node.right);
        System.out.println("right:" + right);
        return Math.max(left, right) + 1;
    }

    private static List<TreeNode> nodeList = null;

    public static void createBinTree() {
        nodeList = new LinkedList<TreeNode>();
        // 将一个数组的值依次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
            nodeList.add(new TreeNode(array[nodeIndex]));
        }
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).left = nodeList
                    .get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).right = nodeList
                    .get(parentIndex * 2 + 2);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = array.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).left = nodeList
                .get(lastParentIndex * 2 + 1);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).right = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
    }
}
