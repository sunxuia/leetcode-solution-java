package q550;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/ipo/
 *
 * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
 * LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited
 * resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to
 * maximize its total capital after finishing at most k distinct projects.
 *
 * You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is needed
 * to start the corresponding project. Initially, you have W capital. When you finish a project, you will obtain its
 * pure profit and the profit will be added to your total capital.
 *
 * To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital, and
 * output your final maximized capital.
 *
 * Example 1:
 *
 * Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
 *
 * Output: 4
 *
 * Explanation: Since your initial capital is 0, you can only start the project indexed 0.
 * After finishing it you will obtain profit 1 and your capital becomes 1.
 * With capital 1, you can either start the project indexed 1 or the project indexed 2.
 * Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum
 * capital.
 * Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
 *
 * Note:
 *
 * You may assume all numbers in the input are non-negative integers.
 * The length of Profits array and Capital array will not exceed 50,000.
 * The answer is guaranteed to fit in a 32-bit signed integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q502_IPO {

    /**
     * 暴力破解的方式肯定会超时, 这题可以使用贪婪算法
     * 这种遍历循环的方法可以通过OJ 但是很慢
     */
    @Answer
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        final int n = Profits.length;
        boolean[] used = new boolean[n];
        while (k-- > 0) {
            int max = -1;
            for (int i = 0; i < n; i++) {
                // 每次循环都找出利润最大成本最小的项目
                if (!used[i] && W >= Capital[i]) {
                    if (max == -1
                            || Profits[i] > Profits[max]
                            || Profits[i] == Profits[max] && Capital[i] < Capital[max]) {
                        max = i;
                    }
                }
            }
            if (max == -1) {
                return W;
            }
            W += Profits[max];
            used[max] = true;
        }
        return W;
    }

    // 针对上面的改进, 使用优先队列.
    // LeetCode 中针对W 大于所有capital 的测试用例还可以进行对应优化.
    @Answer
    public int findMaximizedCapital2(int k, int W, int[] Profits, int[] Capital) {
        final int n = Profits.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) ->
                Profits[a] == Profits[b] ? Capital[a] - Capital[b] : Profits[b] - Profits[a]);
        for (int i = 0; i < n; i++) {
            pq.add(i);
        }
        List<Integer> remains = new ArrayList<>();
        while (k-- > 0) {
            remains.clear();
            int max = -1;
            while (!pq.isEmpty()) {
                int idx = pq.remove();
                if (W < Capital[idx]) {
                    remains.add(idx);
                } else {
                    max = idx;
                    break;
                }
            }
            if (max < 0) {
                return W;
            }
            W += Profits[max];
            for (Integer remain : remains) {
                pq.add(remain);
            }
        }
        return W;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}).expect(4);

    @TestData
    public DataExpectation border = DataExpectation.createWith(1, 0, new int[]{1, 2, 3}, new int[]{1, 1, 2}).expect(0);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime1 = DataExpectation.createWith(111, 12,
            TestDataFileHelper.read(testDataFile, 1, int[].class),
            TestDataFileHelper.read(testDataFile, 2, int[].class))
            .expect(126981);

    @TestData
    public DataExpectation overTime2() {
        final int n = 5_0000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return DataExpectation.createWith(n, n, arr, arr.clone()).expect(12_5002_5000);
    }

}
