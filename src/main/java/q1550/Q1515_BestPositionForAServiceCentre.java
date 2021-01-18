package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1515. Best Position for a Service Centre
 * https://leetcode.com/problems/best-position-for-a-service-centre/
 *
 * A delivery company wants to build a new service centre in a new city. The company knows the positions of all the
 * customers in this city on a 2D-Map and wants to build the new centre in a position such that the sum of the euclidean
 * distances to all customers is minimum.
 *
 * Given an array positions where positions[i] = [xi, yi] is the position of the ith customer on the map, return the
 * minimum sum of the euclidean distances to all customers.
 *
 * In other words, you need to choose the position of the service centre [xcentre, ycentre] such that the following
 * formula is minimized:
 * <img src="./Q1515_PIC1.png">
 * Answers within 10^-5 of the actual value will be accepted.
 *
 * Example 1:
 * <img src="./Q1515_PIC2.png">
 * Input: positions = [[0,1],[1,0],[1,2],[2,1]]
 * Output: 4.00000
 * Explanation: As shown, you can see that choosing [xcentre, ycentre] = [1, 1] will make the distance to each customer
 * = 1, the sum of all distances is 4 which is the minimum possible we can achieve.
 *
 * Example 2:
 * <img src="./Q1515_PIC3.png">
 * Input: positions = [[1,1],[3,3]]
 * Output: 2.82843
 * Explanation: The minimum possible sum of distances = sqrt(2) + sqrt(2) = 2.82843
 *
 * Example 3:
 *
 * Input: positions = [[1,1]]
 * Output: 0.00000
 *
 * Example 4:
 *
 * Input: positions = [[1,1],[0,0],[2,0]]
 * Output: 2.73205
 * Explanation: At the first glance, you may think that locating the centre at [1, 0] will achieve the minimum sum, but
 * locating it at [1, 0] will make the sum of distances = 3.
 * Try to locate the centre at [1.0, 0.5773502711] you will see that the sum of distances is 2.73205.
 * Be careful with the precision!
 *
 * Example 5:
 *
 * Input: positions = [[0,1],[3,2],[4,5],[7,6],[8,9],[11,1],[2,12]]
 * Output: 32.94036
 * Explanation: You can use [4.3460852395, 4.9813795505] as the position of the centre.
 *
 * Constraints:
 *
 * 1 <= positions.length <= 50
 * positions[i].length == 2
 * 0 <= positions[i][0], positions[i][1] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1515_BestPositionForAServiceCentre {

    /**
     * 梯度下降法
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/best-position-for-a-service-centre/solution/fu-wu-zhong-xin-de-zui-jia-wei-zhi-by-leetcode-sol/
     * @formatter:on
     */
    @Answer
    public double getMinDistSum(int[][] positions) {
        final int n = positions.length;
        final double eps = 1e-7;
        final double decay = 1e-3;
        double alpha = 1;

        // 调整批大小
        int batchSize = n;

        double x = 0.0, y = 0.0;
        for (int[] pos : positions) {
            x += pos[0];
            y += pos[1];
        }
        x /= n;
        y /= n;

        while (true) {
            // 将数据随机打乱
            shuffle(positions);
            double xPrev = x;
            double yPrev = y;

            for (int i = 0; i < n; i += batchSize) {
                int j = Math.min(i + batchSize, n);
                double dx = 0.0, dy = 0.0;
                // 计算导数，注意处理分母为零的情况
                for (int k = i; k < j; k++) {
                    int[] pos = positions[k];
                    dx += (x - pos[0]) / (Math.sqrt((x - pos[0]) * (x - pos[0]) + (y - pos[1]) * (y - pos[1])) + eps);
                    dy += (y - pos[1]) / (Math.sqrt((x - pos[0]) * (x - pos[0]) + (y - pos[1]) * (y - pos[1])) + eps);
                }
                x -= alpha * dx;
                y -= alpha * dy;

                // 每一轮迭代后，将学习率进行衰减
                alpha *= 1.0 - decay;
            }

            // 判断是否结束迭代
            if (Math.sqrt((x - xPrev) * (x - xPrev) + (y - yPrev) * (y - yPrev)) < eps) {
                break;
            }
        }

        return getDist(x, y, positions);
    }

    private Random rand = new Random();

    private void shuffle(int[][] positions) {
        final int n = positions.length;
        for (int i = 0; i < n; i++) {
            int x = positions[i][0], y = positions[i][1];
            int randIndex = rand.nextInt(n);
            positions[i][0] = positions[randIndex][0];
            positions[i][1] = positions[randIndex][1];
            positions[randIndex][0] = x;
            positions[randIndex][1] = y;
        }
    }

    // 计算服务中心 (xc, yc) 到客户的欧几里得距离之和
    private double getDist(double xc, double yc, int[][] positions) {
        double res = 0;
        for (int[] pos : positions) {
            res += Math.sqrt((pos[0] - xc) * (pos[0] - xc) + (pos[1] - yc) * (pos[1] - yc));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{0, 1}, {1, 0}, {1, 2}, {2, 1}})
            .expectDouble(4.00000);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {3, 3}})
            .expectDouble(2.82843);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 1}})
            .expectDouble(0.00000);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new int[][]{{1, 1}, {0, 0}, {2, 0}})
            .expectDouble(2.73205);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new int[][]{{0, 1}, {3, 2}, {4, 5}, {7, 6}, {8, 9}, {11, 1}, {2, 12}})
            .expectDouble(32.94036);

}
