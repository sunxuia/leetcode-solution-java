package q1250;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1202. Smallest String With Swaps
 * https://leetcode.com/problems/smallest-string-with-swaps/
 *
 * You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2
 * indices(0-indexed) of the string.
 *
 * You can swap the characters at any pair of indices in the given pairs any number of times.
 *
 * Return the lexicographically smallest string that s can be changed to after using the swaps.
 *
 * Example 1:
 *
 * Input: s = "dcab", pairs = [[0,3],[1,2]]
 * Output: "bacd"
 * Explaination:
 * Swap s[0] and s[3], s = "bcad"
 * Swap s[1] and s[2], s = "bacd"
 *
 * Example 2:
 *
 * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
 * Output: "abcd"
 * Explaination:
 * Swap s[0] and s[3], s = "bcad"
 * Swap s[0] and s[2], s = "acbd"
 * Swap s[1] and s[2], s = "abcd"
 *
 * Example 3:
 *
 * Input: s = "cba", pairs = [[0,1],[1,2]]
 * Output: "abc"
 * Explaination:
 * Swap s[0] and s[1], s = "bca"
 * Swap s[1] and s[2], s = "bac"
 * Swap s[0] and s[1], s = "abc"
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 0 <= pairs.length <= 10^5
 * 0 <= pairs[i][0], pairs[i][1] < s.length
 * s only contains lower case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1202_SmallestStringWithSwaps {

    /**
     * 这题可以使用并查集来解决.
     */
    @Answer
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (List<Integer> pair : pairs) {
            parents[findRoot(parents, pair.get(0))] = findRoot(parents, pair.get(1));
        }
        // 统计每个并查集中字符的个数
        Map<Integer, int[]> counts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            counts.computeIfAbsent(findRoot(parents, i), k -> new int[26])[cs[i] - 'a']++;
        }
        // next 表示每个并查集中可用的下标
        Map<Integer, Integer> next = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = findRoot(parents, i);
            int idx = next.getOrDefault(root, 0);
            int[] count = counts.get(root);
            while (count[idx] == 0) {
                idx++;
            }
            count[idx]--;
            cs[i] = (char) ('a' + idx);
            next.put(root, idx);
        }
        return new String(cs);
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = findRoot(parents, parents[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("dcab", Arrays.asList(Arrays.asList(0, 3), Arrays.asList(1, 2)))
            .expect("bacd");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("dcab", Arrays.asList(Arrays.asList(0, 3), Arrays.asList(1, 2), Arrays.asList(0, 2)))
            .expect("abcd");

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("cba", Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 2)))
            .expect("abc");

    @TestData
    public DataExpectation overTime() {
        TestDataFile file = new TestDataFile();
        return DataExpectation.createWith(
                TestDataFileHelper.read(file, 1, String.class),
                TestDataFileHelper.read(file, 2, new JsonTypeWrapper<List<List<Integer>>>() {})
        ).expect(TestDataFileHelper.read(file, 3, String.class));
    }

}
