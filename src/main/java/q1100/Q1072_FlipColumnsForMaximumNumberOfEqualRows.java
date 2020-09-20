package q1100;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1072. Flip Columns For Maximum Number of Equal Rows
 * https://leetcode.com/problems/flip-columns-for-maximum-number-of-equal-rows/
 *
 * Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix and flip every cell in that
 * column.  Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.
 *
 * Return the maximum number of rows that have all values equal after some number of flips.
 *
 * Example 1:
 *
 * Input: [[0,1],[1,1]]
 * Output: 1
 * Explanation: After flipping no values, 1 row has all values equal.
 *
 * Example 2:
 *
 * Input: [[0,1],[1,0]]
 * Output: 2
 * Explanation: After flipping values in the first column, both rows have equal values.
 *
 * Example 3:
 *
 * Input: [[0,0,0],[0,0,1],[1,1,0]]
 * Output: 2
 * Explanation: After flipping values in the first two columns, the last two rows have equal values.
 *
 * Note:
 *
 * 1 <= matrix.length <= 300
 * 1 <= matrix[i].length <= 300
 * All matrix[i].length's are equal
 * matrix[i][j] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1072_FlipColumnsForMaximumNumberOfEqualRows {

    /**
     * 二维数组找规律的题目
     * 参考文档 https://www.acwing.com/solution/LeetCode/content/2375/
     *
     * 对于不同的行, 如果这些行中对应的cell 的值完全相同或完全相反时, 翻转列之后
     * 它们总是会同时变为符合条件的行(同时变为全1 或全 0 或一个全1 一个全0).
     * 如果不同的行中的对应cell 的值不完全相同或不完全相反时, 翻转列之后必然两列中最多只有
     * 一列是全1或全0, 另一行不是.
     * 因此只需要计算这些不同排列组合的行的数量, 找出最多的一个, 这样经过翻转之后这些行的值符合条件,
     * 其他行的值不符合条件.
     */
    @Answer
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int res = 0;
        Map<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < m; i++) {
            // 计算该行的特征值, 这样计算后行相同或相反的特征值就是一样的.
            sb.setLength(0);
            for (int j = 0; j < n; j++) {
                sb.append(matrix[i][0] ^ matrix[i][j]);
            }
            String str = sb.toString();
            map.put(str, map.getOrDefault(str, 0) + 1);
            res = Math.max(res, map.get(str));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 1},
            {1, 1}
    }).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 1},
            {1, 0}
    }).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {0, 0, 1},
            {1, 1, 0}
    }).expect(2);

}
