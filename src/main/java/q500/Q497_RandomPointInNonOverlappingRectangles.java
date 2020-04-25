package q500;

import java.util.Arrays;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/random-point-in-non-overlapping-rectangles/
 *
 * Given a list of non-overlapping axis-aligned rectangles rects, write a function pick which randomly and uniformily
 * picks an integer point in the space covered by the rectangles.
 *
 * Note:
 *
 * An integer point is a point that has integer coordinates.
 * A point on the perimeter of a rectangle is included in the space covered by the rectangles.
 * ith rectangle = rects[i] = [x1,y1,x2,y2], where [x1, y1] are the integer coordinates of the bottom-left
 * corner, and [x2, y2] are the integer coordinates of the top-right corner.
 * length and width of each rectangle does not exceed 2000.
 * 1 <= rects.length <= 100
 * pick return a point as an array of integer coordinates [p_x, p_y]
 * pick is called at most 10000 times.
 *
 * Example 1:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[[[1,1,5,5]]],[],[],[]]
 * Output:
 * [null,[4,1],[4,1],[3,3]]
 *
 * Example 2:
 *
 * Input:
 * ["Solution","pick","pick","pick","pick","pick"]
 * [[[[-2,-2,-1,-1],[1,0,3,0]]],[],[],[],[],[]]
 * Output:
 * [null,[-1,-2],[2,0],[-2,-1],[3,0],[-2,-2]]
 *
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has one argument, the
 * array of rectangles rects. pick has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 */
public class Q497_RandomPointInNonOverlappingRectangles {

    private static class Solution {

        private int[][] rects;

        private int[] areaSum;

        private int totalArea;

        private Random random = new Random();

        public Solution(int[][] rects) {
            this.rects = rects;
            this.areaSum = new int[rects.length];
            for (int i = 0; i < rects.length; i++) {
                totalArea += (rects[i][2] - rects[i][0] + 1) * (rects[i][3] - rects[i][1] + 1);
                areaSum[i] = totalArea;
            }
        }

        public int[] pick() {
            int randomArea = random.nextInt(totalArea + 1);
            int r = Arrays.binarySearch(areaSum, randomArea);
            r = r >= 0 ? r : -r - 1;
            int[] res = new int[2];
            res[0] = random.nextInt(rects[r][2] - rects[r][0] + 1) + rects[r][0];
            res[1] = random.nextInt(rects[r][3] - rects[r][1] + 1) + rects[r][1];
            return res;
        }
    }

    @Test
    public void testMethod() {
        assertRight(new int[][]{{1, 1, 5, 5}});
        assertRight(new int[][]{{-2, -2, -1, -1}, {1, 0, 3, 0}});
        assertRight(new int[][]{
                {53487036, -14015982, 53487038, -14015981},
                {-50242787, -25148635, -50242784, -25148633},
                {1261120, -29805890, 1261122, -29805889},
                {28991269, 54212557, 28991271, 54212559}});
    }

    private void assertRight(int[][] rects) {
        Solution solution = new Solution(rects);
        for (int i = 0; i < 10; i++) {
            int[] p = solution.pick();
            Assert.assertTrue(isInRange(rects, p));
        }
    }

    private boolean isInRange(int[][] rects, int[] point) {
        for (int[] rect : rects) {
            if (point[0] >= rect[0]
                    && point[0] <= rect[2]
                    && point[1] >= rect[1]
                    && point[1] <= rect[3]) {
                return true;
            }
        }
        return false;
    }

}
