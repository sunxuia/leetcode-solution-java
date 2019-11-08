package q250;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.DataExpectationBuilder;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/course-schedule-ii
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 *
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 * expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take
 * to finish all courses.
 *
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all
 * courses, return an empty array.
 *
 * Example 1:
 *
 * Input: 2, [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 * course 0. So the correct course order is [0,1] .
 *
 * Example 2:
 *
 * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,1,2,3] or [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 * courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 *
 * Note:
 *
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how
 * a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
@RunWith(LeetCodeRunner.class)
public class Q210_CourseScheduleII {

    @Answer
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] toEdges = new List[numCourses];
        for (int i = 0; i < numCourses; i++) {
            toEdges[i] = new ArrayList<>();
        }
        for (int[] prerequisite : prerequisites) {
            toEdges[prerequisite[0]].add(prerequisite[1]);
        }
        int[] status = new int[numCourses];
        int[] res = new int[numCourses];
        int resNext = 0;
        Deque<Integer> stack = new ArrayDeque<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            if (status[i] == 0) {
                stack.push(i);
                while (!stack.isEmpty()) {
                    int node = stack.pop();
                    if (node >= 0) {
                        if (status[node] > 0) {
                            continue;
                        }
                        if (status[node] < 0) {
                            return new int[0];
                        }
                        status[node] = -1;
                        stack.push(-node - 1);
                        for (Integer nextNode : toEdges[node]) {
                            stack.push(nextNode);
                        }
                    } else {
                        node = -node - 1;
                        res[resNext++] = node;
                        status[node] = 1;
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(2, new int[][]{{1, 0}}).expect(new int[]{0, 1});

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(4)
            .addArgument(new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})
            .expect(new int[][]{{0, 1, 2, 3}, {0, 2, 1, 3}})
            .assertMethod(DataExpectationBuilder.orExpectAssertMethod)
            .build();

    @TestData
    public DataExpectation emptyResult = DataExpectation
            .createWith(2, new int[][]{{1, 0}, {0, 1}}).expect(new int[0]);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {0, 2}, {1, 2}}).expect(new int[]{2, 1, 0});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(4, new int[][]{{0, 1}, {3, 1}, {1, 3}, {3, 2}}).expect(new int[0]);

}
