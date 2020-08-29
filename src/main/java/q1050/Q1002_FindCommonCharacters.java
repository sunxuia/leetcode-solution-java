package q1050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1002. Find Common Characters
 * https://leetcode.com/problems/find-common-characters/
 *
 * Given an array A of strings made only from lowercase letters, return a list of all characters that show up in all
 * strings within the list (including duplicates).  For example, if a character occurs 3 times in all strings but not 4
 * times, you need to include that character three times in the final answer.
 *
 * You may return the answer in any order.
 *
 * Example 1:
 *
 * Input: ["bella","label","roller"]
 * Output: ["e","l","l"]
 *
 * Example 2:
 *
 * Input: ["cool","lock","cook"]
 * Output: ["c","o"]
 *
 * Note:
 *
 * 1 <= A.length <= 100
 * 1 <= A[i].length <= 100
 * A[i][j] is a lowercase letter
 */
@RunWith(LeetCodeRunner.class)
public class Q1002_FindCommonCharacters {

    @Answer
    public List<String> commonChars(String[] A) {
        int[] count = new int[26];
        Arrays.fill(count, 100);
        int[] curr = new int[26];
        for (String str : A) {
            Arrays.fill(curr, 0);
            for (int i = 0; i < str.length(); i++) {
                curr[str.charAt(i) - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                count[i] = Math.min(count[i], curr[i]);
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            while (count[i]-- > 0) {
                res.add(String.valueOf((char) (i + 'a')));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"bella", "label", "roller"})
            .expect(Arrays.asList("e", "l", "l"))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"cool", "lock", "cook"})
            .expect(Arrays.asList("c", "o"))
            .unorderResult()
            .build();

}
