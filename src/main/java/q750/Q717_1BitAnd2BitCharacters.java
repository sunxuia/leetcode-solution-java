package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/1-bit-and-2-bit-characters/
 *
 * We have two special characters. The first character can be represented by one bit 0. The second character can be
 * represented by two bits (10 or 11).
 *
 * Now given a string represented by several bits. Return whether the last character must be a one-bit character or
 * not. The given string will always end with a zero.
 *
 * Example 1:
 *
 * Input:
 * bits = [1, 0, 0]
 * Output: True
 * Explanation:
 * The only way to decode it is two-bit character and one-bit character. So the last character is one-bit character.
 *
 * Example 2:
 *
 * Input:
 * bits = [1, 1, 1, 0]
 * Output: False
 * Explanation:
 * The only way to decode it is two-bit character and two-bit character. So the last character is NOT one-bit character.
 *
 * Note:
 * 1 <= len(bits) <= 1000.
 * bits[i] is always 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q717_1BitAnd2BitCharacters {

    // 如果前面是1 则是2 位; 不是则是1 位(0) 或 2位的第1 位(1)
    @Answer
    public boolean isOneBitCharacter(int[] bits) {
        // one 表示前面是不是1
        // size 表示对应的编码是1位还是2 位. (11/10 这样的第1 位的1 也是1)
        int one = 0, size = 0;
        for (int bit : bits) {
            size = one + 1;
            one = (1 ^ one) * bit;
        }
        return size == 1;
    }

    // LeetCode 上的另一种做法
    @Answer
    public boolean isOneBitCharacter2(int[] bits) {
        int i = bits.length - 2;
        while (i >= 0 && bits[i] > 0) {
            i--;
        }
        return (bits.length - i) % 2 == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 0, 0}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 0}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 0}).expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{1, 1, 0}).expect(true);

}
