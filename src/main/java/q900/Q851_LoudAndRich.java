package q900;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/loud-and-rich/
 *
 * In a group of N people (labelled 0, 1, 2, ..., N-1), each person has different amounts of money, and different
 * levels of quietness.
 *
 * For convenience, we'll call the person with label x, simply "person x".
 *
 * We'll say that richer[i] = [x, y] if person x definitely has more money than person y.  Note that richer may only
 * be a subset of valid observations.
 *
 * Also, we'll say quiet[x] = q if person x has quietness q.
 *
 * Now, return answer, where answer[x] = y if y is the least quiet person (that is, the person y with the smallest
 * value of quiet[y]), among all people who definitely have equal to or more money than person x.
 *
 *
 *
 * Example 1:
 *
 * Input: richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], quiet = [3,2,5,4,6,1,7,0]
 * Output: [5,5,2,5,4,5,6,7]
 * Explanation:
 * answer[0] = 5.
 * Person 5 has more money than 3, which has more money than 1, which has more money than 0.
 * The only person who is quieter (has lower quiet[x]) is person 7, but
 * it isn't clear if they have more money than person 0.
 *
 * answer[7] = 7.
 * Among all people that definitely have equal to or more money than person 7
 * (which could be persons 3, 4, 5, 6, or 7), the person who is the quietest (has lower quiet[x])
 * is person 7.
 *
 * The other answers can be filled out with similar reasoning.
 *
 * Note:
 *
 * 1 <= quiet.length = N <= 500
 * 0 <= quiet[i] < N, all quiet[i] are different.
 * 0 <= richer.length <= N * (N-1) / 2
 * 0 <= richer[i][j] < N
 * richer[i][0] != richer[i][1]
 * richer[i]'s are all different.
 * The observations in richer are all logically consistent.
 *
 * 题解:
 * richer[i][j]  表示i 比j 有钱, quiet[i] 表示i 的安静程度,
 * 要求返回 res[i], 表示比i 有钱相等 (也就是包括自己) 的里面最不安静(quiet[i] 最小) 的值.
 */
@RunWith(LeetCodeRunner.class)
public class Q851_LoudAndRich {

    // 这题可以抽象为有向树的问题, 边从钱多的指向钱少的,
    // 通过bfs 找出每个节点的前驱节点中 quiet[i] 最小节点即为结果.
    @Answer
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        final int n = quiet.length;
        // 节点的边, 从钱多的指向钱少的
        List<Integer>[] edges = new List[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
        // hasRicher 表示该节点是否有比起更有钱的节点(前驱结点)
        boolean[] hasRicher = new boolean[n];
        for (int[] r : richer) {
            edges[r[0]].add(r[1]);
            hasRicher[r[1]] = true;
        }

        int[] res = new int[n];
        Arrays.fill(res, -1);

        // 将顶点加入队列中
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!hasRicher[i]) {
                queue.add(i);
                res[i] = i;
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : edges[curr]) {
                // 下一个节点未遍历过或下一个节点的quiet 值大于当前的
                if (res[next] == -1 || quiet[res[curr]] < quiet[res[next]]) {
                    int nextMin = res[next] == -1 ? next : res[next];
                    if (quiet[res[curr]] < quiet[nextMin]) {
                        res[next] = res[curr];
                    } else {
                        res[next] = nextMin;
                    }
                    queue.add(next);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            new int[][]{{1, 0}, {2, 1}, {3, 1}, {3, 7}, {4, 3}, {5, 3}, {6, 3}},
            new int[]{3, 2, 5, 4, 6, 1, 7, 0}
    ).expect(new int[]{5, 5, 2, 5, 4, 5, 6, 7});

}
