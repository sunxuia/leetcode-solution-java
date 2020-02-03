package q050;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 *
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *
 * Given linked list: 1->2->3->4->5, and n = 2.
 *
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 *
 * Given n will always be valid.
 *
 * Follow up:
 *
 * Could you do this in one pass?
 */
@RunWith(LeetCodeRunner.class)
public class Q019_RemoveNthNodeFromEndOfList {

    @Answer
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode[] nodes = new ListNode[n + 1];
        int i = 0;
        ListNode node = head;
        while (node != null) {
            nodes[i++] = node;
            i = i % nodes.length;
            node = node.next;
        }
        // 题目中保证 n 是有效的, 所以这里就不需要做越界检查了
        ListNode nodeToRemove = nodes[(i - n + nodes.length) % nodes.length];
        if (nodeToRemove == head) {
            return head.next;
        }
        nodes[(i - n - 1 + nodes.length) % nodes.length].next = nodeToRemove.next;
        return head;
    }

    /**
     * LeetCode 中的做法更加巧妙, 通过一个提前前进 n 位的哨兵来检测末尾
     */
    @Answer
    public ListNode leetCode(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode tail = head;
        while (n-- > 0) {
            tail = tail.next;
        }
        ListNode prev = dummy;
        while (tail != null) {
            tail = tail.next;
            prev = prev.next;
        }
        prev.next = prev.next.next;
        return dummy.next;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, 3, 4, 5))
            .addArgument(2)
            .expect(ListNode.createListNode(1, 2, 3, 5))
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1))
            .addArgument(1)
            .expect(null)
            .build();
}
