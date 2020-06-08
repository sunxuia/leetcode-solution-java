package q850;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/expressive-words/
 *
 * Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii".  In
 * these strings like "heeellooo", we have groups of adjacent letters that are all the same:  "h", "eee", "ll", "ooo".
 *
 * For some given string S, a query word is stretchy if it can be made to be equal to S by any number of applications
 * of the following extension operation: choose a group consisting of characters c, and add some number of characters
 * c to the group so that the size of the group is 3 or more.
 *
 * For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we cannot get
 * "helloo" since the group "oo" has size less than 3.  Also, we could do another extension like "ll" -> "lllll" to
 * get "helllllooo".  If S = "helllllooo", then the query word "hello" would be stretchy because of these two
 * extension operations: query = "hello" -> "hellooo" -> "helllllooo" = S.
 *
 * Given a list of query words, return the number of words that are stretchy.
 *
 *
 *
 * Example:
 * Input:
 * S = "heeellooo"
 * words = ["hello", "hi", "helo"]
 * Output: 1
 * Explanation:
 * We can extend "e" and "o" in the word "hello" to get "heeellooo".
 * We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.
 *
 *
 *
 * Notes:
 *
 * 0 <= len(S) <= 100.
 * 0 <= len(words) <= 100.
 * 0 <= len(words[i]) <= 100.
 * S and all words in words consist only of lowercase letters
 */
@RunWith(LeetCodeRunner.class)
public class Q809_ExpressiveWords {

    // LeetCode 上直接比较字符串会快一点
    @Answer
    public int expressiveWords(String S, String[] words) {
        List<int[]> repeats = getRepeats(S);
        int res = 0;
        for (String word : words) {
            List<int[]> wordRepeats = getRepeats(word);
            if (repeats.size() == wordRepeats.size()) {
                int i = 0;
                while (i < repeats.size()) {
                    int[] sr = repeats.get(i);
                    int[] wr = wordRepeats.get(i);
                    if (sr[0] != wr[0]
                            || sr[1] < wr[1]
                            || sr[1] > wr[1] && sr[1] < 3) {
                        break;
                    }
                    i++;
                }
                res += i == repeats.size() ? 1 : 0;
            }
        }
        return res;
    }

    private List<int[]> getRepeats(String str) {
        List<int[]> repeats = new ArrayList<>();
        char prev = ' ';
        for (char c : str.toCharArray()) {
            if (prev == c) {
                repeats.get(repeats.size() - 1)[1]++;
            } else {
                repeats.add(new int[]{c, 1});
            }
            prev = c;
        }
        return repeats;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("heeellooo", new String[]{"hello", "hi", "helo"})
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("heeellooo", new String[]{"axxxrrzzz"}).expect(0);

}
