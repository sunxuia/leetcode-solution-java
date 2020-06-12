package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/consecutive-numbers-sum/
 *
 * Given a positive integer N, how many ways can we write it as a sum of consecutive positive integers?
 *
 * Example 1:
 *
 * Input: 5
 * Output: 2
 * Explanation: 5 = 5 = 2 + 3
 *
 * Example 2:
 *
 * Input: 9
 * Output: 3
 * Explanation: 9 = 9 = 4 + 5 = 2 + 3 + 4
 *
 * Example 3:
 *
 * Input: 15
 * Output: 4
 * Explanation: 15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
 *
 * Note: 1 <= N <= 10 ^ 9.
 */
@RunWith(LeetCodeRunner.class)
public class Q829_ConsecutiveNumbersSum {

    /**
     * 设 K 为连续数字的开始, len 为连续数字的长度. 则可得
     * (k + k + len - 1) / 2 = N, 化简后可得 k = (2 * N / len + 1 - len) / 2
     * 所以只要找出让 k 是 >= 1 的正整数的len 的情况即可.
     */
    @Answer
    public int consecutiveNumbersSum(int N) {
        int res = 0;
        for (int len = 1; len <= N; len++) {
            double k = (2.0 * N / len + 1 - len) / 2;
            if (k >= 1 && k % 1 == 0) {
                res++;
            } else if (k < 1) {
                break;
            }
        }
        return res;
    }

    /**
     * LeetCode 中的主流解法.
     * 同样是根据 (k + k + len - 1) / 2 = N 这个公式求k 并根据 k>0 的特性,
     * 得出 (2 * N / len + 1 - len) / 2 >= 1, 这个不等式, 化简后得到 len < sqrt(2N),
     * 确定出一个比上面解法要小的len 的取值空间.
     * 参考文档 https://www.cnblogs.com/grandyang/p/11595236.html
     */
    @Answer
    public int consecutiveNumbersSum2(int N) {
        int res = 1;
        for (int i = 2; i < Math.sqrt(2 * N); ++i) {
            if ((N - i * (i - 1) / 2) % i == 0) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(9).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(15).expect(4);

    @TestData
    public DataExpectation arg1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation arg2 = DataExpectation.create(2).expect(1);

    @TestData
    public DataExpectation arg3 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation arg4 = DataExpectation.create(4).expect(1);

    @TestData
    public DataExpectation overTime = DataExpectation.create(53965645).expect(8);

}
