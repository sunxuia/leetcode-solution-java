package q100;

import static util.provided.TreeNode.createByPreOrderTraversal;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/same-tree/
 *
 * Given two binary trees, write a function to check if they are the same or not.
 *
 * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
 *
 * Example 1:
 *
 * Input:     1         1
 * >         / \       / \
 * >        2   3     2   3
 *
 * >       [1,2,3],   [1,2,3]
 *
 * Output: true
 *
 * Example 2:
 *
 * Input:     1         1
 * >         /           \
 * >        2             2
 *
 * >       [1,2],     [1,null,2]
 *
 * Output: false
 *
 * Example 3:
 *
 * Input:     1         1
 * >         / \       / \
 * >        2   1     1   2
 *
 * >       [1,2,1],   [1,1,2]
 *
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q100_SameTree {

    @Answer
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == q) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(createByPreOrderTraversal(1, 2, null, null, 3),
                    createByPreOrderTraversal(1, 2, null, null, 3))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(createByPreOrderTraversal(1, 2),
                    createByPreOrderTraversal(1, null, 2))
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(createByPreOrderTraversal(1, 2, null, null, 1),
                    createByPreOrderTraversal(1, 1, null, null, 2))
            .expect(false);

}
