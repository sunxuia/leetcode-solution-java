package q1200;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1171. Remove Zero Sum Consecutive Nodes from Linked List
 * https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
 *
 * Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no
 * such sequences.
 *
 * After doing so, return the head of the final linked list.  You may return any such answer.
 *
 * (Note that in the examples below, all sequences are serializations of ListNode objects.)
 *
 * Example 1:
 *
 * Input: head = [1,2,-3,3,1]
 * Output: [3,1]
 * Note: The answer [1,2,1] would also be accepted.
 *
 * Example 2:
 *
 * Input: head = [1,2,3,-3,4]
 * Output: [1,2,4]
 *
 * Example 3:
 *
 * Input: head = [1,2,3,-3,-2]
 * Output: [1]
 *
 * Constraints:
 *
 * The given linked list will contain between 1 and 1000 nodes.
 * Each node in the linked list has -1000 <= node.val <= 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q1171_RemoveZeroSumConsecutiveNodesFromLinkedList {

    /**
     * 思路: 如果存在区间和为0 的情况, 则从头开始的总和一定会出现2 个重复的数字.
     */
    @Answer
    public ListNode removeZeroSumSublists(ListNode head) {
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int sum = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            sum += node.val;
            if (map.containsKey(sum)) {
                // 删除中间的节点
                ListNode start = map.get(sum), prev = start;
                int total = sum;
                while ((prev = prev.next) != node) {
                    total += prev.val;
                    map.remove(total);
                }
                start.next = node.next;
            } else {
                map.put(sum, node);
            }
        }
        return dummy.next;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(ListNode.createListNode(1, 2, -3, 3, 1))
            .expect(ListNode.createListNode(3, 1))
            .orExpect(ListNode.createListNode(1, 2, 1))
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, -3, 4))
            .expect(ListNode.createListNode(1, 2, 4));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(ListNode.createListNode(1, 2, 3, -3, -2))
            .expect(ListNode.createListNode(1));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(ListNode.createListNode(1, 3, 2, -3, -2, 5, 5, -5, 1))
            .expect(ListNode.createListNode(1, 5, 1));

}
