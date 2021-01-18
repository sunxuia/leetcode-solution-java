package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1503. Last Moment Before All Ants Fall Out of a Plank
 * https://leetcode.com/problems/last-moment-before-all-ants-fall-out-of-a-plank/
 *
 * We have a wooden plank of the length n units. Some ants are walking on the plank, each ant moves with speed 1 unit
 * per second. Some of the ants move to the left, the other move to the right.
 *
 * When two ants moving in two different directions meet at some point, they change their directions and continue moving
 * again. Assume changing directions doesn't take any additional time.
 *
 * When an ant reaches one end of the plank at a time t, it falls out of the plank imediately.
 *
 * Given an integer n and two integer arrays left and right, the positions of the ants moving to the left and the right.
 * Return the moment when the last ant(s) fall out of the plank.
 *
 * Example 1:
 * <img src="./Q1503_PIC1.png">
 * Input: n = 4, left = [4,3], right = [0,1]
 * Output: 4
 * Explanation: In the image above:
 * -The ant at index 0 is named A and going to the right.
 * -The ant at index 1 is named B and going to the right.
 * -The ant at index 3 is named C and going to the left.
 * -The ant at index 4 is named D and going to the left.
 * Note that the last moment when an ant was on the plank is t = 4 second, after that it falls imediately out of the
 * plank. (i.e. We can say that at t = 4.0000000001, there is no ants on the plank).
 *
 * Example 2:
 * <img src="./Q1503_PIC2.png">
 * Input: n = 7, left = [], right = [0,1,2,3,4,5,6,7]
 * Output: 7
 * Explanation: All ants are going to the right, the ant at index 0 needs 7 seconds to fall.
 *
 * Example 3:
 * <img src="./Q1503_PIC3.png">
 * Input: n = 7, left = [0,1,2,3,4,5,6,7], right = []
 * Output: 7
 * Explanation: All ants are going to the left, the ant at index 7 needs 7 seconds to fall.
 *
 * Example 4:
 *
 * Input: n = 9, left = [5], right = [4]
 * Output: 5
 * Explanation: At t = 1 second, both ants will be at the same intial position but with different direction.
 *
 * Example 5:
 *
 * Input: n = 6, left = [6], right = [0]
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 * 0 <= left.length <= n + 1
 * 0 <= left[i] <= n
 * 0 <= right.length <= n + 1
 * 0 <= right[i] <= n
 * 1 <= left.length + right.length <= n + 1
 * All values of left and right are unique, and each value can appear only in one of the two arrays.
 */
@RunWith(LeetCodeRunner.class)
public class Q1503_LastMomentBeforeAllAntsFallOutOfAPlank {

    /**
     * 脑筋急转弯的题目.
     * 这题的条件两种蚂蚁碰头后立刻调转方向, 和两只蚂蚁没有调转方向是等效的,
     * 向左向右的蚂蚁变成了向右向左的蚂蚁, 走到另一边的时间是一样的.
     */
    @Answer
    public int getLastMoment(int n, int[] left, int[] right) {
        int maxLeft = 0, minRight = n;
        for (int i : left) {
            maxLeft = Math.max(maxLeft, i);
        }
        for (int i : right) {
            minRight = Math.min(minRight, i);
        }
        return Math.max(maxLeft, n - minRight);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[]{4, 3}, new int[]{0, 1})
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(7, new int[]{}, new int[]{0, 1, 2, 3, 4, 5, 6, 7})
            .expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(7, new int[]{0, 1, 2, 3, 4, 5, 6, 7}, new int[]{})
            .expect(7);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(9, new int[]{5}, new int[]{4})
            .expect(5);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(6, new int[]{6}, new int[]{0})
            .expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(20, new int[]{4, 7, 15}, new int[]{9, 3, 13, 10})
            .expect(17);

}
