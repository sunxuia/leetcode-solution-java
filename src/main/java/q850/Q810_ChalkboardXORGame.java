package q850;

import org.junit.runner.RunWith;
import q300.Q292_NimGame;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/chalkboard-xor-game/
 *
 * We are given non-negative integers nums[i] which are written on a chalkboard.  Alice and Bob take turns erasing
 * exactly one number from the chalkboard, with Alice starting first.  If erasing a number causes the bitwise XOR of
 * all the elements of the chalkboard to become 0, then that player loses.  (Also, we'll say the bitwise XOR of one
 * element is that element itself, and the bitwise XOR of no elements is 0.)
 *
 * Also, if any player starts their turn with the bitwise XOR of all the elements of the chalkboard equal to 0, then
 * that player wins.
 *
 * Return True if and only if Alice wins the game, assuming both players play optimally.
 *
 * Example:
 * Input: nums = [1, 1, 2]
 * Output: false
 * Explanation:
 * Alice has two choices: erase 1 or erase 2.
 * If she erases 1, the nums array becomes [1, 2]. The bitwise XOR of all the elements of the chalkboard is 1 XOR 2 =
 * 3. Now Bob can remove any element he wants, because Alice will be the one to erase the last element and she will
 * lose.
 * If Alice erases 2 first, now nums becomes [1, 1]. The bitwise XOR of all the elements of the chalkboard is 1 XOR 1
 * = 0. Alice will lose.
 *
 * 类似题目: {@link Q292_NimGame}
 */
@RunWith(LeetCodeRunner.class)
public class Q810_ChalkboardXORGame {

    // 参考文档 https://www.cnblogs.com/grandyang/p/9445951.html
    // dfs 会超时, 需要找到这题的trick
    @Answer
    public boolean xorGame(int[] nums) {
        if (nums.length % 2 == 0) {
            return true;
        }

        int mask = 0;
        for (int num : nums) {
            mask ^= num;
        }
        return mask == 0;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 1, 2}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 1}).expect(true);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(new int[]{0, 0, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 0, 2, 1, 1, 0})
            .expect(true);

}
