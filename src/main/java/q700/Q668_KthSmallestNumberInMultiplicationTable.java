package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
 *
 * Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from
 * the multiplication table?
 *
 * Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return
 * the k-th smallest number in this table.
 *
 * Example 1:
 *
 * Input: m = 3, n = 3, k = 5
 * Output:
 * Explanation:
 * The Multiplication Table:
 * 1	2	3
 * 2	4	6
 * 3	6	9
 *
 * The 5-th smallest number is 3 (1, 2, 2, 3, 3).
 *
 * Example 2:
 *
 * Input: m = 2, n = 3, k = 6
 * Output:
 * Explanation:
 * The Multiplication Table:
 * 1	2	3
 * 2	4	6
 *
 * The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
 *
 * Note:
 *
 * The m and n will be in the range [1, 30000].
 * The k will be in the range [1, m * n]
 */
@RunWith(LeetCodeRunner.class)
public class Q668_KthSmallestNumberInMultiplicationTable {

    /**
     * 二分法查找对应值, 这个方法在 OJ 上比较慢.
     * 最大值和最小值是确定的, 找出中间的数字, 计算在它之前的数字有多少个,
     * 根据这个数字和k 相比的大小, 判断结果数字在哪里.
     */
    @Answer
    public int findKthNumber(int m, int n, int k) {
        int start = 1, end = m * n;
        while (start < end) {
            int middle = (start + end) / 2;
            // 计算排在 middle 之前的数字
            int count = 0;
            for (int i = 1; i <= n; i++) {
                count += Math.min(m, middle / i);
            }
            if (count < k) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return start;
    }

    /**
     * LeetCode 上比较快的做法. 和上面的做法类似.
     * 主要修改点在于统计数量的lessEqual 方法.
     */
    @Answer
    public int findKthNumber2(int m, int n, int k) {
        int start = 1, end = m * n;
        while (start < end) {
            int middle = (start + end) / 2;
            int count = lessEqual(m, n, middle);
            if (count < k) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return start;
    }

    private int lessEqual(int m, int n, int middle) {
        // i j 表示 [1, j] 使用 i 个数字.
        int i = m, j = 1;
        int count = 0;
        while (i >= 1 && j <= n) {
            if (i * j > middle) {
                i--;
            } else {
                count += i;
                j++;
            }
        }
        return count;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 3, 5).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 3, 6).expect(6);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(9895, 28405, 100787757).expect(31666344);

}
