package q1250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1238. Circular Permutation in Binary Representation
 * https://leetcode.com/problems/circular-permutation-in-binary-representation/
 *
 * Given 2 integers n and start. Your task is return any permutation p of (0,1,2.....,2^n -1) such that :
 *
 * p[0] = start
 * p[i] and p[i+1] differ by only one bit in their binary representation.
 * p[0] and p[2^n -1] must also differ by only one bit in their binary representation.
 *
 * Example 1:
 *
 * Input: n = 2, start = 3
 * Output: [3,2,0,1]
 * Explanation: The binary representation of the permutation is (11,10,00,01).
 * All the adjacent element differ by one bit. Another valid permutation is [3,1,0,2]
 *
 * Example 2:
 *
 * Input: n = 3, start = 2
 * Output: [2,6,7,5,4,0,1,3]
 * Explanation: The binary representation of the permutation is (010,110,111,101,100,000,001,011).
 *
 * Constraints:
 *
 * 1 <= n <= 16
 * 0 <= start < 2 ^ n
 */
@RunWith(LeetCodeRunner.class)
public class Q1238_CircularPermutationInBinaryRepresentation {

    /**
     * 按照hint 中的提示, 这个就是格雷码.
     */
    @Answer
    public List<Integer> circularPermutation(int n, int start) {
        // 生成格雷码
        List<Integer> list = new ArrayList<>(1 << n);
        list.add(0);
        list.add(1);
        for (int i = 1; i < n; i++) {
            for (int j = list.size() - 1; j >= 0; j--) {
                list.add((1 << i) + list.get(j));
            }
        }
        // 将start 移动到数组的头部
        LinkedList<Integer> res = new LinkedList<>(list);
        while (res.getFirst() != start) {
            res.addLast(res.pollFirst());
        }
        return res;
    }

    /**
     * LeetCode 上最快的方法.
     */
    @Answer
    public List<Integer> circularPermutation2(int n, int start) {
        final int len = 1 << n;
        List<Integer> res = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            res.add(start ^ i ^ (i >> 1));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3)
            .expect(Arrays.asList(3, 2, 0, 1))
            .assertMethod(this::assertMethod);

    private void assertMethod(List<Integer> expect, List<Integer> actual,
            BiConsumer<List<Integer>, List<Integer>> original) {
        final int n = expect.size();
        Assert.assertEquals(n, actual.size());
        Assert.assertEquals(expect.get(0), actual.get(0));
        for (int i = 0; i < n; i++) {
            int or = actual.get(i) ^ actual.get((i + 1) % n);
            Assert.assertTrue(or != 0 && (or & (or - 1)) == 0);
        }
    }

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 2)
            .expect(Arrays.asList(2, 6, 7, 5, 4, 0, 1, 3))
            .assertMethod(this::assertMethod);

}
