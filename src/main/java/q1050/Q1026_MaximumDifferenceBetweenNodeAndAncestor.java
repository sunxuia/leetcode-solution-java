package q1050;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1026. Maximum Difference Between Node and Ancestor
 * https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
 *
 * Given the root of a binary tree, find the maximum value V for which there exists different nodes A and B where V =
 * |A.val - B.val| and A is an ancestor of B.
 *
 * (A node A is an ancestor of B if either: any child of A is equal to B, or any child of A is an ancestor of B.)
 *
 * Example 1:
 * (图 Q1026_PIC.png)
 * Input: [8,3,10,1,6,null,14,null,null,4,7,13]
 * Output: 7
 * Explanation:
 * We have various ancestor-node differences, some of which are given below :
 * |8 - 3| = 5
 * |3 - 7| = 4
 * |8 - 1| = 7
 * |10 - 13| = 3
 * Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.
 *
 * Note:
 *
 * The number of nodes in the tree is between 2 and 5000.
 * Each node will have value between 0 and 100000.
 */
@RunWith(LeetCodeRunner.class)
public class Q1026_MaximumDifferenceBetweenNodeAndAncestor {

    @Answer
    public int maxAncestorDiff(TreeNode root) {
        return dfs(root, root.val, root.val);
    }

    private int dfs(TreeNode node, int max, int min) {
        if (node == null) {
            return Math.abs(max - min);
        }
        max = Math.max(max, node.val);
        min = Math.min(min, node.val);
        return Math.max(dfs(node.left, max, min), dfs(node.right, max, min));
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13))
            .expect(7);

}
