package org.llbqhh.study.algorithm.leetcode.链表;

public class L328_奇偶链表 {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode evenHead = head.next;

        ListNode oddCur = head;
        ListNode evenCur = evenHead;

        while (evenCur != null && evenCur.next != null) {
            oddCur.next = evenCur.next;
            oddCur = oddCur.next;
            evenCur.next = oddCur.next;
            evenCur = evenCur.next;
        }
        oddCur.next = evenHead;
        return head;
    }

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
        ListNode re = new L328_奇偶链表().oddEvenList(l1);
        while (re != null) {
            System.out.println(re.val);
            re = re.next;
        }
        System.out.println();
    }
}
