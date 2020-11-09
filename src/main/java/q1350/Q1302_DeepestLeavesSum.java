package q1350;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1302. Deepest Leaves Sum
 * https://leetcode.com/problems/deepest-leaves-sum/
 *
 * Given a binary tree, return the sum of values of its deepest leaves.
 *
 * Example 1:
 * <img src="./Q1302_PIC.png">
 * Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
 * Output: 15
 *
 * Constraints:
 *
 * The number of nodes in the tree is between 1 and 10^4.
 * The value of nodes is between 1 and 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q1302_DeepestLeavesSum {

    /**
     * bfs
     */
    @Answer
    public int deepestLeavesSum(TreeNode root) {
        List<TreeNode> curr = new ArrayList<>();
        List<TreeNode> next = new ArrayList<>();
        next.add(root);
        while (!next.isEmpty()) {
            List<TreeNode> t = curr;
            curr = next;
            next = t;
            next.clear();
            for (TreeNode node : curr) {
                if (node.left != null) {
                    next.add(node.left);
                }
                if (node.right != null) {
                    next.add(node.right);
                }
            }
        }
        return curr.stream().mapToInt(n -> n.val).sum();
    }

    /**
     * dfs
     */
    @Answer
    public int deepestLeavesSum2(TreeNode root) {
        int level = getDepth(root);
        return getSum(root, 1, level);
    }

    private int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getDepth(node.left), getDepth(node.right));
    }

    private int getSum(TreeNode node, int depth, int target) {
        if (node == null) {
            return 0;
        }
        if (depth == target) {
            return node.val;
        }
        return getSum(node.left, depth + 1, target)
                + getSum(node.right, depth + 1, target);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8))
            .expect(15);

}
