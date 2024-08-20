package q2100;

import static util.provided.ListNode.createListNode;

import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2095. Delete the Middle Node of a Linked List</h3>
 * <a href="https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/">
 * https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/
 * </a><br/>
 *
 * <p>You are given the <code>head</code> of a linked list. <strong>Delete</strong> the <strong>middle node</strong>,
 * and return <em>the</em> <code>head</code> <em>of the modified linked list</em>.</p>
 *
 * <p>The <strong>middle node</strong> of a linked list of size <code>n</code> is the <code>&lfloor;n /
 * 2&rfloor;<sup>th</sup></code> node from the <b>start</b> using <strong>0-based indexing</strong>, where
 * <code>&lfloor;x&rfloor;</code> denotes the largest integer less than or equal to <code>x</code>.</p>
 *
 * <ul>
 * 	<li>For <code>n</code> = <code>1</code>, <code>2</code>, <code>3</code>, <code>4</code>, and <code>5</code>, the
 * 	middle nodes are <code>0</code>, <code>1</code>, <code>1</code>, <code>2</code>, and <code>2</code>, respectively
 * 	.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/16/eg1drawio.png" style="width: 500px; height: 77px;" />
 * <pre>
 * <strong>Input:</strong> head = [1,3,4,7,1,2,6]
 * <strong>Output:</strong> [1,3,4,1,2,6]
 * <strong>Explanation:</strong>
 * The above figure represents the given linked list. The indices of the nodes are written below.
 * Since n = 7, node 3 with value 7 is the middle node, which is marked in red.
 * We return the new list after removing this node.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/16/eg2drawio.png" style="width: 250px; height: 43px;" />
 * <pre>
 * <strong>Input:</strong> head = [1,2,3,4]
 * <strong>Output:</strong> [1,2,4]
 * <strong>Explanation:</strong>
 * The above figure represents the given linked list.
 * For n = 4, node 2 with value 3 is the middle node, which is marked in red.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/16/eg3drawio.png" style="width: 150px; height: 58px;" />
 * <pre>
 * <strong>Input:</strong> head = [2,1]
 * <strong>Output:</strong> [2]
 * <strong>Explanation:</strong>
 * The above figure represents the given linked list.
 * For n = 2, node 1 with value 1 is the middle node, which is marked in red.
 * Node 0 with value 2 is the only node remaining after removing node 1.</pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li>The number of nodes in the list is in the range <code>[1, 10<sup>5</sup>]</code>.</li>
 * 	<li><code>1 &lt;= Node.val &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2095_DeleteTheMiddleNodeOfALinkedList {

    @Answer
    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) {
            return null;
        }
        ListNode prev = head;
        ListNode node = head.next.next;
        while (node != null && node.next != null) {
            prev = prev.next;
            node = node.next.next;
        }
        prev.next = prev.next.next;
        return head;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(createListNode(1, 3, 4, 7, 1, 2, 6))
            .expect(createListNode(1, 3, 4, 1, 2, 6));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(createListNode(1, 2, 3, 4))
            .expect(createListNode(1, 2, 4));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(createListNode(2, 1))
            .expect(createListNode(2));

    @TestData
    public DataExpectation border = DataExpectation
            .create(createListNode(1))
            .expect(null);

}
