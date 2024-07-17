package q2050;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 2050. Parallel Courses III
 * https://leetcode.com/problems/parallel-courses-iii/
 *
 * You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given a 2D
 * integer array relations where relations[j] = [prevCoursej, nextCoursej] denotes that course prevCoursej has to be
 * completed before course nextCoursej (prerequisite relationship). Furthermore, you are given a 0-indexed integer array
 * time where time[i] denotes how many months it takes to complete the (i+1)th course.
 *
 * You must find the minimum number of months needed to complete all the courses following these rules:
 *
 * You may start taking a course at any time if the prerequisites are met.
 * Any number of courses can be taken at the same time.
 *
 * Return the minimum number of months needed to complete all the courses.
 *
 * Note: The test cases are generated such that it is possible to complete every course (i.e., the graph is a directed
 * acyclic graph).
 *
 * Example 1:
 * (图Q2050_PIC1.png)
 * Input: n = 3, relations = [[1,3],[2,3]], time = [3,2,5]
 * Output: 8
 * Explanation: The figure above represents the given graph and the time required to complete each course.
 * We start course 1 and course 2 simultaneously at month 0.
 * Course 1 takes 3 months and course 2 takes 2 months to complete respectively.
 * Thus, the earliest time we can start course 3 is at month 3, and the total time required is 3 + 5 = 8 months.
 *
 * Example 2:
 * (图Q2050_PIC2.png)
 * Input: n = 5, relations = [[1,5],[2,5],[3,5],[3,4],[4,5]], time = [1,2,3,4,5]
 * Output: 12
 * Explanation: The figure above represents the given graph and the time required to complete each course.
 * You can start courses 1, 2, and 3 at month 0.
 * You can complete them after 1, 2, and 3 months respectively.
 * Course 4 can be taken only after course 3 is completed, i.e., after 3 months. It is completed after 3 + 4 = 7
 * months.
 * Course 5 can be taken only after courses 1, 2, 3, and 4 have been completed, i.e., after max(1,2,3,7) = 7 months.
 * Thus, the minimum time needed to complete all the courses is 7 + 5 = 12 months.
 *
 * Constraints:
 *
 * 1 <= n <= 5 * 10^4
 * 0 <= relations.length <= min(n * (n - 1) / 2, 5 * 10^4)
 * relations[j].length == 2
 * 1 <= prevCoursej, nextCoursej <= n
 * prevCoursej != nextCoursej
 * All the pairs [prevCoursej, nextCoursej] are unique.
 * time.length == n
 * 1 <= time[i] <= 10^4
 * The given graph is a directed acyclic graph.
 */
@RunWith(LeetCodeRunner.class)
public class Q2050_ParallelCoursesIII {

    @Answer
    public int minimumTime(int n, int[][] relations, int[] time) {
        Course[] courses = new Course[n + 1];
        for (int i = 1; i <= n; i++) {
            courses[i] = new Course();
            courses[i].time = time[i - 1];
        }
        for (int[] relation : relations) {
            Course prev = courses[relation[0]];
            Course next = courses[relation[1]];
            prev.next.add(next);
            next.prevCount++;
        }

        Queue<Course> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (courses[i].prevCount == 0) {
                queue.offer(courses[i]);
            }
        }
        while (!queue.isEmpty()) {
            Course course = queue.poll();
            int endTime = course.startTime + course.time;
            for (Course next : course.next) {
                next.startTime = Math.max(next.startTime, endTime);
                next.prevCount--;
                if (next.prevCount == 0) {
                    queue.offer(next);
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, courses[i].startTime + courses[i].time);
        }
        return res;
    }

    private static class Course {

        // 后置课程
        List<Course> next = new ArrayList<>();

        // 前置课程数量
        int prevCount;

        // 课程时间
        int time;

        // 课程的最晚开始时间
        int startTime;

    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{1, 3}, {2, 3}}, new int[]{3, 2, 5})
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{1, 5}, {2, 5}, {3, 5}, {3, 4}, {4, 5}}, new int[]{1, 2, 3, 4, 5})
            .expect(12);

}
