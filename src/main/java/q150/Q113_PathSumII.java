package q150;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/path-sum-ii/
 *
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given the below binary tree and sum = 22,
 *
 * >       5
 * >      / \
 * >     4   8
 * >    /   / \
 * >   11  13  4
 * >  /  \    / \
 * > 7    2  5   1
 *
 * Return:
 *
 * > [
 * >    [5,4,11,2],
 * >    [5,8,4,5]
 * > ]
 */
@RunWith(LeetCodeRunner.class)
public class Q113_PathSumII {

    @Answer
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(), root, sum);
        return res;
    }

    private void dfs(List<List<Integer>> res, ArrayList<Integer> path, TreeNode node, int sum) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        int remain = sum - node.val;
        if (remain == 0 && node.left == null && node.right == null) {
            res.add((List<Integer>) path.clone());
        }
        dfs(res, path, node.left, remain);
        dfs(res, path, node.right, remain);
        path.remove(path.size() - 1);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByPreOrderTraversal(5, 4, 11, 7, null, null, 2, null, null, null,
                    8, 13, null, null, 4, 5, null, null, 1))
            .addArgument(22)
            .expect(new int[][]{
                    {5, 4, 11, 2},
                    {5, 8, 4, 5}
            }).unorderResult("")
            .build();

}
