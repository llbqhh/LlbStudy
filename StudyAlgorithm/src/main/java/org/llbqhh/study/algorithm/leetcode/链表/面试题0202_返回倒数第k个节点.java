package org.llbqhh.study.algorithm.leetcode.链表;

public class 面试题0202_返回倒数第k个节点 {
    public int kthToLast(ListNode head, int k) {
        if (head == null) {
            throw  new RuntimeException("");
        }
        ListNode slow = head;
        ListNode fast = head;
        int length = 0;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
            length ++;
            if (fast == null && length < k) {
                throw  new RuntimeException("");
            }
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow.val;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        System.out.println(new 面试题0202_返回倒数第k个节点().kthToLast(l1, 2));
    }
}
