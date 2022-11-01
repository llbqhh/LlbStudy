package org.llbqhh.study.algorithm.leetcode.链表;

public class L141_环形链表 {
    // public boolean hasCycle(ListNode head) {
    //     if (head == null || head.next == null) {
    //         return false;
    //     }
    //     // 使用快慢指针，快指针每次走两步，慢指针每次走一步，如果他们相遇，则说明有环
    //     ListNode fast = head;
    //     ListNode slow = head;
    //     while (fast != null && fast.next != null && fast.next.next != null) {
    //         slow = slow.next;
    //         fast = fast.next.next;
    //         if (slow == fast) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 使用快慢指针，快指针每次走两步，慢指针每次走一步，如果他们相遇，则说明有环
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
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
        System.out.println(new L141_环形链表().hasCycle(l1));
    }
}
