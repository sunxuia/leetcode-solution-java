package q650;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/task-scheduler/
 *
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters
 * represent different tasks. Tasks could be done without original order. Each task could be done in one interval.
 * For each interval, CPU could finish one task or just be idle.
 *
 * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n
 * intervals that CPU are doing different tasks or just be idle.
 *
 * You need to return the least number of intervals the CPU will take to finish all the given tasks.
 *
 *
 *
 * Example:
 *
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 *
 *
 *
 * Constraints:
 *
 * The number of tasks is in the range [1, 10000].
 * The integer n is in the range [0, 100].
 */
@RunWith(LeetCodeRunner.class)
public class Q621_TaskScheduler {

    /**
     * 根据Solution 中的提示, 这题的解题思路在于优先分配数量最多的任务,
     * 这样其他任务插在最多的任务中间即可.
     */
    @Answer
    public int leastInterval(char[] tasks, int n) {
        int[] counts = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }

        List<Integer> scheduled = new ArrayList<>();
        for (int count : counts) {
            if (count > 0) {
                scheduled.add(count);
            }
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int total = 0;
        while (true) {
            pq.addAll(scheduled);
            scheduled.clear();
            int i = 0;
            while (i <= n && !pq.isEmpty()) {
                int val = pq.remove() - 1;
                if (val > 0) {
                    scheduled.add(val);
                }
                i++;
            }
            if (scheduled.isEmpty() && pq.isEmpty()) {
                return total + i;
            } else {
                total += n + 1;
            }
        }
    }

    // LeetCode 中最快的解法, 通过计算idle 的数量来计算结果.
    // 详情可以参阅Solution.
    @Answer
    public int leastInterval2(char[] tasks, int n) {
        int[] counts = new int[26];
        for (char c : tasks) {
            counts[c - 'A']++;
        }
        Arrays.sort(counts);
        int maxVal = counts[25] - 1, idleSlots = maxVal * n;
        for (int i = 24; i >= 0 && counts[i] > 0; i--) {
            // 减去空槽的数量
            idleSlots -= Math.min(counts[i], maxVal);
        }
        // 如果idleSlots > 0, 则说明 n+1 的区间内有空槽.
        // 如果idleSlots 都满了, 说明n+1 的区间内都填充满了, tasks 可以不带空隙地调度.
        return idleSlots > 0 ? idleSlots + tasks.length : tasks.length;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 2).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 0).expect(6);

    @TestData
    public DataExpectation normal2() {
        char[] arg = new char[260];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(arg, i * 10, i * 10 + 10, (char) (i + 'A'));
        }
        return DataExpectation.createWith(arg, 2).expect(260);
    }

}
