package q150;

import org.junit.runner.RunWith;
import q700.Q680_ValidPalindromeII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-palindrome/
 *
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *
 * Note: For the purpose of this problem, we define empty string as valid palindrome.
 *
 * Example 1:
 *
 * Input: "A man, a plan, a canal: Panama"
 * Output: true
 *
 * Example 2:
 *
 * Input: "race a car"
 * Output: false
 *
 * 下一题 {@link Q680_ValidPalindromeII}
 */
@RunWith(LeetCodeRunner.class)
public class Q125_ValidPalindrome {

    @Answer
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while(i < j){
            char ci = s.charAt(i), cj = s.charAt(j);
            if(!isAlphanumeric(ci)){
                i++;
            } else if(!isAlphanumeric(cj)){
                j--;
            } else if ( isCharEqual(ci, cj)){
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isAlphanumeric(char c){
        return 'A' <= c && c <= 'Z'
                || 'a' <= c && c <= 'z'
                || '0' <= c && c <= '9';
    }

    private boolean isCharEqual(char a, char b){
        if(a >= 'a') {
            a = (char) (a - 32);
        }
        if(b >= 'a'){
            b   = (char) (b - 32);
        }
        return a == b;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("A man, a plan, a canal: Panama").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("race a car").expect(false);

}
