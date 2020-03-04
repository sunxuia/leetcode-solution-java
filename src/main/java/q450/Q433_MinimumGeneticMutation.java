package q450;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-genetic-mutation/
 *
 * A gene string can be represented by an 8-character long string, with choices from "A", "C", "G", "T".
 *
 * Suppose we need to investigate about a mutation (mutation from "start" to "end"), where ONE mutation is defined as
 * ONE single character changed in the gene string.
 *
 * For example, "AACCGGTT" -> "AACCGGTA" is 1 mutation.
 *
 * Also, there is a given gene "bank", which records all the valid gene mutations. A gene must be in the bank to make
 * it a valid gene string.
 *
 * Now, given 3 things - start, end, bank, your task is to determine what is the minimum number of mutations needed
 * to mutate from "start" to "end". If there is no such a mutation, return -1.
 *
 * Note:
 *
 * Starting point is assumed to be valid, so it might not be included in the bank.
 * If multiple mutations are needed, all mutations during in the sequence must be valid.
 * You may assume start and end string is not the same.
 *
 *
 *
 * Example 1:
 *
 * start: "AACCGGTT"
 * end:   "AACCGGTA"
 * bank: ["AACCGGTA"]
 *
 * return: 1
 *
 *
 *
 * Example 2:
 *
 * start: "AACCGGTT"
 * end:   "AAACGGTA"
 * bank: ["AACCGGTA", "AACCGCTA", "AAACGGTA"]
 *
 * return: 2
 *
 *
 *
 * Example 3:
 *
 * start: "AAAAACCC"
 * end:   "AACCCCCC"
 * bank: ["AAAACCCC", "AAACCCCC", "AACCCCCC"]
 *
 * return: 3
 */
@RunWith(LeetCodeRunner.class)
public class Q433_MinimumGeneticMutation {

    @Answer
    public int minMutation(String start, String end, String[] bank) {
        // 构造连通图
        Map<String, Set<String>> graph = new HashMap<>();
        for (int i = 0; i < bank.length; i++) {
            for (int j = i + 1; j < bank.length; j++) {
                connect(bank[i], bank[j], graph);
            }
        }
        if (!graph.containsKey(start)) {
            for (int i = 0; i < bank.length; i++) {
                connect(start, bank[i], graph);
            }
        }

        // bfs 寻找连通性
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        int round = 0;
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                String str = queue.remove();
                if (str.equals(end)) {
                    return round;
                }
                for (String neighbor : graph.getOrDefault(str, Collections.emptySet())) {
                    if (visited.add(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
            round++;
        }
        return -1;
    }

    private void connect(String str1, String str2, Map<String, Set<String>> graph) {
        Set<String> graph1 = graph.computeIfAbsent(str1, k -> new HashSet<>());
        Set<String> graph2 = graph.computeIfAbsent(str2, k -> new HashSet<>());
        int diffs = 0;
        for (int k = 0; k < 8; k++) {
            if (str1.charAt(k) != str2.charAt(k)) {
                diffs++;
            }
        }
        if (diffs == 1) {
            graph1.add(str2);
            graph2.add(str1);
        }
    }

    /**
     * leetcode 上一种比上面更快的方法, 利用基因的特性,
     * 通过修改现有的片段, 再判断是否在bank 中的方式, 避免了构造图的过程.
     */
    public int minMutation2(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>();
        bankSet.addAll(Arrays.asList(bank));
        char[] charSet = new char[]{'A', 'C', 'G', 'T'};

        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.add(start);
        int level = 0;
        while (!q.isEmpty()) {
            for (int i = q.size(); i > 0; i--) {
                String cur = q.remove();
                if (end.equals(cur)) {
                    return level;
                }

                // 修改现有基因片段, 再判断是否有效
                char[] chs = cur.toCharArray();
                for (int k = 0; k < cur.length(); k++) {
                    char old = chs[k];
                    for (char c : charSet) {
                        chs[k] = c;
                        String next = new String(chs);

                        if (!visited.contains(next) && bankSet.contains(next)) {
                            q.add(next);
                            visited.add(next);
                        }
                    }
                    chs[k] = old;
                }
            }
            level++;
        }
        return -1;
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("AACCGGTT", "AACCGGTA", new String[]{"AACCGGTA"})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("AACCGGTT", "AAACGGTA", new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("AAAAACCC", "AACCCCCC", new String[]{"AAAACCCC", "AAACCCCC", "AACCCCCC"})
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("AACCGGTT", "AACCGGTA", new String[0])
            .expect(-1);

}
