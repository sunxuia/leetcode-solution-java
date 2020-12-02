package q900;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 873. Length of Longest Fibonacci Subsequence
 * https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
 *
 * A sequence X_1, X_2, ..., X_n is fibonacci-like if:
 *
 * 1. n >= 3
 * 2. X_i + X_{i+1} = X_{i+2} for all i + 2 <= n
 *
 * Given a strictly increasing array A of positive integers forming a sequence, find the length of the longest
 * fibonacci-like subsequence of A.  If one does not exist, return 0.
 *
 * (Recall that a subsequence is derived from another sequence A by deleting any number of elements (including none)
 * from A, without changing the order of the remaining elements.  For example, [3, 5, 8] is a subsequence of [3, 4, 5,
 * 6, 7, 8].)
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5,6,7,8]
 * Output: 5
 * Explanation:
 * The longest subsequence that is fibonacci-like: [1,2,3,5,8].
 *
 * Example 2:
 *
 * Input: [1,3,7,11,12,14,18]
 * Output: 3
 * Explanation:
 * The longest subsequence that is fibonacci-like:
 * [1,11,12], [3,11,14] or [7,11,18].
 *
 * Note:
 *
 * 1. 3 <= A.length <= 1000
 * 2. 1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
 * 3. (The time limit has been reduced by 50% for submissions in Java, C, and C++.)
 */
@RunWith(LeetCodeRunner.class)
public class Q873_LengthOfLongestFibonacciSubsequence {

    /**
     * 简单的枚举, 时间复杂度 O(N^3), 比较慢
     */
    @Answer
    public int lenLongestFibSubseq(int[] A) {
        final int n = A.length;
        Set<Integer> nums = new HashSet<>(n);
        for (int i : A) {
            nums.add(i);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int count = 2, a = A[i], b = A[j];
                while (nums.contains(a + b)) {
                    b = a + b;
                    a = b - a;
                    count++;
                }
                res = Math.max(res, count);
            }
        }
        return res < 3 ? 0 : res;
    }

    /**
     * 带缓存的方式, 可以节省一点时间
     */
    @Answer
    public int lenLongestFibSubseq2(int[] A) {
        final int n = A.length;
        Map<Integer, Integer> nums = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            nums.put(A[i], i);
        }
        int[][] cache = new int[n][n];

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                res = Math.max(res, 1 + getLength(nums, cache, A, i, j));
            }
        }
        return res < 3 ? 0 : res;
    }

    private int getLength(Map<Integer, Integer> nums, int[][] cache, int[] A, int i, int j) {
        if (!nums.containsKey(A[i] + A[j])) {
            return 1;
        }
        if (cache[i][j] == 0) {
            cache[i][j] = 1 + getLength(nums, cache, A, j, nums.get(A[i] + A[j]));
        }
        return cache[i][j];
    }

    /**
     * LeetCode 上比较快的一种dp 的解法
     */
    @Answer
    public int lenLongestFibSubseq3(int[] A) {
        final int n = A.length;
        int res = 0;
        int[][] dp = new int[n][n];

        for (int i = 2; i < n; i++) {
            // 左右逼近的方式更新缓存
            int left = 0, right = i - 1;
            while (left < right) {
                int sum = A[left] + A[right];
                if (sum < A[i]) {
                    left++;
                } else if (sum > A[i]) {
                    right--;
                } else {
                    dp[right][i] = dp[left][right] + 1;
                    res = Math.max(res, dp[right][i]);
                    left++;
                    right--;
                }
            }
        }

        return res == 0 ? 0 : res + 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 3, 7, 11, 12, 14, 18}).expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(28);

}
