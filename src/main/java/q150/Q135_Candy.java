package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/candy/
 *
 * There are N children standing in a line. Each child is assigned a rating value.
 *
 * You are giving candies to these children subjected to the following requirements:
 *
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 *
 * What is the minimum candies you must give?
 *
 * Example 1:
 *
 * Input: [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 *
 * Example 2:
 *
 * Input: [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 */
@RunWith(LeetCodeRunner.class)
public class Q135_Candy {

    /**
     * 每次确定一个升降区间来设置值
     */
    @Answer
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        final int len = ratings.length;
        int[] candies = new int[len];
        candies[0] = 1;
        for (int i = 1, j = 0; i < len; j = i - 1) {
            if (ratings[j] < ratings[i]) {
                while (i < len && ratings[i - 1] < ratings[i]) {
                    i++;
                }
                while (++j < i) {
                    candies[j] = candies[j - 1] + 1;
                }
            } else if (ratings[j] == ratings[i]) {
                while (i < len && ratings[i - 1] == ratings[i]) {
                    i++;
                }
                while (++j < i) {
                    candies[j] = 1;
                }
            } else {
                while (i < len && ratings[i - 1] > ratings[i]) {
                    i++;
                }
                candies[j] = Math.max(i - j, candies[j]);
                while (++j < i) {
                    candies[j] = i - j;
                }
            }
        }
        int res = 0;
        for (int candy : candies) {
            res += candy;
        }
        return res;
    }

    /**
     * LeetCode 上一个解题思路, 从前后2 个方向进行递增操作.
     */
    @Answer
    public int candy2(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        final int len = ratings.length;
        int[] candies = new int[len];

        // 从前往后
        candies[0] = 1;
        for (int i = 1; i < len; i++) {
            if (ratings[i - 1] < ratings[i]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                candies[i] = 1;
            }
        }

        // 从后往前
        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int res = 0;
        for (int candy : candies) {
            res += candy;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 0, 2}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 2}).expect(4);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{}).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 3, 4, 5, 2}).expect(11);

}
