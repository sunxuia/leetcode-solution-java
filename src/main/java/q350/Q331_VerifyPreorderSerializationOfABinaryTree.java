package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/
 *
 * One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the
 * node's value. If it is a null node, we record using a sentinel value such as #.
 *
 * >       _9_
 * >      /   \
 * >     3     2
 * >    / \   / \
 * >   4   1  #  6
 * >  / \ / \   / \
 * >  # # # #   # #
 *
 * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents
 * a null node.
 *
 * Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a
 * binary tree. Find an algorithm without reconstructing the tree.
 *
 * Each comma separated value in the string must be either an integer or a character '#' representing null pointer.
 *
 * You may assume that the input format is always valid, for example it could never contain two consecutive commas
 * such as "1,,3".
 *
 * Example 1:
 *
 * Input: "9,3,4,#,#,1,#,#,2,#,6,#,#"
 * Output: true
 *
 * Example 2:
 *
 * Input: "1,#"
 * Output: false
 *
 * Example 3:
 *
 * Input: "9,#,#,1"
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q331_VerifyPreorderSerializationOfABinaryTree {

    @Answer
    public boolean isValidSerialization(String preorder) {
        String[] vals = preorder.split(",");
        next = 0;
        return dfs(vals) && next == vals.length;
    }

    private int next;

    private boolean dfs(String[] vals) {
        if (next == vals.length) {
            return false;
        }
        if ("#".equals(vals[next++])) {
            return true;
        }
        return dfs(vals) && dfs(vals);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("9,3,4,#,#,1,#,#,2,#,6,#,#").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("1,#").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create("9,#,#,1").expect(false);

}
