package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/image-smoother/
 *
 * Given a 2D integer matrix M representing the gray scale of an image, you need to design a smoother to make the
 * gray scale of each cell becomes the average gray scale (rounding down) of all the 8 surrounding cells and itself.
 * If a cell has less than 8 surrounding cells, then use as many as you can.
 *
 * Example 1:
 *
 * Input:
 * [[1,1,1],
 * [1,0,1],
 * [1,1,1]]
 * Output:
 * [[0, 0, 0],
 * [0, 0, 0],
 * [0, 0, 0]]
 * Explanation:
 * For the point (0,0), (0,2), (2,0), (2,2): floor(3/4) = floor(0.75) = 0
 * For the point (0,1), (1,0), (1,2), (2,1): floor(5/6) = floor(0.83333333) = 0
 * For the point (1,1): floor(8/9) = floor(0.88888889) = 0
 *
 * Note:
 *
 * 1. The value in the given matrix is in the range of [0, 255].
 * 2. The length and width of the given matrix are in the range of [1, 150].
 */
@RunWith(LeetCodeRunner.class)
public class Q661_ImageSmoother {

    @Answer
    public int[][] imageSmoother(int[][] M) {
        int m = M.length, n = M[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 1;
                int sum = M[i][j];
                if (j > 0) {
                    count++;
                    sum += M[i][j - 1] & 255;
                }
                if (i > 0 && j > 0) {
                    count++;
                    sum += M[i - 1][j - 1] & 255;
                }
                if (i > 0) {
                    count++;
                    sum += M[i - 1][j] & 255;
                }
                if (i > 0 && j < n - 1) {
                    count++;
                    sum += M[i - 1][j + 1] & 255;
                }
                if (j < n - 1) {
                    count++;
                    sum += M[i][j + 1];
                }
                if (i < m - 1 && j < n - 1) {
                    count++;
                    sum += M[i + 1][j + 1];
                }
                if (i < m - 1) {
                    count++;
                    sum += M[i + 1][j];
                }
                if (i < m - 1 && j > 0) {
                    count++;
                    sum += M[i + 1][j - 1];
                }
                M[i][j] += (sum / count) << 8;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = M[i][j] >> 8;
            }
        }
        return M;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    }).expect(new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    });

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {2, 3, 4},
            {5, 6, 7},
            {8, 9, 10},
            {11, 12, 13},
            {14, 15, 16}
    }).expect(new int[][]{
            {4, 4, 5},
            {5, 6, 6},
            {8, 9, 9},
            {11, 12, 12},
            {13, 13, 14}
    });

}
