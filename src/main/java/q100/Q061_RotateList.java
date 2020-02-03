package q100;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rotate-list/
 *
 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
 *
 * Example 1:
 *
 * Input: 1->2->3->4->5->NULL, k = 2
 * Output: 4->5->1->2->3->NULL
 * Explanation:
 * rotate 1 steps to the right: 5->1->2->3->4->NULL
 * rotate 2 steps to the right: 4->5->1->2->3->NULL
 * Example 2:
 *
 * Input: 0->1->2->NULL, k = 4
 * Output: 2->0->1->NULL
 * Explanation:
 * rotate 1 steps to the right: 2->0->1->NULL
 * rotate 2 steps to the right: 1->2->0->NULL
 * rotate 3 steps to the right: 0->1->2->NULL
 * rotate 4 steps to the right: 2->0->1->NULL
 */
@RunWith(LeetCodeRunner.class)
public class Q061_RotateList {

    @Answer
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode tail = head, node = head;

        int length = 1;
        while (tail.next != null) {
            length++;
            tail = tail.next;
        }
        tail.next = head;

        k = k % length;
        while (k-- > 0) {
            node = node.next;
        }
        while (node != tail) {
            head = head.next;
            node = node.next;
        }
        ListNode res = head.next;
        head.next = null;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3, 4, 5), 2)
            .expect(ListNode.createListNode(4, 5, 1, 2, 3));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(0, 1, 2), 4)
            .expect(ListNode.createListNode(2, 0, 1));

    @TestData
    public DataExpectation border1 = DataExpectation
            .createWith(ListNode.createListNode(0), 4)
            .expect(ListNode.createListNode(0));

    @TestData
    public DataExpectation border2 = DataExpectation
            .createWith(ListNode.createListNode(), 4)
            .expect(ListNode.createListNode());

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3), 2000000000)
            .expect(ListNode.createListNode(2, 3, 1));
}
