package q1400;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1372. Longest ZigZag Path in a Binary Tree
 * https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/
 *
 * Given a binary tree root, a ZigZag path for a binary tree is defined as follow:
 *
 * Choose any node in the binary tree and a direction (right or left).
 * If the current direction is right then move to the right child of the current node otherwise move to the left child.
 * Change the direction from right to left or right to left.
 * Repeat the second and third step until you can't move in the tree.
 *
 * Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).
 *
 * Return the longest ZigZag path contained in that tree.
 *
 * Example 1:
 * <img src="./Q1372_PIC1.png">
 * Input: root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
 * Output: 3
 * Explanation: Longest ZigZag path in blue nodes (right -> left -> right).
 *
 * Example 2:
 * <img src="./Q1372_PIC2.png">
 * Input: root = [1,1,1,null,1,null,null,1,1,null,1]
 * Output: 4
 * Explanation: Longest ZigZag path in blue nodes (left -> right -> left -> right).
 *
 * Example 3:
 *
 * Input: root = [1]
 * Output: 0
 *
 * Constraints:
 *
 * Each tree has at most 50000 nodes..
 * Each node's value is between [1, 100].
 */
@RunWith(LeetCodeRunner.class)
public class Q1372_LongestZigzagPathInABinaryTree {

    @Answer
    public int longestZigZag(TreeNode root) {
        return Math.max(dfs(root, 0, true),
                dfs(root, 0, false)) - 1;
    }

    private int dfs(TreeNode node, int depth, boolean fromLeft) {
        if (node == null) {
            return depth;
        }
        int left = dfs(node.left, fromLeft ? 1 : depth + 1, true);
        int right = dfs(node.right, fromLeft ? depth + 1 : 1, false);
        return Math.max(left, right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, null, 1, 1, 1, null, null, 1, 1, null, 1, null, null, null, 1, null, 1))
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 1, 1, null, 1, null, null, 1, 1, null, 1))
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(1))
            .expect(0);

}
