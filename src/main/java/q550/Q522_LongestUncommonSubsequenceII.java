package q550;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-uncommon-subsequence-ii/
 *
 * Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon
 * subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any
 * subsequence of the other strings.
 *
 * A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the
 * order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a
 * subsequence of any string.
 *
 * The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If
 * the longest uncommon subsequence doesn't exist, return -1.
 *
 * Example 1:
 *
 * Input: "aba", "cdc", "eae"
 * Output: 3
 *
 * Note:
 *
 * All the given strings' lengths will not exceed 10.
 * The length of the given list will be in the range of [2, 50].
 */
@RunWith(LeetCodeRunner.class)
public class Q522_LongestUncommonSubsequenceII {

    // 时间复杂度 O(NlogN)
    @Answer
    public int findLUSlength(String[] strs) {
        Map<String, Integer> counts = new HashMap<>();
        for (String str : strs) {
            counts.put(str, 1 + counts.getOrDefault(str, 0));
        }
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> b.length() - a.length());
        counts.forEach((str, count) -> {
            if (count == 1) {
                pq.add(str);
            }
        });
        while (!pq.isEmpty()) {
            String str = pq.remove();
            boolean isSubstring = counts.keySet().stream()
                    .anyMatch(s -> isSubstring(s, str));
            if (!isSubstring) {
                return str.length();
            }
        }
        return -1;
    }

    private boolean isSubstring(String str, String subStr) {
        if (str.length() <= subStr.length()) {
            return false;
        }
        int i = 0, j = 0;
        while (i < str.length() && j < subStr.length()) {
            if (str.charAt(i) == subStr.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == subStr.length();
    }

    // 这个时间复杂度 O(N^2) 但是在LeetCode 跑得比上面的快
    @Answer
    public int findLUSlength2(String[] strs) {
        Arrays.sort(strs, (a, b) -> b.length() == a.length()
                ? b.compareTo(a)
                : b.length() - a.length());
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            // 去除重复的字符串
            if (i > 0 && strs[i - 1].equals(str)
                    || i < strs.length - 1 && str.equals(strs[i + 1])) {
                continue;
            }
            boolean isUnique = true;
            for (int j = 0; j < i && isUnique; j++) {
                isUnique = !isSubstring(strs[j], str);
            }
            if (isUnique) {
                return str.length();
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new String[]{"aba", "cdc", "eae"}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new String[]{"aabbcc", "aabbcc", "cb"}).expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new String[]{"aaa", "aaa", "aa"}).expect(-1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new String[]{"aabbcc", "aabbcc", "cb", "abc"}).expect(2);

}
