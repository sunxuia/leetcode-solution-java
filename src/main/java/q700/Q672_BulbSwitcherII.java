package q700;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import q1400.Q1375_BulbSwitcherIII;
import q350.Q320_BulbSwitcher;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/bulb-switcher-ii/
 *
 * There is a room with n lights which are turned on initially and 4 buttons on the wall. After performing exactly m
 * unknown operations towards buttons, you need to return how many different kinds of status of the n lights could be.
 *
 * Suppose n lights are labeled as number [1, 2, 3 ..., n], function of these 4 buttons are given below:
 *
 * Flip all the lights.
 * Flip lights with even numbers.
 * Flip lights with odd numbers.
 * Flip lights with (3k + 1) numbers, k = 0, 1, 2, ...
 *
 *
 *
 * Example 1:
 *
 * Input: n = 1, m = 1.
 * Output: 2
 * Explanation: Status can be: [on], [off]
 *
 *
 *
 * Example 2:
 *
 * Input: n = 2, m = 1.
 * Output: 3
 * Explanation: Status can be: [on, off], [off, on], [off, off]
 *
 *
 *
 * Example 3:
 *
 * Input: n = 3, m = 1.
 * Output: 4
 * Explanation: Status can be: [off, on, off], [on, off, on], [off, off, off], [off, on, on].
 *
 *
 *
 * Note: n and m both fit in range [0, 1000].
 *
 * 上一题 {@link Q320_BulbSwitcher}
 * 下一题 {@link Q1375_BulbSwitcherIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q672_BulbSwitcherII {

    // 遍历所有可能的情况, 这种方式可以通过OJ 但是比较慢.
    @Answer
    public int flipLights(int n, int m) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append('1');
        }
        Set<String> set = new HashSet<>();
        dfs(sb, m, set, new HashSet<>());
        return set.size();
    }

    private void dfs(StringBuilder sb, int m, Set<String> res, Set<String> visited) {
        if (m == 0) {
            res.add(sb.toString());
            return;
        }
        // 因为开关灯最后只有有限的几种状态, 所以最后大多数都会在这里 return
        if (!visited.add(m + sb.toString())) {
            return;
        }

        doSwitch(sb, 0, 1);
        dfs(sb, m - 1, res, visited);
        doSwitch(sb, 0, 1);

        doSwitch(sb, 0, 2);
        dfs(sb, m - 1, res, visited);
        doSwitch(sb, 0, 2);

        doSwitch(sb, 1, 2);
        dfs(sb, m - 1, res, visited);
        doSwitch(sb, 1, 2);

        doSwitch(sb, 0, 3);
        dfs(sb, m - 1, res, visited);
        doSwitch(sb, 0, 3);
    }

    private void doSwitch(StringBuilder sb, int start, int interval) {
        for (int i = start; i < sb.length(); i += interval) {
            sb.setCharAt(i, (char) ((1 ^ (sb.charAt(i) - '0')) + '0'));
        }
    }

    /**
     * Solution 给出的解答.
     * 说明可以参考 https://www.cnblogs.com/grandyang/p/7595595.html
     */
    public int flipLights2(int n, int m) {
        Set<Integer> seen = new HashSet<>();
        n = Math.min(n, 6);
        int shift = Math.max(0, 6 - n);
        for (int cand = 0; cand < 16; cand++) {
            int bcount = Integer.bitCount(cand);
            if (bcount % 2 == m % 2 && bcount <= m) {
                int lights = 0;
                if ((cand & 1) > 0) {
                    lights ^= 0b111111 >> shift;
                }
                if (((cand >> 1) & 1) > 0) {
                    lights ^= 0b010101 >> shift;
                }
                if (((cand >> 2) & 1) > 0) {
                    lights ^= 0b101010 >> shift;
                }
                if (((cand >> 3) & 1) > 0) {
                    lights ^= 0b100100 >> shift;
                }
                seen.add(lights);
            }
        }
        return seen.size();
    }

    @Answer
    public int flipLights3(int n, int m) {
        n = Math.min(n, 3);
        if (m == 0) {
            return 1;
        }
        if (m == 1) {
            return n == 1 ? 2 : n == 2 ? 3 : 4;
        }
        if (m == 2) {
            return n == 1 ? 2 : n == 2 ? 4 : 7;
        }
        return n == 1 ? 2 : n == 2 ? 4 : 8;
    }


    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 1).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 1).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 1).expect(4);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(4, 100).expect(8);

}
