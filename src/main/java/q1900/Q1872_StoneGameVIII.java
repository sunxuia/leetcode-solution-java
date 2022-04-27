package q1900;

import org.junit.runner.RunWith;
import q1700.Q1690_StoneGameVII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1872. Stone Game VIII
 * https://leetcode.com/problems/stone-game-viii/
 *
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * There are n stones arranged in a row. On each player's turn, while the number of stones is more than one, they will
 * do the following:
 *
 * Choose an integer x > 1, and remove the leftmost x stones from the row.
 * Add the sum of the removed stones' values to the player's score.
 * Place a new stone, whose value is equal to that sum, on the left side of the row.
 *
 * The game stops when only one stone is left in the row.
 *
 * The score difference between Alice and Bob is (Alice's score - Bob's score). Alice's goal is to maximize the score
 * difference, and Bob's goal is the minimize the score difference.
 *
 * Given an integer array stones of length n where stones[i] represents the value of the ith stone from the left, return
 * the score difference between Alice and Bob if they both play optimally.
 *
 * Example 1:
 *
 * Input: stones = [-1,2,-3,4,-5]
 * Output: 5
 * Explanation:
 * - Alice removes the first 4 stones, adds (-1) + 2 + (-3) + 4 = 2 to her score, and places a stone of
 * value 2 on the left. stones = [2,-5].
 * - Bob removes the first 2 stones, adds 2 + (-5) = -3 to his score, and places a stone of value -3 on
 * the left. stones = [-3].
 * The difference between their scores is 2 - (-3) = 5.
 *
 * Example 2:
 *
 * Input: stones = [7,-6,5,10,5,-2,-6]
 * Output: 13
 * Explanation:
 * - Alice removes all stones, adds 7 + (-6) + 5 + 10 + 5 + (-2) + (-6) = 13 to her score, and places a
 * stone of value 13 on the left. stones = [13].
 * The difference between their scores is 13 - 0 = 13.
 *
 * Example 3:
 *
 * Input: stones = [-10,-12]
 * Output: -22
 * Explanation:
 * - Alice can only make one move, which is to remove both stones. She adds (-10) + (-12) = -22 to her
 * score and places a stone of value -22 on the left. stones = [-22].
 * The difference between their scores is (-22) - 0 = -22.
 *
 * Constraints:
 *
 * n == stones.length
 * 2 <= n <= 10^5
 * -10^4 <= stones[i] <= 10^4
 *
 * 上一题 {@link Q1690_StoneGameVII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1872_StoneGameVIII {

    @Answer
    public int stoneGameVIII(int[] stones) {
        final int n = stones.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }

        // dpAlice[i] 表示alice 在从 stones[i] 开始选择时, 能达到的alice-bob 的最大值
        int[] dpAlice = new int[n - 1];
        // dpBob[i] 表示bob 在从stones[i] 开始选择时, 能达到的alice-bob 的最小值
        int[] dpBob = new int[n - 1];
        // 初始化, 只有最后2 个元素的情况, alice 与bob 都是选择所有元素.
        dpAlice[n - 2] = sums[n];
        dpBob[n - 2] = -sums[n];
        for (int i = n - 3; i >= 0; i--) {
            // 选择 stones[i] 是将其放在新区间还是并到之前的区间中
            dpAlice[i] = Math.max(dpAlice[i + 1], sums[i + 2] + dpBob[i + 1]);
            dpBob[i] = Math.min(dpBob[i + 1], dpAlice[i + 1] - sums[i + 2]);
        }
        return dpAlice[0];
    }

    /**
     * leetcode 上比较快的解法, 是对上面解法的进一步优化.
     */
    @Answer
    public int stoneGameVIII_leetcode(int[] stones) {
        final int n = stones.length;
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }

        int res = sum;
        for (int i = n - 1; i >= 2; --i) {
            sum -= stones[i];
            res = Math.max(res, sum - res);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{-1, 2, -3, 4, -5}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, -6, 5, 10, 5, -2, -6}).expect(13);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-10, -12}).expect(-22);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{-4, -2, -3, -1}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{-39, -23, -43, -7, 25, -36, -32, 17, -42, -5, -11})
            .expect(11);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{-53, -56, 90, -74, -50, 29, 37, 64, -31, -54, 74, -80, -18, -69, -44, 73, 99, -47, -35,
                    71, -55, -27, 34, 1, -66, -63, 3, -34, 33, 91, -25, -40, -33, 68, -34, -32, 69, 44, -54})
            .expect(54);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(59846);

}
