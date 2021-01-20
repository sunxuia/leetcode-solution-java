package q1550;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1526. Minimum Number of Increments on Subarrays to Form a Target Array
 * https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
 *
 * Given an array of positive integers target and an array initial of same size with all zeros.
 *
 * Return the minimum number of operations to form a target array from initial if you are allowed to do the following
 * operation:
 *
 * Choose any subarray from initial and increment each value by one.
 *
 * The answer is guaranteed to fit within the range of a 32-bit signed integer.
 *
 * Example 1:
 *
 * Input: target = [1,2,3,2,1]
 * Output: 3
 * Explanation: We need at least 3 operations to form the target array from the initial array.
 * [0,0,0,0,0] increment 1 from index 0 to 4 (inclusive).
 * [1,1,1,1,1] increment 1 from index 1 to 3 (inclusive).
 * [1,2,2,2,1] increment 1 at index 2.
 * [1,2,3,2,1] target array is formed.
 *
 * Example 2:
 *
 * Input: target = [3,1,1,2]
 * Output: 4
 * Explanation: (initial)[0,0,0,0] -> [1,1,1,1] -> [1,1,1,2] -> [2,1,1,2] -> [3,1,1,2] (target).
 *
 * Example 3:
 *
 * Input: target = [3,1,5,4,2]
 * Output: 7
 * Explanation: (initial)[0,0,0,0,0] -> [1,1,1,1,1] -> [2,1,1,1,1] -> [3,1,1,1,1]
 * -> [3,1,2,2,2] -> [3,1,3,3,2] -> [3,1,4,4,2] -> [3,1,5,4,2] (target).
 *
 * Example 4:
 *
 * Input: target = [1,1,1,1]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= target.length <= 10^5
 * 1 <= target[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1526_MinimumNumberOfIncrementsOnSubarraysToFormATargetArray {

    @Answer
    public int minNumberOperations(int[] target) {
        final int n = target.length;

        TreeMap<Integer, Range> map = new TreeMap<>();
        map.put(0, new Range(0, n - 1, 0));

        Integer[] sort = new Integer[n];
        for (int i = 0; i < n; i++) {
            sort[i] = i;
        }
        Arrays.sort(sort, Comparator.comparingInt(i -> target[i]));

        int res = 0;
        for (Integer idx : sort) {
            Range r = map.floorEntry(idx).getValue();
            res += target[idx] - r.level;
            if (r.start < idx) {
                map.put(r.start, new Range(r.start, idx - 1, target[idx]));
            }
            if (idx < r.end) {
                map.put(idx + 1, new Range(idx + 1, r.end, target[idx]));
            }
        }
        return res;
    }

    private static class Range {

        Range(int start, int end, int level) {
            this.start = start;
            this.end = end;
            this.level = level;
        }

        final int start, end, level;
    }

    /**
     * LeetCode 比较快的方法, 思路比较简单
     */
    @Answer
    public int minNumberOperations2(int[] target) {
        int res = 0, prev = 0;
        for (int num : target) {
            if (num > prev) {
                // 出现下降梯度则这里需要被分块处理
                res += num - prev;
            }
            prev = num;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 2, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 1, 1, 2}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 1, 5, 4, 2}).expect(7);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 1, 1, 1}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{4, 2, 2, 4}).expect(6);

}
