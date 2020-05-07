package q650;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/merge-two-binary-trees/
 *
 * Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees
 * are overlapped while the others are not.
 *
 * You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values
 * up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.
 *
 * Example 1:
 *
 * Input:
 * Tree 1                     Tree 2
 * >           1                         2
 * >          / \                       / \
 * >         3   2                     1   3
 * >        /                           \   \
 * >       5                             4   7
 * Output:
 * Merged tree:
 * > 	     3
 * > 	    / \
 * > 	   4   5
 * > 	  / \   \
 * > 	 5   4   7
 */
@RunWith(LeetCodeRunner.class)
public class Q617_MergeTwoBinaryTrees {

    @Answer
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByLevel(1, 3, 2, 5), TreeNode.createByLevel(2, 1, 3, null, 4, null, 7))
            .expect(TreeNode.createByLevel(3, 4, 5, 5, 4, null, 7));

}
