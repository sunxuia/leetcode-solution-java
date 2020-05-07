package q650;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/construct-string-from-binary-tree/
 *
 * You need to construct a string consists of parenthesis and integers from a binary tree with the preorder
 * traversing way.
 *
 * The null node needs to be represented by empty parenthesis pair "()". And you need to omit all the empty
 * parenthesis pairs that don't affect the one-to-one mapping relationship between the string and the original binary
 * tree.
 *
 * Example 1:
 *
 * Input: Binary tree: [1,2,3,4]
 * >        1
 * >      /   \
 * >     2     3
 * >    /
 * >   4
 *
 * Output: "1(2(4))(3)"
 *
 * Explanation: Originallay it needs to be "1(2(4)())(3()())",
 * but you need to omit all the unnecessary empty parenthesis pairs.
 * And it will be "1(2(4))(3)".
 *
 * Example 2:
 *
 * Input: Binary tree: [1,2,3,null,4]
 * >        1
 * >      /   \
 * >     2     3
 * >      \
 * >       4
 *
 * Output: "1(2()(4))(3)"
 *
 * Explanation: Almost the same as the first example,
 * except we can't omit the first parenthesis pair to break the one-to-one mapping relationship between the input and
 * the output.
 */
@RunWith(LeetCodeRunner.class)
public class Q606_ConstructStringFromBinaryTree {

    @Answer
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        preOrder(t, sb);
        return sb.toString();
    }

    private void preOrder(TreeNode node, StringBuilder sb) {
        sb.append(node.val);
        if (node.left == null && node.right == null) {
            return;
        }
        sb.append('(');
        if (node.left != null) {
            preOrder(node.left, sb);
        }
        sb.append(')');
        if (node.right != null) {
            sb.append('(');
            preOrder(node.right, sb);
            sb.append(')');
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(TreeNode.createByLevel(1, 2, 3, 4)).expect("1(2(4))(3)");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, null, 4)).expect("1(2()(4))(3)");

}
