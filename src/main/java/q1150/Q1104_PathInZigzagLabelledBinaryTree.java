package q1150;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1104. Path In Zigzag Labelled Binary Tree
 * https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
 *
 * In an infinite binary tree where every node has two children, the nodes are labelled in row order.
 *
 * In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right, while in the even
 * numbered rows (second, fourth, sixth,...), the labelling is right to left.
 * (图 Q1104_PIC.png)
 * Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with that
 * label.
 *
 * Example 1:
 *
 * Input: label = 14
 * Output: [1,3,4,14]
 *
 * Example 2:
 *
 * Input: label = 26
 * Output: [1,2,6,10,26]
 *
 * Constraints:
 *
 * 1 <= label <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1104_PathInZigzagLabelledBinaryTree {

    @Answer
    public List<Integer> pathInZigZagTree(int label) {
        // label 所在的行数(从0 开始, 题目中是从1 开始)
        int row = (int) (Math.log(label) / Math.log(2));
        Integer[] resArr = new Integer[row + 1];
        int start = (int) Math.pow(2, row);
        // i 表示点在每行中的坐标 (从0 开始, 从左边起始)
        int i = row % 2 == 0 ? label - start : start * 2 - 1 - label;
        for (; row >= 0; row--, i /= 2, start /= 2) {
            resArr[row] = row % 2 == 0 ? start + i : start * 2 - 1 - i;
        }
        return Arrays.asList(resArr);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(14).expect(Arrays.asList(1, 3, 4, 14));

    @TestData
    public DataExpectation example2 = DataExpectation.create(26).expect(Arrays.asList(1, 2, 6, 10, 26));

}
