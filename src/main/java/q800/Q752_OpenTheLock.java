package q800;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/open-the-lock/
 *
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5',
 * '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to
 * be '9'. Each move consists of turning one wheel one slot.
 *
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 *
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the
 * lock will stop turning and you will be unable to open it.
 *
 * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of
 * turns required to open the lock, or -1 if it is impossible.
 *
 * Example 1:
 *
 * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * Output: 6
 * Explanation:
 * A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
 * Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
 * because the wheels of the lock become stuck after the display becomes the dead end "0102".
 *
 * Example 2:
 *
 * Input: deadends = ["8888"], target = "0009"
 * Output: 1
 * Explanation:
 * We can turn the last wheel in reverse to move from "0000" -> "0009".
 *
 * Example 3:
 *
 * Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * Output: -1
 * Explanation:
 * We can't reach the target without getting stuck.
 *
 * Example 4:
 *
 * Input: deadends = ["0000"], target = "8888"
 * Output: -1
 *
 * Note:
 *
 * 1. The length of deadends will be in the range [1, 500].
 * 2. target will not be in the list deadends.
 * 3. Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities
 * '0000' to '9999'.
 */
@RunWith(LeetCodeRunner.class)
public class Q752_OpenTheLock {

    /**
     * 根据题目中的提示得到解答思路:
     * 这题可以认为是1000 个点('0000' - '9999') 的联通性问题, 通过翻转一位就能到达的不同点视为联通,
     * 这样就可以从 '0000' 点出发, 并避开deadends 中排除的点, 到达目标点.
     *
     * 下面的就是简单的BFS 方式的解答.
     */
    @Answer
    public int openLock(String[] deadends, String target) {
        Set<Integer> exclude = new HashSet<>();
        for (String deadend : deadends) {
            exclude.add(getNumber(deadend));
        }
        int expect = getNumber(target);

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        int res = 0;
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                int num = queue.poll();
                if (exclude.add(num)) {
                    if (num == expect) {
                        return res;
                    }
                    for (int d = 1; d <= 1000; d *= 10) {
                        // 某位+1
                        queue.add(num + ((num / d + 1) % 10 - num / d % 10) * d);
                        // 某位-1
                        queue.add(num + ((num / d + 9) % 10 - num / d % 10) * d);
                    }
                }
            }
            res++;
        }
        return -1;
    }

    // 将点转换为对应数字
    private int getNumber(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            res = res * 10 + str.charAt(i) - '0';
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"0201", "0101", "0102", "1212", "2002"}, "0202")
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"8888"}, "0009")
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"}, "8888")
            .expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{"0000"}, "8888")
            .expect(-1);

}
