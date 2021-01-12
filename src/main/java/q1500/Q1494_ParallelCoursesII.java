package q1500;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1494. Parallel Courses II
 * https://leetcode.com/problems/parallel-courses-ii/
 *
 * Given the integer n representing the number of courses at some university labeled from 1 to n, and the array
 * dependencies where dependencies[i] = [xi, yi]  represents a prerequisite relationship, that is, the course xi must be
 * taken before the course yi.  Also, you are given the integer k.
 *
 * In one semester you can take at most k courses as long as you have taken all the prerequisites for the courses you
 * are taking.
 *
 * Return the minimum number of semesters to take all courses. It is guaranteed that you can take all courses in some
 * way.
 *
 * Example 1:
 * <img src="./Q1494_PIC1.png">
 * Input: n = 4, dependencies = [[2,1],[3,1],[1,4]], k = 2
 * Output: 3
 * Explanation: The figure above represents the given graph. In this case we can take courses 2 and 3 in the first
 * semester, then take course 1 in the second semester and finally take course 4 in the third semester.
 *
 * Example 2:
 * <img src="./Q1494_PIC2.png">
 * Input: n = 5, dependencies = [[2,1],[3,1],[4,1],[1,5]], k = 2
 * Output: 4
 * Explanation: The figure above represents the given graph. In this case one optimal way to take all courses is: take
 * courses 2 and 3 in the first semester and take course 4 in the second semester, then take course 1 in the third
 * semester and finally take course 5 in the fourth semester.
 *
 * Example 3:
 *
 * Input: n = 11, dependencies = [], k = 2
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= n <= 15
 * 1 <= k <= n
 * 0 <= dependencies.length <= n * (n-1) / 2
 * dependencies[i].length == 2
 * 1 <= xi, yi <= n
 * xi != yi
 * All prerequisite relationships are distinct, that is, dependencies[i] != dependencies[j].
 * The given graph is a directed acyclic graph.
 */
@RunWith(LeetCodeRunner.class)
public class Q1494_ParallelCoursesII {

