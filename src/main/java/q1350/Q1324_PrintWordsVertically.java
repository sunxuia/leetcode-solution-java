package q1350;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1324. Print Words Vertically
 * https://leetcode.com/problems/print-words-vertically/
 *
 * Given a string s. Return all the words vertically in the same order in which they appear in s.
 *
 * Words are returned as a list of strings, complete with spaces when is necessary. (Trailing spaces are not allowed).
 *
 * Each word would be put on only one column and that in one column there will be only one word.
 *
 * Example 1:
 *
 * Input: s = "HOW ARE YOU"
 * Output: ["HAY","ORO","WEU"]
 * Explanation: Each word is printed vertically.
 * "HAY"
 * "ORO"
 * "WEU"
 *
 * Example 2:
 *
 * Input: s = "TO BE OR NOT TO BE"
 * Output: ["TBONTB","OEROOE","   T"]
 * Explanation: Trailing spaces is not allowed.
 * "TBONTB"
 * "OEROOE"
 * "   T"
 *
 * Example 3:
 *
 * Input: s = "CONTEST IS COMING"
 * Output: ["CIC","OSO","N M","T I","E N","S G","T"]
 *
 * Constraints:
 *
 * 1 <= s.length <= 200
 * s contains only upper case English letters.
 * It's guaranteed that there is only one space between 2 words.
 */
@RunWith(LeetCodeRunner.class)
public class Q1324_PrintWordsVertically {

    @Answer
    public List<String> printVertically(String s) {
        String[] strs = s.split(" ");
        int size = 0;
        for (String str : strs) {
            size = Math.max(size, str.length());
        }
        List<String> res = new ArrayList<>(strs.length);
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            for (String str : strs) {
                if (i < str.length()) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append(' ');
                }
            }
            while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
                sb.setLength(sb.length() - 1);
            }
            res.add(sb.toString());
            sb.setLength(0);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("HOW ARE YOU")
            .expect(List.of("HAY", "ORO", "WEU"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("TO BE OR NOT TO BE")
            .expect(List.of("TBONTB", "OEROOE", "   T"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create("CONTEST IS COMING")
            .expect(List.of("CIC", "OSO", "N M", "T I", "E N", "S G", "T"));

}
