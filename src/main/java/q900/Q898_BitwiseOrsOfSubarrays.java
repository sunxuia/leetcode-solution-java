package q900;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 898. Bitwise ORs of Subarrays
 * https://leetcode.com/problems/bitwise-ors-of-subarrays/
 *
 * We have an array A of non-negative integers.
 *
 * For every (contiguous) subarray B = [A[i], A[i+1], ..., A[j]] (with i <= j), we take the bitwise OR of all the
 * elements in B, obtaining a result A[i] | A[i+1] | ... | A[j].
 *
 * Return the number of possible results.  (Results that occur more than once are only counted once in the final
 * answer.)
 *
 * Example 1:
 *
 * Input: [0]
 * Output: 1
 * Explanation:
 * There is only one possible result: 0.
 *
 * Example 2:
 *
 * Input: [1,1,2]
 * Output: 3
 * Explanation:
 * The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
 * These yield the results 1, 1, 2, 1, 3, 3.
 * There are 3 unique values, so the answer is 3.
 *
 * Example 3:
 *
 * Input: [1,2,4]
 * Output: 6
 * Explanation:
 * The possible results are 1, 2, 3, 4, 6, and 7.
 *
 * Note:
 *
 * 1 <= A.length <= 50000
 * 0 <= A[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q898_BitwiseOrsOfSubarrays {

    // 符合题意的暴力算法, 这个会超时
//    @Answer
    public int subarrayBitwiseORs_BF(int[] A) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < A.length; i++) {
            int mask = 0;
            for (int j = i; j < A.length; j++) {
                mask |= A[j];
                set.add(mask);
            }
        }
        return set.size();
    }

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/10982534.html
     */
    @Answer
    public int subarrayBitwiseORs(int[] A) {
        Set<Integer> res = new HashSet<>();
        Set<Integer>[] temp = new Set[]{new HashSet<Integer>(), new HashSet<Integer>()};
        for (int i = 0; i < A.length; i++) {
            temp[i % 2].clear();
            temp[i % 2].add(A[i]);
            for (Integer val : temp[(i + 1) % 2]) {
                temp[i % 2].add(A[i] | val);
            }
            res.addAll(temp[i % 2]);
        }
        return res.size();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 2}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 4}).expect(6);

    // 5万的数组
    @TestData
    public DataExpectation overTime = DataExpectation.create(TestDataFileHelper.read(int[].class)).expect(242844);

}
