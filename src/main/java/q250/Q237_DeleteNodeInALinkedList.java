package q250;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/delete-node-in-a-linked-list/
 *
 * Write a function to delete a node (except the tail) in a singly linked list, given only access to that node.
 *
 * Given linked list -- head = [4,5,1,9], which looks like following:
 *
 * (图见 Q237_PIC.png)
 *
 * Example 1:
 *
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling
 * your function.
 *
 * Example 2:
 *
 * Input: head = [4,5,1,9], node = 1
 * Output: [4,5,9]
 * Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling
 * your function.
 *
 *
 *
 * Note:
 *
 * The linked list will have at least two elements.
 * All of the nodes' values will be unique.
 * The given node will not be the tail and it will always be a valid node of the linked list.
 * Do not return anything from your function.
 */
@RunWith(LeetCodeRunner.class)
public class Q237_DeleteNodeInALinkedList {

    // 将下一个节点的值给自己, 然后删除下一个节点即可
    @Answer
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    @TestData
    public DataExpectation example1() {
        ListNode list = ListNode.createListNode(4, 5, 1, 9);
        return DataExpectation.builder()
                .addArgument(list.next)
                .assertMethod(() -> {
                    Assert.assertEquals(ListNode.createListNode(4, 1, 9), list);
                }).build();
    }

    @TestData
    public DataExpectation example2() {
        ListNode list = ListNode.createListNode(4, 5, 1, 9);
        return DataExpectation.builder()
                .addArgument(list.next.next)
                .assertMethod(() -> {
                    Assert.assertEquals(ListNode.createListNode(4, 5, 9), list);
                }).build();
    }

}
