package q2100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2054. Two Best Non-Overlapping Events</h3>
 * <a href="https://leetcode.com/problems/two-best-non-overlapping-events/">
 * https://leetcode.com/problems/two-best-non-overlapping-events/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> 2D integer array of <code>events</code> where <code>events[i] =
 * [startTime<sub>i</sub>, endTime<sub>i</sub>, value<sub>i</sub>]</code>. The <code>i<sup>th</sup></code> event starts
 * at <code>startTime<sub>i</sub></code><sub> </sub>and ends at <code>endTime<sub>i</sub></code>, and if you attend this
 * event, you will receive a value of <code>value<sub>i</sub></code>. You can choose <strong>at most</strong>
 * <strong>two</strong> <strong>non-overlapping</strong> events to attend such that the sum of their values is
 * <strong>maximized</strong>.</p>
 *
 * <p>Return <em>this <strong>maximum</strong> sum.</em></p>
 *
 * <p>Note that the start time and end time is <strong>inclusive</strong>: that is, you cannot attend two events where
 * one of them starts and the other ends at the same time. More specifically, if you attend an event with end time
 * <code>t</code>, the next event must start at or after <code>t + 1</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/09/21/picture5.png" style="width: 400px; height: 75px;" />
 * <pre>
 * <strong>Input:</strong> events = [[1,3,2],[4,5,2],[2,4,3]]
 * <strong>Output:</strong> 4
 * <strong>Explanation: </strong>Choose the green events, 0 and 1 for a sum of 2 + 2 = 4.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="Example 1 Diagram" src="https://assets.leetcode.com/uploads/2021/09/21/picture1.png" style="width: 400px;
 * height: 77px;" />
 * <pre>
 * <strong>Input:</strong> events = [[1,3,2],[4,5,2],[1,5,5]]
 * <strong>Output:</strong> 5
 * <strong>Explanation: </strong>Choose event 2 for a sum of 5.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/09/21/picture3.png" style="width: 400px; height: 66px;" />
 * <pre>
 * <strong>Input:</strong> events = [[1,5,3],[1,5,1],[6,6,5]]
 * <strong>Output:</strong> 8
 * <strong>Explanation: </strong>Choose events 0 and 2 for a sum of 3 + 5 = 8.</pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= events.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>events[i].length == 3</code></li>
 * 	<li><code>1 &lt;= startTime<sub>i</sub> &lt;= endTime<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
 * 	<li><code>1 &lt;= value<sub>i</sub> &lt;= 10<sup>6</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2054_TwoBestNonOverlappingEvents {

    // 时间复杂度 nlog(n)
    @Answer
    public int maxTwoEvents(int[][] events) {
        // 按照开始时间排序
        Arrays.sort(events, Comparator.comparingInt(e -> e[0]));
        // 按照结束时间构造优先队列
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e[1]));

        int res = 0, endMax = 0;
        for (int[] event : events) {
            // 这里的时间不能重叠
            while (!queue.isEmpty() && queue.peek()[1] < event[0]) {
                int[] prev = queue.poll();
                endMax = Math.max(endMax, prev[2]);
            }
            res = Math.max(res, endMax + event[2]);
            queue.offer(event);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 3, 2}, {4, 5, 2}, {2, 4, 3}}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 3, 2}, {4, 5, 2}, {1, 5, 5}}).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 5, 3}, {1, 5, 1}, {6, 6, 5}}).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{66, 97, 90}, {98, 98, 68}, {38, 49, 63}, {91, 100, 42}, {92, 100, 22}, {1, 77, 50},
                    {64, 72, 97}})
            .expect(165);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{22, 44, 9}, {93, 96, 48}, {56, 90, 3}, {80, 92, 45}, {63, 73, 69}, {73, 96, 33},
                    {11, 23, 84}, {59, 72, 29}, {89, 100, 46}})
            .expect(153);

}
