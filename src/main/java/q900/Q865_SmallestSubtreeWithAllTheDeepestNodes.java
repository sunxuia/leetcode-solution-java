package q900;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 865. Smallest Subtree with all the Deepest Nodes
 * https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
 *
 * Given a binary tree rooted at root, the depth of each node is the shortest distance to the root.
 *
 * A node is deepest if it has the largest depth possible among any node in the entire tree.
 *
 * The subtree of a node is that node, plus the set of all descendants of that node.
 *
 * Return the node with the largest depth such that it contains all the deepest nodes in its subtree.
 *
 * Example 1:
 *
 * Input: [3,5,1,6,2,0,8,null,null,7,4]
 * Output: [2,7,4]
 * Explanation:
 *
 * (图 Q865_PIC.png)
 *
 * We return the node with value 2, colored in yellow in the diagram.
 * The nodes colored in blue are the deepest nodes of the tree.
 * The input "[3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]" is a serialization of the given tree.
 * The output "[2, 7, 4]" is a serialization of the subtree rooted at the node with value 2.
 * Both the input and output have TreeNode type.
 *
 * Note:
 *
 * The number of nodes in the tree will be between 1 and 500.
 * The values of each node are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q865_SmallestSubtreeWithAllTheDeepestNodes {

    @Answer
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        int maxDepth = getDepth(root);
        return getRes(root, 1, maxDepth);
    }

    private int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getDepth(node.left), getDepth(node.right));
    }

    private TreeNode getRes(TreeNode node, int depth, int maxDepth) {
        if (node == null || depth == maxDepth) {
            return node;
        }
        TreeNode left = getRes(node.left, depth + 1, maxDepth);
        TreeNode right = getRes(node.right, depth + 1, maxDepth);
        return left != null && right != null ? node : left == null ? right : left;
    }

    @TestData
    public DataExpectation example() {
        TreeNode root = TreeNode.createByLevel(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        return DataExpectation.create(root).expect(root.left.right);
    }

}
