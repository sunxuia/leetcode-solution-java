package q1650;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1619. Mean of Array After Removing Some Elements
 * https://leetcode.com/problems/mean-of-array-after-removing-some-elements/
 *
 * Given an integer array arr, return the mean of the remaining integers after removing the smallest 5% and the largest
 * 5% of the elements.
 *
 * Answers within 10-5 of the actual answer will be considered accepted.
 *
 * Example 1:
 *
 * Input: arr = [1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3]
 * Output: 2.00000
 * Explanation: After erasing the minimum and the maximum values of this array, all elements are equal to 2, so the mean
 * is 2.
 *
 * Example 2:
 *
 * Input: arr = [6,2,7,5,1,2,0,3,10,2,5,0,5,5,0,8,7,6,8,0]
 * Output: 4.00000
 *
 * Example 3:
 *
 * Input: arr = [6,0,7,0,7,5,7,8,3,4,0,7,8,1,6,8,1,1,2,4,8,1,9,5,4,3,8,5,10,8,6,6,1,0,6,10,8,2,3,4]
 * Output: 4.77778
 *
 * Example 4:
 *
 * Input: arr = [9,7,8,7,7,8,4,4,6,8,8,7,6,8,8,9,2,6,0,0,1,10,8,6,3,3,5,1,10,9,0,7,10,0,10,4,1,10,6,9,3,6,0,0,2,7,0,
 * 6,7,2,9,7,7,3,0,1,6,1,10,3]
 * Output: 5.27778
 *
 * Example 5:
 *
 * Input: arr = [4,8,4,10,0,7,1,3,7,8,8,3,4,1,6,2,1,1,8,0,9,8,0,3,9,10,3,10,1,10,7,3,2,1,4,9,10,7,6,4,0,8,5,1,2,1,6,
 * 2,5,0,7,10,9,10,3,7,10,5,8,5,7,6,7,6,10,9,5,10,5,5,7,2,10,7,7,8,2,0,1,1]
 * Output: 5.29167
 *
 * Constraints:
 *
 * 20 <= arr.length <= 1000
 * arr.length is a multiple of 20.
 * 0 <= arr[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1619_MeanOfArrayAfterRemovingSomeElements {

    @Answer
    public double trimMean(int[] arr) {
        final int n = arr.length, len = n / 20;
        int sum = 0;
        PriorityQueue<Integer> maxPq = new PriorityQueue<>(len + 1);
        PriorityQueue<Integer> minPq = new PriorityQueue<>(len + 1, Comparator.reverseOrder());
        for (int num : arr) {
            sum += num;
            maxPq.offer(num);
            minPq.offer(num);
            if (maxPq.size() > len) {
                maxPq.poll();
            }
            if (minPq.size() > len) {
                minPq.poll();
            }
        }
        while (!maxPq.isEmpty()) {
            sum -= maxPq.poll();
        }
        while (!minPq.isEmpty()) {
            sum -= minPq.poll();
        }
        return (double) sum / (n - 2 * len);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3})
            .expectDouble(2.00000);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{6, 2, 7, 5, 1, 2, 0, 3, 10, 2, 5, 0, 5, 5, 0, 8, 7, 6, 8, 0})
            .expectDouble(4.00000);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{6, 0, 7, 0, 7, 5, 7, 8, 3, 4, 0, 7, 8, 1, 6, 8, 1, 1, 2, 4, 8, 1, 9, 5, 4, 3, 8, 5, 10, 8,
                    6, 6, 1, 0, 6, 10, 8, 2, 3, 4})
            .expectDouble(4.77778);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new int[]{9, 7, 8, 7, 7, 8, 4, 4, 6, 8, 8, 7, 6, 8, 8, 9, 2, 6, 0, 0, 1, 10, 8, 6, 3, 3, 5, 1, 10,
                    9, 0, 7, 10, 0, 10, 4, 1, 10, 6, 9, 3, 6, 0, 0, 2, 7, 0, 6, 7, 2, 9, 7, 7, 3, 0, 1, 6, 1, 10, 3})
            .expectDouble(5.27778);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new int[]{4, 8, 4, 10, 0, 7, 1, 3, 7, 8, 8, 3, 4, 1, 6, 2, 1, 1, 8, 0, 9, 8, 0, 3, 9, 10, 3, 10, 1,
                    10, 7, 3, 2, 1, 4, 9, 10, 7, 6, 4, 0, 8, 5, 1, 2, 1, 6, 2, 5, 0, 7, 10, 9, 10, 3, 7, 10, 5, 8, 5, 7,
                    6, 7, 6, 10, 9, 5, 10, 5, 5, 7, 2, 10, 7, 7, 8, 2, 0, 1, 1})
            .expectDouble(5.29167);

}
