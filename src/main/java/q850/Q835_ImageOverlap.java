package q850;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/image-overlap/
 *
 * Two images A and B are given, represented as binary, square matrices of the same size.  (A binary matrix has only
 * 0s and 1s as values.)
 *
 * We translate one image however we choose (sliding it left, right, up, or down any number of units), and place it
 * on top of the other image.  After, the overlap of this translation is the number of positions that have a 1 in
 * both images.
 *
 * (Note also that a translation does not include any kind of rotation.)
 *
 * What is the largest possible overlap?
 *
 * Example 1:
 *
 * > Input: A = [[1,1,0],
 * >             [0,1,0],
 * >             [0,1,0]]
 * >        B = [[0,0,0],
 * >             [0,1,1],
 * >             [0,0,1]]
 * Output: 3
 * Explanation: We slide A to right by 1 unit and down by 1 unit.
 *
 * Notes:
 *
 * 1 <= A.length = A[0].length = B.length = B[0].length <= 30
 * 0 <= A[i][j], B[i][j] <= 1
 */
@RunWith(LeetCodeRunner.class)
public class Q835_ImageOverlap {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/10355589.html
     * 通过统计A 和B 中1 的位置, 计算他们之间的相差, 就是移动的距离, 该移动距离此时至少会有一个1 重合.
     * LeetCode 中最快的方式是通过位来表示每行的01值, 然后遍历所有的位移情况来统计结果的.
     */
    @Answer
    public int largestOverlap(int[][] A, int[][] B) {
        final int n = A.length;
        List<Integer> oneA = new ArrayList<>();
        List<Integer> oneB = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] == 1) {
                    oneA.add(i * n + j);
                }
                if (B[i][j] == 1) {
                    oneB.add(i * n + j);
                }
            }
        }

        int[][] moves = new int[n * 2][n * 2];
        for (int a : oneA) {
            int ai = a / n, aj = a % n;
            for (int b : oneB) {
                int bi = b / n, bj = b % n;
                moves[n + ai - bi][n + aj - bj]++;
            }
        }

        int res = 0;
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                res = Math.max(res, moves[i][j]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[][]{
            {1, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    }, new int[][]{
            {0, 0, 0},
            {0, 1, 1},
            {0, 0, 1}
    }).expect(3);

}
