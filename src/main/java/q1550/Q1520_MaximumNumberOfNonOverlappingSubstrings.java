package q1550;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1520. Maximum Number of Non-Overlapping Substrings
 * https://leetcode.com/problems/maximum-number-of-non-overlapping-substrings/
 *
 * Given a string s of lowercase letters, you need to find the maximum number of non-empty substrings of s that meet the
 * following conditions:
 *
 * The substrings do not overlap, that is for any two substrings s[i..j] and s[k..l], either j < k or i > l is true.
 * A substring that contains a certain character c must also contain all occurrences of c.
 *
 * Find the maximum number of substrings that meet the above conditions. If there are multiple solutions with the same
 * number of substrings, return the one with minimum total length. It can be shown that there exists a unique solution
 * of minimum total length.
 *
 * Notice that you can return the substrings in any order.
 *
 * Example 1:
 *
 * Input: s = "adefaddaccc"
 * Output: ["e","f","ccc"]
 * Explanation: The following are all the possible substrings that meet the conditions:
 * [
 * "adefaddaccc"
 * "adefadda",
 * "ef",
 * "e",
 * "f",
 * "ccc",
 * ]
 * If we choose the first string, we cannot choose anything else and we'd get only 1. If we choose "adefadda", we are
 * left with "ccc" which is the only one that doesn't overlap, thus obtaining 2 substrings. Notice also, that it's not
 * optimal to choose "ef" since it can be split into two. Therefore, the optimal way is to choose ["e","f","ccc"] which
 * gives us 3 substrings. No other solution of the same number of substrings exist.
 *
 * Example 2:
 *
 * Input: s = "abbaccd"
 * Output: ["d","bb","cc"]
 * Explanation: Notice that while the set of substrings ["d","abba","cc"] also has length 3, it's considered incorrect
 * since it has larger total length.
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1520_MaximumNumberOfNonOverlappingSubstrings {

    @Answer
    public List<String> maxNumOfSubstrings(String s) {
        final char[] cs = s.toCharArray();
        // 找出每个字符的区间
        int[][] ranges = new int[26][];
        for (int i = 0; i < cs.length; i++) {
            int idx = cs[i] - 'a';
            if (ranges[idx] == null) {
                ranges[idx] = new int[]{i, i};
            } else {
                ranges[idx][1] = i;
            }
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();
        // 遍历26 个字符代表的区间.
        for (int[] curr : ranges) {
            if (curr == null) {
                continue;
            }
            int start = curr[0], end = curr[1];
            int len = 1, i = (start + end) / 2, j = i + 1;
            // 向前向后遍历区间内的字符, 找出满足条件2 的各个区间
            while (len != end - start + 1) {
                len = end - start + 1;
                for (; i > start; i--) {
                    int[] r = ranges[cs[i] - 'a'];
                    start = Math.min(r[0], start);
                    end = Math.max(r[1], end);
                }
                for (; j < end; j++) {
                    int[] r = ranges[cs[j] - 'a'];
                    start = Math.min(r[0], start);
                    end = Math.max(r[1], end);
                }
            }

            // 删除覆盖它的区间
            for (Integer floor = map.floorKey(start);
                    floor != null && map.get(floor) > end;
                    floor = map.lowerKey(floor)) {
                map.remove(floor);
            }
            // 如果当前区间内已经存在更小的区间则舍去该区间
            Integer ceiling = map.ceilingKey(start);
            if (ceiling == null || map.get(ceiling) > end) {
                map.put(start, end);
            }
        }

        List<String> res = new ArrayList<>();
        for (Integer start : map.keySet()) {
            int end = map.get(start);
            res.add(new String(cs, start, end - start + 1));
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法
     */
    @Answer
    public List<String> maxNumOfSubstrings2(String s) {
        final char[] cs = s.toCharArray();
        int[][] ranges = new int[26][];
        for (int i = 0; i < cs.length; i++) {
            int idx = cs[i] - 'a';
            if (ranges[idx] == null) {
                ranges[idx] = new int[]{i, i};
            } else {
                ranges[idx][1] = i;
            }
        }

        List<String> res = new ArrayList<>();
        // prev 表示上一个区间的结束位置
        int prev = -1;
        for (int i = 0; i < cs.length; i++) {
            if (ranges[cs[i] - 'a'][0] != i) {
                // 不是字符的开始区间
                continue;
            }
            int end = extend(cs, ranges, i);
            if (end == -1) {
                // 区间已经被访问过了
                continue;
            }

            String substr = new String(cs, i, end - i + 1);
            if (i > prev) {
                // 不重叠
                res.add(substr);
            } else {
                // 替换之前重叠的部分
                res.set(res.size() - 1, substr);
            }
            prev = end;
        }
        return res;
    }

    // 找出以i 开始的满足条件2 的区间的结束位置
    private int extend(char[] cs, int[][] ranges, int i) {
        int end = ranges[cs[i] - 'a'][1];
        for (int j = i; j < end; j++) {
            int[] r = ranges[cs[j] - 'a'];
            if (r[0] < i) {
                // 这个字符区间已经在之前遍历其他字符区间的时候被访问过了
                // 也就是说这个区间不是以i 开头的
                return -1;
            }
            end = Math.max(end, r[1]);
        }
        return end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("adefaddaccc")
            .expect(List.of("e", "f", "ccc"))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.create("abbaccd")
            .expect(List.of("d", "bb", "cc"))
            .unOrder();

    @TestData
    public DataExpectation normal1 = DataExpectation.create("abab").expect(List.of("abab"));

    @TestData
    public DataExpectation normal2 = DataExpectation.create("ababa").expect(List.of("ababa"));

    @TestData
    public DataExpectation normal3 = DataExpectation.create("abcdabcd").expect(List.of("abcdabcd"));

}
