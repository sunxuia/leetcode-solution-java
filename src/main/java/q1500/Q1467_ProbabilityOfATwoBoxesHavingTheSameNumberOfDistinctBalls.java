package q1500;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1467. Probability of a Two Boxes Having The Same Number of Distinct Balls
 * https://leetcode.com/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/
 *
 * Given 2n balls of k distinct colors. You will be given an integer array balls of size k where balls[i] is the number
 * of balls of color i.
 *
 * All the balls will be shuffled uniformly at random, then we will distribute the first n balls to the first box and
 * the remaining n balls to the other box (Please read the explanation of the second example carefully).
 *
 * Please note that the two boxes are considered different. For example, if we have two balls of colors a and b, and two
 * boxes [] and (), then the distribution [a] (b) is considered different than the distribution [b] (a) (Please read the
 * explanation of the first example carefully).
 *
 * We want to calculate the probability that the two boxes have the same number of distinct balls.
 *
 * Example 1:
 *
 * Input: balls = [1,1]
 * Output: 1.00000
 * Explanation: Only 2 ways to divide the balls equally:
 * - A ball of color 1 to box 1 and a ball of color 2 to box 2
 * - A ball of color 2 to box 1 and a ball of color 1 to box 2
 * In both ways, the number of distinct colors in each box is equal. The probability is 2/2 = 1
 *
 * Example 2:
 *
 * Input: balls = [2,1,1]
 * Output: 0.66667
 * Explanation: We have the set of balls [1, 1, 2, 3]
 * This set of balls will be shuffled randomly and we may have one of the 12 distinct shuffles with equale probability
 * (i.e. 1/12):
 * [1,1 / 2,3], [1,1 / 3,2], [1,2 / 1,3], [1,2 / 3,1], [1,3 / 1,2], [1,3 / 2,1], [2,1 / 1,3], [2,1 / 3,1], [2,3 / 1,1],
 * [3,1 / 1,2], [3,1 / 2,1], [3,2 / 1,1]
 * After that we add the first two balls to the first box and the second two balls to the second box.
 * We can see that 8 of these 12 possible random distributions have the same number of distinct colors of balls in each
 * box.
 * Probability is 8/12 = 0.66667
 *
 * Example 3:
 *
 * Input: balls = [1,2,1,2]
 * Output: 0.60000
 * Explanation: The set of balls is [1, 2, 2, 3, 4, 4]. It is hard to display all the 180 possible random shuffles of
 * this set but it is easy to check that 108 of them will have the same number of distinct colors in each box.
 * Probability = 108 / 180 = 0.6
 *
 * Example 4:
 *
 * Input: balls = [3,2,1]
 * Output: 0.30000
 * Explanation: The set of balls is [1, 1, 1, 2, 2, 3]. It is hard to display all the 60 possible random shuffles of
 * this set but it is easy to check that 18 of them will have the same number of distinct colors in each box.
 * Probability = 18 / 60 = 0.3
 *
 * Example 5:
 *
 * Input: balls = [6,6,6,6,6,6]
 * Output: 0.90327
 *
 * Constraints:
 *
 * 1 <= balls.length <= 8
 * 1 <= balls[i] <= 6
 * sum(balls) is even.
 * Answers within 10^-5 of the actual value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1467_ProbabilityOfATwoBoxesHavingTheSameNumberOfDistinctBalls {

    /**
     * 统计总数的方式, 这种方法会超时.
     */
    @Answer
    public double getProbability_overTime(int[] balls) {
        n = Arrays.stream(balls).sum() / 2;
        dfs(balls, 0, 0, 0, new ArrayList<>(), new ArrayList<>());
        return sames.divide(total, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private int n;

    private BigDecimal total = new BigDecimal(0), sames = new BigDecimal(0);

    private void dfs(int[] balls, int idx, int leftCount, int rightCount, List<Integer> left, List<Integer> right) {
        if (leftCount == n && rightCount == n) {
            long lc = getCount(left);
            long rc = getCount(right);
            BigDecimal prod = new BigDecimal(lc).multiply(new BigDecimal(rc));
            total = total.add(prod);
            if (left.size() == right.size()) {
                sames = sames.add(prod);
            }
            return;
        }
        if (leftCount > n || rightCount > n) {
            return;
        }
        right.add(balls[idx]);
        dfs(balls, idx + 1, leftCount, rightCount + balls[idx], left, right);
        left.add(0);
        for (int i = 1; i < balls[idx]; i++) {
            left.set(left.size() - 1, i);
            right.set(right.size() - 1, balls[idx] - i);
            dfs(balls, idx + 1, leftCount + i, rightCount + balls[idx] - i, left, right);
        }
        left.set(left.size() - 1, balls[idx]);
        right.remove(right.size() - 1);
        dfs(balls, idx + 1, leftCount + balls[idx], rightCount, left, right);
        left.remove(left.size() - 1);
    }

    private long getCount(List<Integer> list) {
        long res = 1;
        int size = 0;
        for (int count : list) {
            res *= insert(size, count);
            size += count;
        }
        return res;
    }

    private long insert(int size, int ins) {
        if (size == 0 || ins == 0) {
            return 1;
        }
        long res = 0;
        for (int i = 0; i <= ins; i++) {
            res += insert(size - 1, ins - i);
        }
        return res;
    }

    /**
     * LeetCode 上的方式, 是上面方式的优化.
     * @formatter:off
     * https://leetcode-cn.com/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/solution/java-xian-fen-kuai-zai-ji-suan-by-aliengod/
     * @formatter:on
     */
    @Answer
    public double getProbability(int[] balls) {
        distCnt = 0;
        double totalCnt = calculatePermu(balls);
        dfs(0, balls, new int[balls.length]);
        return distCnt / totalCnt;
    }

    private static final double[] fact = {1, 1, 2, 6, 24, 120, 720};
    private double distCnt = 0;

    private void dfs(int start, int[] box1, int[] box2) {
        if (start == box1.length) {
            int cnt1 = 0;
            int cnt2 = 0;
            int sum1 = 0;
            int sum2 = 0;
            for (int i = 0; i < box1.length; ++i) {
                sum1 += box1[i];
                sum2 += box2[i];
                if (box1[i] > 0) {
                    cnt1++;
                }
                if (box2[i] > 0) {
                    cnt2++;
                }
            }
            if (cnt1 == cnt2 && sum1 == sum2) {
                distCnt += calculatePermu(box1) * calculatePermu(box2);
            }
            return;
        }
        for (int i = 0; i <= box1[start]; ++i) {
            box1[start] -= i;
            box2[start] += i;
            dfs(start + 1, box1, box2);
            box2[start] -= i;
            box1[start] += i;
        }
    }

    private double calculatePermu(int[] box) {
        double[] total = new double[box.length];
        total[0] = 1;
        int sum = box[0];
        for (int i = 1; i < total.length; ++i) {
            total[i] = total[i - 1];
            for (int k = sum + 1; k <= sum + box[i]; ++k) {
                total[i] *= k;
            }
            total[i] /= fact[box[i]];
            sum += box[i];
        }
        return total[box.length - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1}).expectDouble(1.00000);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 1}).expectDouble(0.66667);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 1, 2}).expectDouble(0.60000);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 2, 1}).expectDouble(0.30000);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{6, 6, 6, 6, 6, 6}).expectDouble(0.90327);

}
