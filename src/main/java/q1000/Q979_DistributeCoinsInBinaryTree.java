package q1000;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 979. Distribute Coins in Binary Tree
 * https://leetcode.com/problems/distribute-coins-in-binary-tree/
 *
 * Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.
 *
 * In one move, we may choose two adjacent nodes and move one coin from one node to another.  (The move may be from
 * parent to child, or from child to parent.)
 *
 * Return the number of moves required to make every node have exactly one coin.
 *
 * Example 1:
 * (图 Q979_PIC1.png)
 * Input: [3,0,0]
 * Output: 2
 * Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.
 *
 * Example 2:
 * (图 Q979_PIC2.png)
 * Input: [0,3,0]
 * Output: 3
 * Explanation: From the left child of the root, we move two coins to the root [taking two moves].  Then, we move one
 * coin from the root of the tree to the right child.
 *
 * Example 3:
 * (图 Q979_PIC3.png)
 * Input: [1,0,2]
 * Output: 2
 *
 * Example 4:
 * (图 Q979_PIC4.png)
 * Input: [1,0,0,null,3]
 * Output: 4
 *
 * Note:
 *
 * 1<= N <= 100
 * 0 <= node.val <= N
 */
@RunWith(LeetCodeRunner.class)
public class Q979_DistributeCoinsInBinaryTree {

    @Answer
    public int distributeCoins(TreeNode root) {
        res = 0;
        dfs(root);
        return res;
    }

    private int res = 0;

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        res += Math.abs(left) + Math.abs(right);
        return node.val - 1 + left + right;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(TreeNode.createByLevel(3, 0, 0)).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(TreeNode.createByLevel(0, 3, 0)).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(TreeNode.createByLevel(1, 0, 2)).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(TreeNode.createByLevel(1, 0, 0, null, 3)).expect(4);

}
