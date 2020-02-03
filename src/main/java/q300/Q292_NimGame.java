package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/nim-game/
 *
 * You are playing the following Nim Game with your friend: There is a heap of stones on the table, each time one of
 * you take turns to remove 1 to 3 stones. The one who removes the last stone will be the winner. You will take the
 * first turn to remove the stones.
 *
 * Both of you are very clever and have optimal strategies for the game. Write a function to determine whether you
 * can win the game given the number of stones in the heap.
 *
 * Example:
 *
 * Input: 4
 * Output: false
 * Explanation: If there are 4 stones in the heap, then you will never win the game;
 * No matter 1, 2, or 3 stones you remove, the last stone will always be
 * removed by your friend.
 *
 * 题解: Nim 游戏, 桌子上有一堆石头, 每人每次可以移走1-3 个石头, 移走最后一个石头的人是胜者, 你先手. 问你是否能赢?
 */
@RunWith(LeetCodeRunner.class)
public class Q292_NimGame {

    /**
     * dp 的思路: 当前是否能赢, 在于是否能将对方逼到无论搬1/2/3 次都会输, 用dp 表示第n 块自己是否会输, 则
     * dp[n-1], dp[n-2], dp[n-3] 中如果有一个为false, 则自己可以搬走那么多数量(k), 让对方进入
     * dp[n-k]=false 的点, 对方搬的时候, 就肯定是输, 自己肯定赢.
     * 不过测试用例中的数字比较大, 一个一个找过去会超时, 不过观察dp 排列可以找到一个规律:
     * i     : 01 02 03 04 05 06 07 08 09 10 11 12 ...
     * dp[i] : √  √  √  ×  √  √  √  ×  √  √  √  ×  ...
     * 凡是 4 的倍数会输, 其它的会赢. (所以这个游戏的先手优势很大)
     */
    @Answer
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(4).expect(false);

    @TestData
    public DataExpectation arg5 = DataExpectation.create(5).expect(true);

    @TestData
    public DataExpectation arg6 = DataExpectation.create(6).expect(true);

    @TestData
    public DataExpectation arg8 = DataExpectation.create(8).expect(false);

    @TestData
    public DataExpectation overMemory = DataExpectation.create(1348820612).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(2147483647).expect(true);

}
