package q450;

import static java.util.Arrays.asList;

import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
 *
 * You are given a doubly linked list which in addition to the next and previous pointers, it could have a child
 * pointer, which may or may not point to a separate doubly linked list. These child lists may have one or more
 * children of their own, and so on, to produce a multilevel data structure, as shown in the example below.
 *
 * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the
 * first level of the list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * Output: [1,2,3,7,8,11,12,9,10,4,5,6]
 * Explanation:
 *
 * The multilevel linked list in the input is as follows:
 *
 *
 *
 * After flattening the multilevel linked list it becomes:
 *
 *
 * Example 2:
 *
 * Input: head = [1,2,null,3]
 * Output: [1,3,2]
 * Explanation:
 *
 * The input multilevel linked list is as follows:
 *
 * >    1---2---NULL
 * >    |
 * >    3---NULL
 *
 * Example 3:
 *
 * Input: head = []
 * Output: []
 *
 *
 *
 * How multilevel linked list is represented in test case:
 *
 * We use the multilevel linked list from Example 1 above:
 *
 * >   1---2---3---4---5---6--NULL
 * >           |
 * >           7---8---9---10--NULL
 * >               |
 * >               11--12--NULL
 *
 * The serialization of each level is as follows:
 *
 * [1,2,3,4,5,6,null]
 * [7,8,9,10,null]
 * [11,12,null]
 *
 * To serialize all levels together we will add nulls in each level to signify no node connects to the upper node of
 * the previous level. The serialization becomes:
 *
 * [1,2,3,4,5,6,null]
 * [null,null,7,8,9,10,null]
 * [null,11,12,null]
 *
 * Merging the serialization of each level and removing trailing nulls we obtain:
 *
 * [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 *
 *
 *
 * Constraints:
 *
 * Number of Nodes will not exceed 1000.
 * 1 <= Node.val <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q430_FlattenAMultilevelDoublyLinkedList {

    // Definition for a Node.
    private static class Node {

        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    @Answer
    public Node flatten(Node head) {
        if (head != null) {
            flatTail(head);
        }
        return head;
    }

    private Node flatTail(Node node) {
        while (true) {
            if (node.child != null) {
                Node childTail = flatTail(node.child);
                Node next = node.next;
                node.next = node.child;
                node.child.prev = node;
                node.child = null;
                childTail.next = next;
                if (next != null) {
                    next.prev = childTail;
                }
                node = childTail;
            }
            if (node.next == null) {
                return node;
            }
            node = node.next;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(createNode(asList(0, 1, 2, asList(3, 7, asList(8, 11, 12), 9, 10), 4, 5, 6)).child)
            .expect(createNode(asList(0, 1, 2, 3, 7, 8, 11, 12, 9, 10, 4, 5, 6)).child)
            .assertMethod(this::assertMethod)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(createNode(asList(0, asList(1, 3), 2)).child)
            .expect(createNode(asList(0, 1, 3, 2)).child)
            .assertMethod(this::assertMethod)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(null)
            .expect(null)
            .assertMethod(this::assertMethod)
            .build();

    /**
     * 创建测试的Node 数据
     *
     * @param nodeVal 可以是Integer, 可以是List, 如果是Integer 表示一个没有子节点的节点,
     * 如果是List 表示该节点有子节点, List 的第一个元素是该节点的值(一定是Integer), 剩余的是子节点的值.
     */
    private Node createNode(Object nodeVal) {
        if (nodeVal instanceof Integer) {
            Node node = new Node();
            node.val = (int) nodeVal;
            return node;
        } else if (nodeVal instanceof List) {
            List list = (List) nodeVal;
            Node node = new Node();
            node.val = (int) list.get(0);
            Node dummy = new Node();
            Node prev = dummy;
            for (int i = 1; i < list.size(); i++) {
                Node curr = createNode(list.get(i));
                prev.next = curr;
                curr.prev = prev;
                prev = curr;
            }
            if (dummy.next != null) {
                node.child = dummy.next;
                dummy.next.prev = null;
            }
            return node;
        } else {
            return null;
        }
    }

    private void assertMethod(Node expect, Node actual) {
        if (expect == actual) {
            return;
        }
        Assert.assertTrue(expect != null && actual != null);
        Assert.assertEquals(expect.val, actual.val);
        Assert.assertNull(actual.child);
        Assert.assertTrue(expect.prev == actual.prev
                || expect.prev != null && actual.prev != null && expect.prev.val == actual.prev.val);
        assertMethod(expect.next, actual.next);
    }

}
