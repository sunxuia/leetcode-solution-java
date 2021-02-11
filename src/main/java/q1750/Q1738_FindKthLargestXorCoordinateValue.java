package q1750;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1738. Find Kth Largest XOR Coordinate Value
 * https://leetcode.com/problems/find-kth-largest-xor-coordinate-value/
 *
 * You are given a 2D matrix of size m x n, consisting of non-negative integers. You are also given an integer k.
 *
 * The value of coordinate (a, b) of the matrix is the XOR of all matrix[i][j] where 0 <= i <= a < m and 0 <= j <= b < n
 * (0-indexed).
 *
 * Find the kth largest value (1-indexed) of all the coordinates of matrix.
 *
 * Example 1:
 *
 * Input: matrix = [[5,2],[1,6]], k = 1
 * Output: 7
 * Explanation: The value of coordinate (0,1) is 5 XOR 2 = 7, which is the largest value.
 *
 * Example 2:
 *
 * Input: matrix = [[5,2],[1,6]], k = 2
 * Output: 5
 * Explanation: The value of coordinate (0,0) is 5 = 5, which is the 2nd largest value.
 *
 * Example 3:
 *
 * Input: matrix = [[5,2],[1,6]], k = 3
 * Output: 4
 * Explanation: The value of coordinate (1,0) is 5 XOR 1 = 4, which is the 3rd largest value.
 *
 * Example 4:
 *
 * Input: matrix = [[5,2],[1,6]], k = 4
 * Output: 0
 * Explanation: The value of coordinate (1,1) is 5 XOR 2 XOR 1 XOR 6 = 0, which is the 4th largest value.
 *
 * Constraints:
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 1000
 * 0 <= matrix[i][j] <= 10^6
 * 1 <= k <= m * n
 */
@RunWith(LeetCodeRunner.class)
public class Q1738_FindKthLargestXorCoordinateValue {

    @Answer
    public int kthLargestValue(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(k + 1);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) {
                    matrix[i][j] ^= matrix[i - 1][j];
                }
                if (j > 0) {
                    matrix[i][j] ^= matrix[i][j - 1];
                }
                if (i > 0 && j > 0) {
                    matrix[i][j] ^= matrix[i - 1][j - 1];
                }
                pq.offer(matrix[i][j]);
                if (pq.size() > k) {
                    pq.poll();
                }
            }
        }
        return pq.poll();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{{5, 2}, {1, 6}}, 1).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{{5, 2}, {1, 6}}, 2).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[][]{{5, 2}, {1, 6}}, 3).expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[][]{{5, 2}, {1, 6}}, 4).expect(0);

}
