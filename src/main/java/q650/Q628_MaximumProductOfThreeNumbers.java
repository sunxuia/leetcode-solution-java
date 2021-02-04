package q650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-product-of-three-numbers/
 *
 * Given an integer array, find three numbers whose product is maximum and output the maximum product.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: 6
 *
 *
 *
 * Example 2:
 *
 * Input: [1,2,3,4]
 * Output: 24
 *
 *
 *
 * Note:
 *
 * The length of the given array will be in range [3,10^4] and all elements are in the range [-1000, 1000].
 * Multiplication of any three numbers in the input won't exceed the range of 32-bit signed integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q628_MaximumProductOfThreeNumbers {

    @Answer
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums, 0, 3);
        int max1 = nums[2], max2 = nums[1], max3 = nums[0];
        int min1 = nums[0], min2 = nums[1];
        for (int i = 3; i < nums.length; i++) {
            int num = nums[i];
            if (num > max1) {
                int t = num;
                num = max1;
                max1 = t;
            }
            if (num > max2) {
                int t = num;
                num = max2;
                max2 = t;
            }
            if (num > max3) {
                max3 = num;
            }

            num = nums[i];
            if (num < min1) {
                int t = num;
                num = min1;
                min1 = t;
            }
            if (num < min2) {
                min2 = num;
            }
        }
        return Math.max(max1 * max2 * max3, max1 * min1 * min2);
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(new int[]{1, 2, 3}).expect(6);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(24);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{
            722, 634, -504, -379, 163, -613, -842, -578, 750, 951, -158, 30, -238, -392, -487, -797, -157, -374, 999,
            -5, -521, -879, -858, 382, 626, 803, -347, 903, -205, 57, -342, 186, -736, 17, 83, 726, -960, 343, -984,
            937, -758, -122, 577, -595, -544, -559, 903, -183, 192, 825, 368, -674, 57, -959, 884, 29, -681, -339, 582,
            969, -95, -455, -275, 205, -548, 79, 258, 35, 233, 203, 20, -936, 878, -868, -458, -882, 867, -664, -892,
            -687, 322, 844, -745, 447, -909, -586, 69, -88, 88, 445, -553, -666, 130, -640, -918, -7, -420, -368, 250,
            -786
    }).expect(943695360);

}
