package q700;

import java.util.HashMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/split-array-into-consecutive-subsequences/
 *
 * Given an array nums sorted in ascending order, return true if and only if you can split it into 1 or more
 * subsequences such that each subsequence consists of consecutive integers and has length at least 3.
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,3,3,4,5]
 * Output: True
 * Explanation:
 * You can split them into two consecutive subsequences :
 * 1, 2, 3
 * 3, 4, 5
 *
 * Example 2:
 *
 * Input: [1,2,3,3,4,4,5,5]
 * Output: True
 * Explanation:
 * You can split them into two consecutive subsequences :
 * 1, 2, 3, 4, 5
 * 3, 4, 5
 *
 * Example 3:
 *
 * Input: [1,2,3,4,4,5]
 * Output: False
 *
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q659_SplitArrayIntoConsecutiveSubsequences {

    // Solution 中给出的贪婪匹配方法如下
    @Answer
    public boolean isPossible(int[] nums) {
        Counter count = new Counter();
        Counter tails = new Counter();
        for (int num : nums) {
            count.increase(num);
        }

        for (int num : nums) {
            if (!count.has(num)) {
                continue;
            } else if (tails.has(num)) {
                tails.minus(num);
                tails.increase(num + 1);
            } else if (count.has(num + 1) && count.has(num + 2)) {
                count.minus(num + 1);
                count.minus(num + 2);
                tails.increase(num + 3);
            } else {
                return false;
            }
            count.minus(num);
        }
        return true;
    }

    private static class Counter extends HashMap<Integer, Integer> {

        public boolean has(int k) {
            Integer res = super.get(k);
            return res != null && res > 0;
        }

        public void increase(int k) {
            put(k, getOrDefault(k, 0) + 1);
        }

        public void minus(int k) {
            put(k, getOrDefault(k, 0) - 1);
        }
    }

    // LeetCode 上比较快的解法
    @Answer
    public boolean isPossible2(int[] nums) {
        int preCL1 = 0, preCL2 = 0, preCL3 = 0;
        // 之前3 个连续数字的次数
        int currCL1, currCL2, currCL3;
        int preNum = Integer.MIN_VALUE, currNum;

        int i = 0;
        while (i < nums.length) {
            // 获得当前数字(currNum) 和数量(count)
            int count = 0;
            for (currNum = nums[i]; i < nums.length && currNum == nums[i]; i++) {
                count++;
            }

            // 是否和前一个数字连续
            if (preNum + 1 == currNum) {
                if (count < preCL1 + preCL2) {
                    return false;
                }
                currCL2 = preCL1;
                currCL3 = preCL2;

                int residual = count - preCL1 - preCL2;
                int minNumToExtend = Math.min(residual, preCL3);
                currCL3 += minNumToExtend;
                currCL1 = Math.max(0, residual - minNumToExtend);
            } else {
                if (preCL1 != 0 || preCL2 != 0) {
                    return false;
                }
                currCL1 = count;
                currCL3 = currCL2 = 0;
            }
            preNum = currNum;
            preCL1 = currCL1;
            preCL2 = currCL2;
            preCL3 = currCL3;
        }

        return preCL1 == 0 && preCL2 == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 3, 4, 5}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 3, 4, 4, 5, 5}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 4, 5}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 5, 5, 5, 6, 7, 8, 8, 8, 9}).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            9, 10, 11, 12, 13, 14, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 41, 42, 42, 43, 44, 45, 46, 47,
            47, 48, 48, 49, 49, 50, 50, 51, 51, 51, 52, 52, 52, 53, 53, 53, 54, 54, 54, 55, 55, 55, 56, 56, 56, 57, 57,
            57, 58, 58, 58, 59, 59, 59, 59, 60, 60, 60, 60, 61, 61, 61, 61, 62, 62, 62, 62, 63, 63, 63, 63, 64, 64, 64,
            64, 65, 65, 65, 65, 66, 66, 66, 66, 67, 67, 67, 67, 68, 68, 68, 68, 69, 69, 69, 69, 70, 70, 70, 70, 71, 71,
            71, 71, 72, 72, 72, 72, 73, 73, 73, 73, 74, 74, 74, 74, 75, 75, 75, 75, 76, 76, 76, 76, 76, 77, 77, 77, 77,
            77, 78, 78, 78, 78, 78, 79, 79, 79, 79, 80, 80, 80, 80, 81, 81, 81, 81, 82, 82, 82, 82, 83, 83, 83, 83, 84,
            84, 84, 84, 85, 85, 85, 85, 85, 86, 86, 86, 86, 86, 86, 87, 87, 87, 87, 87, 87, 88, 88, 88, 88, 88, 88, 89,
            89, 89, 89, 89, 89, 90, 90, 90, 90, 90, 90, 91, 91, 91, 91, 91, 91, 92, 92, 92, 92, 92, 92, 93, 93, 93, 93,
            93, 93, 94, 94, 94, 94, 94, 94, 95, 95, 95, 95, 95, 95, 96, 96, 96, 96, 96, 96, 96, 97, 97, 97, 97, 97, 97,
            97, 98, 98, 98, 98, 98, 98, 98, 99, 99, 99, 99, 99, 99, 99, 100, 100, 100, 100, 100, 100, 100, 101, 101,
            101, 101, 101, 101, 101, 102, 102, 102, 102, 102, 102, 102, 103, 103, 103, 103, 103, 103, 103, 104, 104,
            104, 104, 104, 104, 104, 105, 105, 105, 105, 105, 105, 106, 106, 106, 106, 106, 106, 107, 107, 107, 107,
            107, 107, 108, 108, 108, 108, 108, 108, 109, 109, 109, 109, 109, 109, 110, 110, 110, 110, 110, 111, 111,
            111, 111, 111, 112, 112, 112, 112, 112, 113, 113, 113, 113, 113, 114, 114, 114, 114, 114, 115, 115, 115,
            115, 115, 116, 116, 116, 116, 116, 117, 117, 117, 117, 118, 118, 118, 118, 119, 119, 119, 119, 120, 120,
            120, 120, 121, 121, 121, 122, 122, 122, 123, 123, 123, 124, 124, 124, 125, 125, 125, 126, 126, 126, 127,
            127, 127, 128, 128, 128, 129, 129, 129, 130, 130, 130, 131, 131, 132, 132, 133, 133, 134, 134, 135, 135,
            136, 136, 137, 137, 138, 138, 139, 139, 140, 140, 141, 141, 142, 142, 143, 143, 144, 144, 145, 145, 146,
            146, 147
    }).expect(true);

}
