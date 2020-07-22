package org.llbqhh.study.algorithm.leetcode.链表;

/**
 * 快慢指针
 */
public class 面试题0207_链表相交 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        //取两个数组的长度
        int aLength = getListLength(headA);
        int bLength = getListLength(headB);

        //长链表（快指针）先走|aLength - bLength|步
        ListNode slow,fast;
        if (aLength > bLength) {
            slow = headB;
            fast = headA;
        } else {
            slow = headA;
            fast = headB;
        }

        for (int i = 0; i < Math.abs(aLength - bLength); i++) {
            fast = fast.next;
        }

        // 同时走，如果相交，则返回交点
        while (slow != null) {
            if (slow == fast) {
                break;
            }
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public int getListLength(ListNode head) {
        int i = 0;
        while (head != null) {
            i++;
            head = head.next;
        }
        return i;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        ListNode l7 = new ListNode(7);
        // l1与l4在l6相交
        l1.next = l2;
        l2.next = l3;
        l3.next = l6;
        l4.next = l5;
        l5.next = l6;
        l6.next = l7;
        ListNode re = new 面试题0207_链表相交().getIntersectionNode(l1, l4);
        System.out.println(re.val);
    }
}
