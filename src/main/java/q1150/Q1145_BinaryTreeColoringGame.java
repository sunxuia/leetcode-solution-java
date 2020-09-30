package q1150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1145. Binary Tree Coloring Game
 * https://leetcode.com/problems/binary-tree-coloring-game/
 *
 * Two players play a turn based game on a binary tree.  We are given the root of this binary tree, and the number of
 * nodes n in the tree.  n is odd, and each node has a distinct value from 1 to n.
 *
 * Initially, the first player names a value x with 1 <= x <= n, and the second player names a value y with 1 <= y <= n
 * and y != x.  The first player colors the node with value x red, and the second player colors the node with value y
 * blue.
 *
 * Then, the players take turns starting with the first player.  In each turn, that player chooses a node of their color
 * (red if player 1, blue if player 2) and colors an uncolored neighbor of the chosen node (either the left child, right
 * child, or parent of the chosen node.)
 *
 * If (and only if) a player cannot choose such a node in this way, they must pass their turn.  If both players pass
 * their turn, the game ends, and the winner is the player that colored more nodes.
 *
 * You are the second player.  If it is possible to choose such a y to ensure you win the game, return true.  If it is
 * not possible, return false.
 *
 * Example 1:
 * <img src="Q1145_PIC.png">
 * Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
 * Output: true
 * Explanation: The second player can choose the node with value 2.
 *
 * Constraints:
 *
 * root is the root of a binary tree with n nodes and distinct node values from 1 to n.
 * n is odd.
 * 1 <= x <= n <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1145_BinaryTreeColoringGame {

    @Answer
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        Node[] nodes = new Node[n + 1];
        buildNodes(nodes, root, null);
        Node red = nodes[x], total = nodes[root.val];
        return red.parent != null && total.count - red.count > red.count
                || red.left != null && red.left.count > total.count - red.left.count
                || red.right != null && red.right.count > total.count - red.right.count;
    }

    private static class Node {

        Node parent, left, right;

        int count;
    }

    private Node buildNodes(Node[] nodes, TreeNode tree, Node parent) {
        Node res = new Node();
        res.parent = parent;
        res.count = 1;
        if (tree.left != null) {
            res.left = buildNodes(nodes, tree.left, res);
            res.count += res.left.count;
        }
        if (tree.right != null) {
            res.right = buildNodes(nodes, tree.right, res);
            res.count += res.right.count;
        }
        nodes[tree.val] = res;
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 11, 3)
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            TreeNode.createByLevel(8, 6, 7, 3, 4, null, 9, null, 2, 5, null, null, null, null, null, null, 1), 9, 4)
            .expect(true);

}
