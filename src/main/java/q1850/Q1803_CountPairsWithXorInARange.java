package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1803. Count Pairs With XOR in a Range
 * https://leetcode.com/problems/count-pairs-with-xor-in-a-range/
 *
 * Given a (0-indexed) integer array nums and two integers low and high, return the number of nice pairs.
 *
 * A nice pair is a pair (i, j) where 0 <= i < j < nums.length and low <= (nums[i] XOR nums[j]) <= high.
 *
 * Example 1:
 *
 * Input: nums = [1,4,2,7], low = 2, high = 6
 * Output: 6
 * Explanation: All nice pairs (i, j) are as follows:
 * - (0, 1): nums[0] XOR nums[1] = 5
 * - (0, 2): nums[0] XOR nums[2] = 3
 * - (0, 3): nums[0] XOR nums[3] = 6
 * - (1, 2): nums[1] XOR nums[2] = 6
 * - (1, 3): nums[1] XOR nums[3] = 3
 * - (2, 3): nums[2] XOR nums[3] = 5
 *
 * Example 2:
 *
 * Input: nums = [9,8,4,2,1], low = 5, high = 14
 * Output: 8
 * Explanation: All nice pairs (i, j) are as follows:
 * - (0, 2): nums[0] XOR nums[2] = 13
 * - (0, 3): nums[0] XOR nums[3] = 11
 * - (0, 4): nums[0] XOR nums[4] = 8
 * - (1, 2): nums[1] XOR nums[2] = 12
 * - (1, 3): nums[1] XOR nums[3] = 10
 * - (1, 4): nums[1] XOR nums[4] = 9
 * - (2, 3): nums[2] XOR nums[3] = 6
 * - (2, 4): nums[2] XOR nums[4] = 5
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i] <= 2 * 10^4
 * 1 <= low <= high <= 2 * 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1803_CountPairsWithXorInARange {

    /**
     * 暴力算法会超时, hint 中提示使用字典树.
     */
    @Answer
    public int countPairs(int[] nums, int low, int high) {
        Tire tire = constructTire(nums);
        int res = 0;
        for (int num : nums) {
            res += countLess(tire, num, high, true)
                    - countLess(tire, num, low, false);
        }
        // 要求的配对中 (i, j) i < j, 因此要除以2
        return res / 2;
    }

    private Tire constructTire(int[] nums) {
        Tire root = new Tire();
        for (int num : nums) {
            Tire node = root;
            // 2_0000 的二进制位数最大就是14 (从0开始)
            for (int i = 14; i >= 0; i--) {
                int idx = num >> i & 1;
                if (node.next[idx] == null) {
                    node.next[idx] = new Tire();
                    node.next[idx].prev = node;
                }
                node = node.next[idx];
            }
            while (node != null) {
                node.count++;
                node = node.prev;
            }
        }
        return root;
    }

    /**
     * 字典树.
     * leetcode 上最快的解法对于这个对象可以优化为一个长数组 "arr[01分值选择的数值] = 数量" 这样.
     */
    private static class Tire {

        Tire[] next = new Tire[2];

        Tire prev;

        int count;

    }

    /**
     * 计算与num 的异或结果小于(或等于, 如果contain=true) high 的数字的数量.
     * v = num 在第i 位的值, h = high 在第i 位的值, 根据要求可以得出字典树选择的如下表格:
     * v   h |  0^v   1^v              字典树选择
     * ----------------------------------------------------------
     * 0   0 |   0     1    1^v > h, 不匹配; 0^v = h, 从0 继续筛选
     * 0   1 |   0     1    0^v < h, 全匹配; 1^v = h, 从1 继续筛选
     * 1   0 |   1     0    0^v > h, 不匹配; 1^v = h, 从1 继续筛选
     * 1   1 |   1     0    1^v < h, 全匹配; 0^v = h, 从0 继续筛选
     */
    private int countLess(Tire tire, int num, int high, boolean contain) {
        int res = 0;
        for (int i = 14; i >= 0 && tire != null; i--) {
            int v = num >> i & 1;
            int h = high >> i & 1;
            if (h == 0) {
                tire = tire.next[v];
            } else {
                if (tire.next[v] != null) {
                    res += tire.next[v].count;
                }
                tire = tire.next[1 - v];
            }
        }
        if (contain && tire != null) {
            res += tire.count;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 4, 2, 7}, 2, 6).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{9, 8, 4, 2, 1}, 5, 14).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{7881, 760, 709, 2937, 1245, 720, 5187, 6361, 3793, 141, 7238}, 1492, 3832)
            .expect(16);

}
