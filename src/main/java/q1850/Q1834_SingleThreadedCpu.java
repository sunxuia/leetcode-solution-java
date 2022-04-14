package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1834. Single-Threaded CPU
 * https://leetcode.com/problems/single-threaded-cpu/
 *
 * You are given n tasks labeled from 0 to n - 1 represented by a 2D integer array tasks, where tasks[i] =
 * [enqueueTimei, processingTimei] means that the ith task will be available to process at enqueueTimei and
 * will take processingTimei to finish processing.
 *
 * You have a single-threaded CPU that can process at most one task at a time and will act in the following way:
 *
 * If the CPU is idle and there are no available tasks to process, the CPU remains idle.
 * If the CPU is idle and there are available tasks, the CPU will choose the one with the shortest processing time. If
 * multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
 * Once a task is started, the CPU will process the entire task without stopping.
 * The CPU can finish a task then start a new one instantly.
 *
 * Return the order in which the CPU will process the tasks.
 *
 * Example 1:
 *
 * Input: tasks = [[1,2],[2,4],[3,2],[4,1]]
 * Output: [0,2,3,1]
 * Explanation: The events go as follows:
 * - At time = 1, task 0 is available to process. Available tasks = {0}.
 * - Also at time = 1, the idle CPU starts processing task 0. Available tasks = {}.
 * - At time = 2, task 1 is available to process. Available tasks = {1}.
 * - At time = 3, task 2 is available to process. Available tasks = {1, 2}.
 * - Also at time = 3, the CPU finishes task 0 and starts processing task 2 as it is the shortest. Available tasks =
 * {1}.
 * - At time = 4, task 3 is available to process. Available tasks = {1, 3}.
 * - At time = 5, the CPU finishes task 2 and starts processing task 3 as it is the shortest. Available tasks = {1}.
 * - At time = 6, the CPU finishes task 3 and starts processing task 1. Available tasks = {}.
 * - At time = 10, the CPU finishes task 1 and becomes idle.
 *
 * Example 2:
 *
 * Input: tasks = [[7,10],[7,12],[7,5],[7,4],[7,2]]
 * Output: [4,3,2,0,1]
 * Explanation: The events go as follows:
 * - At time = 7, all the tasks become available. Available tasks = {0,1,2,3,4}.
 * - Also at time = 7, the idle CPU starts processing task 4. Available tasks = {0,1,2,3}.
 * - At time = 9, the CPU finishes task 4 and starts processing task 3. Available tasks = {0,1,2}.
 * - At time = 13, the CPU finishes task 3 and starts processing task 2. Available tasks = {0,1}.
 * - At time = 18, the CPU finishes task 2 and starts processing task 0. Available tasks = {1}.
 * - At time = 28, the CPU finishes task 0 and starts processing task 1. Available tasks = {}.
 * - At time = 40, the CPU finishes task 1 and becomes idle.
 *
 * Constraints:
 *
 * tasks.length == n
 * 1 <= n <= 10^5
 * 1 <= enqueueTimei, processingTimei <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1834_SingleThreadedCpu {

    @Answer
    public int[] getOrder(int[][] tasks) {
        final int n = tasks.length;
        Task[] ts = new Task[n];
        for (int i = 0; i < n; i++) {
            ts[i] = new Task();
            ts[i].index = i;
            ts[i].enqueueTime = tasks[i][0];
            ts[i].processingTime = tasks[i][1];
        }
        Arrays.sort(ts, Comparator.comparingInt(a -> a.enqueueTime));

        PriorityQueue<Task> pq = new PriorityQueue<>((a, b) ->
                a.processingTime == b.processingTime ? a.index - b.index : a.processingTime - b.processingTime);
        int[] res = new int[n];
        int idx = 0, endTime = 0, resLen = 0;
        while (idx < n || !pq.isEmpty()) {
            if (pq.isEmpty()) {
                endTime = Math.max(endTime, ts[idx].enqueueTime);
            }
            while (idx < n && ts[idx].enqueueTime <= endTime) {
                pq.offer(ts[idx++]);
            }
            Task execute = pq.poll();
            res[resLen++] = execute.index;
            endTime += execute.processingTime;
        }
        return res;
    }

    private static class Task {

        int index, enqueueTime, processingTime;

        @Override
        public String toString() {
            return index + " (" + processingTime + ")";
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 4}, {3, 2}, {4, 1}})
            .expect(new int[]{0, 2, 3, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{7, 10}, {7, 12}, {7, 5}, {7, 4}, {7, 2}})
            .expect(new int[]{4, 3, 2, 0, 1});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{19, 13}, {16, 9}, {21, 10}, {32, 25}, {37, 4}, {49, 24}, {2, 15}, {38, 41}, {37, 34},
                    {33, 6}, {45, 4}, {18, 18}, {46, 39}, {12, 24}})
            .expect(new int[]{6, 1, 2, 9, 4, 10, 0, 11, 5, 13, 3, 8, 12, 7});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{35, 36}, {11, 7}, {15, 47}, {34, 2}, {47, 19}, {16, 14}, {19, 8}, {7, 34}, {38, 15},
                    {16, 18}, {27, 22}, {7, 15}, {43, 2}, {10, 5}, {5, 4}, {3, 11}})
            .expect(new int[]{15, 14, 13, 1, 6, 3, 5, 12, 8, 11, 9, 4, 10, 7, 0, 2});

}
