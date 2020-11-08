package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1287. Element Appearing More Than 25% In Sorted Array
 * https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/
 *
 * Given an integer array sorted in non-decreasing order, there is exactly one integer in the array that occurs more
 * than 25% of the time.
 *
 * Return that integer.
 *
 * Example 1:
 * Input: arr = [1,2,2,6,6,6,6,7,10]
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1287_ElementAppearingMoreThan25InSortedArray {

    /**
     * 返回次数最多的次数, 没有利用 >25% 这个限制.
     */
    @Answer
    public int findSpecialInteger(int[] arr) {
        int res = arr[0], maxCount = 1, count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] == arr[i]) {
                count++;
            } else {
                count = 1;
            }
            if (count > maxCount) {
                res = arr[i];
                maxCount = count;
            }
        }
        return res;
    }

    /**
     * 因为只有1 个次数 >25%, 所以只有这个数字占据超过 1/4 长度.
     */
    @Answer
    public int findSpecialInteger2(int[] arr) {
        final int n = arr.length;
        for (int i = 0; i + n / 4 < n; i++) {
            if (arr[i] == arr[i + n / 4]) {
                return arr[i];
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 2, 6, 6, 6, 6, 7, 10}).expect(6);

}
