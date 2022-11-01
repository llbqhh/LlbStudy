package org.llbqhh.study.algorithm.leetcode.链表;

public class 剑指Offer24_反转链表 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

        ListNode reverseNode = new 剑指Offer24_反转链表().reverseList(l1);
        while (reverseNode != null) {
            System.out.print(reverseNode.val);
            reverseNode = reverseNode.next;
        }
        System.out.println();
    }

    public ListNode reverseList(ListNode head) {
        return reverseList02(head);
    }


    // 迭代
    public ListNode reverseList01(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 递归
    public ListNode reverseList02(ListNode head) {
        ListNode reverseHead = reverseList02Internal(head);
        if (reverseHead != null) {
            head.next = null;
        }

        return reverseHead;
    }

    public ListNode reverseList02Internal(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        } else {
            ListNode next = head.next;
            ListNode reverseHead = reverseList02Internal(next);
            next.next = head;
            return reverseHead;
        }
    }
}