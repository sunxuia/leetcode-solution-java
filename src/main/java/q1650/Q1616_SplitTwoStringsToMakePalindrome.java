package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1616. Split Two Strings to Make Palindrome
 * https://leetcode.com/problems/split-two-strings-to-make-palindrome/
 *
 * You are given two strings a and b of the same length. Choose an index and split both strings at the same index,
 * splitting a into two strings: aprefix and asuffix where a = aprefix + asuffix, and splitting b into two strings:
 * bprefix and bsuffix where b = bprefix + bsuffix. Check if aprefix + bsuffix or bprefix + asuffix forms a palindrome.
 *
 * When you split a string s into sprefix and ssuffix, either ssuffix or sprefix is allowed to be empty. For example, if
 * s = "abc", then "" + "abc", "a" + "bc", "ab" + "c" , and "abc" + "" are valid splits.
 *
 * Return true if it is possible to form a palindrome string, otherwise return false.
 *
 * Notice that x + y denotes the concatenation of strings x and y.
 *
 * Example 1:
 *
 * Input: a = "x", b = "y"
 * Output: true
 * Explaination: If either a or b are palindromes the answer is true since you can split in the following way:
 * aprefix = "", asuffix = "x"
 * bprefix = "", bsuffix = "y"
 * Then, aprefix + bsuffix = "" + "y" = "y", which is a palindrome.
 *
 * Example 2:
 *
 * Input: a = "abdef", b = "fecab"
 * Output: true
 *
 * Example 3:
 *
 * Input: a = "ulacfd", b = "jizalu"
 * Output: true
 * Explaination: Split them at index 3:
 * aprefix = "ula", asuffix = "cfd"
 * bprefix = "jiz", bsuffix = "alu"
 * Then, aprefix + bsuffix = "ula" + "alu" = "ulaalu", which is a palindrome.
 *
 * Example 4:
 *
 * Input: a = "xbdef", b = "xecab"
 * Output: false
 *
 * Constraints:
 *
 * 1 <= a.length, b.length <= 105
 * a.length == b.length
 * a and b consist of lowercase English letters
 */
@RunWith(LeetCodeRunner.class)
public class Q1616_SplitTwoStringsToMakePalindrome {

    @Answer
    public boolean checkPalindromeFormation(String a, String b) {
        char[] acs = a.toCharArray();
        char[] bcs = b.toCharArray();
        return judgePalidrome(acs, bcs)
                || judgePalidrome(bcs, acs);
    }

    private boolean judgePalidrome(char[] acs, char[] bcs) {
        final int n = acs.length;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (acs[i] != bcs[j]) {
                return isPalindrome(acs, i, j)
                        || isPalindrome(bcs, i, j);
            }
        }
        return true;
    }

    private boolean isPalindrome(char[] cs, int left, int right) {
        while (left < right) {
            if (cs[left] != cs[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("x", "y").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abdef", "fecab").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("ulacfd", "jizalu").expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("xbdef", "xecab").expect(false);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            TestDataFileHelper.read(testDataFile, 1, String.class),
            TestDataFileHelper.read(testDataFile, 2, String.class)
    ).expect(true);

}
