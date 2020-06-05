package q800;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/
 *
 * We are given an array A of positive integers, and two positive integers L and R (L <= R).
 *
 * Return the number of (contiguous, non-empty) subarrays such that the value of the maximum array element in that
 * subarray is at least L and at most R.
 *
 * Example :
 * Input:
 * A = [2, 1, 4, 3]
 * L = 2
 * R = 3
 * Output: 3
 * Explanation: There are three subarrays that meet the requirements: [2], [2, 1], [3].
 *
 * Note:
 *
 * L, R  and A[i] will be an integer in the range [0, 10^9].
 * The length of A will be in the range of [1, 50000].
 */
@RunWith(LeetCodeRunner.class)
public class Q795_NumberOfSubarraysWithBoundedMaximum {

    // 简单的暴力枚举, 时间复杂度 O(N^2), 这个比较慢
    @Answer
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = i, max = 0; j < A.length; j++) {
                max = Math.max(max, A[j]);
                if (R < max) {
                    break;
                }
                if (L <= max) {
                    res++;
                }
            }
        }
        return res;
    }

    // 构造递减栈的方式.
    // 这个时间复杂度是 O(N) 但是在OJ 上跑得比上面的还慢.
    @Answer
    public int numSubarrayBoundedMax2(int[] A, int L, int R) {
        final int n = A.length;
        // 分别表示以i 为最高点的区间, 左右的最大长度(都包括自身).
        int[] left = new int[n], right = new int[n];
        List<Integer> min = new ArrayList<>();
        min.add(-1);
        for (int i = 0; i < n; i++) {
            if (R < A[i]) {
                min.clear();
                min.add(i);
            } else if (A[i] < L) {
                continue;
            } else {
                for (int j = min.size() - 1;
                    // 同时移除与自己相同值的元素
                        j > 0 && A[min.get(j)] <= A[i];
                        j--) {
                    min.remove(j);
                }
                left[i] = i - min.get(min.size() - 1);
                min.add(i);
            }
        }

        min.clear();
        min.add(n);
        for (int i = n - 1; i >= 0; i--) {
            if (R < A[i]) {
                min.clear();
                min.add(i);
            } else if (A[i] < L) {
                continue;
            } else {
                for (int j = min.size() - 1;
                    // 这里在寻找右边的时候为了避免重复不会移除与自己相同值的元素
                        j > 0 && A[min.get(j)] < A[i];
                        j--) {
                    min.remove(j);
                }
                right[i] = min.get(min.size() - 1) - i;
                min.add(i);
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            res += left[i] * right[i];
        }
        return res;
    }

    // LeetCode 上最快的做法. 时间复杂度 O(N)
    @Answer
    public int numSubarrayBoundedMax3(int[] A, int L, int R) {
        // break on maximum
        int res = 0;
        int i = 0;
        while (i < A.length) {
            // 寻找最大值 <= R 的区间.
            int start = i;
            while (i < A.length && A[i] <= R) {
                i++;
            }
            int end = i - 1;
            res += getCount(start, end, A, L);
            i++;
        }
        return res;
    }

    // 在最大值 <= R 的区间内寻找符合条件的子区间数量
    private int getCount(int start, int end, int[] A, int L) {
        if (start > end) {
            return 0;
        }

        int i = start;
        int len = end - start + 1;
        int res = (len * (len + 1)) / 2;
        // 把最大值 <L 的区间的组合数量
        while (i <= end) {
            int s = i;
            while (i <= end && A[i] < L) {
                i++;
            }
            int e = i - 1;
            if (e >= s) {
                len = e - s + 1;
                res -= (len * (len + 1)) / 2;
            }
            i++;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{2, 1, 4, 3}, 2, 3).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{2, 9, 2, 5, 6}, 2, 8).expect(7);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{73, 55, 36, 5, 55, 14, 9, 7, 72, 52}, 32, 69).expect(22);

}
