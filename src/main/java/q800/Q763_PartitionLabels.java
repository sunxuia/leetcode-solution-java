package q800;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/partition-labels/
 *
 * A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that
 * each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Example 1:
 *
 * Input: S = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 *
 * Note:
 *
 * 1. S will have length in range [1, 500].
 * 2. S will consist of lowercase letters ('a' to 'z') only.
 */
@RunWith(LeetCodeRunner.class)
public class Q763_PartitionLabels {

    @Answer
    public List<Integer> partitionLabels(String S) {
        char[] sc = S.toCharArray();
        int[] lasts = new int[26];
        for (int i = 0; i < sc.length; i++) {
            lasts[sc[i] - 'a'] = i;
        }

        List<Integer> res = new ArrayList<>();
        boolean[] exist = new boolean[26];
        int prev = -1, count = 0;
        for (int i = 0; i < sc.length; i++) {
            int idx = sc[i] - 'a';
            if (!exist[idx]) {
                // 新的字符
                exist[idx] = true;
                count++;
            }
            if (lasts[idx] == i) {
                // 该字符结束
                count--;
            }
            if (count == 0) {
                // 当前区间内所有字符结束
                res.add(i - prev);
                prev = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("ababcbacadefegdehijhklij").expect(Arrays.asList(9, 7, 8));

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("abcd").expect(Arrays.asList(1, 1, 1, 1));

}
