package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/group-anagrams/
 *
 * Given an array of strings, group anagrams together.
 *
 * Example:
 *
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 *
 * Note:
 *
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 */
@RunWith(LeetCodeRunner.class)
public class Q049_GroupAnagrams {

    /**
     * 这题如果使用HashCode 的方式会出错, 这个和C# 解题时使用质数计算hashcode 的结果不一样.
     */
    @Answer
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            StringBuilder sb = new StringBuilder(chars.length);
            for (char c : chars) {
                sb.append(c);
            }
            String key = sb.toString();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        return map.values().stream().collect(Collectors.toList());
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.builder()
            .addArgument(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"})
            .expect(new String[][]{
                    {"ate", "eat", "tea"},
                    {"nat", "tan"},
                    {"bat"}
            }).unorderResult()
            .build();
}
