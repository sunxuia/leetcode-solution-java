package q1050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1018. Binary Prefix Divisible By 5
 * https://leetcode.com/problems/binary-prefix-divisible-by-5/
 *
 * Given an array A of 0s and 1s, consider N_i: the i-th subarray from A[0] to A[i] interpreted as a binary number (from
 * most-significant-bit to least-significant-bit.)
 *
 * Return a list of booleans answer, where answer[i] is true if and only if N_i is divisible by 5.
 *
 * Example 1:
 *
 * Input: [0,1,1]
 * Output: [true,false,false]
 * Explanation:
 * The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.  Only the first number is divisible by
 * 5, so answer[0] is true.
 *
 * Example 2:
 *
 * Input: [1,1,1]
 * Output: [false,false,false]
 *
 * Example 3:
 *
 * Input: [0,1,1,1,1,1]
 * Output: [true,false,false,false,true,false]
 *
 * Example 4:
 *
 * Input: [1,1,1,0,1]
 * Output: [false,false,false,false,false]
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * A[i] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1018_BinaryPrefixDivisibleBy5 {

    @Answer
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> res = new ArrayList<>(A.length);
        int num = 0;
        for (int a : A) {
            num = (num * 2 + a) % 5;
            res.add(num == 0);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 1})
            .expect(Arrays.asList(true, false, false));

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1})
            .expect(Arrays.asList(false, false, false));

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 1, 1, 1, 1, 1})
            .expect(Arrays.asList(true, false, false, false, true, false));

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 1, 1, 0, 1})
            .expect(Arrays.asList(false, false, false, false, false));

}
