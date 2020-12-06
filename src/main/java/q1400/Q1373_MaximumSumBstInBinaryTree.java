package q1400;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1373. Maximum Sum BST in Binary Tree
 * https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/
 *
 * Given a binary tree root, the task is to return the maximum sum of all keys of any sub-tree which is also a Binary
 * Search Tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 * <img src="./Q1373_PIC1.png">
 * Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
 * Output: 20
 * Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.
 *
 * Example 2:
 * <img src="./Q1373_PIC2.png">
 * Input: root = [4,3,null,1,2]
 * Output: 2
 * Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key equal to 2.
 *
 * Example 3:
 *
 * Input: root = [-4,-2,-5]
 * Output: 0
 * Explanation: All values are negatives. Return an empty BST.
 *
 * Example 4:
 *
 * Input: root = [2,1,3]
 * Output: 6
 *
 * Example 5:
 *
 * Input: root = [5,4,8,3,null,6,3]
 * Output: 7
 *
 * Constraints:
 *
 * The given binary tree will have between 1 and 40000 nodes.
 * Each node's value is between [-4 * 10^4 , 4 * 10^4].
 */
@RunWith(LeetCodeRunner.class)
public class Q1373_MaximumSumBstInBinaryTree {

    @Answer
    public int maxSumBST(TreeNode root) {
        dfs(root);
        return max.sum;
    }

    DfsRes max = NOT_BST;

    private DfsRes dfs(TreeNode node) {
        if (node == null) {
            return null;
        }
        DfsRes left = dfs(node.left);
        DfsRes right = dfs(node.right);
        if (left != null && (left.invalid || left.max >= node.val)
                || right != null && (right.invalid || right.min <= node.val)) {
            return NOT_BST;
        }

        DfsRes res = new DfsRes(false, node.val);
        if (left != null) {
            res.min = left.min;
            res.sum += left.sum;
        }
        if (right != null) {
            res.max = right.max;
            res.sum += right.sum;
        }
        if (max.sum < res.sum) {
            max = res;
        }
        return res;
    }

    private static DfsRes NOT_BST = new DfsRes(true, 0);

    private static class DfsRes {

        boolean invalid;

        int max, min, sum;

        DfsRes(boolean invalid, int val) {
            this.invalid = invalid;
            this.max = val;
            this.min = val;
            this.sum = val;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 4, 3, 2, 4, 2, 5, null, null, null, null, null, null, 4, 6))
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation.create(TreeNode.createByLevel(4, 3, null, 1, 2)).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(TreeNode.createByLevel(-4, -2, -5)).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(TreeNode.createByLevel(2, 1, 3)).expect(6);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(TreeNode.createByLevel(5, 4, 8, 3, null, 6, 3))
            .expect(7);

}
