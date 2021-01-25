package q1600;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1585. Check If String Is Transformable With Substring Sort Operations
 * https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/
 *
 * Given two strings s and t, you want to transform string s into string t using the following operation any number of
 * times:
 *
 * Choose a non-empty substring in s and sort it in-place so the characters are in ascending order.
 *
 * For example, applying the operation on the underlined substring in "14234" results in "12344".
 *
 * Return true if it is possible to transform string s into string t. Otherwise, return false.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "84532", t = "34852"
 * Output: true
 * Explanation: You can transform s into t using the following sort operations:
 * "84532" (from index 2 to 3) -> "84352"
 * "84352" (from index 0 to 2) -> "34852"
 *
 * Example 2:
 *
 * Input: s = "34521", t = "23415"
 * Output: true
 * Explanation: You can transform s into t using the following sort operations:
 * "34521" -> "23451"
 * "23451" -> "23415"
 *
 * Example 3:
 *
 * Input: s = "12345", t = "12435"
 * Output: false
 *
 * Example 4:
 *
 * Input: s = "1", t = "2"
 * Output: false
 *
 * Constraints:
 *
 * s.length == t.length
 * 1 <= s.length <= 10^5
 * s and t only contain digits from '0' to '9'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1585_CheckIfStringIsTransformableWithSubstringSortOperations {

    @Answer
    public boolean isTransformable(String s, String t) {
        final int n = s.length();
        final char[] sc = s.toCharArray();
        final char[] tc = t.toCharArray();

        // 检查是否存在大数字提前的情况
        int[] counts = new int[10];
        for (int i = 0; i < n; i++) {
            counts[sc[i] - '0']++;
            counts[tc[i] - '0']--;
            for (int sum = 0, v = 9; v > 0; v--) {
                sum += counts[v];
                if (sum < 0) {
                    return false;
                }
            }
        }

        // 检查字符数是否相等
        for (int i = 0; i < 10; i++) {
            if (counts[i] != 0) {
                return false;
            }
        }

        // 检查大数字在小数字前面的情况的次数
        // 对于t 中大数字在小数字前的情况, 在s 中必须有对应
        int[][] prevs = new int[10][10];
        for (int i = 0; i < n; i++) {
            int v = tc[i] - '0';
            counts[v]++;
            for (int j = v + 1; j < 10; j++) {
                prevs[v][j] += counts[j];
            }
        }
        Arrays.fill(counts, 0);
        for (int i = 0; i < n; i++) {
            int v = sc[i] - '0';
            counts[v]++;
            for (int j = v + 1; j < 10; j++) {
                prevs[v][j] -= counts[j];
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (prevs[i][j] > 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * LeetCode 中比较简单的方式.
     */
    @Answer
    public boolean isTransformable2(String s, String t) {
        final int n = t.length();
        // 保存s 中每个数字对应的坐标
        Queue<Integer>[] p = new Queue[10];
        for (int i = 0; i < 10; i++) {
            p[i] = new LinkedList<>();
        }
        for (int i = 0; i < n; i++) {
            p[s.charAt(i) - '0'].offer(i);
        }

        // 遍历t 中的数字
        for (int i = 0; i < n; i++) {
            int v = t.charAt(i) - '0';
            if (p[v].isEmpty()) {
                // s 与t 中数字不对等
                return false;
            }
            int si = p[v].poll();
            for (int j = 0; j < v; j++) {
                if (!p[j].isEmpty() && p[j].peek() < si) {
                    // 在s 中存在比v 更小的数字 (也就是说在t 中大数字被提前了)
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("84532", "34852").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("34521", "23415").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("12345", "12435").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("1", "2").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("01101", "10011").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("891", "198").expect(false);

}
