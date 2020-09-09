package q1050;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1028. Recover a Tree From Preorder Traversal
 * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
 *
 * We run a preorder depth first search on the root of a binary tree.
 *
 * At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of
 * this node.  (If the depth of a node is D, the depth of its immediate child is D+1.  The depth of the root node is
 * 0.)
 *
 * If a node has only one child, that child is guaranteed to be the left child.
 *
 * Given the output S of this traversal, recover the tree and return its root.
 *
 * Example 1:
 * (图 Q1028_PIC1.png)
 * Input: "1-2--3--4-5--6--7"
 * Output: [1,2,5,3,4,6,7]
 *
 * Example 2:
 * (图 Q1028_PIC2.png)
 * Input: "1-2--3---4-5--6---7"
 * Output: [1,2,5,3,null,6,null,4,null,7]
 *
 * Example 3:
 * (图 Q1028_PIC3.png)
 * Input: "1-401--349---90--88"
 * Output: [1,401,null,349,88,90]
 *
 * Note:
 *
 * The number of nodes in the original tree is between 1 and 1000.
 * Each node will have a value between 1 and 10^9.
 */
@RunWith(LeetCodeRunner.class)
public class Q1028_RecoverATreeFromPreorderTraversal {

    @Answer
    public TreeNode recoverFromPreorder(String S) {
        return createTreeNode(getNodes(S));
    }

    private static class Part {

        int depth, val;
    }

    private List<Part> getNodes(String str) {
        List<Part> res = new ArrayList<>();
        res.add(new Part());
        char prev = '-';
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '-') {
                if (prev != '-') {
                    res.add(new Part());
                }
                res.get(res.size() - 1).depth++;
            } else {
                Part part = res.get(res.size() - 1);
                part.val = part.val * 10 + c - '0';
            }
            prev = c;
        }
        return res;
    }

    private TreeNode createTreeNode(List<Part> parts) {
        TreeNode dummy = new TreeNode(0);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(dummy);
        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            while (stack.size() > part.depth + 1) {
                stack.pop();
            }
            TreeNode curr = new TreeNode(part.val);
            TreeNode parent = stack.peek();
            if (parent.left == null) {
                parent.left = curr;
            } else {
                parent.right = curr;
            }
            stack.push(curr);
        }
        return dummy.left;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1-2--3--4-5--6--7")
            .expect(TreeNode.createByLevel(1, 2, 5, 3, 4, 6, 7));

    @TestData
    public DataExpectation example2 = DataExpectation.create("1-2--3---4-5--6---7")
            .expect(TreeNode.createByLevel(1, 2, 5, 3, null, 6, null, 4, null, 7));

    @TestData
    public DataExpectation example3 = DataExpectation.create("1-401--349---90--88")
            .expect(TreeNode.createByLevel(1, 401, null, 349, 88, 90));

}
