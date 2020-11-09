package q1350;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1315. Sum of Nodes with Even-Valued Grandparent
 * https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/
 *
 * Given a binary tree, return the sum of values of nodes with even-valued grandparent.  (A grandparent of a node is the
 * parent of its parent, if it exists.)
 *
 * If there are no nodes with an even-valued grandparent, return 0.
 *
 * Example 1:
 * <img src="./Q1315_PIC.png">
 * Input: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 * Output: 18
 * Explanation: The red nodes are the nodes with even-value grandparent while the blue nodes are the even-value
 * grandparents.
 *
 * Constraints:
 *
 * The number of nodes in the tree is between 1 and 10^4.
 * The value of nodes is between 1 and 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q1315_SumOfNodesWithEvenValuedGrandparent {

    @Answer
    public int sumEvenGrandparent(TreeNode root) {
        TreeNode dummy = new TreeNode(1);
        return dfs(root, dummy, dummy);
    }

    private int dfs(TreeNode node, TreeNode parent, TreeNode grandparent) {
        if (node == null) {
            return 0;
        }
        int res = 0;
        if (grandparent.val % 2 == 0) {
            res += node.val;
        }
        res += dfs(node.left, node, parent);
        res += dfs(node.right, node, parent);
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(6, 7, 8, 2, 7, 1, 3, 9, null, 1, 4, null, null, null, 5))
            .expect(18);

}
