package q850;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/split-array-into-fibonacci-sequence/
 *
 * Given a string S of digits, such as S = "123456579", we can split it into a Fibonacci-like sequence [123, 456, 579].
 *
 * Formally, a Fibonacci-like sequence is a list F of non-negative integers such that:
 *
 * 0 <= F[i] <= 2^31 - 1, (that is, each integer fits a 32-bit signed integer type);
 * F.length >= 3;
 * and F[i] + F[i+1] = F[i+2] for all 0 <= i < F.length - 2.
 *
 * Also, note that when splitting the string into pieces, each piece must not have extra leading zeroes, except if
 * the piece is the number 0 itself.
 *
 * Return any Fibonacci-like sequence split from S, or return [] if it cannot be done.
 *
 * Example 1:
 *
 * Input: "123456579"
 * Output: [123,456,579]
 *
 * Example 2:
 *
 * Input: "11235813"
 * Output: [1,1,2,3,5,8,13]
 *
 * Example 3:
 *
 * Input: "112358130"
 * Output: []
 * Explanation: The task is impossible.
 *
 * Example 4:
 *
 * Input: "0123"
 * Output: []
 * Explanation: Leading zeroes are not allowed, so "01", "2", "3" is not valid.
 *
 * Example 5:
 *
 * Input: "1101111"
 * Output: [110, 1, 111]
 * Explanation: The output [11, 0, 11, 11] would also be accepted.
 *
 * Note:
 *
 * 1 <= S.length <= 200
 * S contains only digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q842_SplitArrayIntoFibonacciSequence {

    // 很无聊但是却挺花时间的一题
    @Answer
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> res = new ArrayList<>();
        // Integer.MAX_VALUE 是10 位数字
        int iLimit = S.charAt(0) == '0' ? 2 : Math.min(S.length() - 1, 11);
        for (int i = 1; i < iLimit; i++) {
            int jLimit = S.charAt(i) == '0' ? i + 2 : S.length();
            for (int j = i + 1; j < jLimit; j++) {
                long val1 = Long.parseLong(S.substring(0, i));
                long val2 = Long.parseLong(S.substring(i, j));
                if (val1 + val2 > Integer.MAX_VALUE) {
                    break;
                }

                res.clear();
                res.add((int) val1);
                int k = i;
                String str = String.valueOf(val2);
                while (val2 <= Integer.MAX_VALUE && S.startsWith(str, k)) {
                    res.add((int) val2);
                    val2 = val1 + val2;
                    val1 = val2 - val1;
                    k += str.length();
                    str = String.valueOf(val2);
                }
                if (k == S.length()) {
                    return res;
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * LeetCode 上比较快的DFS 解法
     */
    @Answer
    public List<Integer> splitIntoFibonacci2(String S) {
        char[] sc = S.toCharArray();
        List<Integer> res = new ArrayList<>();
        helper(sc, 0, res);
        return res;
    }

    private boolean helper(char[] sc, int pos, List<Integer> list) {
        if (pos == sc.length) {
            if (list.size() > 2) {
                return true;
            }
            return false;
        }

        int num = 0;
        for (int i = pos; i < sc.length; i++) {
            num = num * 10 + sc[i] - '0';

            if (num < 0) {
                return false;
            }

            int len = list.size();
            if (len < 2 || list.get(len - 1) + list.get(len - 2) == num) {
                list.add(num);
                if (helper(sc, i + 1, list)) {
                    return true;
                }
                list.remove(len - 1);
            }

            if (i == pos && sc[i] == '0') {
                return false;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("123456579").expect(new int[]{123, 456, 579});

    @TestData
    public DataExpectation example2 = DataExpectation.create("11235813").expect(new int[]{1, 1, 2, 3, 5, 8, 13});

    @TestData
    public DataExpectation example3 = DataExpectation.create("112358130").expect(new int[0]);

    @TestData
    public DataExpectation example4 = DataExpectation.create("0123").expect(new int[0]);

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("1101111")
            .expect(new int[]{110, 1, 111})
            .orExpect(new int[]{11, 0, 11, 11})
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1011").expect(new int[]{1, 0, 1, 1});

    @TestData
    public DataExpectation normal2 = DataExpectation.create("0112").expect(new int[]{0, 1, 1, 2});

}
