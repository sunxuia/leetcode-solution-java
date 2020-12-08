package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1385. Find the Distance Value Between Two Arrays
 * https://leetcode.com/problems/find-the-distance-value-between-two-arrays/
 *
 * Given two integer arrays arr1 and arr2, and the integer d, return the distance value between the two arrays.
 *
 * The distance value is defined as the number of elements arr1[i] such that there is not any element arr2[j] where
 * |arr1[i]-arr2[j]| <= d.
 *
 * Example 1:
 *
 * Input: arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2
 * Output: 2
 * Explanation:
 * For arr1[0]=4 we have:
 * |4-10|=6 > d=2
 * |4-9|=5 > d=2
 * |4-1|=3 > d=2
 * |4-8|=4 > d=2
 * For arr1[1]=5 we have:
 * |5-10|=5 > d=2
 * |5-9|=4 > d=2
 * |5-1|=4 > d=2
 * |5-8|=3 > d=2
 * For arr1[2]=8 we have:
 * |8-10|=2 <= d=2
 * |8-9|=1 <= d=2
 * |8-1|=7 > d=2
 * |8-8|=0 <= d=2
 *
 * Example 2:
 *
 * Input: arr1 = [1,4,2,3], arr2 = [-4,-3,6,10,20,30], d = 3
 * Output: 2
 *
 * Example 3:
 *
 * Input: arr1 = [2,1,100,3], arr2 = [-5,-2,10,-3,7], d = 6
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= arr1.length, arr2.length <= 500
 * -10^3 <= arr1[i], arr2[j] <= 10^3
 * 0 <= d <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1385_FindTheDistanceValueBetweenTwoArrays {

    @Answer
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int res = 0;
        loop:
        for (int i : arr1) {
            for (int j : arr2) {
                if (Math.abs(i - j) <= d) {
                    continue loop;
                }
            }
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 5, 8}, new int[]{10, 9, 1, 8}, 2)
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 4, 2, 3}, new int[]{-4, -3, 6, 10, 20, 30}, 3)
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 1, 100, 3}, new int[]{-5, -2, 10, -3, 7}, 6)
            .expect(1);

}
