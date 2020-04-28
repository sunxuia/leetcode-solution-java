package q550;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-largest-value-in-each-tree-row/
 *
 * You need to find the largest value in each row of a binary tree.
 *
 * Example:
 *
 * Input:
 *
 * >           1
 * >          / \
 * >         3   2
 * >        / \   \
 * >       5   3   9
 *
 * Output: [1, 3, 9]
 */
@RunWith(LeetCodeRunner.class)
public class Q515_FindLargestValueInEachTreeRow {

    // dfs 的做法
    @Answer
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    private void dfs(TreeNode node, int depth, List<Integer> res) {
        if (node == null) {
            return;
        }
        if (res.size() <= depth) {
            res.add(node.val);
        } else if (res.get(depth) < node.val) {
            res.set(depth, node.val);
        }
        dfs(node.left, depth + 1, res);
        dfs(node.right, depth + 1, res);
    }

    // bfs 的做法
    @Answer
    public List<Integer> largestValues_BFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int max = Integer.MIN_VALUE;
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                max = Math.max(max, node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(max);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(1, 3, 2, 5, 3, null, 9))
            .expect(Arrays.asList(1, 3, 9));

}
