package q2000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1968. Array With Elements Not Equal to Average of Neighbors
 * https://leetcode.com/problems/array-with-elements-not-equal-to-average-of-neighbors/
 *
 * You are given a 0-indexed array nums of distinct integers. You want to rearrange the elements in the array such that
 * every element in the rearranged array is not equal to the average of its neighbors.
 *
 * More formally, the rearranged array should have the property such that for every i in the range 1 <= i < nums.length
 * - 1, (nums[i-1] + nums[i+1]) / 2 is not equal to nums[i].
 *
 * Return any rearrangement of nums that meets the requirements.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5]
 * Output: [1,2,4,5,3]
 * Explanation:
 * When i=1, nums[i] = 2, and the average of its neighbors is (1+4) / 2 = 2.5.
 * When i=2, nums[i] = 4, and the average of its neighbors is (2+5) / 2 = 3.5.
 * When i=3, nums[i] = 5, and the average of its neighbors is (4+3) / 2 = 3.5.
 *
 * Example 2:
 *
 * Input: nums = [6,2,0,9,7]
 * Output: [9,7,6,2,0]
 * Explanation:
 * When i=1, nums[i] = 7, and the average of its neighbors is (9+6) / 2 = 7.5.
 * When i=2, nums[i] = 6, and the average of its neighbors is (7+2) / 2 = 4.5.
 * When i=3, nums[i] = 2, and the average of its neighbors is (6+0) / 2 = 3.
 *
 * Constraints:
 *
 * 3 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1968_ArrayWithElementsNotEqualToAverageOfNeighbors {

    // 参考文档
    // https://leetcode.com/problems/array-with-elements-not-equal-to-average-of-neighbors/solutions/1403927/java-c-python-easy-solution/
    @Answer
    public int[] rearrangeArray(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i += 2) {
            int t = nums[i];
            nums[i] = nums[i - 1];
            nums[i - 1] = t;
        }
        return nums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3, 4, 5})
            .assertMethod(this::expectAssertMethod)
            .build();

    private void expectAssertMethod(int[] res) {
        for (int i = 1; i < res.length - 1; i++) {
            if (res[i - 1] + res[i + 1] == res[i] * 2) {
                throw new RuntimeException(String.format("(%d + %d) / 2 = %d",
                        res[i - 1], res[i + 1], res[i]));
            }
        }
    }

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{6, 2, 0, 9, 7})
            .assertMethod(this::expectAssertMethod)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{42, 6, 10, 21, 26, 44, 31, 18, 25, 48, 35, 2, 29, 5, 20})
            .assertMethod(this::expectAssertMethod)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{3, 1, 12, 10, 7, 6, 17, 14, 4, 13})
            .assertMethod(this::expectAssertMethod)
            .build();

}
