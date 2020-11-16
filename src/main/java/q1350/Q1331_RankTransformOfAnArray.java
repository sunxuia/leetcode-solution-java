package q1350;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1331. Rank Transform of an Array
 * https://leetcode.com/problems/rank-transform-of-an-array/
 *
 * Given an array of integers arr, replace each element with its rank.
 *
 * The rank represents how large the element is. The rank has the following rules:
 *
 * Rank is an integer starting from 1.
 * The larger the element, the larger the rank. If two elements are equal, their rank must be the same.
 * Rank should be as small as possible.
 *
 * Example 1:
 *
 * Input: arr = [40,10,20,30]
 * Output: [4,1,2,3]
 * Explanation: 40 is the largest element. 10 is the smallest. 20 is the second smallest. 30 is the third smallest.
 *
 * Example 2:
 *
 * Input: arr = [100,100,100]
 * Output: [1,1,1]
 * Explanation: Same elements share the same rank.
 *
 * Example 3:
 *
 * Input: arr = [37,12,28,9,100,56,80,5,12]
 * Output: [5,3,4,2,8,6,7,1,3]
 *
 * Constraints:
 *
 * 0 <= arr.length <= 10^5
 * -10^9 <= arr[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1331_RankTransformOfAnArray {

    @Answer
    public int[] arrayRankTransform(int[] arr) {
        final int n = arr.length;
        Integer[] sort = new Integer[n];
        for (int i = 0; i < n; i++) {
            sort[i] = i;
        }
        Arrays.sort(sort, Comparator.comparingInt(a -> arr[a]));

        int[] res = new int[n];
        int prev = Integer.MIN_VALUE, order = 0;
        for (int i : sort) {
            if (prev != arr[i]) {
                order++;
                prev = arr[i];
            }
            res[i] = order;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{40, 10, 20, 30})
            .expect(new int[]{4, 1, 2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{100, 100, 100})
            .expect(new int[]{1, 1, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{37, 12, 28, 9, 100, 56, 80, 5, 12})
            .expect(new int[]{5, 3, 4, 2, 8, 6, 7, 1, 3});

}
