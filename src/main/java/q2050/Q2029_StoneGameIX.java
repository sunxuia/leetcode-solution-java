package q2050;

import org.junit.runner.RunWith;
import q1900.Q1872_StoneGameVIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 2029. Stone Game IX
 * https://leetcode.com/problems/stone-game-ix/
 *
 * Alice and Bob continue their games with stones. There is a row of n stones, and each stone has an associated value.
 * You are given an integer array stones, where stones[i] is the value of the ith stone.
 *
 * Alice and Bob take turns, with Alice starting first. On each turn, the player may remove any stone from stones. The
 * player who removes a stone loses if the sum of the values of all removed stones is divisible by 3. Bob will win
 * automatically if there are no remaining stones (even if it is Alice's turn).
 *
 * Assuming both players play optimally, return true if Alice wins and false if Bob wins.
 *
 * Example 1:
 *
 * Input: stones = [2,1]
 * Output: true
 * Explanation: The game will be played as follows:
 * - Turn 1: Alice can remove either stone.
 * - Turn 2: Bob removes the remaining stone.
 * The sum of the removed stones is 1 + 2 = 3 and is divisible by 3. Therefore, Bob loses and Alice wins the game.
 *
 * Example 2:
 *
 * Input: stones = [2]
 * Output: false
 * Explanation: Alice will remove the only stone, and the sum of the values on the removed stones is 2.
 * Since all the stones are removed and the sum of values is not divisible by 3, Bob wins the game.
 *
 * Example 3:
 *
 * Input: stones = [5,1,2,4,3]
 * Output: false
 * Explanation: Bob will always win. One possible way for Bob to win is shown below:
 * - Turn 1: Alice can remove the second stone with value 1. Sum of removed stones = 1.
 * - Turn 2: Bob removes the fifth stone with value 3. Sum of removed stones = 1 + 3 = 4.
 * - Turn 3: Alices removes the fourth stone with value 4. Sum of removed stones = 1 + 3 + 4 = 8.
 * - Turn 4: Bob removes the third stone with value 2. Sum of removed stones = 1 + 3 + 4 + 2 = 10.
 * - Turn 5: Alice removes the first stone with value 5. Sum of removed stones = 1 + 3 + 4 + 2 + 5 = 15.
 * Alice loses the game because the sum of the removed stones (15) is divisible by 3. Bob wins the game.
 *
 * Constraints:
 *
 * 1 <= stones.length <= 10^5
 * 1 <= stones[i] <= 10^4
 *
 * 上一题 {@link Q1872_StoneGameVIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q2029_StoneGameIX {

    /**
     * 将所有stone 都 %3, 统计数量.
     * 不考虑0 的数量的情况下, alice 和bob 为了避免失败, 能选择的序列是固定的:
     * - 如果alice 首先选择 1, 则序列是 (1-1)-(2-1)-(2-1)-..., 可以扣掉额外的 1 和 2;
     * - 如果alice 首先选择 2, 则序列是 (2-2)-(1-2)-(1-2)-..., 可以扣掉额外的 1 和 2;
     * 在考虑0 的情况下, 连续两次使用0 可以让结果与上面一致, 因此可以扣掉1 额外的结果;
     */
    @Answer
    public boolean stoneGameIX(int[] stones) {
        int[] cts = new int[3];
        for (int stone : stones) {
            cts[stone % 3]++;
        }
        cts[0] &= 1;
        // 避免扣出边界值的情况
        int minus = Math.max(0, Math.min(cts[1], cts[2]) - 2);
        cts[1] -= minus;
        cts[2] -= minus;
        return dfs(cts, 0, 1) == 1;
    }

    private int dfs(int[] cts, int sum, int p) {
        if (cts[0] == 0 && cts[1] == 0 && cts[2] == 0) {
            return -1;
        }
        int res = -p;
        if (cts[0] > 0 && sum % 3 != 0) {
            cts[0]--;
            res = dfs(cts, sum, -p);
            cts[0]++;
        }
        if (res == -p && cts[1] > 0 && (sum + 1) % 3 != 0) {
            cts[1]--;
            res = dfs(cts, (sum + 1) % 3, -p);
            cts[1]++;
        }
        if (res == -p && cts[2] > 0 && (sum + 2) % 3 != 0) {
            cts[2]--;
            res = dfs(cts, (sum + 2) % 3, -p);
            cts[2]++;
        }
        return res;
    }

    /**
     * LeetCode 上最快的解法, 是对上面规律的进一步利用.
     */
    @Answer
    public boolean stoneGameIX2(int[] stones) {
        int[] cts = new int[3];
        for (int stone : stones) {
            cts[stone % 3]++;
        }
        if (cts[0] % 2 == 0 && (cts[1] == 0 || cts[2] == 0)) {
            return false;
        }
        return Math.abs(cts[1] - cts[2]) >= 3 || cts[0] % 2 != 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{5, 1, 2, 4, 3}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{20, 3, 20, 17, 2, 12, 15, 17, 4}).expect(true);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(new int[]{2, 33, 90, 62, 43, 21, 96, 20, 18, 84, 74, 61, 100, 5, 11, 4, 67, 96, 18, 6, 68, 82, 32,
                    76, 33, 93, 33, 71, 32, 30, 63, 37, 46, 95, 51, 63, 77, 63, 84, 52, 78, 66, 76, 66, 9, 73, 92, 79,
                    65, 29, 42, 64, 46, 84, 95, 71, 15, 68, 55, 9, 22, 64, 56, 83, 52, 47, 38, 19, 59, 32, 89, 29, 56,
                    84, 57, 90, 96, 19, 38, 13, 49, 65, 93, 8, 30, 15, 12, 40, 84, 7, 6, 75, 36, 31, 6, 78, 64, 33, 49})
            .expect(false);

    @TestData
    public DataExpectation overMemory = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(true);

}
