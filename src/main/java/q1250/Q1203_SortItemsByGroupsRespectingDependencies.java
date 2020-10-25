package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1203. Sort Items by Groups Respecting Dependencies
 * https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/
 *
 * There are n items each belonging to zero or one of m groups where group[i] is the group that the i-th item belongs to
 * and it's equal to -1 if the i-th item belongs to no group. The items and the groups are zero indexed. A group can
 * have no item belonging to it.
 *
 * Return a sorted list of the items such that:
 *
 * The items that belong to the same group are next to each other in the sorted list.
 * There are some relations between these items where beforeItems[i] is a list containing all the items that should come
 * before the i-th item in the sorted array (to the left of the i-th item).
 *
 * Return any solution if there is more than one solution and return an empty list if there is no solution.
 *
 * Example 1:
 * <img src="Q1203_PIC.png">
 * Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
 * Output: [6,3,4,1,5,2,0,7]
 *
 * Example 2:
 *
 * Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
 * Output: []
 * Explanation: This is the same as example 1 except that 4 needs to be before 6 in the sorted list.
 *
 * Constraints:
 *
 * 1 <= m <= n <= 3*10^4
 * group.length == beforeItems.length == n
 * -1 <= group[i] <= m-1
 * 0 <= beforeItems[i].length <= n-1
 * 0 <= beforeItems[i][j] <= n-1
 * i != beforeItems[i][j]
 * beforeItems[i] does not contain duplicates elements.
 *
 * 题解: 更好的题目说明参见
 * https://leetcode-cn.com/problems/sort-items-by-groups-respecting-dependencies/
 */
@RunWith(LeetCodeRunner.class)
public class Q1203_SortItemsByGroupsRespectingDependencies {

