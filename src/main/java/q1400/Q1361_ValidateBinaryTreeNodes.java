package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1361. Validate Binary Tree Nodes
 * https://leetcode.com/problems/validate-binary-tree-nodes/
 *
 * You have n binary tree nodes numbered from 0 to n - 1 where node i has two children leftChild[i] and rightChild[i],
 * return true if and only if all the given nodes form exactly one valid binary tree.
 *
 * If node i has no left child then leftChild[i] will equal -1, similarly for the right child.
 *
 * Note that the nodes have no values and that we only use the node numbers in this problem.
 *
 * Example 1:
 * <img src="./Q1361_PIC1.png">
 * Input: n = 4, leftChild = [1,-1,3,-1], rightChild = [2,-1,-1,-1]
 * Output: true
 *
 * Example 2:
 * <img src="./Q1361_PIC2.png">
 * Input: n = 4, leftChild = [1,-1,3,-1], rightChild = [2,3,-1,-1]
 * Output: false
 *
 * Example 3:
 * <img src="./Q1361_PIC3.png">
 * Input: n = 2, leftChild = [1,0], rightChild = [-1,-1]
 * Output: false
 *
 * Example 4:
 * <img src="./Q1361_PIC4.png">
 * Input: n = 6, leftChild = [1,-1,-1,4,-1,-1], rightChild = [2,-1,-1,5,-1,-1]
 * Output: false
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 * leftChild.length == rightChild.length == n
 * -1 <= leftChild[i], rightChild[i] <= n - 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1361_ValidateBinaryTreeNodes {

    @Answer
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        // 判断是否有环路
        for (int i = 0; i < n; i++) {
            int root = getRoot(parents, i);
            if (leftChild[i] > -1) {
                int left = getRoot(parents, leftChild[i]);
                if (left == root) {
                    return false;
                }
                parents[left] = root;
            }
            if (rightChild[i] > -1) {
                int right = getRoot(parents, rightChild[i]);
                if (right == root) {
                    return false;
                }
                parents[right] = root;
            }
        }

        // 计算树的个数
        int root = 0;
        for (int i = 0; i < n; i++) {
            if (getRoot(parents, i) == i) {
                root++;
            }
        }
        return root == 1;
    }

    private int getRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = getRoot(parents, parents[i]);
    }

    /**
     * leetcode 上的另一种解法, 通过计算入度来进行判断.
     */
    @Answer
    public boolean validateBinaryTreeNodes2(int n, int[] leftChild, int[] rightChild) {
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            // 每个顶点的入度不能超过1
            if (leftChild[i] != -1 && inDegree[leftChild[i]]++ == 1) {
                return false;
            }
            if (rightChild[i] != -1 && inDegree[rightChild[i]]++ == 1) {
                return false;
            }
        }

        int root = -1;
        for (int i = 0; i < n; i++) {
            // 入度为0 则说明是顶点
            if (inDegree[i] == 0) {
                if (root != -1) {
                    // 有多个顶点(多个树)
                    return false;
                }
                root = i;
            }
        }

        return countNodes(leftChild, rightChild, root) == n;
    }

    // dfs 判断从顶点是否能访问到所有顶点.
    private int countNodes(int[] l, int[] r, int root) {
        if (root == -1) {
            return 0;
        }
        return 1 + countNodes(l, r, l[root]) + countNodes(l, r, r[root]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[]{1, -1, 3, -1}, new int[]{2, -1, -1, -1})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(4, new int[]{1, -1, 3, -1}, new int[]{2, 3, -1, -1})
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(2, new int[]{1, 0}, new int[]{-1, -1})
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(6, new int[]{1, -1, -1, 4, -1, -1}, new int[]{2, -1, -1, 5, -1, -1})
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(4, new int[]{1, 0, 3, -1}, new int[]{-1, -1, -1, -1})
            .expect(false);

}
