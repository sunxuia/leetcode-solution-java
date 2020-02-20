package q450;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-palindrome/
 *
 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that
 * can be built with those letters.
 *
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 *
 * Note:
 * Assume the length of given string will not exceed 1,010.
 *
 * Example:
 *
 * Input:
 * "abccccdd"
 *
 * Output:
 * 7
 *
 * Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
@RunWith(LeetCodeRunner.class)
public class Q409_LongestPalindrome {

    // 可以用数组减少一点时间.
    @Answer
    public int longestPalindrome(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int total = 0, odd = 0;
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            int count = entry.getValue();
            if (count % 2 == 0) {
                total += count;
            } else {
                total += count - 1;
                odd = 1;
            }
        }
        return total + odd;
    }

    @TestData
    public DataExpectation example = DataExpectation.create("abccccdd").expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(
            "civilwartestingwhetherthatnaptionoranyn"
                    + "artionsoconceivedandsodedicatedcanlonge"
                    + "ndureWeareqmetonagreatbattlefiemldoftzh"
                    + "atwarWehavecometodedicpateaportionoftha"
                    + "tfieldasafinalrestingplaceforthosewhohe"
                    + "regavetheirlivesthatthatnationmightlive"
                    + "Itisaltogetherfangandproperthatweshould"
                    + "dothisButinalargersensewecannotdedicate"
                    + "wecannotconsecratewecannothallowthisgro"
                    + "undThebravelmenlivinganddeadwhostruggle"
                    + "dherehaveconsecrateditfaraboveourpoorpo"
                    + "nwertoaddordetractTgheworldadswfilllitt"
                    + "lenotlenorlongrememberwhatwesayherebuti"
                    + "tcanneverforgetwhattheydidhereItisforus"
                    + "thelivingrathertobededicatedheretotheul"
                    + "nfinishedworkwhichtheywhofoughtherehave"
                    + "thusfarsonoblyadvancedItisratherforusto"
                    + "beherededicatedtothegreattdafskremainin"
                    + "gbeforeusthatfromthesehonoreddeadwetake"
                    + "increaseddevotiontothatcauseforwhichthe"
                    + "ygavethelastpfullmeasureofdevotionthatw"
                    + "eherehighlyresolvethatthesedeadshallnot"
                    + "havediedinvainthatthisnationunsderGodsh"
                    + "allhaveanewbirthoffreedomandthatgovernm"
                    + "entofthepeoplebythepeopleforthepeoplesh"
                    + "allnotperishfromtheearth")
            .expect(983);

}
