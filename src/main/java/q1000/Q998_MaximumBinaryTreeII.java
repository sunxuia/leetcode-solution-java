package q1000;

import org.junit.runner.RunWith;
import q700.Q654_MaximumBinaryTree;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 998. Maximum Binary Tree II
 * https://leetcode.com/problems/maximum-binary-tree-ii/
 *
 * We are given the root node of a maximum tree: a tree where every node has a value greater than any other value in its
 * subtree.
 *
 * Just as in the previous problem ({@link Q654_MaximumBinaryTree}), the given tree was constructed from an list A
 * (root = Construct(A)) recursively with the following Construct(A) routine:
 *
 * If A is empty, return null.
 * Otherwise, let A[i] be the largest element of A.  Create a root node with value A[i].
 * The left child of root will be Construct([A[0], A[1], ..., A[i-1]])
 * The right child of root will be Construct([A[i+1], A[i+2], ..., A[A.length - 1]])
 * Return root.
 *
 * Note that we were not given A directly, only a root node root = Construct(A).
 *
 * Suppose B is a copy of A with the value val appended to it.  It is guaranteed that B has unique values.
 *
 * Return Construct(B).
 *
 * Example 1:
 * (图 Q998_PIC1.png)
 * Input: root = [4,1,3,null,null,2], val = 5
 * Output: [5,4,null,1,3,null,null,2]
 * Explanation: A = [1,4,2,3], B = [1,4,2,3,5]
 *
 * Example 2:
 * (图 Q998_PIC2.png)
 * Input: root = [5,2,4,null,1], val = 3
 * Output: [5,2,4,null,1,null,3]
 * Explanation: A = [2,1,5,4], B = [2,1,5,4,3]
 *
 * Example 3:
 * (图 Q998_PIC3.png)
 * Input: root = [5,2,3,null,1], val = 4
 * Output: [5,2,4,null,1,3]
 * Explanation: A = [2,1,5,3], B = [2,1,5,3,4]
 *
 * Constraints:
 *
 * 1 <= B.length <= 100
 *
 * 上一题 {@link Q654_MaximumBinaryTree}
 *
 * 题解: 上一题是根据数组参数, 选择其中最大的元素作为节点值, 左边的放到左节点, 右边的放到右节点递归生成.
 * 这题的参数root 则是根据上一题的规则生成的结果, 现在要在这个数组后面加1 个数字val, 现在要求这个新数组的结果.
 */
@RunWith(LeetCodeRunner.class)
public class Q998_MaximumBinaryTreeII {

    @Answer
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val > val) {
            root.right = insertIntoMaxTree(root.right, val);
            return root;
        }
        TreeNode res = new TreeNode(val);
        res.left = root;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(4, 1, 3, null, null, 2), 5)
            .expect(TreeNode.createByLevel(5, 4, null, 1, 3, null, null, 2));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 2, 4, null, 1), 3)
            .expect(TreeNode.createByLevel(5, 2, 4, null, 1, null, 3));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 2, 3, null, 1), 4)
            .expect(TreeNode.createByLevel(5, 2, 4, null, 1, 3));

}
