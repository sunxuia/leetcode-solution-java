package q400;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
 *
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest
 * element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * Example:
 *
 * matrix = [
 * [ 1,  5,  9],
 * [10, 11, 13],
 * [12, 13, 15]
 * ],
 * k = 8,
 *
 * return 13.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ n^2.
 */
@RunWith(LeetCodeRunner.class)
public class Q378_KthSmallestElementInASortedMatrix {

    // 使用优先队列, 时间复杂度 nlog(n)
    @Answer
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> matrix[a[0]][a[1]]));
        for (int i = 0; i < matrix.length; i++) {
            pq.add(new int[]{i, 0});
        }
        while (--k > 0) {
            int[] arr = pq.poll();
            arr[1]++;
            if (arr[1] < matrix[0].length) {
                pq.add(arr);
            }
        }
        int[] arr = pq.poll();
        return matrix[arr[0]][arr[1]];
    }

    // LeetCode 上比较快的做法. 时间复杂度 nlog(n)
    @Answer
    public int kthSmallest2(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        int low = matrix[0][0];
        int high = matrix[m - 1][n - 1] + 1;
        while (low < high) {
            int middle = low + (high - low) / 2;

            // i 向下, j 向左进行逼近 mid, 同时计算小于mid 的数字count
            int count = 0;
            for (int i = 0, j = n - 1; i < m; i++) {
                while (j >= 0 && matrix[i][j] > middle) {
                    j--;
                }
                count += j + 1;
            }

            if (count < k) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }

        return low;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[][]{
            {1, 5, 9},
            {10, 11, 13},
            {12, 13, 15}
    }, 8).expect(13);

}
