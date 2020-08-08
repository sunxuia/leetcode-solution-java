package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 915. Partition Array into Disjoint Intervals
 * https://leetcode.com/problems/partition-array-into-disjoint-intervals/
 *
 * Given an array A, partition it into two (contiguous) subarrays left and right so that:
 *
 * Every element in left is less than or equal to every element in right.
 * left and right are non-empty.
 * left has the smallest possible size.
 *
 * Return the length of left after such a partitioning.  It is guaranteed that such a partitioning exists.
 *
 * Example 1:
 *
 * Input: [5,0,3,8,6]
 * Output: 3
 * Explanation: left = [5,0,3], right = [8,6]
 *
 * Example 2:
 *
 * Input: [1,1,1,0,6,12]
 * Output: 4
 * Explanation: left = [1,1,1,0], right = [6,12]
 *
 * Note:
 *
 * 2 <= A.length <= 30000
 * 0 <= A[i] <= 10^6
 * It is guaranteed there is at least one way to partition A as described.
 *
 * 题解: 将数组A 分为2 部分, 左边的数字 <= A[0], 右边的数字 >= A[0], 且左边长度尽可能小.
 */
@RunWith(LeetCodeRunner.class)
public class Q915_PartitionArrayIntoDisjointIntervals {

    @Answer
    public int partitionDisjoint(int[] A) {
        final int n = A.length;
        // 右边(包括自己)最小的值
        int[] rightMin = new int[n];
        rightMin[n - 1] = A[n - 1];
        for (int i = n - 2; i > 0; i--) {
            rightMin[i] = Math.min(A[i], rightMin[i + 1]);
        }

        int max = A[0];
        for (int i = 1; i < n; i++) {
            if (max <= rightMin[i]) {
                return i;
            }
            max = Math.max(max, A[i]);
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 0, 3, 8, 6}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 0, 6, 12}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{24, 11, 49, 80, 63, 8, 61, 22, 73, 85}).expect(9);

}
