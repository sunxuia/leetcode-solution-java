package q1750;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1721. Swapping Nodes in a Linked List
 * https://leetcode.com/problems/swapping-nodes-in-a-linked-list/
 *
 * You are given the head of a linked list, and an integer k.
 *
 * Return the head of the linked list after swapping the values of the kth node from the beginning and the kth node from
 * the end (the list is 1-indexed).
 *
 * Example 1:
 * <img src="./Q1721_PIC.jpg">
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [1,4,3,2,5]
 *
 * Example 2:
 *
 * Input: head = [7,9,6,6,7,8,3,0,9,5], k = 5
 * Output: [7,9,6,6,8,7,3,0,9,5]
 *
 * Example 3:
 *
 * Input: head = [1], k = 1
 * Output: [1]
 *
 * Example 4:
 *
 * Input: head = [1,2], k = 1
 * Output: [2,1]
 *
 * Example 5:
 *
 * Input: head = [1,2,3], k = 2
 * Output: [1,2,3]
 *
 * Constraints:
 *
 * The number of nodes in the list is n.
 * 1 <= k <= n <= 10^5
 * 0 <= Node.val <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1721_SwappingNodesInALinkedList {

    @Answer
    public ListNode swapNodes(ListNode head, int k) {
        ListNode firstK = head;
        for (int i = 1; i < k; i++) {
            firstK = firstK.next;
        }
        ListNode lastK = head, tail = firstK;
        while (tail.next != null) {
            lastK = lastK.next;
            tail = tail.next;
        }
        int t = firstK.val;
        firstK.val = lastK.val;
        lastK.val = t;
        return head;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3, 4, 5), 2)
            .expect(ListNode.createListNode(1, 4, 3, 2, 5));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(7, 9, 6, 6, 7, 8, 3, 0, 9, 5), 5)
            .expect(ListNode.createListNode(7, 9, 6, 6, 8, 7, 3, 0, 9, 5));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(ListNode.createListNode(1), 1)
            .expect(ListNode.createListNode(1));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(ListNode.createListNode(1, 2), 1)
            .expect(ListNode.createListNode(2, 1));

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(ListNode.createListNode(1, 2, 3), 2)
            .expect(ListNode.createListNode(1, 2, 3));

}
