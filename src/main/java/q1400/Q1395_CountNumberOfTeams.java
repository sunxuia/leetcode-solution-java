package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1395. Count Number of Teams
 * https://leetcode.com/problems/count-number-of-teams/
 *
 * There are n soldiers standing in a line. Each soldier is assigned a unique rating value.
 *
 * You have to form a team of 3 soldiers amongst them under the following rules:
 *
 * 1. Choose 3 soldiers with index (i, j, k) with rating (rating[i], rating[j], rating[k]).
 * 2. A team is valid if:  (rating[i] < rating[j] < rating[k]) or
 * (rating[i] > rating[j] > rating[k]) where (0 <= i < j < k < n).
 *
 * Return the number of teams you can form given the conditions. (soldiers can be part of multiple teams).
 *
 * Example 1:
 *
 * Input: rating = [2,5,3,4,1]
 * Output: 3
 * Explanation: We can form three teams given the conditions. (2,3,4), (5,4,1), (5,3,1).
 *
 * Example 2:
 *
 * Input: rating = [2,1,3]
 * Output: 0
 * Explanation: We can't form any team given the conditions.
 *
 * Example 3:
 *
 * Input: rating = [1,2,3,4]
 * Output: 4
 *
 * Constraints:
 *
 * n == rating.length
 * 1 <= n <= 200
 * 1 <= rating[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1395_CountNumberOfTeams {

    /**
     * 暴力求解, 时间复杂度 O(N^3)
     */
    @Answer
    public int numTeams(int[] rating) {
        final int n = rating.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (rating[i] < rating[j] && rating[j] < rating[k]
                            || rating[i] > rating[j] && rating[j] > rating[k]) {
                        res++;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 选定中间值, 时间复杂度 O(N^2)
     */
    @Answer
    public int numTeams2(int[] rating) {
        final int n = rating.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            // i 排在中间, 递增排列时前后的数量.
            int before = 0, after = 0;
            for (int j = 0; j < i; j++) {
                if (rating[j] < rating[i]) {
                    before++;
                }
            }
            for (int j = i + 1; j < n; j++) {
                if (rating[i] < rating[j]) {
                    after++;
                }
            }
            res += before * after;

            // i 排在中间, 递减排列时前后的数量.
            before = after = 0;
            for (int j = 0; j < i; j++) {
                if (rating[j] > rating[i]) {
                    before++;
                }
            }
            for (int j = i + 1; j < n; j++) {
                if (rating[i] > rating[j]) {
                    after++;
                }
            }
            res += before * after;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 5, 3, 4, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 3}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(4);

}
