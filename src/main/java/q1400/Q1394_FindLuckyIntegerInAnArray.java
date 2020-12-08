package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1394. Find Lucky Integer in an Array
 * https://leetcode.com/problems/find-lucky-integer-in-an-array/
 *
 * Given an array of integers arr, a lucky integer is an integer which has a frequency in the array equal to its value.
 *
 * Return a lucky integer in the array. If there are multiple lucky integers return the largest of them. If there is no
 * lucky integer return -1.
 *
 * Example 1:
 *
 * Input: arr = [2,2,3,4]
 * Output: 2
 * Explanation: The only lucky number in the array is 2 because frequency[2] == 2.
 *
 * Example 2:
 *
 * Input: arr = [1,2,2,3,3,3]
 * Output: 3
 * Explanation: 1, 2 and 3 are all lucky numbers, return the largest of them.
 *
 * Example 3:
 *
 * Input: arr = [2,2,2,3,3]
 * Output: -1
 * Explanation: There are no lucky numbers in the array.
 *
 * Example 4:
 *
 * Input: arr = [5]
 * Output: -1
 *
 * Example 5:
 *
 * Input: arr = [7,7,7,7,7,7,7]
 * Output: 7
 *
 * Constraints:
 *
 * 1 <= arr.length <= 500
 * 1 <= arr[i] <= 500
 */
@RunWith(LeetCodeRunner.class)
public class Q1394_FindLuckyIntegerInAnArray {

    @Answer
    public int findLucky(int[] arr) {
        int[] bucket = new int[501];
        for (int val : arr) {
            bucket[val]++;
        }
        for (int i = 500; i > 0; i--) {
            if (bucket[i] == i) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 3, 4}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 2, 3, 3, 3}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 2, 2, 3, 3}).expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{5}).expect(-1);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{7, 7, 7, 7, 7, 7, 7}).expect(7);

}
