package q1050;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1042. Flower Planting With No Adjacent
 * https://leetcode.com/problems/flower-planting-with-no-adjacent/
 *
 * You have N gardens, labelled 1 to N.  In each garden, you want to plant one of 4 types of flowers.
 *
 * paths[i] = [x, y] describes the existence of a bidirectional path from garden x to garden y.
 *
 * Also, there is no garden that has more than 3 paths coming into or leaving it.
 *
 * Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have
 * different types of flowers.
 *
 * Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)-th garden.
 * The flower types are denoted 1, 2, 3, or 4.  It is guaranteed an answer exists.
 *
 * Example 1:
 *
 * Input: N = 3, paths = [[1,2],[2,3],[3,1]]
 * Output: [1,2,3]
 *
 * Example 2:
 *
 * Input: N = 4, paths = [[1,2],[3,4]]
 * Output: [1,2,1,2]
 *
 * Example 3:
 *
 * Input: N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
 * Output: [1,2,3,4]
 *
 * Note:
 *
 * 1 <= N <= 10000
 * 0 <= paths.size <= 20000
 * No garden has 4 or more paths coming into or leaving it.
 * It is guaranteed an answer exists.
 */
@RunWith(LeetCodeRunner.class)
public class Q1042_FlowerPlantingWithNoAdjacent {

    @Answer
    public int[] gardenNoAdj(int N, int[][] paths) {
        List<Integer>[] neighbors = new List[N];
        for (int i = 0; i < N; i++) {
            neighbors[i] = new ArrayList<>();
        }
        for (int[] path : paths) {
            int a = path[0] - 1, b = path[1] - 1;
            neighbors[a].add(b);
            neighbors[b].add(a);
        }

        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            if (res[i] == 0) {
                dfs(res, neighbors, i);
            }
        }
        return res;
    }

    private void dfs(int[] res, List<Integer>[] neighbors, int i) {
        if (res[i] != 0) {
            return;
        }
        res[i] = -1;
        for (int neighbor : neighbors[i]) {
            dfs(res, neighbors, neighbor);
        }
        setColor:
        for (int color = 1; color <= 4; color++) {
            for (Integer neighbor : neighbors[i]) {
                if (res[neighbor] == color) {
                    continue setColor;
                }
            }
            res[i] = color;
            break;
        }
    }

    @TestData
    public DataExpectation example1 = createTestData(3, new int[][]{{1, 2}, {2, 3}, {3, 1}});

    private DataExpectation createTestData(int N, int[][] paths) {
        return DataExpectation.builder()
                .addArgument(N)
                .addArgument(paths)
                .assertMethod((int[] res) -> {
                    Assert.assertEquals(N, res.length);

                    List<Integer>[] neighbors = new List[N];
                    for (int i = 0; i < N; i++) {
                        neighbors[i] = new ArrayList<>();
                    }
                    for (int[] path : paths) {
                        int a = path[0] - 1, b = path[1] - 1;
                        neighbors[a].add(b);
                        neighbors[b].add(a);
                    }

                    for (int i = 0; i < N; i++) {
                        Assert.assertTrue(1 <= res[i] && res[i] <= 4);
                        for (int neighbor : neighbors[i]) {
                            Assert.assertNotEquals(res[neighbor], res[i]);
                        }
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData(4, new int[][]{{1, 2}, {3, 4}});

    @TestData
    public DataExpectation example3 = createTestData(4, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}, {1, 3}, {2, 4}});

}
