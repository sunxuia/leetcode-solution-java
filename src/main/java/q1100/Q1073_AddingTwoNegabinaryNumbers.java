package q1100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import q1050.Q1017_ConvertToBase2;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1073. Adding Two Negabinary Numbers
 * https://leetcode.com/problems/adding-two-negabinary-numbers/
 *
 * Given two numbers arr1 and arr2 in base -2, return the result of adding them together.
 *
 * Each number is given in array format:  as an array of 0s and 1s, from most significant bit to least significant bit.
 * For example, arr = [1,1,0,1] represents the number (-2)^3 + (-2)^2 + (-2)^0 = -3.  A number arr in array format is
 * also guaranteed to have no leading zeros: either arr == [0] or arr[0] == 1.
 *
 * Return the result of adding arr1 and arr2 in the same format: as an array of 0s and 1s with no leading zeros.
 *
 * Example 1:
 *
 * Input: arr1 = [1,1,1,1,1], arr2 = [1,0,1]
 * Output: [1,0,0,0,0]
 * Explanation: arr1 represents 11, arr2 represents 5, the output represents 16.
 *
 * Note:
 *
 * 1 <= arr1.length <= 1000
 * 1 <= arr2.length <= 1000
 * arr1 and arr2 have no leading zeros
 * arr1[i] is 0 or 1
 * arr2[i] is 0 or 1
 *
 * 相关题目 {@link Q1017_ConvertToBase2}
 */
@RunWith(LeetCodeRunner.class)
public class Q1073_AddingTwoNegabinaryNumbers {

    @Answer
    public int[] addNegabinary(int[] arr1, int[] arr2) {
        final int m = arr1.length, n = arr2.length;
        final int max = Math.max(m, n);
        List<Integer> resList = new ArrayList<>(max + 2);
        for (int i = 0, carry = 0; i < max || carry != 0; i++) {
            int val1 = i < m ? arr1[m - 1 - i] : 0;
            int val2 = i < n ? arr2[n - 1 - i] : 0;
            int sum = val1 + val2 + carry;
            if (sum >= 0) {
                // sum = 0, 1, 2, 3
                resList.add(sum % 2);
                carry = sum / -2;
            } else {
                // sum = -1
                resList.add(1);
                carry = 1;
            }
        }
        // 去掉结果中的前导0
        while (resList.size() > 1 && resList.get(resList.size() - 1) == 0) {
            resList.remove(resList.size() - 1);
        }

        int[] res = new int[resList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = resList.get(resList.size() - 1 - i);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 1})
            .expect(new int[]{1, 0, 0, 0, 0});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1}, new int[]{1})
            .expect(new int[]{1, 1, 0});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1}, new int[]{1, 1})
            .expect(new int[]{0});

}
