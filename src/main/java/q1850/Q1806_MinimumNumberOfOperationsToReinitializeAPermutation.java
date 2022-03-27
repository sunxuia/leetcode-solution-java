package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1806. Minimum Number of Operations to Reinitialize a Permutation
 * https://leetcode.com/problems/minimum-number-of-operations-to-reinitialize-a-permutation/
 *
 * You are given an even integer n. You initially have a permutation perm of size n where perm[i] == i (0-indexed).
 *
 * In one operation, you will create a new array arr, and for each i:
 *
 * If i % 2 == 0, then arr[i] = perm[i / 2].
 * If i % 2 == 1, then arr[i] = perm[n / 2 + (i - 1) / 2].
 *
 * You will then assign arr to perm.
 *
 * Return the minimum non-zero number of operations you need to perform on perm to return the permutation to its initial
 * value.
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: 1
 * Explanation: perm = [0,1] initially.
 * After the 1st operation, perm = [0,1]
 * So it takes only 1 operation.
 *
 * Example 2:
 *
 * Input: n = 4
 * Output: 2
 * Explanation: perm = [0,1,2,3] initially.
 * After the 1st operation, perm = [0,2,1,3]
 * After the 2nd operation, perm = [0,1,2,3]
 * So it takes only 2 operations.
 *
 * Example 3:
 *
 * Input: n = 6
 * Output: 4
 *
 * Constraints:
 *
 * 2 <= n <= 1000
 * n is even.
 */
@RunWith(LeetCodeRunner.class)
public class Q1806_MinimumNumberOfOperationsToReinitializeAPermutation {

    /**
     * 暴力算法
     */
    @Answer
    public int reinitializePermutation(int n) {
        int[][] nums = new int[2][n];
        for (int i = 0; i < n; i++) {
            nums[0][i] = i;
        }
        int res = 0;
        loop:
        while (true) {
            int from = res % 2, to = 1 - res % 2;
            res++;
            for (int i = 0; i < n; i += 2) {
                nums[to][i / 2] = nums[from][i];
            }
            for (int i = 1; i < n; i += 2) {
                nums[to][n / 2 + (i - 1) / 2] = nums[from][i];
            }
            for (int i = 0; i < n; i++) {
                if (nums[to][i] != i) {
                    continue loop;
                }
            }
            return res;
        }
    }

    /**
     * leetcode 上最快的算法
     * 就是算nums[1]=1 这个数字再跑到位置1 所需要的次数.
     */
    @Answer
    public int reinitializePermutation2(int n) {
        // 第1 次, nums[1] 将跑到 n / 2 + (pos - 1) / 2 = n/2 这个位置
        int res = 1, pos = n / 2;
        while (pos != 1) {
            if (pos % 2 == 0) {
                pos = pos / 2;
            } else {
                pos = n / 2 + (pos - 1) / 2;
            }
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(6).expect(4);

    @TestData
    public DataExpectation val8 = DataExpectation.create(8).expect(3);

    @TestData
    public DataExpectation val10 = DataExpectation.create(10).expect(6);

}
