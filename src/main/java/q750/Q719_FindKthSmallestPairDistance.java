package q750;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/find-k-th-smallest-pair-distance/
 *
 * Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is
 * defined as the absolute difference between A and B.
 *
 * Example 1:
 *
 * Input:
 * nums = [1,3,1]
 * k = 1
 * Output: 0
 * Explanation:
 * Here are all the pairs:
 * (1,3) -> 2
 * (1,1) -> 0
 * (3,1) -> 2
 * Then the 1st smallest distance pair is (1,1), and its distance is 0.
 *
 * Note:
 *
 * 2 <= len(nums) <= 10000.
 * 0 <= nums[i] < 1000000.
 * 1 <= k <= len(nums) * (len(nums) - 1) / 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q719_FindKthSmallestPairDistance {

    /**
     * 使用优先队列的方式时间复杂度 O(N*N*logK) 会超时.
     * 根据题目中的 Hint 猜到可以选定一个数字, 然后通过二分查找的方式找到目标数字, 这样来逼近结果.
     */
    @Answer
    public int smallestDistancePair(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        final int n = map.size();
        int[] list = map.keySet().stream().mapToInt(i -> i).sorted().toArray();
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + map.get(list[i]);
        }

        int start = 0, end = list[n - 1] - list[0];
        while (start < end) {
            int mid = start + (end - start) / 2;
            // 计算2 个数字相差小于mid 的数量
            int count = 0, left = 0, right = 0;
            while (right < n) {
                while (list[right] - list[left] > mid) {
                    left++;
                }
                int size = sums[right + 1] - sums[right];
                count += (size * (size - 1)) / 2 + size * (sums[right] - sums[left]);
                right++;
            }

            if (count < k) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    /**
     * LeetCode 上比较快的解法, 相比上面省略了重复数字的处理, 在LeetCode 中更快
     */
    @Answer
    public int smallestDistancePair2(int[] nums, int k) {
        Arrays.sort(nums);
        int start = 0;
        int end = nums[nums.length - 1] - nums[0];
        while (start < end) {
            int mid = (start + end) / 2;
            int count = 0, left = 0, right = 0;
            while (right < nums.length) {
                while (nums[right] - nums[left] > mid) {
                    left++;
                }
                count += right - left;
                right++;
            }

            if (count < k) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 3, 1}, 1).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 6, 1}, 3).expect(5);

    // 1万个数字
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.readIntegerArray("Q719_TestData_1"), 25000000)
            .expect(1);

    // 500 个各不相同的数字
    @TestData
    public DataExpectation overTime2 = DataExpectation
            .createWith(TestDataFileHelper.readIntegerArray("Q719_TestData_2"), 62500)
            .expect(302668);

}
