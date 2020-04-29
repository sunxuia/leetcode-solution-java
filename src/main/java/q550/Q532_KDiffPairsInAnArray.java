package q550;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/k-diff-pairs-in-an-array/
 *
 * Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array.
 * Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their
 * absolute difference is k.
 *
 * Example 1:
 *
 * Input: [3, 1, 4, 1, 5], k = 2
 * Output: 2
 * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
 * Although we have two 1s in the input, we should only return the number of unique pairs.
 *
 * Example 2:
 *
 * Input:[1, 2, 3, 4, 5], k = 1
 * Output: 4
 * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
 *
 * Example 3:
 *
 * Input: [1, 3, 1, 5, 4], k = 0
 * Output: 1
 * Explanation: There is one 0-diff pair in the array, (1, 1).
 *
 * Note:
 *
 * The pairs (i, j) and (j, i) count as the same pair.
 * The length of the array won't exceed 10,000.
 * All the integers in the given input belong to the range: [-1e7, 1e7].
 */
@RunWith(LeetCodeRunner.class)
public class Q532_KDiffPairsInAnArray {

    @Answer
    public int findPairs(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0 && entry.getValue() > 1
                    || k > 0 && map.containsKey(entry.getKey() + k)) {
                res++;
            }
        }
        return res;
    }

    // LeetCode 中最快的是时间复杂度为 O(NlogN) 的排序方式
    @Answer
    public int findPairs2(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            int idx = Arrays.binarySearch(nums, i + 1, nums.length, nums[i] + k);
            if (idx > 0) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 1, 4, 1, 5}, 2).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 3, 4, 5}, 1).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 3, 1, 5, 4}, 0).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 2, 3, 4, 5}, -1).expect(0);

}
