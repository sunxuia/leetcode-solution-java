package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1539. Kth Missing Positive Number
 * https://leetcode.com/problems/kth-missing-positive-number/
 *
 * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
 *
 * Find the kth positive integer that is missing from this array.
 *
 * Example 1:
 *
 * Input: arr = [2,3,4,7,11], k = 5
 * Output: 9
 * Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
 *
 * Example 2:
 *
 * Input: arr = [1,2,3,4], k = 2
 * Output: 6
 * Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 1000
 * 1 <= arr[i] <= 1000
 * 1 <= k <= 1000
 * arr[i] < arr[j] for 1 <= i < j <= arr.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1539_KthMissingPositiveNumber {

    @Answer
    public int findKthPositive(int[] arr, int k) {
        int prev = 0;
        for (int num : arr) {
            if (prev + k < num) {
                break;
            }
            k -= num - prev - 1;
            prev = num;
        }
        return prev + k;
    }

    /**
     * LeetCode 上更简洁的做法.
     */
    @Answer
    public int findKthPositive2(int[] arr, int k) {
        for (int num : arr) {
            if (num > k) {
                break;
            }
            k++;
        }
        return k;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{2, 3, 4, 7, 11}, 5).expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, 2).expect(6);

}
