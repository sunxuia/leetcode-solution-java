package q900;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import q050.Q001_TwoSum;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 888. Fair Candy Swap
 * https://leetcode.com/problems/fair-candy-swap/
 *
 * Alice and Bob have candy bars of different sizes: A[i] is the size of the i-th bar of candy that Alice has, and B[j]
 * is the size of the j-th bar of candy that Bob has.
 *
 * Since they are friends, they would like to exchange one candy bar each so that after the exchange, they both have the
 * same total amount of candy.  (The total amount of candy a person has is the sum of the sizes of candy bars they
 * have.)
 *
 * Return an integer array ans where ans[0] is the size of the candy bar that Alice must exchange, and ans[1] is the
 * size of the candy bar that Bob must exchange.
 *
 * If there are multiple answers, you may return any one of them.  It is guaranteed an answer exists.
 *
 * Example 1:
 *
 * Input: A = [1,1], B = [2,2]
 * Output: [1,2]
 *
 * Example 2:
 *
 * Input: A = [1,2], B = [2,3]
 * Output: [1,2]
 *
 * Example 3:
 *
 * Input: A = [2], B = [1,3]
 * Output: [2,3]
 *
 * Example 4:
 *
 * Input: A = [1,2,5], B = [2,4]
 * Output: [5,4]
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 1 <= B.length <= 10000
 * 1 <= A[i] <= 100000
 * 1 <= B[i] <= 100000
 * It is guaranteed that Alice and Bob have different total amounts of candy.
 * It is guaranteed there exists an answer.
 *
 * 题解: 通过交换一组A 和B 中的元素, 使得数组的和相等, 现要返回这2 个元素, 题目保证肯定有解.
 */
@RunWith(LeetCodeRunner.class)
public class Q888_FairCandySwap {

    /**
     * 解法类似 {@link Q001_TwoSum}
     */
    @Answer
    public int[] fairCandySwap(int[] A, int[] B) {
        int sumA = Arrays.stream(A).sum();
        int sumB = Arrays.stream(B).sum();
        // 相差sumA - sumB, 只要A 中的元素减去diff, B 中的元素加上diff 即可(换成对面的元素).
        int diff = (sumA - sumB) / 2;
        Set<Integer> set = Arrays.stream(A).boxed().collect(Collectors.toSet());
        for (int b : B) {
            if (set.contains(b + diff)) {
                return new int[]{b + diff, b};
            }
        }
        return null;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1}, new int[]{2, 2}).expect(new int[]{1, 2});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2}, new int[]{2, 3}).expect(new int[]{1, 2});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2}, new int[]{1, 3}).expect(new int[]{2, 3});

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1, 2, 5}, new int[]{2, 4}).expect(new int[]{5, 4});

}
