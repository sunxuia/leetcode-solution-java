package q1650;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1636. Sort Array by Increasing Frequency
 * https://leetcode.com/problems/sort-array-by-increasing-frequency/
 *
 * Given an array of integers nums, sort the array in increasing order based on the frequency of the values. If multiple
 * values have the same frequency, sort them in decreasing order.
 *
 * Return the sorted array.
 *
 * Example 1:
 *
 * Input: nums = [1,1,2,2,2,3]
 * Output: [3,1,1,2,2,2]
 * Explanation: '3' has a frequency of 1, '1' has a frequency of 2, and '2' has a frequency of 3.
 *
 * Example 2:
 *
 * Input: nums = [2,3,1,3,2]
 * Output: [1,3,3,2,2]
 * Explanation: '2' and '3' both have a frequency of 2, so they are sorted in decreasing order.
 *
 * Example 3:
 *
 * Input: nums = [-1,1,-6,4,5,-6,1,4,1]
 * Output: [5,-1,4,4,-6,-6,1,1,1]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * -100 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1636_SortArrayByIncreasingFrequency {

    @Answer
    public int[] frequencySort(int[] nums) {
        return Arrays.stream(nums)
                .collect((Supplier<HashMap<Integer, Integer>>) HashMap::new,
                        (counts, i) -> counts.put(i, counts.getOrDefault(i, 0) + 1),
                        (left, right) -> right.forEach((num, count) ->
                                left.put(num, left.getOrDefault(num, 0) + count)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                        .thenComparing(Map.Entry.<Integer, Integer>comparingByKey().reversed()))
                .flatMapToInt(p -> IntStream.range(0, p.getValue()).map(i -> p.getKey()))
                .toArray();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{1, 1, 2, 2, 2, 3})
            .expect(new int[]{3, 1, 1, 2, 2, 2});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{2, 3, 1, 3, 2})
            .expect(new int[]{1, 3, 3, 2, 2});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{-1, 1, -6, 4, 5, -6, 1, 4, 1})
            .expect(new int[]{5, -1, 4, 4, -6, -6, 1, 1, 1});

}
