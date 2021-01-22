package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1551. Minimum Operations to Make Array Equal
 * https://leetcode.com/problems/minimum-operations-to-make-array-equal/
 *
 * You have an array arr of length n where arr[i] = (2 * i) + 1 for all valid values of i (i.e. 0 <= i < n).
 *
 * In one operation, you can select two indices x and y where 0 <= x, y < n and subtract 1 from arr[x] and add 1 to
 * arr[y] (i.e. perform arr[x] -=1 and arr[y] += 1). The goal is to make all the elements of the array equal. It is
 * guaranteed that all the elements of the array can be made equal using some operations.
 *
 * Given an integer n, the length of the array. Return the minimum number of operations needed to make all the elements
 * of arr equal.
 *
 * Example 1:
 *
 * Input: n = 3
 * Output: 2
 * Explanation: arr = [1, 3, 5]
 * First operation choose x = 2 and y = 0, this leads arr to be [2, 3, 4]
 * In the second operation choose x = 2 and y = 0 again, thus arr = [3, 3, 3].
 *
 * Example 2:
 *
 * Input: n = 6
 * Output: 9
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1551_MinimumOperationsToMakeArrayEqual {

    /**
     * 很无聊的求等差数列规律的题目.
     * 平均数就是 n, 变换方式就是前 n/2 个元素需要将值变为 n (后面的元素会对称变化),
     * 因此得公式 ∑(n-(2i-1)), i=[1, n/2] (为了简化公式, i 从 1 开始), 化简后得到如下结果.
     */
    @Answer
    public int minOperations(int n) {
        return n / 2 * ((n + 1) / 2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(6).expect(9);

}
