package q2000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1996. The Number of Weak Characters in the Game
 * https://leetcode.com/problems/the-number-of-weak-characters-in-the-game/
 *
 * You are playing a game that contains multiple characters, and each of the characters has two main properties: attack
 * and defense. You are given a 2D integer array properties where properties[i] = [attacki, defensei] represents the
 * properties of the ith character in the game.
 *
 * A character is said to be weak if any other character has both attack and defense levels strictly greater than this
 * character's attack and defense levels. More formally, a character i is said to be weak if there exists another
 * character j where attackj > attacki and defensej > defensei.
 *
 * Return the number of weak characters.
 *
 * Example 1:
 *
 * Input: properties = [[5,5],[6,3],[3,6]]
 * Output: 0
 * Explanation: No character has strictly greater attack and defense than the other.
 *
 * Example 2:
 *
 * Input: properties = [[2,2],[3,3]]
 * Output: 1
 * Explanation: The first character is weak because the second character has a strictly greater attack and defense.
 *
 * Example 3:
 *
 * Input: properties = [[1,5],[10,4],[4,3]]
 * Output: 1
 * Explanation: The third character is weak because the second character has a strictly greater attack and defense.
 *
 * Constraints:
 *
 * 2 <= properties.length <= 10^5
 * properties[i].length == 2
 * 1 <= attacki, defensei <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1996_TheNumberOfWeakCharactersInTheGame {

    /**
     * 排序的解法.
     */
    @Answer
    public int numberOfWeakCharacters(int[][] properties) {
        // 按照 [attack, defense] 从大到小排序
        Arrays.sort(properties, (a, b) -> a[0] == b[0] ? b[1] - a[1] : b[0] - a[0]);
        // 大于当前遍历的 attack 值的角色
        int[] prev = new int[]{Integer.MAX_VALUE, 0};
        // 与当前遍历的 attack 值相同的角色(相同 attack 中 defense 最大)
        int[] same = prev;
        int res = 0;
        for (int[] property : properties) {
            if (property[0] != same[0]) {
                if (prev[1] < same[1]) {
                    prev = same;
                }
                same = property;
            }
            if (prev[1] > property[1]) {
                res++;
            }
        }
        return res;
    }

    /**
     * leetcode 上最快的解法, 时间复杂度 O(n)
     */
    @Answer
    public int numberOfWeakCharacters_leetcode(int[][] properties) {
        int maxAttack = 0;
        for (int[] property : properties) {
            maxAttack = Math.max(maxAttack, property[0]);
        }

        int[] maxDefense = new int[maxAttack + 2];
        // 找出每个attack 值对应的最大角色 defense 值
        for (int[] property : properties) {
            maxDefense[property[0]] = Math.max(maxDefense[property[0]], property[1]);
        }

        // 找出 >= 当前attack 值的角色的最大defense 值
        for (int i = maxAttack - 1; i >= 0; i--) {
            maxDefense[i] = Math.max(maxDefense[i], maxDefense[i + 1]);
        }

        // 遍历结果, 如果当前角色 defense 值 < 当前角色 attack+1 对应的最大defense 值,
        // 则说明有比这个值更大的角色 [attack, defense] 值存在.
        int res = 0;
        for (int[] property : properties) {
            if (property[1] < maxDefense[property[0] + 1]) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{5, 5}, {6, 3}, {3, 6}}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{2, 2}, {3, 3}}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 5}, {10, 4}, {4, 3}}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{{1, 1}, {2, 1}, {2, 2}, {1, 2}}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{{5, 5}, {3, 6}, {6, 9}}).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[][]{{10, 1}, {5, 1}, {7, 10}, {4, 1}, {5, 9}, {6, 9}, {7, 2}, {1, 10}})
            .expect(4);

}
