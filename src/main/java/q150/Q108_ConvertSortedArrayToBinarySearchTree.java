package q150;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.DataExpectationBuilder;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 *
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 *
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
 * of every node never differ by more than 1.
 *
 * Example:
 *
 * Given the sorted array: [-10,-3,0,5,9],
 *
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 *
 * >       0
 * >      / \
 * >    -3   9
 * >    /   /
 * >  -10  5
 */
@RunWith(LeetCodeRunner.class)
public class Q108_ConvertSortedArrayToBinarySearchTree {

    @Answer
    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length);
    }

    private TreeNode dfs(int[] nums, int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive) {
            return null;
        }
        int middle = (startInclusive + endExclusive) / 2;
        TreeNode node = new TreeNode(nums[middle]);
        node.left = dfs(nums, startInclusive, middle);
        node.right = dfs(nums, middle + 1, endExclusive);
        return node;
    }

    @TestData
    public DataExpectation example() {
        DataExpectationBuilder builder = DataExpectation.builder();
        int[] arg = new int[]{-10, -3, 0, 5, 9};
        builder.addArgument(arg);
        builder.expect(null);
        builder.<TreeNode>assertMethod((a, res) -> {
            assertBst(res, Long.MIN_VALUE, Long.MAX_VALUE);
            assertBalancedTree(res);
            AssertUtils.assertEquals(arg, res.inOrderTraversal());
        });
        return builder.build();
    }

    private void assertBst(TreeNode node, long lower, long upper) {
        if (node != null) {
            Assert.assertFalse("expect bst but is not", node.left != null && node.left.val <= lower);
            Assert.assertFalse("expect bst but is not", node.right != null && node.right.val >= upper);
            assertBst(node.left, lower, node.val);
            assertBst(node.right, node.val, upper);
        }
    }

    private int[] assertBalancedTree(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = assertBalancedTree(node.left);
        int[] right = assertBalancedTree(node.right);
        int max = Math.max(left[0], right[0]);
        int min = Math.min(left[1], right[1]);
        Assert.assertFalse("not balanced tree", Math.abs(max - min) > 1);
        return new int[]{max + 1, min + 1};
    }

}
