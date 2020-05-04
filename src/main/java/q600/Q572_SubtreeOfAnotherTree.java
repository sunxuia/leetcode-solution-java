package q600;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/subtree-of-another-tree/
 *
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values
 * with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The
 * tree s could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *
 * >      3
 * >     / \
 * >    4   5
 * >   / \
 * >  1   2
 *
 * Given tree t:
 *
 * >    4
 * >   / \
 * >  1   2
 *
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 * Example 2:
 * Given tree s:
 *
 * >      3
 * >     / \
 * >    4   5
 * >   / \
 * >  1   2
 * >     /
 * >    0
 *
 * Given tree t:
 *
 * >    4
 * >   / \
 * >  1   2
 *
 * Return false.
 */
@RunWith(LeetCodeRunner.class)
public class Q572_SubtreeOfAnotherTree {

    @Answer
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return isSubtree(s, t, false);
    }

    private boolean isSubtree(TreeNode s, TreeNode t, boolean exact) {
        if (s == null || t == null) {
            return s == t;
        }
        if (s.val == t.val
                && isSubtree(s.left, t.left, true)
                && isSubtree(s.right, t.right, true)) {
            return true;
        }
        return !exact && (isSubtree(s.left, t, false)
                || isSubtree(s.right, t, false));
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(3, 4, 5, 1, 2), TreeNode.createByLevel(4, 1, 2))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(3, 4, 5, 1, 2, null, null, null, null, 0),
                    TreeNode.createByLevel(4, 1, 2))
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TreeNode.createByLevel(3, 4, 5, 1, null, 2),
                    TreeNode.createByLevel(3, 1, 2))
            .expect(false);

}
