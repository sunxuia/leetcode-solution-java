package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1079. Letter Tile Possibilities
 * https://leetcode.com/problems/letter-tile-possibilities/
 *
 * You have n  tiles, where each tile has one letter tiles[i] printed on it.
 *
 * Return the number of possible non-empty sequences of letters you can make using the letters printed on those tiles.
 *
 * Example 1:
 *
 * Input: tiles = "AAB"
 * Output: 8
 * Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
 *
 * Example 2:
 *
 * Input: tiles = "AAABBC"
 * Output: 188
 *
 * Example 3:
 *
 * Input: tiles = "V"
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= tiles.length <= 7
 * tiles consists of uppercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1079_LetterTilePossibilities {

    /**
     * 因为数据量比较小, 时间复杂度 O(2^n), 最大 2^7 = 128, 所以不需要缓存.
     */
    @Answer
    public int numTilePossibilities(String tiles) {
        final int n = tiles.length();
        int[] counts = new int[26];
        for (int i = 0; i < n; i++) {
            counts[tiles.charAt(i) - 'A']++;
        }
        int len = 0, mask = 0;
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                mask += counts[i] << len;
                len += 3;
            }
        }
        return dfs(len, mask) - 1;
    }

    private int dfs(int len, int mask) {
        int res = 1;
        for (int i = 0; i < len; i += 3) {
            if ((mask >> i & 0b111) != 0) {
                res += dfs(len, mask - (1 << i));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("AAB").expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.create("AAABBC").expect(188);

    @TestData
    public DataExpectation example3 = DataExpectation.create("V").expect(1);

}
