package q1050;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1019. Next Greater Node In Linked List
 * https://leetcode.com/problems/next-greater-node-in-linked-list/
 *
 * We are given a linked list with head as the first node.  Let's number the nodes in the list: node_1, node_2, node_3,
 * ... etc.
 *
 * Each node may have a next larger value: for node_i, next_larger(node_i) is the node_j.val such that j > i, node_j.val
 * > node_i.val, and j is the smallest possible choice.  If such a j does not exist, the next larger value is 0.
 *
 * Return an array of integers answer, where answer[i] = next_larger(node_{i+1}).
 *
 * Note that in the example inputs (not outputs) below, arrays such as [2,1,5] represent the serialization of a linked
 * list with a head node value of 2, second node value of 1, and third node value of 5.
 *
 * Example 1:
 *
 * Input: [2,1,5]
 * Output: [5,5,0]
 *
 * Example 2:
 *
 * Input: [2,7,4,3,5]
 * Output: [7,0,5,5,0]
 *
 * Example 3:
 *
 * Input: [1,7,5,1,9,2,5,1]
 * Output: [7,9,9,9,0,5,0,0]
 *
 * Note:
 *
 * 1 <= node.val <= 10^9 for each node in the linked list.
 * The given list has length in the range [0, 10000].
 */
@RunWith(LeetCodeRunner.class)
public class Q1019_NextGreaterNodeInLinkedList {

    /**
     * 递减栈解法
     */
    @Answer
    public int[] nextLargerNodes(ListNode head) {
        List<Integer> list = new ArrayList<>();
        Deque<int[]> stack = new ArrayDeque<>();
        for (; head != null; head = head.next) {
            int val = head.val;
            while (!stack.isEmpty() && stack.peek()[1] < val) {
                int[] pair = stack.pop();
                list.set(pair[0], val);
            }
            stack.push(new int[]{list.size(), val});
            list.add(0);
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(ListNode.createListNode(2, 1, 5))
            .expect(new int[]{5, 5, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(ListNode.createListNode(2, 7, 4, 3, 5))
            .expect(new int[]{7, 0, 5, 5, 0});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(ListNode.createListNode(1, 7, 5, 1, 9, 2, 5, 1))
            .expect(new int[]{7, 9, 9, 9, 0, 5, 0, 0});

}
