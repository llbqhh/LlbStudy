package org.llbqhh.study.algorithm.leetcode.链表;

/**
 * 快慢指针
 */
public class 面试题0202_返回倒数第k个节点 {
    public int kthToLast(ListNode head, int k) {
        if (head == null) {
            throw  new RuntimeException("");
        }
        // 快慢指针
        // 快指针先走k步，然后两个指针同时往前走，当快指针指向null时，慢指针就是所求结果
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
