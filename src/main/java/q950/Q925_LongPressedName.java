package q950;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 925. Long Pressed Name
 * https://leetcode.com/problems/long-pressed-name/
 *
 * Your friend is typing his name into a keyboard.  Sometimes, when typing a character c, the key might get long
 * pressed, and the character will be typed 1 or more times.
 *
 * You examine the typed characters of the keyboard.  Return True if it is possible that it was your friends name, with
 * some characters (possibly none) being long pressed.
 *
 * Example 1:
 *
 * Input: name = "alex", typed = "aaleex"
 * Output: true
 * Explanation: 'a' and 'e' in 'alex' were long pressed.
 *
 * Example 2:
 *
 * Input: name = "saeed", typed = "ssaaedd"
 * Output: false
 * Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.
 *
 * Example 3:
 *
 * Input: name = "leelee", typed = "lleeelee"
 * Output: true
 *
 * Example 4:
 *
 * Input: name = "laiden", typed = "laiden"
 * Output: true
 * Explanation: It's not necessary to long press any character.
 *
 * Constraints:
 *
 * 1 <= name.length <= 1000
 * 1 <= typed.length <= 1000
 * The characters of name and typed are lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q925_LongPressedName {

    @Answer
    public boolean isLongPressedName(String name, String typed) {
        List<int[]> nameList = encode(name);
        List<int[]> typedList = encode(typed);
        if (nameList.size() != typedList.size()) {
            return false;
        }
        for (int i = 0; i < nameList.size(); i++) {
            int[] namePair = nameList.get(i);
            int[] typedPair = typedList.get(i);
            if (namePair[0] != typedPair[0] || namePair[1] > typedPair[1]) {
                return false;
            }
        }
        return true;
    }

    private List<int[]> encode(String str) {
        List<int[]> res = new ArrayList<>();
        char prev = ' ';
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (prev == c) {
                res.get(res.size() - 1)[1]++;
            } else {
                res.add(new int[]{c, 1});
                prev = c;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("alex", "aaleex").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("saeed", "ssaaedd").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("leelee", "lleeelee").expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("laiden", "laiden").expect(true);

}
