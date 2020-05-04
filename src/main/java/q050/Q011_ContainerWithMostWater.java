package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical
 * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together
 * with x-axis forms a container, such that the container contains the most water.
 *
 * Note: You may not slant the container and n is at least 2.
 *
 * (图片见 Q011_PIC.jpg)
 *
 * The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue
 * section) the container can contain is 49.
 * Example:
 *
 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 *
 * 题解:
 * 简单来说就是找出柱状图中的2 个柱子, 以为两壁, 以x 轴为底, 构成一个蓄水池(柱子没有体积), 求最大的蓄水池的蓄水平方.
 */
@RunWith(LeetCodeRunner.class)
public class Q011_ContainerWithMostWater {

    /**
     * 暴力解法. 这个时间复杂度 O(n^2)
     */
    @Answer
    public int bruteForce(int[] height) {
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int w = j - i, h = Math.min(height[i], height[j]);
                res = Math.max(w * h, res);
            }
        }
        return res;
    }

    /**
     * 两边逼近的方式.
     */
    @Answer
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int res = 0;
        while (left < right) {
            res = Math.max(res, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) {
                // 逼近的时候进行了一些优化, 如果下一个柱子还没有上一个高, 面积肯定要比原来小.
                // 用比较操作替代了乘法和交换操作, 基本没节省时间.
                int oLeft = left;
                do {
                    left++;
                } while (height[left] <= height[oLeft] && left < right);
            } else {
                int oRight = right;
                do {
                    right--;
                } while (height[right] <= height[oRight] && left < right);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.builder()
            .addArgument(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})
            .expect(49)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 4, 3})
            .expect(4)
            .build();

}
