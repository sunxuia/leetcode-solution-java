package q1900;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import q1700.Q1696_JumpGameVI;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1871. Jump Game VII
 * https://leetcode.com/problems/jump-game-vii/
 *
 * You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning, you are standing at
 * index 0, which is equal to '0'. You can move from index i to index j if the following conditions are fulfilled:
 *
 * i + minJump <= j <= min(i + maxJump, s.length - 1), and
 * s[j] == '0'.
 *
 * Return true if you can reach index s.length - 1 in s, or false otherwise.
 *
 * Example 1:
 *
 * Input: s = "011010", minJump = 2, maxJump = 3
 * Output: true
 * Explanation:
 * In the first step, move from index 0 to index 3.
 * In the second step, move from index 3 to index 5.
 *
 * Example 2:
 *
 * Input: s = "01101110", minJump = 2, maxJump = 3
 * Output: false
 *
 * Constraints:
 *
 * 2 <= s.length <= 10^5
 * s[i] is either '0' or '1'.
 * s[0] == '0'
 * 1 <= minJump <= maxJump < s.length
 *
 * 上一题 {@link Q1696_JumpGameVI}
 */
@RunWith(LeetCodeRunner.class)
public class Q1871_JumpGameVII {

    @Answer
    public boolean canReach(String s, int minJump, int maxJump) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        if (cs[n - 1] != '0') {
            return false;
        }
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        for (int i = 1; i < n; i++) {
            if (cs[i] == '1') {
                continue;
            }
            while (!queue.isEmpty()
                    && queue.peek() < i - maxJump) {
                queue.poll();
            }
            if (queue.isEmpty()) {
                return false;
            }
            if (queue.peek() <= i - minJump) {
                queue.offer(i);
            }
        }
        return !queue.isEmpty() && queue.getLast() == n - 1;
    }

    /**
     * 仿照 leetcode 上比较快的做法, 与上面的做法类似, 将访问过的位标记为'x', 然后统计区间内的数量
     */
    @Answer
    public boolean canReach_leetcode(String s, int minJump, int maxJump) {
        char[] cs = s.toCharArray();
        cs[0] = 'x';
        for (int i = minJump, count = 0; i < cs.length; i++) {
            if (i - 1 - maxJump >= 0 && cs[i - 1 - maxJump] == 'x') {
                count--;
            }
            if (cs[i - minJump] == 'x') {
                count++;
            }
            if (cs[i] == '0' && count > 0) {
                cs[i] = 'x';
            }
        }
        return cs[cs.length - 1] == 'x';
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("011010", 2, 3).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("01101110", 2, 3).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("00111010", 3, 5).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("0000000000", 2, 5).expect(true);

    @TestData
    public DataExpectation overTime() {
        char[] cs = new char[10_0000];
        Arrays.fill(cs, 0, 5_0000, '0');
        Arrays.fill(cs, 5_0000, 9_9999, '1');
        cs[9_9999] = '0';
        return DataExpectation.createWith(new String(cs), 1, 50000).expect(true);
    }

}
