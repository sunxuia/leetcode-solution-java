package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1017. Convert to Base -2
 * https://leetcode.com/problems/convert-to-base-2/
 *
 * Given a number N, return a string consisting of "0"s and "1"s that represents its value in base -2 (negative two).
 *
 * The returned string must have no leading zeroes, unless the string is "0".
 *
 * Example 1:
 *
 * Input: 2
 * Output: "110"
 * Explantion: (-2) ^ 2 + (-2) ^ 1 = 2
 *
 * Example 2:
 *
 * Input: 3
 * Output: "111"
 * Explantion: (-2) ^ 2 + (-2) ^ 1 + (-2) ^ 0 = 3
 *
 * Example 3:
 *
 * Input: 4
 * Output: "100"
 * Explantion: (-2) ^ 2 = 4
 *
 * Note:
 *
 * 0 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1017_ConvertToBase2 {

    @Answer
    public String baseNeg2(int N) {
        int pow2 = 1, posSum = 1, negSum = 0;
        while (posSum < N) {
            pow2 *= 4;
            posSum += pow2;
            negSum -= pow2 / 2;
        }

        StringBuilder sb = new StringBuilder();
        while (pow2 != 0) {
            if (pow2 > 0) {
                // 判断 N 是否大于最大可表示的数字
                posSum -= pow2;
                if (N > posSum) {
                    sb.append('1');
                    N -= pow2;
                } else {
                    sb.append('0');
                }
            } else {
                // 判断 N 是否小于最小可表示的数字
                negSum -= pow2;
                if (N < negSum) {
                    sb.append('1');
                    N -= pow2;
                } else {
                    sb.append('0');
                }
            }
            pow2 /= -2;
        }
        return sb.toString();
    }

    /**
     * LeetCode 上的解答
     */
    @Answer
    public String baseNeg2_2(int N) {
        StringBuilder sb = new StringBuilder();
        while (N != 0) {
            sb.append(N & 1);
            N = -(N >> 1);
        }
        return sb.length() > 0 ? sb.reverse().toString() : "0";
    }


    @TestData
    public DataExpectation val0 = DataExpectation.create(0).expect("0");

    @TestData
    public DataExpectation val1 = DataExpectation.create(1).expect("1");

    @TestData
    public DataExpectation val2 = DataExpectation.create(2).expect("110");

    @TestData
    public DataExpectation val3 = DataExpectation.create(3).expect("111");

    @TestData
    public DataExpectation val4 = DataExpectation.create(4).expect("100");

    @TestData
    public DataExpectation val6 = DataExpectation.create(6).expect("11010");

    @TestData
    public DataExpectation val9 = DataExpectation.create(9).expect("11001");

}
