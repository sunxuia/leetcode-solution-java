package q250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import q200.Q169_MajorityElement;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/majority-element-ii/
 *
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 *
 * Note: The algorithm should run in linear time and in O(1) space.
 *
 * Example 1:
 *
 * Input: [3,2,3]
 * Output: [3]
 *
 * Example 2:
 *
 * Input: [1,1,1,3,3,2,2,2]
 * Output: [1,2]
 *
 * 相关题目: {@link Q169_MajorityElement}
 */
@RunWith(LeetCodeRunner.class)
public class Q229_MajorityElementII {

    // 时间复杂度要求 O(n), 空间复杂度要求 O(1)
    // 这这些相关题目可以通过摩尔投票法来解决, 这里通过2 次遍历, 第一次找出最多的2 个数字, 第二次验证这2 个数字是否是超过 n/3 次.
    @Answer
    public List<Integer> majorityElement(int[] nums) {
        int a = 0, b = 0, count1 = 0, count2 = 0, n = nums.length;
        for (int num : nums) {
            if (num == a) {
                count1++;
            } else if (num == b) {
                count2++;
            } else if (count1 == 0) {
                a = num;
                count1 = 1;
            } else if (count2 == 0) {
                b = num;
                count2 = 1;
            } else {
                --count1;
                --count2;
            }
        }
        count1 = count2 = 0;
        for (int num : nums) {
            if (num == a) {
                count1++;
            } else if (num == b) {
                count2++;
            }
        }
        List<Integer> res = new ArrayList<>(2);
        if (count1 > nums.length / 3) {
            res.add(a);
        }
        if (count2 > nums.length / 3) {
            res.add(b);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{3, 2, 3})
            .expect(Collections.singleton(3));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 1, 1, 3, 3, 2, 2, 2})
            .expect(Arrays.asList(1, 2));

}
