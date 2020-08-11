package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 922. Sort Array By Parity II
 * https://leetcode.com/problems/sort-array-by-parity-ii/
 *
 * Given an array A of non-negative integers, half of the integers in A are odd, and half of the integers are even.
 *
 * Sort the array so that whenever A[i] is odd, i is odd; and whenever A[i] is even, i is even.
 *
 * You may return any answer array that satisfies this condition.
 *
 * Example 1:
 *
 * Input: [4,2,5,7]
 * Output: [4,5,2,7]
 * Explanation: [4,7,2,5], [2,5,4,7], [2,7,4,5] would also have been accepted.
 *
 * Note:
 *
 * 2 <= A.length <= 20000
 * A.length % 2 == 0
 * 0 <= A[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q922_SortArrayByParityII {

    @Answer
    public int[] sortArrayByParityII(int[] A) {
        int even = 0, odd = 1;
        for (int i = 0; i < A.length; i++) {
            while (A[i] % 2 != i % 2) {
                int idx;
                if (A[i] % 2 == 0) {
                    idx = even;
                    even += 2;
                } else {
                    idx = odd;
                    odd += 2;
                }
                int t = A[i];
                A[i] = A[idx];
                A[idx] = t;
            }
        }
        return A;
    }

    // 参考LeetCode 解答, 针对上面的改进, 把奇数位的偶数放到偶数下标
    @Answer
    public int[] sortArrayByParityII2(int[] A) {
        int even = 0;
        for (int i = 1; i < A.length; i += 2) {
            if (A[i] % 2 == 0) {
                while (A[even] % 2 == 0) {
                    even += 2;
                }
                int t = A[i];
                A[i] = A[even];
                A[even] = t;
            }
        }
        return A;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{4, 2, 5, 7})
            .expect(new int[]{4, 5, 2, 7})
            .orExpect(new int[]{4, 7, 2, 5})
            .orExpect(new int[]{2, 5, 4, 7})
            .orExpect(new int[]{2, 7, 4, 5})
            .build();

}
