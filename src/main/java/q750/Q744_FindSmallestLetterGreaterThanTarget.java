package q750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-smallest-letter-greater-than-target/
 *
 * Given a list of sorted characters letters containing only lowercase letters, and given a target letter target,
 * find the smallest element in the list that is larger than the given target.
 *
 * Letters also wrap around. For example, if the target is target = 'z' and letters = ['a', 'b'], the answer is 'a'.
 *
 * Examples:
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "a"
 * Output: "c"
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "c"
 * Output: "f"
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "d"
 * Output: "f"
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "g"
 * Output: "j"
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "j"
 * Output: "c"
 *
 * Input:
 * letters = ["c", "f", "j"]
 * target = "k"
 * Output: "c"
 *
 * Note:
 *
 * letters has a length in range [2, 10000].
 * letters consists of lowercase letters, and contains at least 2 unique letters.
 * target is a lowercase letter.
 */
@RunWith(LeetCodeRunner.class)
public class Q744_FindSmallestLetterGreaterThanTarget {

    @Answer
    public char nextGreatestLetter(char[] letters, char target) {
        char res = target;
        target++;
        for (char letter : letters) {
            if ((res - target + 26) % 26 > (letter - target + 26) % 26) {
                res = letter;
            }
        }
        return res;
    }

    /**
     * 因为题目有假设 letters 是排序过的, 所以利用排序的特性, 时间复杂度降到 O(logN)
     */
    @Answer
    public char nextGreatestLetter2(char[] letters, char target) {
        if (letters[0] > target || letters[letters.length - 1] <= target) {
            return letters[0];
        }
        int idx = Arrays.binarySearch(letters, (char) (target + 1));
        return letters[idx < 0 ? -idx - 1 : idx];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'a').expect('c');

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'c').expect('f');

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'd').expect('f');

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'g').expect('j');

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'j').expect('c');

    @TestData
    public DataExpectation example6 = DataExpectation.createWith(new char[]{'c', 'f', 'j'}, 'k').expect('c');

}
