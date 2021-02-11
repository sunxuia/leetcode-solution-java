package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1734. Decode XORed Permutation
 * https://leetcode.com/problems/decode-xored-permutation/
 *
 * There is an integer array perm that is a permutation of the first n positive integers, where n is always odd.
 *
 * It was encoded into another integer array encoded of length n - 1, such that encoded[i] = perm[i] XOR perm[i + 1].
 * For example, if perm = [1,3,2], then encoded = [2,1].
 *
 * Given the encoded array, return the original array perm. It is guaranteed that the answer exists and is unique.
 *
 * Example 1:
 *
 * Input: encoded = [3,1]
 * Output: [1,2,3]
 * Explanation: If perm = [1,2,3], then encoded = [1 XOR 2,2 XOR 3] = [3,1]
 *
 * Example 2:
 *
 * Input: encoded = [6,5,4,6]
 * Output: [2,4,1,5,3]
 *
 * Constraints:
 *
 * 3 <= n < 10^5
 * n is odd.
 * encoded.length == n - 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1734_DecodeXoredPermutation {

    /**
     * 根据 hint 中的提示, 得出如下结果.
     */
    @Answer
    public int[] decode(int[] encoded) {
        final int n = encoded.length + 1;

        // 求出所有数字的 xor 结果
        int x = 0;
        for (int i = 1; i <= n; i++) {
            x ^= i;
        }
        int[] res = new int[n];

        // 第一个值 v0 = x ^ v1 ^ v2 ^ v3 ... vn 得到
        res[0] = x;
        for (int i = 1; i < n - 1; i += 2) {
            res[0] ^= encoded[i];
        }

        // 由第一个值还原出结果
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] ^ encoded[i - 1];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 1}).expect(new int[]{1, 2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{6, 5, 4, 6}).expect(new int[]{2, 4, 1, 5, 3});

}
