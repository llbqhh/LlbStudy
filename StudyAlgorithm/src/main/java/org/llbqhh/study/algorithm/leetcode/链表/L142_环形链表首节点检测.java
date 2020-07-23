package org.llbqhh.study.algorithm.leetcode.链表;

public class L142_环形链表首节点检测 {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        // 首先判断是否有环，如果有环，取得快慢指针相遇时走的步数n
        ListNode slow = head;
        ListNode fast = head;
        int step = 0;
        boolean hasCycle = false;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            step++;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        if (!hasCycle) {
            return null;
        }

        // 从新设置快慢指针，让快指针先走n步，然后快慢指针同时走，相遇时的节点就是所求的入环首节点（实际上只需要将慢指针重新指向头节点，快指针不动即可）
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l2;
        System.out.println(new L142_环形链表首节点检测().detectCycle(l1));
    }
}
