package q1100;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1080. Insufficient Nodes in Root to Leaf Paths
 * https://leetcode.com/problems/insufficient-nodes-in-root-to-leaf-paths/
 *
 * Given the root of a binary tree, consider all root to leaf paths: paths from the root to any leaf.  (A leaf is a node
 * with no children.)
 *
 * A node is insufficient if every such root to leaf path intersecting this node has sum strictly less than limit.
 *
 * Delete all insufficient nodes simultaneously, and return the root of the resulting binary tree.
 *
 * Example 1:
 * (图 Q1080_PIC1.png)
 * Input: root = [1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14], limit = 1
 * (图 Q1080_PIC2.png)
 * Output: [1,2,3,4,null,null,7,8,9,null,14]
 *
 * Example 2:
 * (图 Q1080_PIC3.png)
 * Input: root = [5,4,8,11,null,17,4,7,1,null,null,5,3], limit = 22
 * (图 Q1080_PIC4.png)
 * Output: [5,4,8,11,null,17,4,7,null,null,null,5]
 *
 * Example 3:
 * (图 Q1080_PIC5.png)
 * Input: root = [1,2,-3,-5,null,4,null], limit = -1
 * (图 Q1080_PIC6.png)
 * Output: [1,null,-3,4]
 *
 * Note:
 *
 * The given tree will have between 1 and 5000 nodes.
 * -10^5 <= node.val <= 10^5
 * -10^9 <= limit <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1080_InsufficientNodesInRootToLeafPaths {

    @Answer
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        int sum = dfs(root, limit, 0);
        return sum < limit ? null : root;
    }

    private int dfs(TreeNode node, int limit, int sum) {
        sum += node.val;
        if (node.left == null && node.right == null) {
            return sum;
        }
        int res = Integer.MIN_VALUE;
        if (node.left != null) {
            int maxLeaf = dfs(node.left, limit, sum);
            res = Math.max(res, maxLeaf);
            if (maxLeaf < limit) {
                node.left = null;
            }
        }
        if (node.right != null) {
            int maxLeaf = dfs(node.right, limit, sum);
            res = Math.max(res, maxLeaf);
            if (maxLeaf < limit) {
                node.right = null;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, 4, -99, -99, 7, 8, 9, -99, -99, 12, 13, -99, 14), 1)
            .expect(TreeNode.createByLevel(1, 2, 3, 4, null, null, 7, 8, 9, null, 14));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 4, 8, 11, null, 17, 4, 7, 1, null, null, 5, 3), 22)
            .expect(TreeNode.createByLevel(5, 4, 8, 11, null, 17, 4, 7, null, null, null, 5));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, -3, -5, null, 4, null), -1)
            .expect(TreeNode.createByLevel(1, null, -3, 4));

}
