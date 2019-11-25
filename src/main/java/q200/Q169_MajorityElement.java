package q200;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q250.Q229_MajorityElementII;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

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

    // LeetCode 上的做法. 通过计算数字的出现次数来判断, 因为主要数字的出现次数超过一半, 所以这里的count 不会被扣完.
    // 算法是摩尔投票法
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

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 2, 3}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 1, 1, 1, 2, 2}).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{1, 2, 1, 2, 1}).expect(1);

}
