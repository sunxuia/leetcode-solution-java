package q050;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could
 * represent.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any
 * letters.
 *
 * (图片是功能手机按键)
 * 1          2 (abc)    3 (def)
 * 4 (ghi)    5 (jkl)    6 (mno)
 * 7 (pqrs)   8 (tuv)    9 (wxyz)
 * * (+)      0 ( )      # (↑)
 *
 * Example:
 *
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * Note:
 *
 * Although the above answer is in lexicographical order, your answer could be in any order you want.
 */
@RunWith(LeetCodeRunner.class)
public class Q017_LetterCombinationsOfAPhoneNumber {

    /**
     * bfs 算法.
     */
    @Answer
    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) {
            return Collections.emptyList();
        }
        LinkedList<String> queue = new LinkedList<>();
        queue.offer("");
        for (char c : digits.toCharArray()) {
            char[] candidates = chars[c - '2'];
            for (int i = 0, length = queue.size(); i < length; i++) {
                String str = queue.poll();
                for (char candidate : candidates) {
                    queue.offer(str + candidate);
                }
            }
        }
        return queue;
    }

    private static final char[][] chars = new char[][]{
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'j', 'k', 'l'},
            {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'},
            {'w', 'x', 'y', 'z'}
    };

    /**
     * dfs 算法 (stack 实现). 不过这个反而还慢一点.
     */
    @Answer
    public List<String> dfs(String digits) {
        final int length = digits.length();
        if (length == 0) {
            return Collections.emptyList();
        }
        // digits 中的数字字符转换为对应的要遍历的字母字符
        char[][] table = new char[length][];
        for (int i = 0; i < length; i++) {
            table[i] = chars[digits.charAt(i) - '2'];
        }
        // 结果
        List<String> res = new ArrayList<>((int) Math.pow(3, length));
        // 用于拼接一个结果字符串
        StringBuilder cache = new StringBuilder(length);
        // 保存当前遍历digits 的位置. 为了性能, 用ArrayList 替代Stack. (没省多少时间)
        List<Integer> stack = new ArrayList<>(length + 1);
        // 当前digits 所代表的字符的遍历位置. + 1 是为了避免下面循环中的边界判断
        int[] charIndexes = new int[length + 1];
        charIndexes[0] = -1;
        stack.add(0);
        while (!stack.isEmpty()) {
            int level = stack.remove(stack.size() - 1);
            if (level == table.length) {
                cache.setLength(0);
                for (int i = 0; i < level; i++) {
                    cache.append(table[i][charIndexes[i]]);
                }
                res.add(cache.toString());
            } else {
                if (charIndexes[level] < table[level].length - 1) {
                    charIndexes[level]++;
                    charIndexes[level + 1] = -1;
                    stack.add(level);
                    stack.add(level + 1);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument("23")
            .expect(new String[]{"ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"})
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument("")
            .expect(Collections.emptyList())
            .build();

}
