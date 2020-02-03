package q250;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-linked-list-elements/
 *
 * Remove all elements from a linked list of integers that have value val.
 *
 * Example:
 *
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
 */
@RunWith(LeetCodeRunner.class)
public class Q203_RemoveLinkedListElements {

    @Answer
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        for (ListNode prev = dummy; prev.next != null; ) {
            if (prev.next.val == val) {
                prev.next = prev.next.next;
            } else {
                prev = prev.next;
            }
        }
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 6, 3, 4, 5, 6), 6)
            .expect(ListNode.createListNode(1, 2, 3, 4, 5));

    @TestData
    public DataExpectation border = DataExpectation.createWith(ListNode.createListNode(1, 1), 1).expect(null);

}
