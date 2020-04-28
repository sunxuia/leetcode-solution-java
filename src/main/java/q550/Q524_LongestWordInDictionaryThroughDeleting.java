package q550;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
 *
 * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting
 * some characters of the given string. If there are more than one possible results, return the longest word with
 * the smallest lexicographical order. If there is no possible result, return the empty string.
 *
 * Example 1:
 *
 * Input:
 * s = "abpcplea", d = ["ale","apple","monkey","plea"]
 *
 * Output:
 * "apple"
 *
 * Example 2:
 *
 * Input:
 * s = "abpcplea", d = ["a","b","c"]
 *
 * Output:
 * "a"
 *
 * Note:
 *
 * All the strings in the input will only contain lower-case letters.
 * The size of the dictionary won't exceed 1,000.
 * The length of all the strings in the input won't exceed 1,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q524_LongestWordInDictionaryThroughDeleting {

    @Answer
    public String findLongestWord(String s, List<String> d) {
        String res = "";
        for (String dict : d) {
            if (dict.length() >= res.length() && isSubstring(s, dict)) {
                if (res.length() < dict.length()
                        || res.compareTo(dict) > 0) {
                    res = dict;
                }
            }
        }
        return res;
    }

    private boolean isSubstring(String s, String dict) {
        int i = 0, j = 0;
        while (i < s.length() && j < dict.length()) {
            if (s.charAt(i) == dict.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == dict.length();
    }

    /**
     * LeetCode 上最快的方式, 使用类似KMP 的一个跳跃表的方式来加快字符串的匹配速度.
     */
    @Answer
    public String findLongestWord2(String s, List<String> d) {
        final int n = s.length();
        // jump[i][j] 表示在i 位置上如果下一个字符是 (j + 'a') 应该跳到的位置
        int[][] jump = new int[n + 1][26];
        // -1 是哨兵, 表示找不到匹配, 跳跃结束
        Arrays.fill(jump[n], -1);
        for (int i = n - 1; i >= 0; i--) {
            System.arraycopy(jump[i + 1], 0, jump[i], 0, 26);
            jump[i][s.charAt(i) - 'a'] = i + 1;
        }

        String res = "";
        for (String dict : d) {
            if (dict.length() >= res.length() && isSubstring(dict, jump)) {
                if (res.length() < dict.length()
                        || res.compareTo(dict) > 0) {
                    res = dict;
                }
            }
        }
        return res;
    }

    private boolean isSubstring(String dict, int[][] jump) {
        for (int i = 0, j = 0; j < dict.length(); j++) {
            i = jump[i][dict.charAt(j) - 'a'];
            if (i < 0) {
                // 对于这个字符找不到匹配
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("abpcplea", Arrays.asList("ale", "apple", "monkey", "plea"))
            .expect("apple");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("abpcplea", Arrays.asList("a", "b", "c"))
            .expect("a");

    @TestData
    public DataExpectation border = DataExpectation
            .createWith("", Arrays.asList("b", "a"))
            .expect("");

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            "mjmqqjrmzkvhxlyruonekhhofpzzslupzojfuoztvzmmqvmlhgqxehojfowtrinbatjujaxekbc"
                    + "ydldglkbxsqbbnrkhfdnpfbuaktupfftiljwpgglkjqunvithzlzpgikixqeuimmtbiskemplcv"
                    + "ljqgvlzvnqxgedxqnznddkiujwhdefziydtquoudzxstpjjitmiimbjfgfjikkjycwgnpdxpepps"
                    + "turjwkgnifinccvqzwlbmgpdaodzptyrjjkbqmgdrftfbwgimsmjpknuqtijrsnwvtytqqvook"
                    + "inzmkkkrkgwafohflvuedssukjgipgmypakhlckvizmqvycvbxhlljzejcaijqnfgobuhuiahtm"
                    + "xfzoplmmjfxtggwwxliplntkfuxjcnzcqsaagahbbneugiocexcfpszzomumfqpaiydssmihdoe"
                    + "wahoswhlnpctjmkyufsvjlrflfiktndubnymenlmpyrhjxfdcq",
            TestDataFileHelper.readStringArray("Q524_TestData").then(Arrays::asList))
            .expect("ntgcykxhdfescxxypyew");

}
