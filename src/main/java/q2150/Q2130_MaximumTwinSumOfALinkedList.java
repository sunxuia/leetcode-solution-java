package q2150;

import static util.provided.ListNode.createListNode;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2130. Maximum Twin Sum of a Linked List</h3>
 * <a href="https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/">
 * https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/
 * </a><br/>
 *
 * <p>In a linked list of size <code>n</code>, where <code>n</code> is <strong>even</strong>, the
 * <code>i<sup>th</sup></code> node (<strong>0-indexed</strong>) of the linked list is known as the
 * <strong>twin</strong> of the <code>(n-1-i)<sup>th</sup></code> node, if <code>0 &lt;= i &lt;= (n / 2) -
 * 1</code>.</p>
 *
 * <ul>
 * 	<li>For example, if <code>n = 4</code>, then node <code>0</code> is the twin of node <code>3</code>, and node
 * 	<code>1</code> is the twin of node <code>2</code>. These are the only nodes with twins for <code>n = 4</code>.</li>
 * </ul>
 *
 * <p>The <strong>twin sum </strong>is defined as the sum of a node and its twin.</p>
 *
 * <p>Given the <code>head</code> of a linked list with even length, return <em>the <strong>maximum twin sum</strong>
 * of the linked list</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/03/eg1drawio.png" style="width: 250px; height: 70px;" />
 * <pre>
 * <strong>Input:</strong> head = [5,4,2,1]
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong>
 * Nodes 0 and 1 are the twins of nodes 3 and 2, respectively. All have twin sum = 6.
 * There are no other nodes with twins in the linked list.
 * Thus, the maximum twin sum of the linked list is 6.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/03/eg2drawio.png" style="width: 250px; height: 70px;" />
 * <pre>
 * <strong>Input:</strong> head = [4,2,2,3]
 * <strong>Output:</strong> 7
 * <strong>Explanation:</strong>
 * The nodes with twins present in this linked list are:
 * - Node 0 is the twin of node 3 having a twin sum of 4 + 3 = 7.
 * - Node 1 is the twin of node 2 having a twin sum of 2 + 2 = 4.
 * Thus, the maximum twin sum of the linked list is max(7, 4) = 7.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/03/eg3drawio.png" style="width: 200px; height: 88px;" />
 * <pre>
 * <strong>Input:</strong> head = [1,100000]
 * <strong>Output:</strong> 100001
 * <strong>Explanation:</strong>
 * There is only one node with a twin in the linked list having twin sum of 1 + 100000 = 100001.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li>The number of nodes in the list is an <strong>even</strong> integer in the range <code>[2,
 * 	10<sup>5</sup>]</code>.</li>
 * 	<li><code>1 &lt;= Node.val &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2130_MaximumTwinSumOfALinkedList {

    // 比较慢的解法
    @Answer
    public int pairSum(ListNode head) {
        Deque<ListNode> deque = new ArrayDeque<>();
        while (head != null) {
            deque.offer(head);
            head = head.next;
        }
        int res = 0;
        while (!deque.isEmpty()) {
            int first = deque.removeFirst().val;
            int last = deque.removeLast().val;
            res = Math.max(res, first + last);
        }
        return res;
    }

    // 根据leetcode 提示, 对链路的破坏性解法.
    @Answer
    public int pairSum2(ListNode head) {
        // 找出链路长度
        int n = 0;
        for (ListNode node = head;
                node != null;
                node = node.next) {
            n++;
        }

        // 找出下半段链路的前一个节点(上半段链路的末尾)
        ListNode node = head;
        for (int i = 0; i < n / 2 - 1; i++) {
            node = node.next;
        }
        // 将下半段链路反向
        ListNode half = node.next, prev = null;
        node.next = null;
        while (half.next != null) {
            ListNode next = half.next;
            half.next = prev;
            prev = half;
            half = next;
        }
        half.next = prev;

        // 遍历找出最大结果
        int res = 0;
        while (head != null) {
            int sum = head.val + half.val;
            res = Math.max(res, sum);
            head = head.next;
            half = half.next;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(createListNode(5, 4, 2, 1)).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(createListNode(4, 2, 2, 3)).expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation.create(createListNode(1, 100000)).expect(100001);

}
