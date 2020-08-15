package q950;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 938. Range Sum of BST
 * https://leetcode.com/problems/range-sum-of-bst/
 *
 * Given the root node of a binary search tree, return the sum of values of all nodes with value between L and R
 * (inclusive).
 *
 * The binary search tree is guaranteed to have unique values.
 *
 * Example 1:
 *
 * Input: root = [10,5,15,3,7,null,18], L = 7, R = 15
 * Output: 32
 *
 * Example 2:
 *
 * Input: root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
 * Output: 23
 *
 * Note:
 *
 * The number of nodes in the tree is at most 10000.
 * The final answer is guaranteed to be less than 2^31.
 */
@RunWith(LeetCodeRunner.class)
public class Q938_RangeSumOfBST {

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(10, 5, 15, 3, 7, null, 18), 7, 15)
            .expect(32);
    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(10, 5, 15, 3, 7, 13, 18, 1, null, 6), 6, 10)
            .expect(23);

    @Answer
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        if (L < root.val) {
            res += rangeSumBST(root.left, L, R);
        }
        if (L <= root.val && root.val <= R) {
            res += root.val;
        }
        if (root.val < R) {
            res += rangeSumBST(root.right, L, R);
        }
        return res;
    }

}
