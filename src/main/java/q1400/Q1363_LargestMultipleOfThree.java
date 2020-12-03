package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1363. Largest Multiple of Three
 * https://leetcode.com/problems/largest-multiple-of-three/
 *
 * Given an integer array of digits, return the largest multiple of three that can be formed by concatenating some of
 * the given digits in any order.
 *
 * Since the answer may not fit in an integer data type, return the answer as a string.
 *
 * If there is no answer return an empty string.
 *
 * Example 1:
 *
 * Input: digits = [8,1,9]
 * Output: "981"
 *
 * Example 2:
 *
 * Input: digits = [8,6,7,1,0]
 * Output: "8760"
 *
 * Example 3:
 *
 * Input: digits = [1]
 * Output: ""
 *
 * Example 4:
 *
 * Input: digits = [0,0,0,0,0,0]
 * Output: "0"
 *
 * Constraints:
 *
 * 1 <= digits.length <= 10^4
 * 0 <= digits[i] <= 9
 * The returning answer must not contain unnecessary leading zeros.
 */
@RunWith(LeetCodeRunner.class)
public class Q1363_LargestMultipleOfThree {

    /**
     * 找数学规律的题目.
     * 要是3 的倍数则位数之和也需要是3 的倍数, 如果不是则最多减掉2 位, 否则无法组成3 的倍数.
     */
    @Answer
    public String largestMultipleOfThree(int[] digits) {
        int n = digits.length;
        int[] counts = new int[10];
        int sum = 0;
        for (int digit : digits) {
            counts[digit]++;
            sum += digit;
        }
        if (sum % 3 != 0) {
            // 减去1 位
            for (int i = 1; i < 10; i++) {
                if (counts[i] > 0 && (sum - i) % 3 == 0) {
                    counts[i]--;
                    sum -= i;
                    n -= 1;
                    break;
                }
            }
        }
        if (sum % 3 != 0) {
            // 减去2 位
            for (int i = 1; i < 10; i++) {
                if (counts[i] == 0) {
                    continue;
                }
                for (int j = i; j < 10; j++) {
                    if ((counts[j] > 1 || j != i && counts[j] > 0)
                            && (sum - i - j) % 3 == 0) {
                        counts[i]--;
                        counts[j]--;
                        sum -= i + j;
                        n -= 2;
                        break;
                    }
                }
            }
        }
        if (sum % 3 != 0 || n == 0) {
            return "";
        }
        if (sum == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder(n);
        for (int i = 9; i >= 0; i--) {
            while (counts[i] > 0) {
                sb.append(i);
                counts[i]--;
            }
        }
        return sb.toString();
    }

    /**
     * leetcode 上的另一种解法, 与上面解法思路类似.
     */
    @Answer
    public String largestMultipleOfThree2(int[] digits) {
        int[] buckets = new int[10];
        for (int d : digits) {
            buckets[d]++;
        }
        // 计算超出1 或2 的数量, 然后减去.
        int one = buckets[1] + buckets[4] + buckets[7];
        int two = buckets[2] + buckets[5] + buckets[8];
        int remainder = (one + two * 2) % 3;
        if (remainder == 1) {
            if (one >= 1) {
                // 减去1 位数
                removeDigits(buckets, 1, 1);
            } else {
                // 减去2 位数
                removeDigits(buckets, 2, 2);
            }
        } else if (remainder == 2) {
            if (two >= 1) {
                removeDigits(buckets, 2, 1);
            } else {
                removeDigits(buckets, 1, 2);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            while (buckets[i]-- > 0) {
                sb.append(i);
            }
        }
        if (sb.length() > 0 && sb.charAt(0) == '0') {
            return "0";
        }
        return sb.toString();
    }

    // 减去 (1, 4, 7) 或 (2, 5, 8) 的数量.
    private void removeDigits(int[] buckets, int digit, int cnt) {
        while (cnt > 0) {
            if (buckets[digit] > 0) {
                buckets[digit]--;
                cnt--;
            } else {
                digit += 3;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{8, 1, 9}).expect("981");

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{8, 6, 7, 1, 0}).expect("8760");

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1}).expect("");

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{0, 0, 0, 0, 0, 0}).expect("0");

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{5, 8}).expect("");

}
