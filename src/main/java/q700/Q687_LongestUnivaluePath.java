package q700;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-univalue-path/
 *
 * Given a binary tree, find the length of the longest path where each node in the path has the same value. This
 * path may or may not pass through the root.
 *
 * The length of path between two nodes is represented by the number of edges between them.
 *
 *
 *
 * Example 1:
 *
 * Input:
 *
 * >               5
 * >              / \
 * >             4   5
 * >            / \   \
 * >           1   1   5
 *
 * Output: 2
 *
 *
 *
 * Example 2:
 *
 * Input:
 *
 * >               1
 * >              / \
 * >             4   5
 * >            / \   \
 * >           4   4   5
 *
 * Output: 2
 *
 *
 *
 * Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q687_LongestUnivaluePath {

    @Answer
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return dfs(root, root.val - 1)[1] - 1;
    }

    private int[] dfs(TreeNode node, int parentValue) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs(node.left, node.val);
        int[] right = dfs(node.right, node.val);
        // 在祖宗节点转弯的路径的在此节点能够得到的最大长度
        int pathMax = node.val == parentValue ? Math.max(left[0], right[0]) + 1 : 0;
        // 在此节点转弯的路径的最大长度
        int max = Math.max(1 + left[0] + right[0], Math.max(left[1], right[1]));
        return new int[]{pathMax, max};
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(5, 4, 5, 1, 1, null, 5)).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 4, 5, 4, 4, null, 5)).expect(2);

    @TestData
    public DataExpectation border = DataExpectation.create(null).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByLevel(1, null, 1, 1, 1, 1, 1, 1)).expect(4);

}
