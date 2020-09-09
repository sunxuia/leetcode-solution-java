package q1050;

import org.junit.runner.RunWith;
import q550.Q538_ConvertBSTToGreaterTree;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1038. Binary Search Tree to Greater Sum Tree
 * https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
 *
 * Given the root of a binary search tree with distinct values, modify it so that every node has a new value equal to
 * the sum of the values of the original tree that are greater than or equal to node.val.
 *
 * As a reminder, a binary search tree is a tree that satisfies these constraints:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 *
 * Input: [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
 * Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
 *
 * Constraints:
 *
 * The number of nodes in the tree is between 1 and 100.
 * Each node will have value between 0 and 100.
 * The given tree is a binary search tree.
 *
 * Note: This question is the same as 538: https://leetcode.com/problems/convert-bst-to-greater-tree/
 *
 * 相同题目 {@link Q538_ConvertBSTToGreaterTree}
 */
@RunWith(LeetCodeRunner.class)
public class Q1038_BinarySearchTreeToGreaterSumTree {

    @Answer
    public TreeNode bstToGst(TreeNode root) {
        sum = 0;
        dfs(root);
        return root;
    }

    private int sum;

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.right);
        sum += node.val;
        node.val = sum;
        dfs(node.left);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(4, 1, 6, 0, 2, 5, 7, null, null, null, 3, null, null, null, 8))
            .expect(TreeNode.createByLevel(30, 36, 21, 36, 35, 26, 15, null, null, null, 33, null, null, null, 8));

}