    /**
     * 将items 组织为更大的组 group, 分别建立 group 之间的有向连通图和group 内的有向连通图, 随后dfs 遍历求解.
     * 这种做法比较慢.
     */
    @Answer
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) {
                group[i] = m++;
            }
        }
        Group[] groups = new Group[m];
        for (int i = 0; i < m; i++) {
            groups[i] = new Group();
        }
        for (int i = 0; i < n; i++) {
            groups[group[i]].addItem(i);
            for (int before : beforeItems.get(i)) {
                if (group[before] != group[i]) {
                    groups[group[before]].addNext(groups[group[i]]);
                }
            }
        }

        // 如果组间或组内存在环路则无法排列
        Set<Group> visited = new HashSet<>();
        for (Group gp : groups) {
            if (gp.hasCycle(visited) || !gp.buildGraph(beforeItems)) {
                return new int[0];
            }
        }

        List<Integer> resList = new ArrayList<>(n);
        for (Group gp : groups) {
            if (gp.isRoot()) {
                gp.buildRes(resList, visited);
            }
        }
        return resList.stream().mapToInt(i -> i).toArray();
    }

    private static abstract class Graphable<T extends Graphable<T>> {

        Set<T> nexts = new HashSet<>(), prevs = new HashSet<>();

        void addNext(T next) {
            nexts.add(next);
            next.prevs.add((T) this);
            cycleCkecked = false;
            next.cycleCkecked = false;
        }

        boolean isRoot() {
            return prevs.isEmpty();
        }

        boolean cycleCkecked;

        boolean hasCycle(Set<T> visited) {
            if (cycleCkecked) {
                return false;
            }
            if (!visited.add((T) this)) {
                return true;
            }
            for (T next : nexts) {
                if (next.hasCycle(visited)) {
                    return true;
                }
            }
            visited.remove(this);
            cycleCkecked = true;
            return false;
        }

        void buildRes(List<Integer> resList, Set<T> visited) {
            if (visited.contains(this)) {
                return;
            }
            for (T prev : prevs) {
                if (!visited.contains(prev)) {
                    return;
                }
            }
            visited.add((T) this);
            buildResInternal(resList);
            for (T next : nexts) {
                next.buildRes(resList, visited);
            }
        }

        abstract void buildResInternal(List<Integer> resList);
    }

    private static class Group extends Graphable<Group> {

        Map<Integer, Item> items = new HashMap<>();

        void addItem(int i) {
            items.put(i, new Item(i));
        }

        // 构建组内item 的联系
        boolean buildGraph(List<List<Integer>> beforeItems) {
            for (Integer curr : items.keySet()) {
                Item item = items.get(curr);
                for (Integer before : beforeItems.get(curr)) {
                    if (items.containsKey(before)) {
                        items.get(before).addNext(item);
                    }
                }
            }

            Set<Item> visited = new HashSet<>();
            for (Item item : items.values()) {
                if (item.hasCycle(visited)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        void buildResInternal(List<Integer> resList) {
            Set<Item> visited = new HashSet<>();
            for (Item item : items.values()) {
                if (item.isRoot()) {
                    item.buildRes(resList, visited);
                }
            }
        }
    }

    private static class Item extends Graphable<Item> {

        final int value;

        public Item(int value) {
            this.value = value;
        }

        @Override
        void buildResInternal(List<Integer> resList) {
            resList.add(value);
        }
    }

    /**
     * LeetCode 上比较快的解答, 与上面思路类似, 进行了优化.
     * 这里将item 和group 都视为一个节点, 与上面解法类似, group 与不属于组的item 组成有向连通图,
     * 此外 group 还会有到达属于这个group 的item 的通路(子图是仍然挂在整个图中的)
     */
    @Answer
    public int[] sortItems2(final int n, final int m, int[] group, List<List<Integer>> beforeItems) {
        // 表示每个item 和 group 的入度(从item 或group 而来)
        int[] indegree = new int[n + m];
        // 表示每个item 和 group 的后续节点 (走到item 或group)
        List<Integer>[] graph = new List[n + m];
        for (int i = 0; i < n + m; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < group.length; i++) {
            if (group[i] != -1) {
                // group[i] 走到子item 节点
                graph[n + group[i]].add(i);
                indegree[i]++;
            }
        }

        for (int currItem = 0; currItem < beforeItems.size(); currItem++) {
            for (int beforeItem : beforeItems.get(currItem)) {
                // 前一个节点
                int prevNode = group[beforeItem] == -1 ? beforeItem : n + group[beforeItem];
                // 当前节点
                int currNode = group[currItem] == -1 ? currItem : n + group[currItem];

                if (prevNode == currNode) {
                    // 属于相同的group, 则是group 组内的联系
                    graph[beforeItem].add(currItem);
                    indegree[currItem]++;
                } else {
                    // 属于不同的group, 是group 之间的联系
                    graph[prevNode].add(currNode);
                    indegree[currNode]++;
                }
            }
        }

        List<Integer> resList = new ArrayList<>(n);
        for (int i = 0; i < n + m; i++) {
            // 入度为0 说明为根节点, 此节点没有前提节点或前提节点都加入res 了
            if (indegree[i] == 0) {
                dfs(n, graph, indegree, i, resList);
            }
        }

        if (resList.size() < n) {
            return new int[0];
        }
        return resList.stream().mapToInt(i -> i).toArray();
    }

    private void dfs(final int n, List<Integer>[] graph, int[] indegree, int cur, List<Integer> res) {
        if (cur < n) {
            // 是item 节点
            res.add(cur);
        }
        // 当前节点的入度-1 变为-1, 表示已经遍历过了
        indegree[cur]--;

        // 遍历此节点的下一个节点
        for (int next : graph[cur]) {
            // 当前节点已经遍历, 所以下一个节点的前提又具备了 1 个, 因此入度-1
            if (--indegree[next] == 0) {
                // 入度为 0 则说明此节点的前提都具备了
                dfs(n, graph, indegree, next, res);
            }
        }
    }

    @TestData
    public DataExpectation example1 = createTestData(8, 2, new int[]{-1, -1, 1, 0, 0, 1, 0, -1},
            Arrays.asList(Arrays.asList(), Arrays.asList(6), Arrays.asList(5), Arrays.asList(6), Arrays.asList(3, 6),
                    Arrays.asList(), Arrays.asList(), Arrays.asList()), false);

    private DataExpectation createTestData(
            int n, int m, int[] group, List<List<Integer>> beforeItems, boolean isEmptyResult) {
        int[] realGroup = group.clone();
        return DataExpectation.builder()
                .addArgument(n)
                .addArgument(m)
                .addArgument(group)
                .addArgument(beforeItems)
                .assertMethod((int[] actual) -> {
                    if (isEmptyResult) {
                        AssertUtils.assertEquals(0, actual.length);
                    } else {
                        AssertUtils.assertEquals(n, actual.length);
                        Set<Integer> visitedItems = new HashSet<>(n);
                        Set<Integer> visitedGroups = new HashSet<>(m);
                        for (int i = 0; i < n; i++) {
                            Assert.assertTrue(visitedItems.add(actual[i]));
                            int g = realGroup[actual[i]];
                            if (g != -1 && (i == n - 1 || g != group[actual[i + 1]])) {
                                Assert.assertTrue(visitedGroups.add(g));
                            }
                            for (Integer before : beforeItems.get(actual[i])) {
                                Assert.assertTrue(visitedItems.contains(before));
                            }
                        }
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData(8, 2, new int[]{-1, -1, 1, 0, 0, 1, 0, -1},
            Arrays.asList(Arrays.asList(), Arrays.asList(6), Arrays.asList(5), Arrays.asList(6), Arrays.asList(3),
                    Arrays.asList(), Arrays.asList(4), Arrays.asList()), true);

    @TestData
    public DataExpectation normal1 = createTestData(5, 5, new int[]{2, 0, -1, 3, 0},
            Arrays.asList(Arrays.asList(2, 1, 3), Arrays.asList(2, 4), Arrays.asList(), Arrays.asList(),
                    Arrays.asList()), false);

    @TestData
    public DataExpectation normal2 = createTestData(10, 4, new int[]{2, 2, 2, 1, 0, 1, 3, 2, 0, 1},
            Arrays.asList(Arrays.asList(7, 6, 2, 5, 3), Arrays.asList(), Arrays.asList(), Arrays.asList(),
                    Arrays.asList(7), Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList(),
                    Arrays.asList()), false);

}
