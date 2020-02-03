package q250;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * Reverse a singly linked list.
 *
 * Example:
 *
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 *
 * Follow up:
 *
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
@RunWith(LeetCodeRunner.class)
public class Q206_ReverseLinkedList {

    @Answer
    public ListNode reverseList(ListNode head) {
        ListNode cur = head, prev = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, 4, 5))
            .expect(ListNode.createListNode(5, 4, 3, 2, 1));

    @TestData
    public DataExpectation border1 = DataExpectation.create(null).expect(null);

    @TestData
    public DataExpectation border2 = DataExpectation
            .create(ListNode.createListNode(1))
            .expect(ListNode.createListNode(1));

}
