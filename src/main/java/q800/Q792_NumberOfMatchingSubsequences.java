package q800;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-matching-subsequences/
 */
@RunWith(LeetCodeRunner.class)
public class Q792_NumberOfMatchingSubsequences {

    // 这种方式比较慢
    @Answer
    public int numMatchingSubseq(String S, String[] words) {
        final int n = S.length();
        int[][] jump = new int[n + 1][128];
        Arrays.fill(jump[n], -1);
        for (int i = S.length() - 1; i >= 0; i--) {
            System.arraycopy(jump[i + 1], 0, jump[i], 0, 128);
            jump[i][S.charAt(i)] = i + 1;
        }

        int res = 0;
        for (String word : words) {
            int idx = 0;
            for (int i = 0; i < word.length() && idx != -1; i++) {
                idx = jump[idx][word.charAt(i)];
            }
            if (idx != -1) {
                res++;
            }
        }
        return res;
    }

    // 更简单也更慢的做法, 也能通过OJ
    @Answer
    public int numMatchingSubseq2(String S, String[] words) {
        char[] sc = S.toCharArray();
        int res = 0;
        for (String word : words) {
            char[] ws = word.toCharArray();
            int i = 0;
            for (int j = 0; j < sc.length && i < ws.length; j++) {
                if (ws[i] == sc[j]) {
                    i++;
                }
            }
            if (i == ws.length) {
                res++;
            }
        }
        return res;
    }

    // LeetCode 上最快的做法. 使用队列在遍历S 的时候进行匹配.
    @Answer
    public int numMatchingSubseq3(String S, String[] words) {
        Queue<Item>[] items = new Queue[26];
        for (int i = 0; i < 26; i++) {
            items[i] = new ArrayDeque<>();
        }

        // 构造匹配队列, 将words 中的单词按照首字母放入数组.
        for (String word : words) {
            items[word.charAt(0) - 'a'].offer(new Item(word, 0));
        }

        int res = 0;
        for (char c : S.toCharArray()) {
            Queue<Item> queue = items[c - 'a'];
            // 匹配这个队列中的单词
            for (int i = 0, len = queue.size(); i < len; i++) {
                Item item = queue.poll();
                if (item.index == item.word.length() - 1) {
                    // 已经匹配到头
                    res++;
                } else {
                    // 还没有匹配到头, 根据下一个匹配的字符放入对应队列中.
                    item.index++;
                    items[item.word.charAt(item.index) - 'a'].offer(item);
                }

            }
        }
        return res;
    }

    private static class Item {

        // 代表的word
        String word;
        // 准备被匹配的字符下标
        int index;

        public Item(String word, int index) {
            this.word = word;
            this.index = index;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("abcde", new String[]{"a", "bb", "acd", "ace"}).expect(3);

}
