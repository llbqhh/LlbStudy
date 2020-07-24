package org.llbqhh.study.algorithm.leetcode.链表;

// 归并排序
public class L148_排序链表 {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = getMidListNode(head);
        // 将链表从中间节点断开，成为两个节点
        ListNode head2 = mid.next;
        mid.next = null;

        return mergeTwoLists(sortList(head), sortList(head2));
    }

    private ListNode getMidListNode(ListNode head) {
        ListNode fast = head.next; // 这样可以保证slow指针在偶数节点时处于两个中间节点中靠左的一个
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
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
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(3);
        ListNode l3 = new ListNode(2);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(9);
        ListNode l6 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;

        ListNode re = new L148_排序链表().sortList(l1);
        while (re != null) {
            System.out.print(re.val + ",");
            re = re.next;
        }
        System.out.println();
    }
}
