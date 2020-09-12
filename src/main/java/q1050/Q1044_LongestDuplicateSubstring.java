package q1050;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1044. Longest Duplicate Substring
 * https://leetcode.com/problems/longest-duplicate-substring/
 *
 * Given a string S, consider all duplicated substrings: (contiguous) substrings of S that occur 2 or more times.  (The
 * occurrences may overlap.)
 *
 * Return any duplicated substring that has the longest possible length.  (If S does not have a duplicated substring,
 * the answer is "".)
 *
 * Example 1:
 *
 * Input: "banana"
 * Output: "ana"
 *
 * Example 2:
 *
 * Input: "abcd"
 * Output: ""
 *
 * Note:
 *
 * 2 <= S.length <= 10^5
 * S consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1044_LongestDuplicateSubstring {

    /**
     * 解法有些复杂, 没看懂
     * 参考文档 https://xingxingpark.com/Leetcode-1044-Longest-Duplicate-Substring/
     */
    @Answer
    public String longestDupSubstring(String S) {
        int left = 2;
        int right = S.length() - 1;
        int start = 0;
        int maxLen = 0;

        while (left <= right) {
            int len = left + (right - left) / 2;
            boolean found = false;

            Map<Long, List<Integer>> map = new HashMap<>();
            long hash = hash(S, len);
            map.put(hash, new ArrayList<>());
            map.get(hash).add(0);
            long RM = 1L;
            for (int i = 1; i < len; i++) {
                RM = (R * RM) % Q;
            }

            loop:
            for (int i = 1; i + len <= S.length(); i++) {
                hash = (hash + Q - RM * S.charAt(i - 1) % Q) % Q;
                hash = (hash * R + S.charAt(i + len - 1)) % Q;
                if (!map.containsKey(hash)) {
                    map.put(hash, new ArrayList<>());
                } else {
                    for (int j : map.get(hash)) {
                        if (compare(S, i, j, len)) {
                            found = true;
                            start = i;
                            maxLen = len;
                            break loop;
                        }
                    }
                }
                map.get(hash).add(i);
            }
            if (found) {
                left = len + 1;
            } else {
                right = len - 1;
            }
        }

        return S.substring(start, start + maxLen);
    }

    private static final long Q = (1 << 31) - 1;
    private static final long R = 256;

    private long hash(String S, int len) {
        long h = 0;
        for (int j = 0; j < len; j++) {
            h = (R * h + S.charAt(j)) % Q;
        }
        return h;
    }

    private boolean compare(String S, int i, int j, int len) {
        for (int count = 0; count < len; count++) {
            if (S.charAt(i++) != S.charAt(j++)) {
                return false;
            }
        }
        return true;
    }

    /**
     * LeetCode 上最快的解法
     */
    @Answer
    public String longestDupSubstring2(String S) {
        class Trie {

            Trie[] children;
            final int startPos;
            final int depth;

            Trie(int startPos, int depth) {
                this.startPos = startPos;
                this.depth = depth;
            }

            boolean isLeaf() {
                return children == null;
            }

            int childIndex(int start) {
                return S.charAt(start + depth) - 'a';
            }

            int addNew(int start) {
                if (start + depth == S.length()) {
                    return depth;
                }
                if (isLeaf()) {
                    children = new Trie[28];
                    children[childIndex(startPos)] = new Trie(startPos, depth + 1);
                }
                int newIndex = childIndex(start);
                Trie child = children[newIndex];
                if (child == null) {
                    children[newIndex] = new Trie(start, depth + 1);
                    return depth;
                }
                return child.addNew(start);
            }
        }
        int maxStart = 0, maxLength = 0;
        int length = S.length();
        Trie root = new Trie(0, 0);
        for (int i = 1; i + maxLength < length; i++) {
            int len = root.addNew(i);
            if (len > maxLength) {
                maxLength = len;
                maxStart = i;
            }
        }
        return S.substring(maxStart, maxStart + maxLength);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("banana").expect("ana");

    @TestData
    public DataExpectation example2 = DataExpectation.create("abcd").expect("");

}