    /**
     * 根据hint 的提示, 使用位运算dfs, 比较慢.
     */
    @Answer
    public int minNumberOfSemesters(int n, int[][] dependencies, int k) {
        int[] prerequisites = new int[n];
        for (int[] dependency : dependencies) {
            int from = dependency[0] - 1;
            int to = dependency[1] - 1;
            prerequisites[to] |= 1 << from;
        }
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            setPrerequisite(visited, prerequisites, i);
        }
        return pickCourse(prerequisites, 0, k);
    }

    /**
     * 设置该课程需要的前置课程
     */
    private void setPrerequisite(boolean[] visited, int[] prerequisites, int course) {
        if (visited[course]) {
            return;
        }
        visited[course] = true;
        int mask = prerequisites[course];
        for (int i = 0; mask > 0; i++, mask >>= 1) {
            if ((mask & 1) == 1) {
                setPrerequisite(visited, prerequisites, i);
                prerequisites[course] |= prerequisites[i];
            }
        }
    }

    /**
     * 每学期选择课程
     */
    private int pickCourse(int[] prerequisites, int visited, int k) {
        final int n = prerequisites.length;
        if (visited == (1 << n) - 1) {
            return 0;
        }
        // 获得这学期可选课程
        int count = 0, mask = 0;
        for (int i = 0; i < n; i++) {
            if ((visited >> i & 1) == 0 && (visited | prerequisites[i]) == visited) {
                count++;
                mask |= 1 << i;
            }
        }
        if (count <= k) {
            // 可选课程不超过k 个, 则全部学习
            return 1 + pickCourse(prerequisites, visited | mask, k);
        }
        // 从可选课程中选择k 个
        return 1 + traverse(prerequisites, visited, k, count, mask, 0, 0);
    }

    /**
     * 从每学期的可选课程(>k 个) 中选择k 个课程学习.
     * high 和highMask 表示从高到低遍历时高位的可修课程数和课程, low 和lowMask 表示低位的.
     */
    private int traverse(int[] prerequisites, int visited, int k, int high, int highMask, int low, int lowMask) {
        if (low == k) {
            // 已经选了k 个课程
            return pickCourse(prerequisites, visited | lowMask, k);
        }
        if (low + high == k) {
            // 已排除了多余的课程, 选择这k 个课程
            return pickCourse(prerequisites, visited | lowMask | highMask, k);
        }
        // 要决定是否选择的课程
        int mask = highMask & -highMask;
        return Math.min(
                // 本学期跳过这个课程
                traverse(prerequisites, visited, k, high - 1, highMask ^ mask, low, lowMask),
                // 本学期学习这个课程
                traverse(prerequisites, visited, k, high - 1, highMask ^ mask, low + 1, lowMask | mask)
        );
    }

    /**
     * LeetCode 上比较快的解法.
     */
    @Answer
    public int minNumberOfSemesters2(int n, int[][] dependencies, int k) {
        if (k == 0) {
            return -1;
        }
        List<Node> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(i, new Node(i + 1));
        }
        for (int[] dependency : dependencies) {
            Node from = graph.get(dependency[0] - 1);
            Node to = graph.get(dependency[1] - 1);
            from.nexts.add(to);
            to.indegree += 1;
        }
        return dfs(graph, k);
    }

    private static class Node {

        // 表示课程的编号
        final int no;

        // 表示可以到达的下一个课程
        Set<Node> nexts = new HashSet<>();

        // 表示课程的入度
        int indegree = 0;

        public Node(int no) {
            this.no = no;
        }

        @Override
        public String toString() {
            return String.format("{%d(%d)}", no, indegree);
        }
    }

    private int dfs(List<Node> graph, int k) {
        if (graph.size() <= 1) {
            // 0 或者1 个课程的情况
            return graph.size();
        }
        // 获得可选课程
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : graph) {
            if (node.indegree == 0) {
                nodesToRemove.add(node);
            }
        }
        int removeSize = nodesToRemove.size();
        if (removeSize > k) {
            // 如果可修数量 >k, 则优先移除没有后续课程的课程
            Iterator<Node> it = nodesToRemove.iterator();
            while (it.hasNext() && removeSize > k) {
                Node node = it.next();
                if (node.nexts.isEmpty()) {
                    it.remove();
                    removeSize--;
                }
            }
        }
        if (removeSize <= k) {
            // 可选课程 <=k, 则移除全部节点.
            return removeCourse(graph, nodesToRemove, k);
        }

        int res = graph.size();
        // 位运算遍历各种可能的组合情况, 选择要移除的节点
        // (mask 在遍历中, 1 的数量肯定 <= removeSize, 这样选择1 的数量为k 个的情况)
        for (int mask = 0; mask < (1 << removeSize); mask++) {
            List<Node> selection = new ArrayList<>();
            for (int pos = 0;
                    pos < removeSize && selection.size() <= k;
                    pos++) {
                if ((mask >> pos & 1) == 1) {
                    // 这个课程可选(节点可以移除).
                    selection.add(nodesToRemove.get(pos));
                }
            }
            if (selection.size() == k) {
                // 选择了k 个课程.
                res = Math.min(res, removeCourse(graph, selection, k));
            }
        }
        return res;
    }

    /**
     * 删除节点
     */
    private int removeCourse(List<Node> graph, List<Node> nodesToRemove, int k) {
        graph.removeAll(nodesToRemove);
        for (Node node : nodesToRemove) {
            for (Node next : node.nexts) {
                next.indegree--;
            }
        }
        int res = 1 + dfs(graph, k);
        // (回溯)
        graph.addAll(nodesToRemove);
        for (Node node : nodesToRemove) {
            for (Node next : node.nexts) {
                next.indegree++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{2, 1}, {3, 1}, {1, 4}}, 2)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{2, 1}, {3, 1}, {4, 1}, {1, 5}}, 2)
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(11, new int[][]{}, 2)
            .expect(6);

    /**
     * 这个测试用例说明了这题不能使用贪婪算法.
     */
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(12, new int[][]{{11, 10}, {6, 3}, {2, 5}, {9, 2}, {4, 12},
                    {8, 7}, {9, 5}, {6, 2}, {7, 2}, {7, 4}, {9, 3}, {11, 1}, {4, 3}}, 3)
            .expect(4);

}
