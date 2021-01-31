package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1640. Check Array Formation Through Concatenation
 * https://leetcode.com/problems/check-array-formation-through-concatenation/
 *
 * You are given an array of distinct integers arr and an array of integer arrays pieces, where the integers in pieces
 * are distinct. Your goal is to form arr by concatenating the arrays in pieces in any order. However, you are not
 * allowed to reorder the integers in each array pieces[i].
 *
 * Return true if it is possible to form the array arr from pieces. Otherwise, return false.
 *
 * Example 1:
 *
 * Input: arr = [85], pieces = [[85]]
 * Output: true
 *
 * Example 2:
 *
 * Input: arr = [15,88], pieces = [[88],[15]]
 * Output: true
 * Explanation: Concatenate [15] then [88]
 *
 * Example 3:
 *
 * Input: arr = [49,18,16], pieces = [[16,18,49]]
 * Output: false
 * Explanation: Even though the numbers match, we cannot reorder pieces[0].
 *
 * Example 4:
 *
 * Input: arr = [91,4,64,78], pieces = [[78],[4,64],[91]]
 * Output: true
 * Explanation: Concatenate [91] then [4,64] then [78]
 *
 * Example 5:
 *
 * Input: arr = [1,3,5,7], pieces = [[2,4,6,8]]
 * Output: false
 *
 * Constraints:
 *
 * 1 <= pieces.length <= arr.length <= 100
 * sum(pieces[i].length) == arr.length
 * 1 <= pieces[i].length <= arr.length
 * 1 <= arr[i], pieces[i][j] <= 100
 * The integers in arr are distinct.
 * The integers in pieces are distinct (i.e., If we flatten pieces in a 1D array, all the integers in this array are
 * distinct).
 */
@RunWith(LeetCodeRunner.class)
public class Q1640_CheckArrayFormationThroughConcatenation {

    @Answer
    public boolean canFormArray(int[] arr, int[][] pieces) {
        int[][] map = new int[101][];
        for (int[] piece : pieces) {
            map[piece[0]] = piece;
        }
        int[] curr = new int[0];
        int currIndex = 0;
        for (int num : arr) {
            if (currIndex == curr.length) {
                curr = map[num];
                if (curr == null) {
                    return false;
                }
                currIndex = 0;
            }
            if (curr[currIndex++] != num) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{85}, new int[][]{{85}})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{15, 88}, new int[][]{{88}, {15}})
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{49, 18, 16}, new int[][]{{16, 18, 49}})
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{91, 4, 64, 78}, new int[][]{{78}, {4, 64}, {91}})
            .expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{1, 3, 5, 7}, new int[][]{{2, 4, 6, 8}})
            .expect(false);

}
