package q050;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 *
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 */
@RunWith(LeetCodeRunner.class)
public class Q024_SwapNodesInPairs {

    @Answer
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy, cur = head, next = head.next;
        while (next != null) {
            ListNode moreNext = next.next;
            prev.next = next;
            next.next = cur;
            cur.next = moreNext;

            prev = cur;
            cur = moreNext;
            next = moreNext == null ? null : moreNext.next;
        }
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4))
            .expect(ListNode.createListNode(2, 1, 4, 3))
            .build();

}
