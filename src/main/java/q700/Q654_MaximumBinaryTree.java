package q700;

import org.junit.runner.RunWith;
import q1000.Q998_MaximumBinaryTreeII;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 654. Maximum Binary Tree
 * https://leetcode.com/problems/maximum-binary-tree/
 *
 * Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:
 *
 * The root is the maximum number in the array.
 * The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
 * The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
 *
 * Construct the maximum tree by the given array and output the root node of this tree.
 *
 * Example 1:
 *
 * Input: [3,2,1,6,0,5]
 * Output: return the tree root node representing the following tree:
 *
 * >       6
 * >     /   \
 * >    3     5
 * >     \    /
 * >      2  0
 * >       \
 * >        1
 *
 * Note:
 *
 * 1. The size of the given array will be in the range [1,1000].
 *
 * 下一题 {@link Q998_MaximumBinaryTreeII}
 */
@RunWith(LeetCodeRunner.class)
public class Q654_MaximumBinaryTree {

    @Answer
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int max = start;
        for (int i = start; i <= end; i++) {
            if (nums[max] < nums[i]) {
                max = i;
            }
        }
        TreeNode node = new TreeNode(nums[max]);
        node.left = dfs(nums, start, max - 1);
        node.right = dfs(nums, max + 1, end);
        return node;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{3, 2, 1, 6, 0, 5})
            .expect(TreeNode.createByLevel(6, 3, 5, null, 2, 0, null, null, 1));

}
