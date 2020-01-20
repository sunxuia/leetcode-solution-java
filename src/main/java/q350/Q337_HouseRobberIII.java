package q350;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q250.Q213_HouseRobberII;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/house-robber-iii/
 *
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called
 * the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized
 * that "all houses in this place forms a binary tree". It will automatically contact the police if two
 * directly-linked houses were broken into on the same night.
 *
 * Determine the maximum amount of money the thief can rob tonight without alerting the police.
 *
 * Example 1:
 *
 * Input: [3,2,3,null,3,null,1]
 *
 * >       3
 * >      / \
 * >     2   3
 * >      \   \
 * >       3   1
 *
 * Output: 7
 * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 *
 * Input: [3,4,5,1,3,null,1]
 *
 * >       3
 * >      / \
 * >     4   5
 * >    / \   \
 * >   1   3   1
 *
 * Output: 9
 * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
 *
 * 相关题目: {@link Q213_HouseRobberII}
 */
@RunWith(LeetCodeRunner.class)
public class Q337_HouseRobberIII {

    // 这个可以通过, 但是非常慢
    @Answer
    public int rob(TreeNode root) {
        return dfs(root, false);
    }

    private int dfs(TreeNode node, boolean parentStolen) {
        if (node == null) {
            return 0;
        }
        if (parentStolen) {
            return dfs(node.left, false) + dfs(node.right, false);
        }
        return Math.max(dfs(node.left, false) + dfs(node.right, false),
                node.val + dfs(node.left, true) + dfs(node.right, true));
    }

    // 带cache 的dfs
    @Answer
    public int rob_withCache(TreeNode root) {
        Map<TreeNode, Integer> stealMax = new HashMap<>();
        Map<TreeNode, Integer> notStealMax = new HashMap<>();
        return cacheDfs(root, false, stealMax, notStealMax);
    }

    private int cacheDfs(TreeNode node, boolean parentStolen,
            Map<TreeNode, Integer> stealMax, Map<TreeNode, Integer> notStealMax) {
        if (node == null) {
            return 0;
        }
        Integer steal = 0;
        if (!parentStolen) {
            steal = stealMax.get(node);
            if (steal == null) {
                steal = node.val + cacheDfs(node.left, true, stealMax, notStealMax)
                        + cacheDfs(node.right, true, stealMax, notStealMax);
                stealMax.put(node, steal);
            }
        }

        Integer notSteal = notStealMax.get(node);
        if (notSteal == null) {
            notSteal = cacheDfs(node.left, false, stealMax, notStealMax)
                    + cacheDfs(node.right, false, stealMax, notStealMax);
            notStealMax.put(node, notSteal);
        }
        return Math.max(steal, notSteal);
    }

    // leetcode 上最快的方法, 从底向上进行计算的, 这样既简单也方便.
    @Answer
    public int rob3(TreeNode root) {
        int[] arr = dfs3(root);
        return Math.max(arr[0], arr[1]);
    }

    private int[] dfs3(TreeNode node) {
        if (node == null) {
            return new int[2];
        }
        int[] left = dfs3(node.left);
        int[] right = dfs3(node.right);
        int steal = left[1] + right[1] + node.val;
        int notSteal = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{steal, notSteal};
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(3, 2, 3, null, 3, null, 1))
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(3, 4, 5, 1, 3, null, 1))
            .expect(9);

}
