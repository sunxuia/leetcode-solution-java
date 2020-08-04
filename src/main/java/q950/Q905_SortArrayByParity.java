package q950;

import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 905. Sort Array By Parity
 * https://leetcode.com/problems/sort-array-by-parity/
 *
 * Given an array A of non-negative integers, return an array consisting of all the even elements of A, followed by all
 * the odd elements of A.
 *
 * You may return any answer array that satisfies this condition.
 *
 * Example 1:
 *
 * Input: [3,1,2,4]
 * Output: [2,4,3,1]
 * The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
 *
 * Note:
 *
 * 1 <= A.length <= 5000
 * 0 <= A[i] <= 5000
 */
@RunWith(LeetCodeRunner.class)
public class Q905_SortArrayByParity {

    @Answer
    public int[] sortArrayByParity(int[] A) {
        for (int i = 0, j = 0; i < A.length; i++) {
            if ((A[i] & 1) == 0) {
                int t = A[j];
                A[j++] = A[i];
                A[i] = t;
            }
        }
        return A;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{3, 1, 2, 4})
            .assertMethod(res -> {
                ObjectEqualsHelper helper = new ObjectEqualsHelper();
                helper.unorder("");
                AssertUtils.assertEquals(new int[]{2, 4}, (int[]) res, 0, 2, helper);
                AssertUtils.assertEquals(new int[]{0, 0, 1, 3}, (int[]) res, 2, 4, helper);
            })
            .build();

}
