package q1100;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1090. Largest Values From Labels
 * https://leetcode.com/problems/largest-values-from-labels/
 *
 * We have a set of items: the i-th item has value values[i] and label labels[i].
 *
 * Then, we choose a subset S of these items, such that:
 *
 * |S| <= num_wanted
 * For every label L, the number of items in S with label L is <= use_limit.
 *
 * Return the largest possible sum of the subset S.
 *
 * Example 1:
 *
 * Input: values = [5,4,3,2,1], labels = [1,1,2,2,3], num_wanted = 3, use_limit = 1
 * Output: 9
 * Explanation: The subset chosen is the first, third, and fifth item.
 *
 * Example 2:
 *
 * Input: values = [5,4,3,2,1], labels = [1,3,3,3,2], num_wanted = 3, use_limit = 2
 * Output: 12
 * Explanation: The subset chosen is the first, second, and third item.
 *
 * Example 3:
 *
 * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 1
 * Output: 16
 * Explanation: The subset chosen is the first and fourth item.
 *
 * Example 4:
 *
 * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 2
 * Output: 24
 * Explanation: The subset chosen is the first, second, and fourth item.
 *
 * Note:
 *
 * 1 <= values.length == labels.length <= 20000
 * 0 <= values[i], labels[i] <= 20000
 * 1 <= num_wanted, use_limit <= values.length
 *
 * 题解:
 * values 是一组值, 每个值对应的标签是labels, 现在要求子数组S 的最大和.
 * 这题既限制子数组中元素总数(num_wanted), 也限制相同标签的元素总数(use_limit).
 * ( |S| <= num_wanted 条件表示S 中选择的元素数量 <= num_wanted. )
 */
@RunWith(LeetCodeRunner.class)
public class Q1090_LargestValuesFromLabels {

    @Answer
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        final int n = values.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(n,
                (a, b) -> values[b] - values[a]);
        for (int i = 0; i < n; i++) {
            pq.add(i);
        }
        Map<Integer, Integer> counts = new HashMap<>();
        int res = 0;
        while (num_wanted > 0 && !pq.isEmpty()) {
            int index = pq.poll();
            int count = counts.getOrDefault(labels[index], 0);
            if (count < use_limit) {
                num_wanted--;
                res += values[index];
                counts.put(labels[index], count + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{5, 4, 3, 2, 1}, new int[]{1, 1, 2, 2, 3}, 3, 1)
            .expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 4, 3, 2, 1}, new int[]{1, 3, 3, 3, 2}, 3, 2)
            .expect(12);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{9, 8, 8, 7, 6}, new int[]{0, 0, 0, 1, 1}, 3, 1)
            .expect(16);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{9, 8, 8, 7, 6}, new int[]{0, 0, 0, 1, 1}, 3, 2)
            .expect(24);

}
