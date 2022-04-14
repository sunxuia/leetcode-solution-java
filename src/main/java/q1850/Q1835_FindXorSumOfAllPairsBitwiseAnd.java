package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1835. Find XOR Sum of All Pairs Bitwise AND
 * https://leetcode.com/problems/find-xor-sum-of-all-pairs-bitwise-and/
 *
 * The XOR sum of a list is the bitwise XOR of all its elements. If the list only contains one element, then its XOR sum
 * will be equal to this element.
 *
 * For example, the XOR sum of [1,2,3,4] is equal to 1 XOR 2 XOR 3 XOR 4 = 4, and the XOR sum of [3] is equal to 3.
 *
 * You are given two 0-indexed arrays arr1 and arr2 that consist only of non-negative integers.
 *
 * Consider the list containing the result of arr1[i] AND arr2[j] (bitwise AND) for every (i, j) pair where
 * 0 <= i < arr1.length and 0 <= j < arr2.length.
 *
 * Return the XOR sum of the aforementioned list.
 *
 * Example 1:
 *
 * Input: arr1 = [1,2,3], arr2 = [6,5]
 * Output: 0
 * Explanation: The list = [1 AND 6, 1 AND 5, 2 AND 6, 2 AND 5, 3 AND 6, 3 AND 5] = [0,1,2,0,2,1].
 * The XOR sum = 0 XOR 1 XOR 2 XOR 0 XOR 2 XOR 1 = 0.
 *
 * Example 2:
 *
 * Input: arr1 = [12], arr2 = [4]
 * Output: 4
 * Explanation: The list = [12 AND 4] = [4]. The XOR sum = 4.
 *
 * Constraints:
 *
 * 1 <= arr1.length, arr2.length <= 10^5
 * 0 <= arr1[i], arr2[j] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1835_FindXorSumOfAllPairsBitwiseAnd {

    /**
     * 找规律的题.
     * 比如 [a, b, c] AND [x, y] 的结果是 ax^bx^cx ^ ay^by^cy,
     * x 和y 就像筛子一样会筛掉对应位数的值(固定为0), 则方程就可以简化为 (a^b^c)x ^ (a^b^c)y
     */
    @Answer
    public int getXORSum(int[] arr1, int[] arr2) {
        int xor1 = 0;
        for (int num : arr1) {
            xor1 ^= num;
        }
        int res = 0;
        for (int num : arr2) {
            res ^= xor1 & num;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3}, new int[]{6, 5}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{12}, new int[]{4}).expect(4);

}
