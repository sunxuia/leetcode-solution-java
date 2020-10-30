package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1239. Maximum Length of a Concatenated String with Unique Characters
 * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
 *
 * Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
 *
 * Return the maximum possible length of s.
 *
 * Example 1:
 *
 * Input: arr = ["un","iq","ue"]
 * Output: 4
 * Explanation: All possible concatenations are "","un","iq","ue","uniq" and "ique".
 * Maximum length is 4.
 *
 * Example 2:
 *
 * Input: arr = ["cha","r","act","ers"]
 * Output: 6
 * Explanation: Possible solutions are "chaers" and "acters".
 *
 * Example 3:
 *
 * Input: arr = ["abcdefghijklmnopqrstuvwxyz"]
 * Output: 26
 *
 * Constraints:
 *
 * 1 <= arr.length <= 16
 * 1 <= arr[i].length <= 26
 * arr[i] contains only lower case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1239_MaximumLengthOfAConcatenatedStringWithUniqueCharacters {

    /**
     * bfs
     */
    @Answer
    public int maxLength(List<String> arr) {
        int res = 0;
        List<Node> list = new ArrayList<>();
        list.add(new Node(0, 0));
        loop:
        for (String str : arr) {
            int mask = 0;
            for (int i = 0; i < str.length(); i++) {
                int idx = str.charAt(i) - 'a';
                if ((mask >> idx & 1) == 1) {
                    continue loop;
                }
                mask |= 1 << idx;
            }
            for (int i = 0, len = list.size(); i < len; i++) {
                Node node = list.get(i);
                if ((node.mask & mask) == 0) {
                    Node next = new Node(node.len + str.length(), node.mask | mask);
                    res = Math.max(res, next.len);
                    list.add(next);
                }
            }
        }
        return res;
    }

    private static class Node {

        final int len, mask;

        Node(int len, int mask) {
            this.len = len;
            this.mask = mask;
        }
    }

    /**
     * dfs
     */
    @Answer
    public int maxLength2(List<String> arr) {
        List<Node> nodes = new ArrayList<>(arr.size());
        loop:
        for (String str : arr) {
            int mask = 0;
            for (int i = 0; i < str.length(); i++) {
                int idx = str.charAt(i) - 'a';
                if ((mask >> idx & 1) == 1) {
                    continue loop;
                }
                mask |= 1 << idx;
            }
            nodes.add(new Node(str.length(), mask));
        }

        return dfs(nodes, 0, 0);
    }

    private int dfs(List<Node> nodes, int i, int mask) {
        if (i == nodes.size()) {
            return 0;
        }
        Node node = nodes.get(i);
        int res = dfs(nodes, i + 1, mask);
        if ((mask & node.mask) == 0) {
            res = Math.max(res, dfs(nodes, i + 1, mask | node.mask) + node.len);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(Arrays.asList("un", "iq", "ue")).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(Arrays.asList("cha", "r", "act", "ers")).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.create(Arrays.asList("abcdefghijklmnopqrstuvwxyz")).expect(26);

}
