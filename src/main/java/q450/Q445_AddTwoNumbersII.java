package q450;

import org.junit.runner.RunWith;
import q050.Q002_AddTwoNumber;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/add-two-numbers-ii/
 *
 * You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes
 * first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Follow up:
 * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
 *
 * Example:
 *
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 *
 * 上一题 {@link Q002_AddTwoNumber}
 */
@RunWith(LeetCodeRunner.class)
public class Q445_AddTwoNumbersII {

    /**
     * 反序的做法. 不使用额外空间, 也不创建额外 ListNode, 直接利用被破坏了的参数的 ListNode 了.
     */
    @Answer
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);
        int carry = 0;
        ListNode curr = new ListNode(0);
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;

            // 使用l1 来保存结果.
            ListNode next = l1.next;
            l1.next = curr;
            curr = l1;
            l1 = next;
            l2 = l2.next;
        }
        if (l1 == null) {
            l1 = l2;
        }
        while (l1 != null) {
            int sum = l1.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;
            ListNode next = l1.next;
            l1.next = curr;
            curr = l1;
            l1 = next;
        }
        if (carry == 1) {
            curr.val = 1;
            return curr;
        } else {
            return curr.next;
        }
    }

    private ListNode reverse(ListNode node) {
        ListNode prev = null, next;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    // 不反序的做法. 因为不能破坏l1 和l2, 所以需要创建完整的结果 ListNode.
    @Answer
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        // 确保l1 比l2 长 (如果不是则交换l1 和 l2)
        int len1 = getLength(l1);
        int len2 = getLength(l2);
        if (len1 < len2) {
            int t = len1;
            len1 = len2;
            len2 = t;
            ListNode tl = l1;
            l1 = l2;
            l2 = tl;
        }

        // 从头到尾先加一遍, 不考虑进位的问题, 此时链表顺序是从低位到高位的.
        ListNode dummy = new ListNode(1);
        while (len1 > len2) {
            ListNode node = new ListNode(l1.val);
            node.next = dummy.next;
            dummy.next = node;

            l1 = l1.next;
            len1--;
        }
        while (l1 != null) {
            ListNode node = new ListNode(l1.val + l2.val);
            node.next = dummy.next;
            dummy.next = node;

            l1 = l1.next;
            l2 = l2.next;
        }

        // 将链表反序, 并做进位计算.
        int carry = 0;
        ListNode prev = null;
        for (ListNode node = dummy.next, next; node != null; node = next) {
            node.val += carry;
            carry = node.val / 10;
            node.val %= 10;

            next = node.next;
            node.next = prev;
            prev = node;
        }
        if (carry == 0) {
            return prev;
        } else {
            dummy.next = prev;
            return dummy;
        }
    }

    private int getLength(ListNode node) {
        int res = 0;
        while (node != null) {
            res++;
            node = node.next;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            ListNode.createListNode(7, 2, 4, 3),
            ListNode.createListNode(5, 6, 4)
    ).expect(ListNode.createListNode(7, 8, 0, 7));

}
