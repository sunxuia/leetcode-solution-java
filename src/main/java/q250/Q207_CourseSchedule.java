package q250;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/course-schedule/
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 *
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 * expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
 *
 * Example 1:
 *
 * Input: 2, [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 *
 * Example 2:
 *
 * Input: 2, [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should
 * also have finished course 1. So it is impossible.
 *
 * Note:
 *
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how
 * a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
@RunWith(LeetCodeRunner.class)
public class Q207_CourseSchedule {

    // 有向环环路检测的一个题目
    // dfs 解法, 通过判断是否会遇到遍历路径中的节点来检测环.
    @Answer
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // LeetCode 上测试用例是稀疏矩阵, 使用二维boolean 数组速度会慢很多.
        List<Integer>[] preCourses = new List[numCourses];
        for (int i = 0; i < numCourses; i++) {
            preCourses[i] = new ArrayList<>();
        }
        for (int[] prerequisite : prerequisites) {
            preCourses[prerequisite[0]].add(prerequisite[1]);
        }
        boolean[] finished = new boolean[numCourses];
        boolean[] unFinished = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(finished, unFinished, preCourses, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(boolean[] finished, boolean[] unFinished, List<Integer>[] preCourses, int curr) {
        if (finished[curr]) {
            return true;
        }
        if (unFinished[curr]) {
            return false;
        }
        unFinished[curr] = true;
        for (int pre : preCourses[curr]) {
            if (!dfs(finished, unFinished, preCourses, pre)) {
                return false;
            }
        }
        finished[curr] = true;
        unFinished[curr] = false;
        return true;
    }

    // bfs 的解法, 通过删除入度为0 的节点的方式来检测环路, 如果有环路则肯定存在入度不为0 的节点.
    @Answer
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        List<Integer>[] preCourses = new List[numCourses];
        for (int i = 0; i < numCourses; i++) {
            preCourses[i] = new ArrayList<>();
        }
        int[] enterCount = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            preCourses[prerequisite[0]].add(prerequisite[1]);
            enterCount[prerequisite[1]]++;
        }
        // queue 中是入度为0 的节点, 这些节点肯定不在环中.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (enterCount[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int node = queue.remove();
            // 将这个节点从环中删除, 遍历它到达的节点, 入度 -1.
            for (Integer pre : preCourses[node]) {
                enterCount[pre]--;
                // 到达的节点也变成入度为0 的节点了.
                if (enterCount[pre] == 0) {
                    queue.add(pre);
                }
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if (enterCount[i] > 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation examle1 = DataExpectation.createWith(2, new int[][]{{1, 0}}).expect(true);

    @TestData
    public DataExpectation examle2 = DataExpectation.createWith(2, new int[][]{{1, 0}, {0, 1}}).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(2000, TestDataFileHelper.read2DArray("Q207_LongTestData"))
            .expect(true);

}
