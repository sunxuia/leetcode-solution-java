package q900;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 876. Middle of the Linked List
 * https://leetcode.com/problems/middle-of-the-linked-list/
 *
 * Given a non-empty, singly linked list with head node head, return a middle node of linked list.
 *
 * If there are two middle nodes, return the second middle node.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5]
 * Output: Node 3 from this list (Serialization: [3,4,5])
 * The returned node has value 3.  (The judge's serialization of this node is [3,4,5]).
 * Note that we returned a ListNode object ans, such that:
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, and ans.next.next.next = NULL.
 *
 * Example 2:
 *
 * Input: [1,2,3,4,5,6]
 * Output: Node 4 from this list (Serialization: [4,5,6])
 * Since the list has two middle nodes with values 3 and 4, we return the second one.
 *
 * Note:
 *
 * The number of nodes in the given list will be between 1 and 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q876_MiddleOfTheLinkedList {

    @Answer
    public ListNode middleNode(ListNode head) {
        ListNode ahead = head;
        while (ahead != null && ahead.next != null) {
            head = head.next;
            ahead = ahead.next.next;
        }
        return head;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, 4, 5))
            .expect(ListNode.createListNode(3, 4, 5));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, 4, 5, 6))
            .expect(ListNode.createListNode(4, 5, 6));

}
