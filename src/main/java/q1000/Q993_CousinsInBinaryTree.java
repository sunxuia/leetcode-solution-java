package q1000;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 993. Cousins in Binary Tree
 * https://leetcode.com/problems/cousins-in-binary-tree/
 *
 * In a binary tree, the root node is at depth 0, and children of each depth k node are at depth k+1.
 *
 * Two nodes of a binary tree are cousins if they have the same depth, but have different parents.
 *
 * We are given the root of a binary tree with unique values, and the values x and y of two different nodes in the tree.
 *
 * Return true if and only if the nodes corresponding to the values x and y are cousins.
 *
 * Example 1:
 * (图Q993_PIC1.png)
 * Input: root = [1,2,3,4], x = 4, y = 3
 * Output: false
 *
 * Example 2:
 * (图Q993_PIC2.png)
 * Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
 * Output: true
 *
 * Example 3:
 * (图Q993_PIC3.png)
 * Input: root = [1,2,3,null,4], x = 2, y = 3
 * Output: false
 *
 * Constraints:
 *
 * The number of nodes in the tree will be between 2 and 100.
 * Each node has a unique integer value from 1 to 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q993_CousinsInBinaryTree {

    @Answer
    public boolean isCousins(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;
        xDepth = -1;
        yDepth = -1;
        dfs(null, root, 0);
        return xDepth == yDepth && xParent != yParent;
    }

    private int x, y, xDepth, yDepth;

    private TreeNode xParent, yParent;

    private void dfs(TreeNode parent, TreeNode node, int depth) {
        if (node == null) {
            return;
        }
        if (node.val == x) {
            xDepth = depth;
            xParent = parent;
        } else if (node.val == y) {
            yDepth = depth;
            yParent = parent;
        } else if (xDepth == -1 || yDepth == -1) {
            dfs(node, node.left, depth + 1);
            dfs(node, node.right, depth + 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, 4), 4, 3)
            .expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, null, 4, null, 5), 5, 4)
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, null, 4), 2, 3)
            .expect(false);

}
