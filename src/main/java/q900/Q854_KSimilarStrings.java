package q900;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import q800.Q765_CouplesHoldingHands;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/k-similar-strings/
 *
 * Strings A and B are K-similar (for some non-negative integer K) if we can swap the positions of two letters in A
 * exactly K times so that the resulting string equals B.
 *
 * Given two anagrams A and B, return the smallest K for which A and B are K-similar.
 *
 * Example 1:
 *
 * Input: A = "ab", B = "ba"
 * Output: 1
 *
 * Example 2:
 *
 * Input: A = "abc", B = "bca"
 * Output: 2
 *
 * Example 3:
 *
 * Input: A = "abac", B = "baca"
 * Output: 2
 *
 * Example 4:
 *
 * Input: A = "aabc", B = "abca"
 * Output: 2
 *
 * Note:
 *
 * 1 <= A.length == B.length <= 20
 * A and B contain only lowercase letters from the set {'a', 'b', 'c', 'd', 'e', 'f'}
 *
 * 类似题目 {@link Q765_CouplesHoldingHands} 相比这题, couples 是不重复的.
 */
@RunWith(LeetCodeRunner.class)
public class Q854_KSimilarStrings {

    // 挺无聊的BFS, 没什么特别的技巧
    // 参考文档 https://www.cnblogs.com/grandyang/p/11343552.html
    @Answer
    public int kSimilarity(String A, String B) {
        final int n = A.length();
        char[] bc = B.toCharArray();
        int res = 0;
        Queue<String> queue = new ArrayDeque<>();
        queue.add(A);
        Set<String> visited = new HashSet<>();
        while (true) {
            for (int len = queue.size(); len > 0; len--) {
                String curr = queue.poll();
                if (!visited.add(curr)) {
                    continue;
                }
                if (curr.equals(B)) {
                    return res;
                }

                char[] cc = curr.toCharArray();
                int i = 0;
                while (i < n && cc[i] == bc[i]) {
                    i++;
                }
                // 寻找第一个和 bc[i] 不同的字符, 然后进行替换. (剩下的在之后bfs 中替换)
                // 在循环中找出所有可以的替换位置, 然后替换.
                for (int j = i + 1; j < n; j++) {
                    if (cc[j] == bc[j] || cc[j] != bc[i]) {
                        continue;
                    }
                    char c = cc[i];
                    cc[i] = cc[j];
                    cc[j] = c;
                    queue.add(new String(cc));
                    cc[j] = cc[i];
                    cc[i] = c;
                }
            }
            res++;
        }
    }

    // dfs 的方式, 差不多的思路, 不用缓存时间也差不多.
    @Answer
    public int kSimilarity2(String A, String B) {
        return dfs(A.toCharArray(), B.toCharArray(), 0);
    }

    private int dfs(char[] ac, char[] bc, int i) {
        if (i == ac.length) {
            return 0;
        }
        if (ac[i] == bc[i]) {
            return dfs(ac, bc, i + 1);
        }

        int times = Integer.MAX_VALUE;
        for (int j = i + 1; j < ac.length; j++) {
            if (ac[j] != bc[j] && ac[j] == bc[i]) {
                char c = ac[i];
                ac[i] = ac[j];
                ac[j] = c;
                times = Math.min(times, dfs(ac, bc, i + 1));
                ac[j] = ac[i];
                ac[i] = c;
            }
        }
        return 1 + times;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ab", "ba").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abc", "bca").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("abac", "baca").expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("aabc", "abca").expect(2);

}
