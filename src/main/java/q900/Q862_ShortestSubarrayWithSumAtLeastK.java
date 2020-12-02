package q900;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 862. Shortest Subarray with Sum at Least K
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 *
 * Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.
 *
 * If there is no non-empty subarray with sum at least K, return -1.
 *
 * Example 1:
 *
 * Input: A = [1], K = 1
 * Output: 1
 *
 * Example 2:
 *
 * Input: A = [1,2], K = 4
 * Output: -1
 *
 * Example 3:
 *
 * Input: A = [2,-1,2], K = 3
 * Output: 3
 *
 * Note:
 *
 * 1 <= A.length <= 50000
 * -10 ^ 5 <= A[i] <= 10 ^ 5
 * 1 <= K <= 10 ^ 9
 */
@RunWith(LeetCodeRunner.class)
public class Q862_ShortestSubarrayWithSumAtLeastK {

    // 直接遍历, 时间复杂度 O(N^2), 这种做法会超时
//    @Answer
    public int shortestSubarray_overTime(int[] A, int K) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            int j = i, sum = 0;
            while (j < A.length && sum < K) {
                sum += A[j++];
            }
            if (sum >= K) {
                res = Math.min(res, j - i);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // 使用双端队列的方式, 时间复杂度是 O(N)
    @Answer
    public int shortestSubarray(int[] A, int K) {
        final int n = A.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + A[i];
        }

        int res = Integer.MAX_VALUE;
        // 双端队列表示连续区间的起始位置(结束位置是i), 连续区间的和从前到后是递减的(且都 > 0).
        Deque<Integer> starts = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            starts.offerLast(i);
            // >= K 的最小长度.
            while (!starts.isEmpty() && sums[i + 1] - sums[starts.getFirst()] >= K) {
                res = Math.min(res, i - starts.pollFirst() + 1);
            }
            // 剔除 <= 0 的区间.
            while (!starts.isEmpty() && sums[i + 1] - sums[starts.getLast()] <= 0) {
                starts.pollLast();
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1}, 1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2}, 4).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, -1, 2}, 3).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{56, -21, 56, 35, -9}, 61)
            .expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{-47, 45, 92, 86, 17, -22, 77, 62, -1, 42}, 180)
            .expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 2984435)
            .expect(11455);

}
