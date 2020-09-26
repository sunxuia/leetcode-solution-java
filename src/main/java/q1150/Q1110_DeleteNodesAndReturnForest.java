package q1150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1110. Delete Nodes And Return Forest
 * https://leetcode.com/problems/delete-nodes-and-return-forest/
 *
 * Given the root of a binary tree, each node in the tree has a distinct value.
 *
 * After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
 *
 * Return the roots of the trees in the remaining forest.  You may return the result in any order.
 *
 * Example 1:
 * <img src="Q1110_PIC.png" />
 * Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
 * Output: [[1,2,null,4],[6],[7]]
 *
 * Constraints:
 *
 * The number of nodes in the given tree is at most 1000.
 * Each node has a distinct value between 1 and 1000.
 * to_delete.length <= 1000
 * to_delete contains distinct values between 1 and 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q1110_DeleteNodesAndReturnForest {

    @Answer
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        boolean[] deletes = new boolean[1001];
        for (int d : to_delete) {
            deletes[d] = true;
        }
        List<TreeNode> res = new ArrayList<>();
        dfs(res, root, true, deletes);
        return res;
    }

    private TreeNode dfs(List<TreeNode> res, TreeNode node, boolean parentRemoved, boolean[] deletes) {
        if (node == null) {
            return null;
        }
        if (deletes[node.val]) {
            dfs(res, node.left, true, deletes);
            dfs(res, node.right, true, deletes);
            return null;
        }
        if (parentRemoved) {
            res.add(node);
        }
        node.left = dfs(res, node.left, false, deletes);
        node.right = dfs(res, node.right, false, deletes);
        return node;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7))
            .addArgument(new int[]{3, 5})
            .expect(Arrays.asList(
                    TreeNode.createByLevel(1, 2, null, 4),
                    TreeNode.createByLevel(6),
                    TreeNode.createByLevel(7)))
            .unorderResult()
            .build();

}
