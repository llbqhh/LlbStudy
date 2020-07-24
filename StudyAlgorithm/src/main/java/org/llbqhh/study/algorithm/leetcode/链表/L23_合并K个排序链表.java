package org.llbqhh.study.algorithm.leetcode.链表;

public class L23_合并K个排序链表 {
//    // 迭代
//    public ListNode mergeKLists(ListNode[] lists) {
//        if (lists == null || lists.length == 0) {
//            return null;
//        }
//
//        ListNode l1 = lists[0];
//
//        for (int i = 1; i < lists.length; i++) {
//            l1 = mergeTwoLists(l1, lists[i]);
//        }
//
//        return l1;
//    }

    // 分治
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return mergeKLists(lists, 0, lists.length-1);
    }

    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        int mid = start + (end - start) / 2;
        return mergeTwoLists(mergeKLists(lists, start, mid), mergeKLists(lists, mid + 1, end)) ;
    }

    // 合并两个链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public static void main(String[] args) {
        ListNode l11 = new ListNode(1);
        ListNode l12 = new ListNode(3);
        ListNode l13 = new ListNode(6);
        ListNode l21 = new ListNode(1);
        ListNode l22 = new ListNode(2);
        ListNode l23 = new ListNode(5);
        ListNode l31 = new ListNode(2);
        ListNode l32 = new ListNode(4);
        ListNode l33 = new ListNode(7);
        l11.next = l12;
        l12.next = l13;
        l21.next = l22;
        l22.next = l23;
        l31.next = l32;
        l32.next = l33;
        ListNode[] lists = {l11, l21, l31};
        ListNode re = new L23_合并K个排序链表().mergeKLists(lists);
        while (re != null) {
            System.out.print(re.val + ",");
            re = re.next;
        }
        System.out.println();
    }
}
