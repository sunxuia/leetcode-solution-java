package q700;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/stickers-to-spell-word/
 *
 * We are given N different types of stickers. Each sticker has a lowercase English word on it.
 *
 * You would like to spell out the given target string by cutting individual letters from your collection of stickers
 * and rearranging them.
 *
 * You can use each sticker more than once if you want, and you have infinite quantities of each sticker.
 *
 * What is the minimum number of stickers that you need to spell out the target? If the task is impossible, return -1.
 *
 * Example 1:
 *
 * Input:
 *
 * ["with", "example", "science"], "thehat"
 *
 * Output:
 *
 * 3
 *
 * Explanation:
 *
 * We can use 2 "with" stickers, and 1 "example" sticker.
 * After cutting and rearrange the letters of those stickers, we can form the target "thehat".
 * Also, this is the minimum number of stickers necessary to form the target string.
 *
 * Example 2:
 *
 * Input:
 *
 * ["notice", "possible"], "basicbasic"
 *
 * Output:
 *
 * -1
 *
 * Explanation:
 *
 * We can't form the target "basicbasic" from cutting letters from the given stickers.
 *
 * Note:
 * 1. stickers has length in the range [1, 50].
 * 2. stickers consists of lowercase English words (without apostrophes).
 * 3. target has length in the range [1, 15], and consists of lowercase English letters.
 * 4. In all test cases, all words were chosen randomly from the 1000 most common US English words, and the target was
 * chosen as a concatenation of two random words.
 * 5. The time limit may be more challenging than usual. It is expected that a 50 sticker test case can be solved within
 * 35ms on average.
 */
@RunWith(LeetCodeRunner.class)
public class Q691_StickersToSpellWord {

    /**
     * 多重背包问题, Solution 中给出的DP 解法如下:
     * 说明可以参见 https://www.cnblogs.com/grandyang/p/8468045.html
     */
    @Answer
    public int minStickers(String[] stickers, String target) {
        int n = target.length();
        int[] dp = new int[1 << n];
        Arrays.fill(dp, 1, 1 << n, -1);

        for (int state = 0; state < 1 << n; state++) {
            if (dp[state] == -1) {
                continue;
            }
            for (String sticker : stickers) {
                int now = state;
                for (char letter : sticker.toCharArray()) {
                    for (int i = 0; i < n; i++) {
                        if (((now >> i) & 1) == 1) {
                            continue;
                        }
                        if (target.charAt(i) == letter) {
                            now |= 1 << i;
                            break;
                        }
                    }
                }
                if (dp[now] == -1 || dp[now] > dp[state] + 1) {
                    dp[now] = dp[state] + 1;
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"with", "example", "science"}, "thehat").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"notice", "possible"}, "basicbasic").expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"bring", "plane", "should", "against", "chick"}, "greatscore")
            .expect(7);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new String[]{"these", "guess", "about", "garden", "him"}, "atomher")
            .expect(3);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new String[]{"soil", "since", "lift", "are", "lot", "twenty", "put"}, "appearreason")
            .expect(7);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new String[]{
                    "control", "heart", "interest", "stream", "sentence", "soil", "wonder", "them", "month", "slip",
                    "table", "miss", "boat", "speak", "figure", "no", "perhaps", "twenty", "throw", "rich", "capital",
                    "save", "method", "store", "meant", "life", "oil", "string", "song", "food", "am", "who", "fat",
                    "if", "put", "path", "come", "grow", "box", "great", "word", "object", "stead", "common", "fresh",
                    "the", "operate", "where", "road", "mean"}, "stoodcrease")
            .expect(3);

}
