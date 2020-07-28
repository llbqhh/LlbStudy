package org.llbqhh.study.algorithm.leetcode.树;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L104_二叉树的最大深度 {
//    // 递归
//    public int maxDepth(TreeNode root) {
//        return maxDepth(root, 1);
//    }
//
//    public int maxDepth(TreeNode root, int depth) {
//        if (root == null) {
//            return 0;
//        } else if (root.left == null && root.right == null) {
//            return depth;
//        }
//
//        depth++;
//        return Math.max(maxDepth(root.left, depth), maxDepth(root.right, depth));
//    }

    // 广度优先搜索
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<TreeNode> q = new LinkedList<>();
        q.push(root);
        int re = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size > 0) {
                TreeNode tmp = q.poll();
                if (tmp.left != null) {
                    q.add(tmp.left);
                }
                if (tmp.right != null) {
                    q.add(tmp.right);
                }
                size--;
            }
            re++;
        }
        return re;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(3);
        TreeNode r11 = new TreeNode(4);
        TreeNode r21 = new TreeNode(5);
        root.left = l1;
        root.right = r1;
        root.left.left = r11;
        root.right.right = r21;
        System.out.println(new L104_二叉树的最大深度().maxDepth(root));
    }
}
