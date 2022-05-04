package q1900;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1882. Process Tasks Using Servers
 * https://leetcode.com/problems/process-tasks-using-servers/
 *
 * You are given two 0-indexed integer arrays servers and tasks of lengths n and m respectively. servers[i] is the
 * weight of the ith server, and tasks[j] is the time needed to process the jth task in seconds.
 *
 * Tasks are assigned to the servers using a task queue. Initially, all servers are free, and the queue is empty.
 *
 * At second j, the jth task is inserted into the queue (starting with the 0th task being inserted at second 0). As long
 * as there are free servers and the queue is not empty, the task in the front of the queue will be assigned to a free
 * server with the smallest weight, and in case of a tie, it is assigned to a free server with the smallest index.
 *
 * If there are no free servers and the queue is not empty, we wait until a server becomes free and immediately assign
 * the next task. If multiple servers become free at the same time, then multiple tasks from the queue will be assigned
 * in order of insertion following the weight and index priorities above.
 *
 * A server that is assigned task j at second t will be free again at second t + tasks[j].
 *
 * Build an array ans of length m, where ans[j] is the index of the server the jth task will be assigned to.
 *
 * Return the array ans.
 *
 * Example 1:
 *
 * Input: servers = [3,3,2], tasks = [1,2,3,2,1,2]
 * Output: [2,2,0,2,1,2]
 * Explanation: Events in chronological order go as follows:
 * - At second 0, task 0 is added and processed using server 2 until second 1.
 * - At second 1, server 2 becomes free. Task 1 is added and processed using server 2 until second 3.
 * - At second 2, task 2 is added and processed using server 0 until second 5.
 * - At second 3, server 2 becomes free. Task 3 is added and processed using server 2 until second 5.
 * - At second 4, task 4 is added and processed using server 1 until second 5.
 * - At second 5, all servers become free. Task 5 is added and processed using server 2 until second 7.
 *
 * Example 2:
 *
 * Input: servers = [5,1,4,3,2], tasks = [2,1,2,4,5,2,1]
 * Output: [1,4,1,4,1,3,2]
 * Explanation: Events in chronological order go as follows:
 * - At second 0, task 0 is added and processed using server 1 until second 2.
 * - At second 1, task 1 is added and processed using server 4 until second 2.
 * - At second 2, servers 1 and 4 become free. Task 2 is added and processed using server 1 until second 4.
 * - At second 3, task 3 is added and processed using server 4 until second 7.
 * - At second 4, server 1 becomes free. Task 4 is added and processed using server 1 until second 9.
 * - At second 5, task 5 is added and processed using server 3 until second 7.
 * - At second 6, task 6 is added and processed using server 2 until second 7.
 *
 * Constraints:
 *
 * servers.length == n
 * tasks.length == m
 * 1 <= n, m <= 2 * 10^5
 * 1 <= servers[i], tasks[j] <= 2 * 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1882_ProcessTasksUsingServers {

    @Answer
    public int[] assignTasks(int[] servers, int[] tasks) {
        final int n = servers.length;
        final int m = tasks.length;
        // 可用server 的队列
        PriorityQueue<Integer> weightPq = new PriorityQueue<>(
                (a, b) -> servers[a] == servers[b] ? a - b : servers[a] - servers[b]);
        for (int i = 0; i < n; i++) {
            weightPq.offer(i);
        }

        // 处理中的server 的队列
        int[] ends = new int[n];
        PriorityQueue<Integer> processPq = new PriorityQueue<>(Comparator.comparingInt(a -> ends[a]));

        int[] ans = new int[m];
        int time = 0;
        for (int i = 0; i < m; i++) {
            time = Math.max(time, i);
            // 如果没有server可用, 那就等到server 可用为止
            if (weightPq.isEmpty()) {
                time = Math.max(time, ends[processPq.peek()]);
            }
            while (!processPq.isEmpty()
                    && ends[processPq.peek()] <= time) {
                weightPq.offer(processPq.poll());
            }
            int server = weightPq.poll();
            ans[i] = server;
            ends[server] = time + tasks[i];
            processPq.offer(server);
        }
        return ans;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{3, 3, 2}, new int[]{1, 2, 3, 2, 1, 2})
            .expect(new int[]{2, 2, 0, 2, 1, 2});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 1, 4, 3, 2}, new int[]{2, 1, 2, 4, 5, 2, 1})
            .expect(new int[]{1, 4, 1, 4, 1, 3, 2});

    // 这里有来不及分配的
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{10, 63, 95, 16, 85, 57, 83, 95, 6, 29, 71},
                    new int[]{70, 31, 83, 15, 32, 67, 98, 65, 56, 48, 38, 90, 5})
            .expect(new int[]{8, 0, 3, 9, 5, 1, 10, 6, 4, 2, 7, 9, 0});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{31, 96, 73, 90, 15, 11, 1, 90, 72, 9, 30, 88},
                    new int[]{87, 10, 3, 5, 76, 74, 38, 64, 16, 64, 93, 95, 60, 79, 54, 26, 30, 44, 64, 71})
            .expect(new int[]{6, 9, 5, 4, 10, 5, 0, 8, 4, 2, 11, 9, 3, 7, 1, 4, 0, 4, 1, 8});

}
