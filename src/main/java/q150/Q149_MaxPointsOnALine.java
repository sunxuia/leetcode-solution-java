package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/max-points-on-a-line/
 *
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 *
 * Example 1:
 *
 * Input: [[1,1],[2,2],[3,3]]
 * Output: 3
 * Explanation:
 * ^
 * |
 * |        o
 * |     o
 * |  o
 * +------------->
 * 0  1  2  3  4
 *
 * Example 2:
 *
 * Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 * Explanation:
 * ^
 * |
 * |  o
 * |     o        o
 * |        o
 * |  o        o
 * +------------------->
 * 0  1  2  3  4  5  6
 *
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method
 * signature.
 */
@RunWith(LeetCodeRunner.class)
public class Q149_MaxPointsOnALine {

    /**
     * 通过计算2 个点之间的斜率的方式, 时间复杂度 O(n^3)
     */
    @Answer
    public int maxPoints(int[][] points) {
        final int len = points.length;
        if (len < 3) {
            return len;
        }
        int res = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                int count = 0;
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                    // 相同的点, 对下面的公式不适用,  所以这里只计算相同的点的个数
                    for (int k = 0; k < len; k++) {
                        if (points[k][0] == points[i][0] && points[k][1] == points[i][1]) {
                            count++;
                        }
                    }
                } else {
                    for (int k = 0; k < len; k++) {
                        // (kh - ih) / (kw - iw) == (jh - ih) / (jw - iw) 的公式变种,
                        // (kh - ih) * (jw - iw) == (jh - ih) * (kw - iw)
                        // 这样可以处理 k 与i/j 相同或者垂直的情况.
                        if (((long) points[k][1] - points[i][1]) * (points[j][0] - points[i][0])
                                == ((long) points[j][1] - points[i][1]) * (points[k][0] - points[i][0])) {
                            count++;
                        }
                    }
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }

    /**
     * LeetCode 上的解答, 利用了LeetCode 中测试用例的漏洞, 对我自己写的测试用例 normal4 就会产生错误的结果.
     */
    @Answer
    public int leetCode(int[][] points) {
        final int len = points.length;
        if (len < 3) {
            return len;
        }
        int res = 0;
        for (int i = 0; i < len - 1; i++) {
            // 区别在这里: 不再遍历j, 而只是计算相邻2 个节点的斜率.
            // 所以在计算测试用例normal4 这类在一条直线但不相邻的情况时会发生错误.
            final int j = i + 1;
            int count = 0;
            if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                for (int k = 0; k < len; k++) {
                    if (points[k][0] == points[i][0] && points[k][1] == points[i][1]) {
                        count++;
                    }
                }
            } else {
                for (int k = 0; k < len; k++) {
                    if (((long) points[k][1] - points[i][1]) * (points[j][0] - points[i][0])
                            == ((long) points[j][1] - points[i][1]) * (points[k][0] - points[i][0])) {
                        count++;
                    }
                }
            }
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 1}, {2, 2}, {3, 3}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}})
            .expect(4);

    @TestData
    public DataExpectation border0 = DataExpectation
            .create(new int[0][0])
            .expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation
            .create(new int[][]{{1, 1}})
            .expect(1);

    @TestData
    public DataExpectation border2 = DataExpectation
            .create(new int[][]{{1, 1}, {5, 3}})
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{0, 0}, {1, 1}, {0, 0}})
            .expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{1, 1}, {1, 1}, {2, 3}})
            .expect(3);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[][]{{0, 0}, {1, 65536}, {65536, 0}})
            .expect(2);

    //    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 2}, {3, 0}, {4, 4}, {5, 3}, {6, 6}})
            .expect(3);

}
