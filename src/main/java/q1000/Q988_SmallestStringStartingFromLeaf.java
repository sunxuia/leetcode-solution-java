package q1000;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 988. Smallest String Starting From Leaf
 * https://leetcode.com/problems/smallest-string-starting-from-leaf/
 *
 * Given the root of a binary tree, each node has a value from 0 to 25 representing the letters 'a' to 'z': a value of 0
 * represents 'a', a value of 1 represents 'b', and so on.
 *
 * Find the lexicographically smallest string that starts at a leaf of this tree and ends at the root.
 *
 * (As a reminder, any shorter prefix of a string is lexicographically smaller: for example, "ab" is lexicographically
 * smaller than "aba".  A leaf of a node is a node that has no children.)
 *
 * Example 1:
 * (图 Q988_PIC1.png)
 * Input: [0,1,2,3,4,3,4]
 * Output: "dba"
 *
 * Example 2:
 * (图 Q988_PIC2.png)
 * Input: [25,1,3,1,3,0,2]
 * Output: "adz"
 *
 * Example 3:
 * (图 Q988_PIC3.png)
 * Input: [2,2,1,null,1,0,null,0]
 * Output: "abc"
 *
 * Note:
 *
 * The number of nodes in the given tree will be between 1 and 8500.
 * Each node in the tree will have a value between 0 and 25.
 */
@RunWith(LeetCodeRunner.class)
public class Q988_SmallestStringStartingFromLeaf {

    @Answer
    public String smallestFromLeaf(TreeNode root) {
        res = "zz";
        dfs(new StringBuilder(), root);
        return res;
    }

    private String res;

    private void dfs(StringBuilder sb, TreeNode node) {
        if (node == null) {
            return;
        }
        sb.append((char) (node.val + 'a'));
        if (node.left == null && node.right == null) {
            String end = new StringBuilder(sb).reverse().toString();
            if (end.compareTo(res) < 0) {
                res = end;
            }
        } else {
            dfs(sb, node.left);
            dfs(sb, node.right);
        }
        sb.setLength(sb.length() - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(0, 1, 2, 3, 4, 3, 4))
            .expect("dba");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(25, 1, 3, 1, 3, 0, 2))
            .expect("adz");

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(2, 2, 1, null, 1, 0, null, 0))
            .expect("abc");

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByLevel(0, null, 1))
            .expect("ba");

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(TreeNode.createByLevel(4, 0, 1, 1))
            .expect("bae");

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(TreeNode.createByLevel(25, 1, null, 0, 0, 1, null, null, null, 0))
            .expect("ababz");

}
