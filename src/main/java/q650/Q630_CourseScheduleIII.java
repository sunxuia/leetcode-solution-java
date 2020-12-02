package q650;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import q250.Q210_CourseScheduleII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/course-schedule-iii/
 *
 * There are n different online courses numbered from 1 to n. Each course has some duration(course length) t and
 * closed on dth day. A course should be taken continuously for t days and must be finished before or on the dth day.
 * You will start at the 1st day.
 *
 * Given n online courses represented by pairs (t,d), your task is to find the maximal number of courses that can be
 * taken.
 *
 * Example:
 *
 * Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
 * Output: 3
 * Explanation:
 * There're totally 4 courses, but you can take 3 courses at most:
 * First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next
 * course on the 101st day.
 * Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the
 * next course on the 1101st day.
 * Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day.
 * The 4th course cannot be taken now, since you will finish it on the 3300th day, which exceeds the closed date.
 *
 *
 *
 * Note:
 *
 * 1. The integer 1 <= d, t, n <= 10,000.
 * 2. You can't take two courses simultaneously.
 *
 * 上一题 {@link Q210_CourseScheduleII}
 */
@RunWith(LeetCodeRunner.class)
public class Q630_CourseScheduleIII {

    // 这题常规的dfs 会超时, 带cache 占用空间也比较大.
    // Solution 中比较好的解答如下, 使用优先队列来实现.
    @Answer
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, Comparator.comparingInt(a -> a[1]));
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        int time = 0;
        for (int[] c : courses) {
            if (time + c[0] <= c[1]) {
                // 当前项目可以执行
                queue.add(c[0]);
                time += c[0];
            } else if (!queue.isEmpty() && queue.peek() > c[0]) {
                // 当前项目比之前项目耗时小, 移除之前的一个项目, 加入当前项目
                time += c[0] - queue.remove();
                queue.add(c[0]);
            }
        }
        return queue.size();
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}
    }).expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(int[][].class)).expect(9931);

}
