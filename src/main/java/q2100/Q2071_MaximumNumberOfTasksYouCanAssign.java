package q2100;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * <h3>[Hard] 2071. Maximum Number of Tasks You Can Assign</h3>
 * <a href="https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/">
 * https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/
 * </a><br/>
 *
 * <p>You have <code>n</code> tasks and <code>m</code> workers. Each task has a strength requirement stored in a
 * <strong>0-indexed</strong> integer array <code>tasks</code>, with the <code>i<sup>th</sup></code> task requiring
 * <code>tasks[i]</code> strength to complete. The strength of each worker is stored in a <strong>0-indexed</strong>
 * integer array <code>workers</code>, with the <code>j<sup>th</sup></code> worker having <code>workers[j]</code>
 * strength. Each worker can only be assigned to a <strong>single</strong> task and must have a strength
 * <strong>greater
 * than or equal</strong> to the task&#39;s strength requirement (i.e., <code>workers[j] &gt;= tasks[i]</code>).</p>
 *
 * <p>Additionally, you have <code>pills</code> magical pills that will <strong>increase a worker&#39;s
 * strength</strong> by <code>strength</code>. You can decide which workers receive the magical pills, however, you may
 * only give each worker <strong>at most one</strong> magical pill.</p>
 *
 * <p>Given the <strong>0-indexed </strong>integer arrays <code>tasks</code> and <code>workers</code> and the integers
 * <code>pills</code> and <code>strength</code>, return <em>the <strong>maximum</strong> number of tasks that can be
 * completed.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> tasks = [<u><strong>3</strong></u>,<u><strong>2</strong></u>,<u><strong>1</strong></u>], workers = [<u><strong>0</strong></u>,<u><strong>3</strong></u>,<u><strong>3</strong></u>], pills = 1, strength = 1
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong>
 * We can assign the magical pill and tasks as follows:
 * - Give the magical pill to worker 0.
 * - Assign worker 0 to task 2 (0 + 1 &gt;= 1)
 * - Assign worker 1 to task 1 (3 &gt;= 2)
 * - Assign worker 2 to task 0 (3 &gt;= 3)
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> tasks = [<u><strong>5</strong></u>,4], workers = [<u><strong>0</strong></u>,0,0], pills = 1, strength = 5
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong>
 * We can assign the magical pill and tasks as follows:
 * - Give the magical pill to worker 0.
 * - Assign worker 0 to task 0 (0 + 5 &gt;= 5)
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> tasks = [<u><strong>10</strong></u>,<u><strong>15</strong></u>,30], workers = [<u><strong>0</strong></u>,<u><strong>10</strong></u>,10,10,10], pills = 3, strength = 10
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * We can assign the magical pills and tasks as follows:
 * - Give the magical pill to worker 0 and worker 1.
 * - Assign worker 0 to task 0 (0 + 10 &gt;= 10)
 * - Assign worker 1 to task 1 (10 + 10 &gt;= 15)
 * The last pill is not given because it will not make any worker strong enough for the last task.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == tasks.length</code></li>
 * 	<li><code>m == workers.length</code></li>
 * 	<li><code>1 &lt;= n, m &lt;= 5 * 10<sup>4</sup></code></li>
 * 	<li><code>0 &lt;= pills &lt;= m</code></li>
 * 	<li><code>0 &lt;= tasks[i], workers[j], strength &lt;= 10<sup>9</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2071_MaximumNumberOfTasksYouCanAssign {

    /**
     * 参考 leetcode 解答, tasks 肯定是将最小的几个任务分配给workers 中的最大的几个人.
     * 针对这个结果 k 进行二分查找, 判断是否可以分配这么多任务.
     * 先给 tasks 和 workers 排序, 然后尝试把 tasks 的前 k 个任务分配给 workers 的后 k 个人,
     * 尽量吃药(贪婪匹配), 判断这个k 是否可性, 二分查找出最大的结果.
     * 时间复杂度 max(nlogm, nlogn, mlogm)
     */
    @Answer
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        final int n = tasks.length;
        final int m = workers.length;
        Arrays.sort(tasks);
        Arrays.sort(workers);

        // 二分查找可分配的最大任务数k 在 workers 中的起始下标, 也就是m-k
        int start = m - Math.min(n, m), end = m;
        // 保存待分配的任务 (当前  worker 吃药后可分配的任务队列), 单调递增队列,
        // 尽量把最小的任务分配给不吃药的worker
        Deque<Integer> unassigned = new ArrayDeque<>(n);
        while (start < end) {
            int mid = (start + end) / 2;

            unassigned.clear();
            boolean complete = true;
            // t : 当前遍历到的任务下标, p: 本次循环中已使用的 pills 数量
            int t = 0, p = 0;
            // worker 从小到大遍历, 给每个worker 分配一个task, 并更新 ascend 队列.
            for (int w = mid; w < m && complete; w++) {
                // 将此worker 吃药后能处理的任务加入队列(里面都是他吃药后能处理的)
                while (t < n && tasks[t] <= workers[w] + strength) {
                    unassigned.addLast(tasks[t++]);
                }
                if (unassigned.isEmpty()) {
                    // 没有可以分配的任务
                    complete = false;
                } else if (unassigned.getFirst() <= workers[w]) {
                    // 可以不吃药直接处理, 这里处理最小的,
                    // 因为后面的worker 力量更强, 可以处理更大的任务.
                    unassigned.pollFirst();
                } else if (p == pills) {
                    // 没有药了, 且当前当前task 无法直接分配给当前worker,
                    // 因为可分配的 worker 和 task 数量都是相等的,
                    // 一个分配不了, 即使后面的worker 给了当前task, 后面的task 也分配不了.
                    complete = false;
                } else {
                    // 取出当前worker 能吃药解决的最大task
                    unassigned.pollLast();
                    p++;
                }
            }

            if (complete) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return m - start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{3, 2, 1}, new int[]{0, 3, 3}, 1, 1)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 4}, new int[]{0, 0, 0}, 1, 5)
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.
            createWith(new int[]{10, 15, 30}, new int[]{0, 10, 10, 10, 10}, 3, 10)
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.
            createWith(new int[]{5, 9, 8, 5, 9}, new int[]{1, 6, 4, 2, 6}, 1, 5)
            .expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.
            createWith(new int[]{35}, new int[]{83, 20, 4, 66}, 3, 41)
            .expect(1);

    private TestDataFile file = new TestDataFile();

    @TestData
    public DataExpectation normal3 = DataExpectation.
            createWith(TestDataFileHelper.read(file, 1, int[].class),
                    TestDataFileHelper.read(file, 2, int[].class), 164, 7421)
            .expect(51);

    @TestData
    public DataExpectation normal4 = DataExpectation.
            createWith(TestDataFileHelper.read(file, 3, int[].class),
                    TestDataFileHelper.read(file, 4, int[].class), 66, 9620)
            .expect(112);

    @TestData
    public DataExpectation overtime = DataExpectation
            .createWith(TestDataFileHelper.read(file, 5, int[].class),
                    TestDataFileHelper.read(file, 6, int[].class), 10000, 1000000000)
            .expect(50000);

}
