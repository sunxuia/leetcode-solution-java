package q1000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 971. Flip Binary Tree To Match Preorder Traversal
 * https://leetcode.com/problems/flip-binary-tree-to-match-preorder-traversal/
 *
 * Given a binary tree with N nodes, each node has a different value from {1, ..., N}.
 *
 * A node in this binary tree can be flipped by swapping the left child and the right child of that node.
 *
 * Consider the sequence of N values reported by a preorder traversal starting from the root.  Call such a sequence of N
 * values the voyage of the tree.
 *
 * (Recall that a preorder traversal of a node means we report the current node's value, then preorder-traverse the left
 * child, then preorder-traverse the right child.)
 *
 * Our goal is to flip the least number of nodes in the tree so that the voyage of the tree matches the voyage we are
 * given.
 *
 * If we can do so, then return a list of the values of all nodes flipped.  You may return the answer in any order.
 *
 * If we cannot do so, then return the list [-1].
 *
 * Example 1:
 * (图Q971_PIC1.png)
 * Input: root = [1,2], voyage = [2,1]
 * Output: [-1]
 *
 * Example 2:
 * (图Q971_PIC2.png)
 * Input: root = [1,2,3], voyage = [1,3,2]
 * Output: [1]
 *
 * Example 3:
 * (图Q971_PIC3.png)
 * Input: root = [1,2,3], voyage = [1,2,3]
 * Output: []
 *
 * Note:
 *
 * 1 <= N <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q971_FlipBinaryTreeToMatchPreorderTraversal {

    @Answer
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        index = 0;
        List<Integer> res = new ArrayList<>();
        dfs(res, root, voyage);
        if (index != voyage.length) {
            return Collections.singletonList(-1);
        }
        return res;
    }

    private int index;

    private boolean dfs(List<Integer> res, TreeNode node, int[] voyage) {
        if (node == null) {
            return true;
        }
        if (index == voyage.length) {
            index = -1;
            return false;
        }
        if (node.val != voyage[index]) {
            return false;
        }
        index++;
        if (dfs(res, node.left, voyage) && dfs(res, node.right, voyage)) {
            return true;
        }
        if (dfs(res, node.right, voyage) && dfs(res, node.left, voyage)) {
            res.add(node.val);
            return true;
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2), new int[]{2, 1})
            .expect(Collections.singletonList(-1));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3), new int[]{1, 3, 2})
            .expect(Collections.singletonList(1));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3), new int[]{1, 2, 3})
            .expect(Collections.emptyList());

}
