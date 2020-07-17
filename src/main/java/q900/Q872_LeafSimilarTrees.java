package q900;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 872. Leaf-Similar Trees
 * https://leetcode.com/problems/leaf-similar-trees/
 *
 * Consider all the leaves of a binary tree.  From left to right order, the values of those leaves form a leaf value
 * sequence.
 *
 * (图 Q872_PIC.png)
 *
 * For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).
 *
 * Two binary trees are considered leaf-similar if their leaf value sequence is the same.
 *
 * Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.
 *
 * Constraints:
 *
 * Both of the given trees will have between 1 and 200 nodes.
 * Both of the given trees will have values between 0 and 200
 */
@RunWith(LeetCodeRunner.class)
public class Q872_LeafSimilarTrees {

    @Answer
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leafValue1 = getLeafValue(root1);
        List<Integer> leafValue2 = getLeafValue(root2);
        return leafValue1.equals(leafValue2);
    }

    // 使用栈来辅助遍历, 也可以使用dfs
    private List<Integer> getLeafValue(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left == null && node.right == null) {
                res.add(node.val);
            } else {
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByLevel(3, 5, 1, 6, 2, 9, 8, null, null, 7, 4),
                    TreeNode.createByLevel(3, 5, 1, 6, 7, 4, 2, null, null, null, null, null, null, 9, 8))
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TreeNode.createByLevel(18, 35, 22, null, 103, 43, 101, 58, null, 97),
                    TreeNode.createByLevel(94, 102, 17, 122, null, null, 54, 58, 101, 97))
            .expect(false);

}
