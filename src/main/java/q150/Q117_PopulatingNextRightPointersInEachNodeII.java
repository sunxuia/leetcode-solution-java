package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
 *
 * Given a binary tree
 *
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 *
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer
 * should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 *
 *
 *
 * Example:
 *
 * (图见 Q117_PIC.png)
 *
 * Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,
 * "right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5",
 * "left":null,"next":null,"right":{"$id":"6","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}
 *
 * Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5
 * ","left":null,"next":null,"right":null,"val":7},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"6",
 * "left":null,"next":null,"right":{"$ref":"5"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"6
 * "},"val":1}
 *
 * Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to
 * its next right node, just like in Figure B.
 *
 *
 *
 * Note:
 *
 * You may only use constant extra space.
 * Recursive approach is fine, implicit stack space does not count as extra space for this problem.
 */
@RunWith(LeetCodeRunner.class)
public class Q117_PopulatingNextRightPointersInEachNodeII {


    public static class Node {

        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }

        public static Node makeTreeFromArray(Integer... nums) {
            return makeTreeFromPreOrderTraversal(nums, new int[]{0});
        }

        private static Node makeTreeFromPreOrderTraversal(Integer[] nums, int[] index) {
            if (index[0] >= nums.length || nums[index[0]] == null) {
                index[0]++;
                return null;
            }
            Node node = new Node(nums[index[0]++], null, null, null);
            node.left = makeTreeFromPreOrderTraversal(nums, index);
            node.right = makeTreeFromPreOrderTraversal(nums, index);
            return node;
        }
    }

    @Answer
    public Node connect(Node root) {
        Node node = root;
        Node dummyHead = new Node(0, null, null, null);
        while (node != null) {
            dummyHead.next = null;
            Node cur = node, childPrev = dummyHead;
            while (cur != null) {
                if (cur.left != null) {
                    childPrev.next = cur.left;
                    childPrev = cur.left;
                }
                if (cur.right != null) {
                    childPrev.next = cur.right;
                    childPrev = cur.right;
                }
                cur = cur.next;
            }
            node = dummyHead.next;
        }
        return root;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(Node.makeTreeFromArray(1, 2, 4, null, null, 5, null, null, 3, null, 7))
            .build();

}
