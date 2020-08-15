package q950;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 936. Stamping The Sequence
 * https://leetcode.com/problems/stamping-the-sequence/
 *
 * You want to form a target string of lowercase letters.
 *
 * At the beginning, your sequence is target.length length '?' marks.  You also have a 'stamp' of lowercase letters.
 *
 * On each turn, you may place the stamp over the sequence, and replace every letter in the sequence with the
 * corresponding letter from the stamp.  You can make up to 10 * target.length turns.
 *
 * For example, if the initial sequence is "?????", and your stamp is "abc",  then you may make "abc??", "?abc?",
 * "??abc" in the first turn.  (Note that the stamp must be fully contained in the boundaries of the sequence in order
 * to stamp.)
 *
 * If the sequence is possible to stamp, then return an array of the index of the left-most letter being stamped at each
 * turn.  If the sequence is not possible to stamp, return an empty array.
 *
 * For example, if the sequence is "ababc", and the stamp is "abc", then we could return the answer [0, 2],
 * corresponding to the moves "?????" -> "abc??" -> "ababc".
 *
 * Also, if the sequence is possible to stamp, it is guaranteed it is possible to stamp within 10 * target.length moves.
 * Any answers specifying more than this number of moves will not be accepted.
 *
 * Example 1:
 *
 * Input: stamp = "abc", target = "ababc"
 * Output: [0,2]
 * ([1,0,2] would also be accepted as an answer, as well as some other answers.)
 *
 * Example 2:
 *
 * Input: stamp = "abca", target = "aabcaca"
 * Output: [3,0,1]
 *
 * Note:
 *
 * 1 <= stamp.length <= target.length <= 1000
 * stamp and target only contain lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q936_StampingTheSequence {

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("abc")
            .addArgument("ababc")
            .expect(new int[]{0, 2})
            .orExpect(new int[]{1, 0, 2})
            .build();
    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("abca")
            .addArgument("aabcaca")
            .expect(new int[]{3, 0, 1})
            .orExpect(new int[]{2, 3, 0, 1})
            .build();

    /**
     * 参考Solution 中的解法
     * https://www.cnblogs.com/grandyang/p/12501661.html 中还介绍了一种更快的解法
     */
    @Answer
    public int[] movesToStamp(String stamp, String target) {
        final int m = stamp.length(), n = target.length();
        char[] sc = stamp.toCharArray();
        char[] tc = target.toCharArray();
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] done = new boolean[n];
        Stack<Integer> ans = new Stack<>();
        List<Node> list = new ArrayList<>();

        for (int i = 0; i <= n - m; i++) {
            // 记录 [i, i+m) 窗口中已经重合和未重合的信息.
            Set<Integer> made = new HashSet<>();
            Set<Integer> todo = new HashSet<>();
            for (int j = 0; j < m; j++) {
                if (tc[i + j] == sc[j]) {
                    made.add(i + j);
                } else {
                    todo.add(i + j);
                }
            }
            list.add(new Node(made, todo));

            // 全部重合, 将窗口下标入队
            if (todo.isEmpty()) {
                ans.push(i);
                for (int j = i; j < i + m; j++) {
                    if (!done[j]) {
                        queue.add(j);
                        done[j] = true;
                    }
                }
            }
        }

        // 遍历重合的下标
        while (!queue.isEmpty()) {
            int i = queue.poll();

            // 遍历可能受影响的窗口, j 表示窗口的起始下标
            for (int j = Math.max(0, i - m + 1); j <= Math.min(n - m, i); j++) {
                Node window = list.get(j);
                if (window.todo.contains(i)) {
                    window.todo.remove(i);
                    if (window.todo.isEmpty()) {
                        ans.push(j);
                        for (int v : window.made) {
                            if (!done[v]) {
                                queue.add(v);
                                done[v] = true;
                            }
                        }
                    }
                }
            }
        }

        for (boolean b : done) {
            if (!b) {
                return new int[0];
            }
        }

        int[] res = new int[ans.size()];
        for (int t = 0; !ans.isEmpty(); t++) {
            res[t] = ans.pop();
        }
        return res;
    }

    private static class Node {

        // 已经重合的, 尚未重合的
        final Set<Integer> made, todo;

        Node(Set<Integer> m, Set<Integer> t) {
            made = m;
            todo = t;
        }
    }

}
