package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 917. Reverse Only Letters
 * https://leetcode.com/problems/reverse-only-letters/
 *
 * Given a string S, return the "reversed" string where all characters that are not a letter stay in the same place, and
 * all letters reverse their positions.
 *
 * Example 1:
 *
 * Input: "ab-cd"
 * Output: "dc-ba"
 *
 * Example 2:
 *
 * Input: "a-bC-dEf-ghIj"
 * Output: "j-Ih-gfE-dCba"
 *
 * Example 3:
 *
 * Input: "Test1ng-Leet=code-Q!"
 * Output: "Qedo1ct-eeLg=ntse-T!"
 *
 * Note:
 *
 * S.length <= 100
 * 33 <= S[i].ASCIIcode <= 122
 * S doesn't contain \ or "
 */
@RunWith(LeetCodeRunner.class)
public class Q917_ReverseOnlyLetters {

    @Answer
    public String reverseOnlyLetters(String S) {
        char[] sc = S.toCharArray();
        int left = 0, right = sc.length - 1;
        while (left < right) {
            while (left < right && !Character.isLetter(sc[left])) {
                left++;
            }
            while (left < right && !Character.isLetter(sc[right])) {
                right--;
            }
            if (left < right) {
                char t = sc[left];
                sc[left] = sc[right];
                sc[right] = t;
                left++;
                right--;
            }
        }
        return new String(sc);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ab-cd").expect("dc-ba");

    @TestData
    public DataExpectation example2 = DataExpectation.create("a-bC-dEf-ghIj").expect("j-Ih-gfE-dCba");

    @TestData
    public DataExpectation example3 = DataExpectation.create("Test1ng-Leet=code-Q!").expect("Qedo1ct-eeLg=ntse-T!");

}
