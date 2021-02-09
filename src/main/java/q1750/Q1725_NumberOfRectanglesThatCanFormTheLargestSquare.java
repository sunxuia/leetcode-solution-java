package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1725. Number Of Rectangles That Can Form The Largest Square
 * https://leetcode.com/problems/number-of-rectangles-that-can-form-the-largest-square/
 *
 * You are given an array rectangles where rectangles[i] = [li, wi] represents the ith rectangle of length li and width
 * wi.
 *
 * You can cut the ith rectangle to form a square with a side length of k if both k <= li and k <= wi. For example, if
 * you have a rectangle [4,6], you can cut it to get a square with a side length of at most 4.
 *
 * Let maxLen be the side length of the largest square you can obtain from any of the given rectangles.
 *
 * Return the number of rectangles that can make a square with a side length of maxLen.
 *
 * Example 1:
 *
 * Input: rectangles = [[5,8],[3,9],[5,12],[16,5]]
 * Output: 3
 * Explanation: The largest squares you can get from each rectangle are of lengths [5,3,5,5].
 * The largest possible square is of length 5, and you can get it out of 3 rectangles.
 *
 * Example 2:
 *
 * Input: rectangles = [[2,3],[3,7],[4,3],[3,7]]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= rectangles.length <= 1000
 * rectangles[i].length == 2
 * 1 <= li, wi <= 10^9
 * li != wi
 */
@RunWith(LeetCodeRunner.class)
public class Q1725_NumberOfRectanglesThatCanFormTheLargestSquare {

    @Answer
    public int countGoodRectangles(int[][] rectangles) {
        int maxLen = 0;
        for (int[] rectangle : rectangles) {
            maxLen = Math.max(maxLen, Math.min(rectangle[0], rectangle[1]));
        }

        int res = 0;
        for (int[] rectangle : rectangles) {
            if (Math.min(rectangle[0], rectangle[1]) == maxLen) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{5, 8}, {3, 9}, {5, 12}, {16, 5}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{2, 3}, {3, 7}, {4, 3}, {3, 7}})
            .expect(3);

}
