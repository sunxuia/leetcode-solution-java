package q1650;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1632. Rank Transform of a Matrix
 * https://leetcode.com/problems/rank-transform-of-a-matrix/
 *
 * Given an m x n matrix, return a new matrix answer where answer[row][col] is the rank of matrix[row][col].
 *
 * The rank is an integer that represents how large an element is compared to other elements. It is calculated using the
 * following rules:
 *
 * 1. The rank is an integer starting from 1.
 * 2. If two elements p and q are in the same row or column, then:
 * 2.1. If p < q then rank(p) < rank(q)
 * 2.2. If p == q then rank(p) == rank(q)
 * 2.3. If p > q then rank(p) > rank(q)
 * 3. The rank should be as small as possible.
 *
 * It is guaranteed that answer is unique under the given rules.
 *
 * Example 1:
 * <img src="./Q1632_PIC1.jpg">
 * Input: matrix = [[1,2],[3,4]]
 * Output: [[1,2],[2,3]]
 * Explanation:
 * The rank of matrix[0][0] is 1 because it is the smallest integer in its row and column.
 * The rank of matrix[0][1] is 2 because matrix[0][1] > matrix[0][0] and matrix[0][0] is rank 1.
 * The rank of matrix[1][0] is 2 because matrix[1][0] > matrix[0][0] and matrix[0][0] is rank 1.
 * The rank of matrix[1][1] is 3 because matrix[1][1] > matrix[0][1], matrix[1][1] > matrix[1][0], and both matrix[0][1]
 * and matrix[1][0] are rank 2.
 *
 * Example 2:
 * <img src="./Q1632_PIC2.jpg">
 * Input: matrix = [[7,7],[7,7]]
 * Output: [[1,1],[1,1]]
 *
 * Example 3:
 * <img src="./Q1632_PIC3.jpg">
 * Input: matrix = [[20,-21,14],[-19,4,19],[22,-47,24],[-19,4,19]]
 * Output: [[4,2,3],[1,3,4],[5,1,6],[1,3,4]]
 *
 * Example 4:
 * <img src="./Q1632_PIC4.jpg">
 * Input: matrix = [[7,3,6],[1,4,5],[9,8,2]]
 * Output: [[5,1,4],[1,2,3],[6,3,1]]
 *
 * Constraints:
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 500
 * -10^9 <= matrix[row][col] <= 10^9
 *
 * 题解: 这个不是求矩阵的秩, leetcode-cn 上的翻译有问题, 比的就是元素之间的排名.
 */
@RunWith(LeetCodeRunner.class)
public class Q1632_RankTransformOfAMatrix {

    /**
     * 参考文档
     * @formatter:off
     * https://leetcode-cn.com/problems/rank-transform-of-a-matrix/solution/java-bing-cha-ji-chao-xiang-xi-jiang-jie-by-charle/
     * @formatter:on
     */
    @Answer
    public int[][] matrixRankTransform(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        // matrix 中元素的排序 (下标二维转一维)
        int[] ranks = new int[m * n];

        // 并查集, 用于合并相同大小的元素, 保证相同大小的元素秩相同, 且应为这些相同元素中秩的最大值
        int[] roots = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            roots[i] = i;
        }

        // 对矩阵中值进行的排序
        Integer[] sort = new Integer[m * n];
        for (int i = 0; i < m * n; i++) {
            sort[i] = i;
        }
        Arrays.sort(sort, Comparator.comparingInt(i -> matrix[i / n][i % n]));

        //由于经过排序后，小的元素先考虑，如果出现更新的位置(i, j)
        // 所在的行、列已经更新过，那么当前位置的秩必然大于等于已经更新过的位置的秩,
        // 因此记录i行, j列之前最后一次更新秩的索引, 然后根据索引找到最后一次更新的秩的值, 从行列取到最大值就是(i, j)位置的秩了

        //rowMaxRank[i] = j 表示第 i 行目前(上一次更新的)最大的秩是 matrix[i][j] 的秩
        int[] rowMaxRank = new int[m];
        Arrays.fill(rowMaxRank, -1);
        //colMaxRank[j] = i 表示第 j 列目前(上一次更新的)最大的秩是 matrix[i][j] 的秩
        int[] colMaxRank = new int[n];
        Arrays.fill(colMaxRank, -1);

        // 按照值的大小从小到大遍历矩阵位置
        for (final int idx : sort) {
            final int i = idx / n, j = idx % n;
            // 排名
            int rank = 1;

            // 更新最大排名的索引 (行)
            if (rowMaxRank[i] != -1) {
                // 上次更新过的行最大元素位置
                int max = rowMaxRank[i];
                int maxIdx = i * n + max;
                int leader = find(roots, maxIdx);

                // 相同元素排名相同
                if (matrix[i][j] == matrix[i][max]) {
                    // 合并相同元素
                    union(roots, idx, maxIdx);
                    rank = ranks[leader];
                } else {
                    //当前元素大于之前元素(因为是按照升序遍历的), 则排名+1
                    rank = ranks[leader] + 1;
                }
            }
            rowMaxRank[i] = j;

            // 更新最大排名的索引 (列)
            if (colMaxRank[j] != -1) {
                // 上次更新后的列最大元素位置
                int max = colMaxRank[j];
                int maxIdx = max * n + j;
                int leader = find(roots, maxIdx);

                // 相同元素排名相同
                if (matrix[i][j] == matrix[max][j]) {
                    // 合并相同元素
                    union(roots, idx, maxIdx);
                    //由于在 rowMaxRank[i] != -1 的条件中可能更新过了 rank,
                    // 而我们需要的是行、列中最大的秩, 故取 max
                    rank = Math.max(rank, ranks[leader]);
                } else {
                    rank = Math.max(rank, ranks[leader] + 1);
                }
            }
            colMaxRank[j] = i;

            // 更新当前索引位置的秩的值, 由于有相同元素, 故只更新当前位置leader的秩的值
            ranks[find(roots, idx)] = rank;
        }

        // 转换结果
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = ranks[find(roots, i * n + j)];
            }
        }
        return res;
    }

    public int find(int[] roots, int i) {
        return roots[i] = (roots[i] == i ? i : find(roots, roots[i]));
    }

    public void union(int[] roots, int a, int b) {
        roots[find(roots, a)] = find(roots, b);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {3, 4}})
            .expect(new int[][]{{1, 2}, {2, 3}});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{7, 7}, {7, 7}})
            .expect(new int[][]{{1, 1}, {1, 1}});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{20, -21, 14}, {-19, 4, 19}, {22, -47, 24}, {-19, 4, 19}})
            .expect(new int[][]{{4, 2, 3}, {1, 3, 4}, {5, 1, 6}, {1, 3, 4}});

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new int[][]{{7, 3, 6}, {1, 4, 5}, {9, 8, 2}})
            .expect(new int[][]{{5, 1, 4}, {1, 2, 3}, {6, 3, 1}});

}
