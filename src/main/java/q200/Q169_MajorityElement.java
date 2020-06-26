package q200;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q250.Q229_MajorityElementII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/majority-element/
 *
 * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊
 * n/2 ⌋ times.
 *
 * You may assume that the array is non-empty and the majority element always exist in the array.
 *
 * Example 1:
 *
 * Input: [3,2,3]
 * Output: 3
 *
 * Example 2:
 *
 * Input: [2,2,1,1,1,2,2]
 * Output: 2
 *
 * 相关题目: {@link Q229_MajorityElementII}
 */
@RunWith(LeetCodeRunner.class)
public class Q169_MajorityElement {

    // 使用map 的简单做法, 这个比较慢也比较占用空间.
    // 时间复杂度 O(N), 空间复杂度 O(N)
    @Answer
    public int majorityElement_map(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if (pair.getValue() >= (nums.length + 1) / 2) {
                return pair.getKey();
            }
        }
        return -1;
    }

    // 摩尔投票法, LeetCode 上的做法. 时间复杂度 O(N), 空间复杂度 O(1).
    // 通过计算数字的出现次数来判断, 因为主要数字的出现次数超过一半, 所以这里的count 不会被扣完.
    @Answer
    public int majorityElement(int[] nums) {
        int n = nums[0];
        int count = 0;
        for (int num : nums) {
            if (n == num) {
                count++;
            } else if (count > 0) {
                count--;
            } else {
                n = num;
                count = 1;
            }
        }
        return n;
    }

    // 排序的算法, 时间复杂度 O(NlogN).
    // 因为主要数字超过一半, 所以排序后中间的数字肯定就是这个主要数字.
    @Answer
    public int majorityElement_sort(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    // 分治的解法. 时间复杂度 O(NlogN)
    @Answer
    public int majorityElement_dc(int[] nums) {
        return dc(nums, 0, nums.length - 1);
    }

    private int dc(int[] nums, int start, int end) {
        if (start == end) {
            return nums[start];
        }
        int mid = start + (end - start) / 2;
        int left = dc(nums, start, mid);
        int right = dc(nums, mid + 1, end);
        // 左右相等, 则返回左右值
        if (left == right) {
            return left;
        }
        // 左右不等则返回次数较多的
        int count1 = 0, count2 = 0;
        for (int i = start; i <= end; i++) {
            if (nums[i] == left) {
                count1++;
            } else if (nums[i] == right) {
                count2++;
            }
        }
        return count1 >= count2 ? left : right;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 2, 3}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 1, 1, 1, 2, 2}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 1, 2, 1}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{6, 5, 5}).expect(5);
}
