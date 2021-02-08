package q1750;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import q1150.Q1143_LongestCommonSubsequence;
import q300.Q300_LongestIncreasingSubsequence;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1713. Minimum Operations to Make a Subsequence
 * https://leetcode.com/problems/minimum-operations-to-make-a-subsequence/
 *
 * You are given an array target that consists of distinct integers and another integer array arr that can have
 * duplicates.
 *
 * In one operation, you can insert any integer at any position in arr. For example, if arr = [1,4,1,2], you can add 3
 * in the middle and make it [1,4,3,1,2]. Note that you can insert the integer at the very beginning or end of the
 * array.
 *
 * Return the minimum number of operations needed to make target a subsequence of arr.
 *
 * A subsequence of an array is a new array generated from the original array by deleting some elements (possibly none)
 * without changing the remaining elements' relative order. For example, [2,7,4] is a subsequence of [4,2,3,7,2,1,4]
 * (the underlined elements), while [2,4,2] is not.
 *
 * Example 1:
 *
 * Input: target = [5,1,3], arr = [9,4,2,3,4]
 * Output: 2
 * Explanation: You can add 5 and 1 in such a way that makes arr = [5,9,4,1,2,3,4], then target will be a subsequence of
 * arr.
 *
 * Example 2:
 *
 * Input: target = [6,4,8,1,3,2], arr = [4,7,6,2,3,8,6,1]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= target.length, arr.length <= 10^5
 * 1 <= target[i], arr[i] <= 10^9
 * target contains no duplicates.
 */
@RunWith(LeetCodeRunner.class)
public class Q1713_MinimumOperationsToMakeASubsequence {

    /**
     * 根据提示, 这题类似 {@link Q1143_LongestCommonSubsequence}
     * 但是这里的数据量比较大, 不能使用dp, 需要利用 target 中元素没有重复的特性,
     * 将这题转化为 {@link Q300_LongestIncreasingSubsequence}.
     */
    @Answer
    public int minOperations(int[] target, int[] arr) {
        final int m = target.length, n = arr.length;

        // 转换为最长递增子序列问题
        Map<Integer, Integer> indexes = new HashMap<>(m);
        for (int i = 0; i < m; i++) {
            indexes.put(target[i], i);
        }
        for (int i = 0; i < n; i++) {
            arr[i] = indexes.getOrDefault(arr[i], -1);
        }

        // 求最长子序列
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        for (int i = 0; i < n; i++) {
            int idx = Collections.binarySearch(list, arr[i]);
            idx = idx >= 0 ? idx : -idx - 1;
            if (idx == list.size()) {
                list.add(arr[i]);
            } else {
                list.set(idx, arr[i]);
            }
        }
        return m - list.size() + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{5, 1, 3}, new int[]{9, 4, 2, 3, 4})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{6, 4, 8, 1, 3, 2}, new int[]{4, 7, 6, 2, 3, 8, 6, 1})
            .expect(3);

    private TestDataFile testDataFile = new TestDataFile();

    /**
     * 6万 * 6万
     */
    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            TestDataFileHelper.read(testDataFile, 1, int[].class),
            TestDataFileHelper.read(testDataFile, 2, int[].class)
    ).expect(30094);

}
