package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1534. Count Good Triplets
 * https://leetcode.com/problems/count-good-triplets/
 *
 * Given an array of integers arr, and three integers a, b and c. You need to find the number of good triplets.
 *
 * A triplet (arr[i], arr[j], arr[k]) is good if the following conditions are true:
 *
 * 0 <= i < j < k < arr.length
 * |arr[i] - arr[j]| <= a
 * |arr[j] - arr[k]| <= b
 * |arr[i] - arr[k]| <= c
 *
 * Where |x| denotes the absolute value of x.
 *
 * Return the number of good triplets.
 *
 * Example 1:
 *
 * Input: arr = [3,0,1,1,9,7], a = 7, b = 2, c = 3
 * Output: 4
 * Explanation: There are 4 good triplets: [(3,0,1), (3,0,1), (3,1,1), (0,1,1)].
 *
 * Example 2:
 *
 * Input: arr = [1,1,2,2,3], a = 0, b = 0, c = 1
 * Output: 0
 * Explanation: No triplet satisfies all conditions.
 *
 * Constraints:
 *
 * 3 <= arr.length <= 100
 * 0 <= arr[i] <= 1000
 * 0 <= a, b, c <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1534_CountGoodTriplets {

    @Answer
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        final int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(arr[i] - arr[j]) <= a) {
                    for (int k = j + 1; k < n; k++) {
                        if (Math.abs(arr[i] - arr[k]) <= c
                                && Math.abs(arr[j] - arr[k]) <= b) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{3, 0, 1, 1, 9, 7}, 7, 2, 3)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 2, 2, 3}, 0, 0, 1)
            .expect(0);

}
