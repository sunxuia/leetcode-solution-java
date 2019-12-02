package q300;

import org.junit.runner.RunWith;
import q150.Q137_SingleNumberII;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/single-number-iii/
 *
 * Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear
 * exactly twice. Find the two elements that appear only once.
 *
 * Example:
 *
 * Input:  [1,2,1,3,2,5]
 * Output: [3,5]
 *
 * Note:
 *
 * The order of the result is not important. So in the above example, [5, 3] is also correct.
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?
 *
 * 相关题目: {@link Q137_SingleNumberII}
 */
@RunWith(LeetCodeRunner.class)
public class Q260_SingleNumberIII {

    // 网上的解法
    @Answer
    public int[] singleNumber(int[] nums) {
        // 第一遍遍历 :
        // 找出要找的2 个数字的异或结果
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // 获得最后一位的比特(0/1)
        diff &= -diff;

        int[] res = {0, 0};

        // 第二遍遍历 :
        // 将数组按照最后一位的比特位分为2 组.
        for (int num : nums) {
            if ((num & diff) == 0) {
                res[0] ^= num;
            } else {
                res[1] ^= num;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 1, 3, 2, 5})
            .expect(new int[]{3, 5})
            .unorderResult()
            .build();

}
