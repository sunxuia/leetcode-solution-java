package q250;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/palindrome-linked-list/
 *
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Example 1:
 *
 * Input: 1->2
 * Output: false
 *
 * Example 2:
 *
 * Input: 1->2->2->1
 * Output: true
 *
 * Follow up:
 * Could you do it in O(n) time and O(1) space?
 */
@RunWith(LeetCodeRunner.class)
public class Q234_PalindromeLinkedList {

    // 题目要求 O(n) 的时间复杂度和 O(1) 的空间复杂度
    // 通过在链表中间将后面的链表反向, 然后比较 head -> middle 和 tail -> middle 是否一致来实现.
    @Answer
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode a = head, b = head;
        while (b.next != null && b.next.next != null) {
            a = a.next;
            b = b.next.next;
        }
        b = a.next;
        a.next = null;
        while (b != null) {
            ListNode t = b.next;
            b.next = a;
            a = b;
            b = t;
        }
        while (a != null && head != null) {
            if (a.val != head.val) {
                return false;
            }
            a = a.next;
            head = head.next;
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(ListNode.createListNode(1, 2)).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(ListNode.createListNode(1, 2, 2, 1)).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(ListNode.createListNode(1, 2, 1, 2)).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(ListNode.createListNode(1, 1)).expect(true);

}
