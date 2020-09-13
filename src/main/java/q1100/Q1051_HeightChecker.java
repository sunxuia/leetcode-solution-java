package q1100;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1051. Height Checker
 * https://leetcode.com/problems/height-checker/
 *
 * Students are asked to stand in non-decreasing order of heights for an annual photo.
 *
 * Return the minimum number of students that must move in order for all students to be standing in non-decreasing order
 * of height.
 *
 * Notice that when a group of students is selected they can reorder in any possible way between themselves and the non
 * selected students remain on their seats.
 *
 * Example 1:
 *
 * Input: heights = [1,1,4,2,1,3]
 * Output: 3
 * Explanation:
 * Current array : [1,1,4,2,1,3]
 * Target array  : [1,1,1,2,3,4]
 * On index 2 (0-based) we have 4 vs 1 so we have to move this student.
 * On index 4 (0-based) we have 1 vs 3 so we have to move this student.
 * On index 5 (0-based) we have 3 vs 4 so we have to move this student.
 *
 * Example 2:
 *
 * Input: heights = [5,1,2,3,4]
 * Output: 5
 *
 * Example 3:
 *
 * Input: heights = [1,2,3,4,5]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= heights.length <= 100
 * 1 <= heights[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1051_HeightChecker {

    @Answer
    public int heightChecker(int[] heights) {
        int[] arr = heights.clone();
        Arrays.sort(arr);
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res += arr[i] == heights[i] ? 0 : 1;
        }
        return res;
    }

    // 桶排序
    @Answer
    public int heightChecker2(int[] heights) {
        int[] bucket = new int[101];
        for (int height : heights) {
            bucket[height]++;
        }
        int res = 0, h = 0;
        for (int height : heights) {
            while (bucket[h] == 0) {
                h++;
            }
            bucket[h]--;
            res += height == h ? 0 : 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 4, 2, 1, 3}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 1, 2, 3, 4}).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(0);

}
