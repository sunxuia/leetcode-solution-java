package q900;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 894. All Possible Full Binary Trees
 * https://leetcode.com/problems/all-possible-full-binary-trees/
 *
 * A full binary tree is a binary tree where each node has exactly 0 or 2 children.
 *
 * Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one
 * possible tree.
 *
 * Each node of each tree in the answer must have node.val = 0.
 *
 * You may return the final list of trees in any order.
 *
 * Example 1:
 *
 * Input: 7
 * Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,
 * null,0,0],[0,0,0,0,0,null,null,0,0]]
 * Explanation:
 * (图Q894_PIC.png)
 *
 * Note:
 *
 * 1 <= N <= 20
 */
@RunWith(LeetCodeRunner.class)
public class Q894_AllPossibleFullBinaryTrees {

    @Answer
    public List<TreeNode> allPossibleFBT(int N) {
        if (N == 1) {
            return Collections.singletonList(new TreeNode(0));
        }
        List<TreeNode> res = new ArrayList<>();
        for (int i = 1; i < N; i += 2) {
            List<TreeNode> left = allPossibleFBT(i);
            List<TreeNode> right = allPossibleFBT(N - i - 1);
            for (TreeNode leftNode : left) {
                for (TreeNode rightNode : right) {
                    TreeNode node = new TreeNode(0);
                    node.left = leftNode;
                    node.right = rightNode;
                    res.add(node);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(7)
            .expect(Arrays.asList(
                    TreeNode.createByLevel(0, 0, 0, null, null, 0, 0, null, null, 0, 0),
                    TreeNode.createByLevel(0, 0, 0, null, null, 0, 0, 0, 0),
                    TreeNode.createByLevel(0, 0, 0, 0, 0, 0, 0),
                    TreeNode.createByLevel(0, 0, 0, 0, 0, null, null, null, null, 0, 0),
                    TreeNode.createByLevel(0, 0, 0, 0, 0, null, null, 0, 0)))
            .unorderResult()
            .build();

}
