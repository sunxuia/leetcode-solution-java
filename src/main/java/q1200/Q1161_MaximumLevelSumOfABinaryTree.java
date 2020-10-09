package q1200;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1161. Maximum Level Sum of a Binary Tree
 * https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/
 *
 * Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.
 *
 * Return the smallest level X such that the sum of all the values of nodes at level X is maximal.
 *
 * Example 1:
 *
 * Input: root = [1,7,0,7,-8,null,null]
 * Output: 2
 * Explanation:
 * Level 1 sum = 1.
 * Level 2 sum = 7 + 0 = 7.
 * Level 3 sum = 7 + -8 = -1.
 * So we return the level with the maximum sum which is level 2.
 *
 * Example 2:
 *
 * Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
 * Output: 2
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 104].
 * -105 <= Node.val <= 105
 */
@RunWith(LeetCodeRunner.class)
public class Q1161_MaximumLevelSumOfABinaryTree {

    @Answer
    public int maxLevelSum(TreeNode root) {
        int res = 0, resSum = Integer.MIN_VALUE;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        for (int level = 1; !queue.isEmpty(); level++) {
            int sum = 0;
            for (int len = queue.size(); len > 0; len--) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (sum > resSum) {
                res = level;
                resSum = sum;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 7, 0, 7, -8, null, null))
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(989, null, 10250, 98693, -89388, null, null, null, -32127))
            .expect(2);

}
