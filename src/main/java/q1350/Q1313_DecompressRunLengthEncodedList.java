package q1350;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1313. Decompress Run-Length Encoded List
 * https://leetcode.com/problems/decompress-run-length-encoded-list/
 *
 * We are given a list nums of integers representing a list compressed with run-length encoding.
 *
 * Consider each adjacent pair of elements [freq, val] = [nums[2*i], nums[2*i+1]] (with i >= 0).  For each such pair,
 * there are freq elements with value val concatenated in a sublist. Concatenate all the sublists from left to right to
 * generate the decompressed list.
 *
 * Return the decompressed list.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: [2,4,4,4]
 * Explanation: The first pair [1,2] means we have freq = 1 and val = 2 so we generate the array [2].
 * The second pair [3,4] means we have freq = 3 and val = 4 so we generate [4,4,4].
 * At the end the concatenation [2] + [4,4,4] is [2,4,4,4].
 *
 * Example 2:
 *
 * Input: nums = [1,1,2,3]
 * Output: [1,3,3]
 *
 * Constraints:
 *
 * 2 <= nums.length <= 100
 * nums.length % 2 == 0
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1313_DecompressRunLengthEncodedList {

    @Answer
    public int[] decompressRLElist(int[] nums) {
        int len = 0;
        for (int i = 0; i < nums.length; i += 2) {
            len += nums[i];
        }
        int[] res = new int[len];
        for (int i = 0, j = 0; i < nums.length; j += nums[i], i += 2) {
            Arrays.fill(res, j, j + nums[i], nums[i + 1]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(new int[]{2, 4, 4, 4});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 2, 3}).expect(new int[]{1, 3, 3});

}
