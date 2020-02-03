package q300;

import org.junit.runner.RunWith;
import q150.Q142_LinkedListCycleII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-the-duplicate-number/
 *
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at
 * least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 *
 * Example 1:
 *
 * Input: [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 *
 * Input: [3,1,3,4,2]
 * Output: 3
 *
 * Note:
 *
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 */
@RunWith(LeetCodeRunner.class)
public class Q287_FindTheDuplicateNumber {

    /**
     * 题目限制很多, 并且提示了时间复杂度为 O(NlongN), 根据网上的提示, 可以使用二分法.
     * 找出 [start, end] 的中间数字 middle, 找出小于这个数字的数量count, 如果 count >= middle, 则说明 [start, middle)
     * 区间存在重复数字, 否则重复数字在[middle, end] 之间.
     */
    @Answer
    public int findDuplicate(int[] nums) {
        int start = 1, end = nums.length - 1;
        while (start < end) {
            int middle = (start + end + 1) / 2;
            int count = 0;
            for (int num : nums) {
                if (num < middle) {
                    count++;
                }
            }
            if (count >= middle) {
                end = middle - 1;
            } else {
                start = middle;
            }
        }
        return start;
    }

    /**
     * LeetCode 上最快的 O(n) 解法, 类似快慢指针寻找环的方式, 因为 [1, n] 之间肯定存在重复数字,
     * 所以数组元素之间的跳转肯定存在一个闭环, 所以题目就变成 {@link Q142_LinkedListCycleII} 中的寻找环的题目了
     */
    @Answer
    public int findDuplicate_ON(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        int head = 0;
        do {
            slow = nums[slow];
            head = nums[head];
        } while (slow != head);
        return slow;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 4, 2, 2}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 1, 3, 4, 2}).expect(3);

}
