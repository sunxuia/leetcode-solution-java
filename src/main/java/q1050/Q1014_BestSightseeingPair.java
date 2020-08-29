package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1014. Best Sightseeing Pair
 * https://leetcode.com/problems/best-sightseeing-pair/
 *
 * Given an array A of positive integers, A[i] represents the value of the i-th sightseeing spot, and two sightseeing
 * spots i and j have distance j - i between them.
 *
 * The score of a pair (i < j) of sightseeing spots is (A[i] + A[j] + i - j) : the sum of the values of the sightseeing
 * spots, minus the distance between them.
 *
 * Return the maximum score of a pair of sightseeing spots.
 *
 * Example 1:
 *
 * Input: [8,1,5,2,6]
 * Output: 11
 * Explanation: i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
 *
 * Note:
 *
 * 2 <= A.length <= 50000
 * 1 <= A[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1014_BestSightseeingPair {

    /**
     * 参考文档 https://massivealgorithms.blogspot.com/2019/04/leetcode-1014-best-sightseeing-pair.html
     */
    @Answer
    public int maxScoreSightseeingPair(int[] A) {
        final int n = A.length;
        int res = 0;
        int maxEndRight = A[n - 1] - (n - 1);
        for (int i = n - 2; i >= 0; i--) {
            maxEndRight = Math.max(maxEndRight, A[i + 1] - (i + 1));
            res = Math.max(res, A[i] + i + maxEndRight);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{8, 1, 5, 2, 6}).expect(11);

}
