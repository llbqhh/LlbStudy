package org.llbqhh.study.algorithm.leetcode.链表;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class L2_两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return new ListNode(0);
        }

        // 进位标志
        int flag = 0;
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        while (l1 != null || l2 != null || flag == 1) {
            // 取出两个节点在当前位的值
            int i1 = l1 == null ? 0 : l1.val;
            int i2 = l2 == null ? 0 : l2.val;

            int curRes = i1 + i2 + flag;
            flag = curRes / 10;
            cur.next = new ListNode(curRes % 10);
            cur = cur.next;

            // 两个节点继续遍历
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l11 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);
        l11.next = l12;
        l12.next = l13;

        ListNode l21 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(4);
        l21.next = l22;
        l22.next = l23;

        ListNode res = new L2_两数相加().addTwoNumbers(l11, l21);
        while (res != null) {
            System.out.print(res.val);
            res = res.next;
        }
    }
}