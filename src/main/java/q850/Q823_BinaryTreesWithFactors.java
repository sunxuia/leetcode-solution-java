package q850;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-trees-with-factors/
 *
 * Given an array of unique integers, each integer is strictly greater than 1.
 *
 * We make a binary tree using these integers and each number may be used for any number of times.
 *
 * Each non-leaf node's value should be equal to the product of the values of it's children.
 *
 * How many binary trees can we make?  Return the answer modulo 10 ** 9 + 7.
 *
 * Example 1:
 *
 * Input: A = [2, 4]
 * Output: 3
 * Explanation: We can make these trees: [2], [4], [4, 2, 2]
 *
 * Example 2:
 *
 * Input: A = [2, 4, 5, 10]
 * Output: 7
 * Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].
 *
 *
 *
 * Note:
 *
 * 1 <= A.length <= 1000.
 * 2 <= A[i] <= 10 ^ 9.
 */
@RunWith(LeetCodeRunner.class)
public class Q823_BinaryTreesWithFactors {

    @Answer
    public int numFactoredBinaryTrees(int[] A) {
        Arrays.sort(A);
        Map<Integer, Integer> nums = Arrays.stream(A)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> 1));
        long res = 0;
        for (int i = 0; i < A.length; i++) {
            long count = nums.get(A[i]);
            for (int j = 0; j < i; j++) {
                if (A[i] % A[j] == 0 && nums.containsKey(A[i] / A[j])) {
                    count += (long) nums.get(A[j]) * nums.get(A[i] / A[j]);
                    count %= 10_0000_0007;
                }
            }
            nums.put(A[i], (int) count);
            res = (res + count) % 10_0000_0007;
        }
        return (int) res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 4}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 4, 5, 10}).expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{
            45, 42, 2, 18, 23, 1170, 12, 41, 40, 9, 47, 24, 33, 28, 10, 32, 29, 17, 46, 11,
            759, 37, 6, 26, 21, 49, 31, 14, 19, 8, 13, 7, 27, 22, 3, 36, 34, 38, 39, 30, 43,
            15, 4, 16, 35, 25, 20, 44, 5, 48
    }).expect(777);

}
