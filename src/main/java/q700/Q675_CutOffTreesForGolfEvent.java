package q700;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/cut-off-trees-for-golf-event/
 *
 * You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map,
 * in this map:
 *
 * 1. 0 represents the obstacle can't be reached.
 * 2. 1 represents the ground can be walked through.
 * 3. The place with number bigger than 1 represents a tree can be walked through, and this positive number
 * represents the tree's height.
 *
 * In one step you can walk in any of the four directions top, bottom, left and right also when standing in a point
 * which is a tree you can decide whether or not to cut off the tree.
 *
 * You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree with
 * lowest height first. And after cutting, the original place has the tree will become a grass (value 1).
 *
 * You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the
 * trees. If you can't cut off all the trees, output -1 in that situation.
 *
 * You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.
 *
 * Example 1:
 *
 * Input:
 * [
 * [1,2,3],
 * [0,0,4],
 * [7,6,5]
 * ]
 * Output: 6
 *
 *
 *
 * Example 2:
 *
 * Input:
 * [
 * [1,2,3],
 * [0,0,0],
 * [7,6,5]
 * ]
 * Output: -1
 *
 *
 *
 * Example 3:
 *
 * Input:
 * [
 * [2,3,4],
 * [0,0,5],
 * [8,7,6]
 * ]
 * Output: 6
 * Explanation: You started from the point (0,0) and you can cut off the tree in (0,0) directly without walking.
 *
 *
 *
 * Constraints:
 *
 * 1 <= forest.length <= 50
 * 1 <= forest[i].length <= 50
 * 0 <= forest[i][j] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q675_CutOffTreesForGolfEvent {

    // bfs 的方式, 比较慢
    @Answer
    public int cutOffTree(List<List<Integer>> forest) {
        List<Integer> points = getPoints(forest);

        // 从 (0, 0) 开始按顺序去往这些点
        int res = 0, curr = 0;
        for (int next : points) {
            int steps = bfs(forest, curr, next);
            if (steps == -1) {
                return -1;
            }
            res += steps;
            curr = next;
        }
        return res;
    }

    final int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    // 找出要到达的点并排序
    private List<Integer> getPoints(List<List<Integer>> forest) {
        final int m = forest.size(), n = forest.get(0).size();
        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    points.add(i * n + j);
                }
            }
        }
        points.sort(Comparator.comparingInt(p -> forest.get(p / n).get(p % n)));
        return points;
    }

    // 通过BFS 获取从 curr 到target 的距离(要走的步数)
    private int bfs(List<List<Integer>> forest, int curr, int target) {
        final int m = forest.size(), n = forest.get(0).size();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(curr);
        Set<Integer> visited = new HashSet<>();
        visited.add(curr);
        for (int step = 0; !queue.isEmpty(); step++) {
            for (int i = queue.size(); i > 0; i--) {
                int p = queue.poll();
                if (p == target) {
                    return step;
                }

                int y = p / n, x = p % n;
                for (int[] direction : DIRECTIONS) {
                    y += direction[0];
                    x += direction[1];
                    if (y > -1 && y < m && x > -1 && x < n
                            && forest.get(y).get(x) > 0
                            && visited.add(y * n + x)) {
                        queue.offer(y * n + x);
                    }
                    y -= direction[0];
                    x -= direction[1];
                }
            }
        }
        return -1;
    }

    /**
     * 因为题目中涉及到了障碍物.
     * 所以可以使用A* 算法优化上面的bfs 寻路, 不过OJ 中这种方法比BFS 还要慢.
     * (数组创建移动到方法外耗时不影响OJ 结果)
     * Solution 中还提供了一种简化的A* 算法的实现.
     */
    @Answer
    public int cutOffTree2(List<List<Integer>> forest) {
        List<Integer> points = getPoints(forest);

        // 从 (0, 0) 开始按顺序去往这些点
        int res = 0, curr = 0;
        for (int next : points) {
            int steps = aStar(forest, curr, next);
            if (steps == -1) {
                return -1;
            }
            res += steps;
            curr = next;
        }
        return res;
    }

    /**
     * A* 算法的文档可以参见之前的C# 的LeetCode 项目或者如下链接
     * http://www.cppblog.com/mythit/archive/2009/04/19/80492.aspx
     * A* 类似BFS 但是通过权重值来选择每次优先遍历的点, 这样当首次到达终点时剩余的点就不用遍历了.
     */
    private int aStar(List<List<Integer>> forest, int start, int end) {
        final int m = forest.size(), n = forest.get(0).size();
        // 表示最短路径中该点的上一个点
        int[] parent = new int[m * n];
        // 表示A* 算法中的G 值(距离上一个点的距离)
        // 定义是: 从起点沿着产生的路径, 移动到网格上指定方格的移动耗费
        int[] gValue = new int[m * n];
        // 表示A* 算法中的H 值(距离目标点的预估距离, 因为不计算路上的障碍物)
        // 定义是: 从网格上该点移动到终点end 的预估移动耗费
        int[] hValue = new int[m * n];

        // 开放列表, 表示该节点已经到达但是还未从这个点出发到其他点去过.
        // 排序是按照A* 算法中的F 值 (= H + G) 进行排序的.
        PriorityQueue<Integer> opens = new PriorityQueue<>(
                Comparator.comparingInt(p -> hValue[p] + gValue[p]));
        // 状态: 1 表示在开放列表中, -1 表示在关闭列表中, 0 表示既不在开放列表也不在关闭列表.
        // 关闭列表表示该节点已经到达且已经从该点出发到其他点去过了.
        int[] status = new int[m * n];

        // 初始化, 添加起始点
        opens.add(start);
        status[start] = 1;

        int curr = start;
        while (!opens.isEmpty()) {
            // 取出预估的最短路径的点
            curr = opens.poll();
            if (curr == end) {
                // 已经到达终点
                break;
            }
            // 加入关闭列表
            status[curr] = -1;

            // 以当前点为中心出发去往下一个点
            int currY = curr / n, currX = curr % n;
            for (int[] direction : DIRECTIONS) {
                int y = currY + direction[0];
                int x = currX + direction[1];
                int neighbor = y * n + x;
                if (y == -1 || y == m || x == -1 || x == n
                        || forest.get(y).get(x) == 0
                        || status[neighbor] == -1) {
                    continue;
                }
                // 计算G 值, 这里因为只有上下左右移动所以算路径消耗为 100
                // (真实场景中路径消耗应当为2 个点的距离 * 单位距离的权重)
                int g = gValue[curr] + 100;
                if (status[neighbor] == 1) {
                    // 已经在开放列表中则更新neighbor 点的上一步为g 值小的点
                    if (g < gValue[neighbor]) {
                        gValue[neighbor] = g;
                        parent[neighbor] = curr;
                    }
                } else {
                    // 不在开放列表(也不在关闭列表) 中的点则加入开放列表,
                    // 之后还要从这个点出发去往其他点.
                    gValue[neighbor] = g;
                    parent[neighbor] = curr;
                    // 采用曼哈顿距离计算到终点的路径.
                    // 这里的每格路径消耗权重为1, 要比上面的g 值的权重小是因为这是预估值,
                    // 排序时应当以g 值为优先, 即当g 值相同的时候会优先选择h 值小的点.
                    hValue[neighbor] = Math.abs(y - end / n) + Math.abs(x - end % n);
                    opens.add(neighbor);
                    status[neighbor] = 1;
                }
            }
        }

        // 没有找到路径
        if (curr != end) {
            return -1;
        }
        // 根据上一步回溯到起点
        int steps = 0;
        for (int i = end; i != start; i = parent[i]) {
            steps++;
        }
        return steps;
    }

    /**
     * Solution 中提供的一种 "Hadlock" 算法, 不过这个算法代码不全.
     * 如下链接是discuss 中有人贴的 C++ 代码, 转换为java 代码.
     * https://leetcode.com/problems/cut-off-trees-for-golf-event/discuss/148935/C%2B%2B-3-methods-beat-99
     */
    @Answer
    public int cutOffTree3(List<List<Integer>> forest) {
        List<Integer> points = getPoints(forest);

        // 从 (0, 0) 开始按顺序去往这些点
        int res = 0, curr = 0;
        for (int next : points) {
            int steps = hadlocks(forest, curr, next);
            if (steps == -1) {
                return -1;
            }
            res += steps;
            curr = next;
        }
        return res;
    }

    /**
     * 这个方法里使用一个叫做detour 的变量表示从起点到中间之间需要绕多少路, 因为一次绕路就是一来一回2 格.
     * 新的节点周边的4 个节点与终点end 之间的距离, 如果近了就是原来的最短路径, 如果远了就说明绕了1 次路了,
     * 到达终点的时候通过计算最短距离加上绕路距离就是步数了.
     */
    int hadlocks(List<List<Integer>> forest, int start, int end) {
        final int m = forest.size(), n = forest.get(0).size();
        int endY = end / n, endX = end % n;
        boolean[] visited = new boolean[m * n];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            // 绕路次数
            int detour = queue.poll();
            // 当前位置
            int curr = queue.poll();
            int currY = curr / n, currX = curr % n;
            if (curr == end) {
                // 不考虑障碍的最短距离 + 绕路次数*2 就是结果了
                return Math.abs(start / n - currY) + Math.abs(start % n - currX) + detour * 2;
            }
            int distance = Math.abs(currY - endY) + Math.abs(currX - endX);

            for (int[] direction : DIRECTIONS) {
                int y = currY + direction[0];
                int x = currX + direction[1];
                int neighbor = y * n + x;
                if (y == -1 || y == m || x == -1 || x == n
                        || forest.get(y).get(x) == 0
                        || visited[neighbor]) {
                    // 如果这个点已经被访问过了, 则这个点即使可以被访问,
                    // 绕路的距离也是之前访问的小(或等于), 因此将其舍弃, 避免了BFS 中的无效遍历.
                    continue;
                }
                visited[neighbor] = true;
                int toDistance = Math.abs(y - endY) + Math.abs(x - endX);
                // 新的点到终点距离远了就说明绕路了
                queue.offer(toDistance < distance ? detour : detour + 1);
                queue.offer(neighbor);
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(0, 0, 4),
            Arrays.asList(7, 6, 5)
    )).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(0, 0, 0),
            Arrays.asList(7, 6, 5)
    )).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(Arrays.asList(
            Arrays.asList(2, 3, 4),
            Arrays.asList(0, 0, 5),
            Arrays.asList(8, 7, 6)
    )).expect(6);

    @TestData
    public DataExpectation normal1() {
        return DataExpectation.create(Arrays.asList(
                Arrays.asList(69438, 55243, 0, 43779, 5241, 93591, 73380),
                Arrays.asList(847, 49990, 53242, 21837, 89404, 63929, 48214),
                Arrays.asList(90332, 49751, 0, 3088, 16374, 70121, 25385),
                Arrays.asList(14694, 4338, 87873, 86281, 5204, 84169, 5024),
                Arrays.asList(31711, 47313, 1885, 28332, 11646, 42583, 31460),
                Arrays.asList(59845, 94855, 29286, 53221, 9803, 41305, 60749),
                Arrays.asList(95077, 50343, 27947, 92852, 0, 0, 19731),
                Arrays.asList(86158, 63553, 56822, 90251, 0, 23826, 17478),
                Arrays.asList(60387, 23279, 78048, 78835, 5310, 99720, 0),
                Arrays.asList(74799, 48845, 60658, 29773, 96129, 90443, 14391),
                Arrays.asList(65448, 63358, 78089, 93914, 7931, 68804, 72633),
                Arrays.asList(93431, 90868, 55280, 30860, 59354, 62083, 47669),
                Arrays.asList(81064, 93220, 22386, 22341, 95485, 20696, 13436),
                Arrays.asList(50083, 0, 89399, 43882, 0, 13593, 27847),
                Arrays.asList(0, 12256, 33652, 69301, 73395, 93440, 0),
                Arrays.asList(42818, 87197, 81249, 33936, 7027, 5744, 64710),
                Arrays.asList(35843, 0, 99746, 52442, 17494, 49407, 63016),
                Arrays.asList(86042, 44524, 0, 0, 26787, 97651, 28572),
                Arrays.asList(54183, 83466, 96754, 89861, 84143, 13413, 72921),
                Arrays.asList(89405, 52305, 39907, 27366, 14603, 0, 14104),
                Arrays.asList(70909, 61104, 70236, 30365, 0, 30944, 98378),
                Arrays.asList(20124, 87188, 6515, 98319, 78146, 99325, 88919),
                Arrays.asList(89669, 0, 64218, 85795, 2449, 48939, 12869),
                Arrays.asList(93539, 28909, 90973, 77642, 0, 72170, 98359),
                Arrays.asList(88628, 16422, 80512, 0, 38651, 50854, 55768),
                Arrays.asList(13639, 2889, 74835, 80416, 26051, 78859, 25721),
                Arrays.asList(90182, 23154, 16586, 0, 27459, 3272, 84893),
                Arrays.asList(2480, 33654, 87321, 93272, 93079, 0, 38394),
                Arrays.asList(34676, 72427, 95024, 12240, 72012, 0, 57763),
                Arrays.asList(97957, 56, 83817, 45472, 0, 24087, 90245),
                Arrays.asList(32056, 0, 92049, 21380, 4980, 38458, 3490),
                Arrays.asList(21509, 76628, 0, 90430, 10113, 76264, 45840),
                Arrays.asList(97192, 58807, 74165, 65921, 45726, 47265, 56084),
                Arrays.asList(16276, 27751, 37985, 47944, 54895, 80706, 2372),
                Arrays.asList(28438, 53073, 0, 67255, 38416, 63354, 69262),
                Arrays.asList(23926, 75497, 91347, 58436, 73946, 39565, 10841),
                Arrays.asList(34372, 69647, 44093, 62680, 32424, 69858, 68719),
                Arrays.asList(24425, 4014, 94871, 1031, 99852, 88692, 31503),
                Arrays.asList(24475, 12295, 33326, 37771, 37883, 74568, 25163),
                Arrays.asList(0, 18411, 88185, 60924, 29028, 69789, 0),
                Arrays.asList(34697, 75631, 7636, 16190, 60178, 39082, 7052),
                Arrays.asList(24876, 9570, 53630, 98605, 22331, 79320, 88317),
                Arrays.asList(27204, 89103, 15221, 91346, 35428, 94251, 62745),
                Arrays.asList(26636, 28759, 12998, 58412, 38113, 14678, 0),
                Arrays.asList(80871, 79706, 45325, 3861, 12504, 0, 4872),
                Arrays.asList(79662, 15626, 995, 80546, 64775, 0, 68820),
                Arrays.asList(25160, 82123, 81706, 21494, 92958, 33594, 5243))
        ).expect(5637);
    }

}
