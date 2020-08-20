package q1000;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 967. Numbers With Same Consecutive Differences
 * https://leetcode.com/problems/numbers-with-same-consecutive-differences/
 *
 * Return all non-negative integers of length N such that the absolute difference between every two consecutive digits
 * is K.
 *
 * Note that every number in the answer must not have leading zeros except for the number 0 itself. For example, 01 has
 * one leading zero and is invalid, but 0 is valid.
 *
 * You may return the answer in any order.
 *
 * Example 1:
 *
 * Input: N = 3, K = 7
 * Output: [181,292,707,818,929]
 * Explanation: Note that 070 is not a valid number, because it has leading zeroes.
 *
 * Example 2:
 *
 * Input: N = 2, K = 1
 * Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]
 *
 * Note:
 *
 * 1 <= N <= 9
 * 0 <= K <= 9
 */
@RunWith(LeetCodeRunner.class)
public class Q967_NumbersWithSameConsecutiveDifferences {

    //    @DebugWith("normal4")
    @Answer
    public int[] numsSameConsecDiff(int N, int K) {
        List<Integer> list = new ArrayList<>();
        for (int i = N == 1 ? 0 : 1; i < 10; i++) {
            dfs(list, N, K, 1, i);
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    private void dfs(List<Integer> list, int n, int k, int curr, int num) {
        if (curr == n) {
            list.add(num);
            return;
        }
        if (num % 10 + k < 10) {
            dfs(list, n, k, curr + 1, num * 10 + num % 10 + k);
        }
        if (k > 0 && num % 10 >= k) {
            dfs(list, n, k, curr + 1, num * 10 + num % 10 - k);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(3)
            .addArgument(7)
            .expect(new int[]{181, 292, 707, 818, 929})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(1)
            .expect(new int[]{10, 12, 21, 23, 32, 34, 43, 45, 54, 56, 65, 67, 76, 78, 87, 89, 98})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(1)
            .addArgument(0)
            .expect(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(1)
            .addArgument(1)
            .expect(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(0)
            .expect(new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(3)
            .addArgument(1)
            .expect(new int[]{101, 121, 123, 210, 212, 232, 234, 321, 323, 343, 345, 432, 434, 454, 456, 543, 545, 565,
                    567, 654, 656, 676, 678, 765, 767, 787, 789, 876, 878, 898, 987, 989})
            .unorderResult()
            .build();

}
