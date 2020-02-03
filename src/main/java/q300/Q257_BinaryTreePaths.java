package q300;

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
 * https://leetcode.com/problems/binary-tree-paths/
 *
 * Given a binary tree, return all root-to-leaf paths.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Input:
 *
 * >     1
 * >   /   \
 * >  2     3
 * >   \
 * >    5
 *
 * Output: ["1->2->5", "1->3"]
 *
 * Explanation: All root-to-leaf paths are: 1->2->5, 1->3
 */
@RunWith(LeetCodeRunner.class)
public class Q257_BinaryTreePaths {

    @Answer
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs(res, new StringBuilder(), root);
        return res;
    }

    private void dfs(List<String> res, StringBuilder sb, TreeNode node) {
        if (node == null) {
            return;
        }
        int oldLen = sb.length();
        sb.append("->").append(node.val);
        if (node.left == null && node.right == null) {
            res.add(sb.substring(2, sb.length()));
        } else {
            dfs(res, sb, node.left);
            dfs(res, sb, node.right);
        }
        sb.setLength(oldLen);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(1, 2, 3, null, 5))
            .expect(Arrays.asList("1->2->5", "1->3"))
            .unorderResult("")
            .build();

}
