package q150;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
 *
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The
 * binary tree has the following definition:
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
 * (图见 Q116_PIC.bmp)
 *
 * Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,
 * "right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":{
 * "$id":"6","left":null,"next":null,"right":null,"val":6},"next":null,"right":{"$id":"7","left":null,"next":null,
 * "right":null,"val":7},"val":3},"val":1}
 *
 * Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5
 * ","left":null,"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,"val":6},"right":null,
 * "val":5},"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,"right":{"$ref":"6"},"val":3}
 * ,"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}
 *
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to
 * point to its next right node, just like in Figure B.
 *
 *
 *
 * Note:
 *
 * You may only use constant extra space.
 * Recursive approach is fine, implicit stack space does not count as extra space for this problem.
 */
@RunWith(LeetCodeRunner.class)
public class Q116_PopulatingNextRightPointersInEachNode {

    private static class Node {

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

    /**
     * 这题判断起来太复杂了, 所以就没写测试判断.
     */
    @Answer
    public Node bfs(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node dummy = new Node(0, null, null, null);
        while (!queue.isEmpty()) {
            Node prev = dummy;
            for (int i = queue.size(); i > 0; i--) {
                Node node = queue.remove();
                if (node != null) {
                    prev.next = node;
                    prev = node;
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return root;
    }

    //  题目要求O(1) 的空间占用
    @Answer
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        // 从顶往下设置.
        // 这里因为是平衡二叉树, 所以当root.left == null 的时候这个树也到底了
        Node node = root;
        while (node.left != null) {
            node.left.next = node.right;
            // 水平遍历这一层的节点
            Node prev = node, cur = node.next;
            while (cur != null) {
                prev.right.next = cur.left;
                cur.left.next = cur.right;
                prev = cur;
                cur = cur.next;
            }
            // 进入下一层
            node = node.left;
        }
        return root;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(Node.makeTreeFromArray(1, 2, 4, null, null, 5, null, null, 3, 6, null, null, 7))
            .build();

}
