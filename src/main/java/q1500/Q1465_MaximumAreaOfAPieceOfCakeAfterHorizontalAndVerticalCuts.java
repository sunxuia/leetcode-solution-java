package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1465. Maximum Area of a Piece of Cake After Horizontal and Vertical Cuts
 * https://leetcode.com/problems/maximum-area-of-a-piece-of-cake-after-horizontal-and-vertical-cuts/
 *
 * Given a rectangular cake with height h and width w, and two arrays of integers horizontalCuts and verticalCuts where
 * horizontalCuts[i] is the distance from the top of the rectangular cake to the ith horizontal cut and similarly,
 * verticalCuts[j] is the distance from the left of the rectangular cake to the jth vertical cut.
 *
 * Return the maximum area of a piece of cake after you cut at each horizontal and vertical position provided in the
 * arrays horizontalCuts and verticalCuts. Since the answer can be a huge number, return this modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./Q1465_PIC1.png">
 * Input: h = 5, w = 4, horizontalCuts = [1,2,4], verticalCuts = [1,3]
 * Output: 4
 * Explanation: The figure above represents the given rectangular cake. Red lines are the horizontal and vertical cuts.
 * After you cut the cake, the green piece of cake has the maximum area.
 *
 * Example 2:
 * <img src="./Q1465_PIC2.png">
 * Input: h = 5, w = 4, horizontalCuts = [3,1], verticalCuts = [1]
 * Output: 6
 * Explanation: The figure above represents the given rectangular cake. Red lines are the horizontal and vertical cuts.
 * After you cut the cake, the green and yellow pieces of cake have the maximum area.
 *
 * Example 3:
 *
 * Input: h = 5, w = 4, horizontalCuts = [3], verticalCuts = [3]
 * Output: 9
 *
 * Constraints:
 *
 * 2 <= h, w <= 10^9
 * 1 <= horizontalCuts.length < min(h, 10^5)
 * 1 <= verticalCuts.length < min(w, 10^5)
 * 1 <= horizontalCuts[i] < h
 * 1 <= verticalCuts[i] < w
 * It is guaranteed that all elements in horizontalCuts are distinct.
 * It is guaranteed that all elements in verticalCuts are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1465_MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts {

    @Answer
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int height = 0, width = 0;
        for (int i = 0; i <= horizontalCuts.length; i++) {
            int prev = i == 0 ? 0 : horizontalCuts[i - 1];
            int curr = i == horizontalCuts.length ? h : horizontalCuts[i];
            height = Math.max(height, curr - prev);
        }
        for (int i = 0; i <= verticalCuts.length; i++) {
            int prev = i == 0 ? 0 : verticalCuts[i - 1];
            int curr = i == verticalCuts.length ? w : verticalCuts[i];
            width = Math.max(width, curr - prev);
        }
        return (int) ((long) height * width % 10_0000_0007);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, 4, new int[]{1, 2, 4}, new int[]{1, 3})
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, 4, new int[]{3, 1}, new int[]{1})
            .expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, 4, new int[]{3}, new int[]{3})
            .expect(9);

}
