package q050;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not
 * a multiple of k then left-out nodes in the end should remain as it is.
 *
 * Example:
 *
 * Given this linked list: 1->2->3->4->5
 *
 * For k = 2, you should return: 2->1->4->3->5
 *
 * For k = 3, you should return: 3->2->1->4->5
 *
 * Note:
 *
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 */
@RunWith(LeetCodeRunner.class)
public class Q025_ReverseNodesInKGroup {

    /**
     * 相比上一题由 2 个元素改为了多个元素. 这个也可以使用哨兵的方式来实现,
     * 能让空间复杂度变为 O(1)
     */
    @Answer
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode[] nodes = new ListNode[k];
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy, next = head;
        while (true) {
            int i = 0;
            while (i < k && next != null) {
                nodes[i++] = next;
                next = next.next;
            }
            if (i != k) {
                break;
            }
            for (i = 1; i < k; i++) {
                nodes[i].next = nodes[i - 1];
            }
            nodes[0].next = next;
            prev.next = nodes[k - 1];
            prev = nodes[0];
        }
        return dummy.next;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4, 5))
            .addArgument(2)
            .expect(ListNode.createListNode(2, 1, 4, 3, 5))
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4, 5))
            .addArgument(3)
            .expect(ListNode.createListNode(3, 2, 1, 4, 5))
            .build();

}
