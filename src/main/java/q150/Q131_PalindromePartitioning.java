package q150;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/palindrome-partitioning/
 *
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 *
 * Return all possible palindrome partitioning of s.
 *
 * Example:
 *
 * Input: "aab"
 * Output:
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q131_PalindromePartitioning {

    /**
     * 这种dfs 的方式就已经够快的了
     */
    @Answer
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(s.length()), s, 0);
        return res;
    }

    @SuppressWarnings("unchecked")
    private void dfs(List<List<String>> res, ArrayList<String> buffer, String s, int index) {
        if (index == s.length()) {
            res.add((List<String>) buffer.clone());
            return;
        }
        for (int i = index; i < s.length(); i++) {
            if (isPalindrome(s, index, i)) {
                buffer.add(s.substring(index, i + 1));
                dfs(res, buffer, s, i + 1);
                buffer.remove(buffer.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }


    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument("aab")
            .expect(new String[][]{
                    {"aa", "b"},
                    {"a", "a", "b"}
            }).unorderResult("")
            .build();


    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument("")
            .expect(Collections.singleton(Collections.emptyList()))
            .build();

}
