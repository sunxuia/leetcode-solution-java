package q100;


import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-linked-list-ii/
 *
 * Reverse a linked list from position m to n. Do it in one-pass.
 *
 * Note: 1 ≤ m ≤ n ≤ length of list.
 *
 * Example:
 *
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
 * Output: 1->4->3->2->5->NULL
 */
@RunWith(LeetCodeRunner.class)
public class Q092_ReverseLinkedListII {

    @Answer
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == n) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode start = dummy;
        n -= m;
        while (--m > 0) {
            start = start.next;
        }
        ListNode prev = start.next, next = prev.next;
        while (n-- > 0) {
            ListNode t = next.next;
            next.next = prev;
            prev = next;
            next = t;
        }
        start.next.next = next;
        start.next = prev;
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3, 4, 5), 2, 4)
            .expect(ListNode.createListNode(1, 4, 3, 2, 5));
}
