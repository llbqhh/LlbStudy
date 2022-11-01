package org.llbqhh.study.algorithm.leetcode.链表;

public class L21_合并两个有序链表 {
//    // 迭代
//    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//        if (l1 == null) {
//            return l2;
//        } else if (l2 == null) {
//            return l1;
//        }
//
//        ListNode dummy = new ListNode(-1);
//        ListNode curr = dummy;
//        while (l1 != null && l2 != null) {
//            if (l1.val > l2.val) {
//                curr.next = l2;
//                curr = l2;
//                l2 = l2.next;
//            } else {
//                curr.next = l1;
//                curr = l1;
//                l1 = l1.next;
//            }
//        }
//
//        if (l1 == null) {
//            curr.next = l2;
//        } else {
//            curr.next = l1;
//        }
//
//        return dummy.next;
//    }

    // 递归
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
        l11.next = l12;
        l12.next = l13;
        l21.next = l22;
        l22.next = l23;
        ListNode re = new L21_合并两个有序链表().mergeTwoLists(l11, l21);
        while (re != null) {
            System.out.print(re.val + ",");
            re = re.next;
        }
        System.out.println();
    }
}
