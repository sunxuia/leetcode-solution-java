package q1450;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1442. Count Triplets That Can Form Two Arrays of Equal XOR
 * https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/
 *
 * Given an array of integers arr.
 *
 * We want to select three indices i, j and k where (0 <= i < j <= k < arr.length).
 *
 * Let's define a and b as follows:
 *
 * a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
 * b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
 *
 * Note that ^ denotes the bitwise-xor operation.
 *
 * Return the number of triplets (i, j and k) Where a == b.
 *
 * Example 1:
 *
 * Input: arr = [2,3,1,6,7]
 * Output: 4
 * Explanation: The triplets are (0,1,2), (0,2,2), (2,3,4) and (2,4,4)
 *
 * Example 2:
 *
 * Input: arr = [1,1,1,1,1]
 * Output: 10
 *
 * Example 3:
 *
 * Input: arr = [2,3]
 * Output: 0
 *
 * Example 4:
 *
 * Input: arr = [1,3,5,7,9]
 * Output: 3
 *
 * Example 5:
 *
 * Input: arr = [7,11,12,9,5,2,7,17,22]
 * Output: 8
 *
 * Constraints:
 *
 * 1 <= arr.length <= 300
 * 1 <= arr[i] <= 10^8
 */
@RunWith(LeetCodeRunner.class)
public class Q1442_CountTripletsThatCanFormTwoArraysOfEqualXor {

    /**
     * 暴力求解, 时间复杂度 O(N^3)
     */
    @Answer
    public int countTriplets(int[] arr) {
        final int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int a = arr[i];
            for (int j = i + 1; j < n; j++) {
                int b = 0;
                for (int k = j; k < n; k++) {
                    b ^= arr[k];
                    if (a == b) {
                        res++;
                    }
                }
                a ^= arr[j];
            }
        }
        return res;
    }

    /**
     * 根据位运算的特点得到的解法, 时间复杂度 O(N^2).
     * 特点是如果 a == b 则 a^b == 0.
     * 从头到尾做异或运算, 如果i 和k 位置得到的结果一样, 则对于(i, k] 之间的
     * 任意点j, 都存在mask[i:j) ^ mask[j:k] == 0, 即 mask[i:j) == mask[j:k]
     */
    @Answer
    public int countTriplets2(int[] arr) {
        final int n = arr.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(0, new ArrayList<>(List.of(-1)));
        int res = 0, mask = 0;
        for (int i = 0; i < n; i++) {
            mask ^= arr[i];
            if (map.containsKey(mask)) {
                List<Integer> list = map.get(mask);
                // 统计当前点到相同mask 的点的距离.
                for (int prev : list) {
                    res += i - prev - 1;
                }
                list.add(i);
            } else {
                map.put(mask, new ArrayList<>(List.of(i)));
            }
        }
        return res;
    }


    /**
     * 针对上面解法的优化, 时间复杂度 O(N).
     */
    @Answer
    public int countTriplets3(int[] arr) {
        final int n = arr.length;
        Map<Integer, Accumulate> map = new HashMap<>();
        map.put(0, new Accumulate(-1));
        int res = 0, mask = 0;
        for (int i = 0; i < n; i++) {
            mask ^= arr[i];
            if (map.containsKey(mask)) {
                Accumulate acc = map.get(mask);
                acc.sum += (i - acc.idx) * acc.count - 1;
                acc.idx = i;
                acc.count++;
                res += acc.sum;
            } else {
                map.put(mask, new Accumulate(i));
            }
        }
        return res;
    }

    private static class Accumulate {

        // 从idx 到之前各点的路程之和为sum.
        // 包括idx 在内的所有的点有count 个.
        int sum, idx, count;

        Accumulate(int idx) {
            this.idx = idx;
            this.count = 1;
        }
    }

    /**
     * LeetCode 上的解法.
     * 是针对上面countTriplets 的解法的优化, 时间复杂度 O(N^2).
     * 同样利用了异或的特点若 a == b 则 a^b == 0.
     */
    @Answer
    public int countTriplets4(int[] arr) {
        final int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int x = arr[i];
            for (int j = i + 1; j < n; j++) {
                x ^= arr[j];
                if (x == 0) {
                    res += j - i;
                }
            }
        }

        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 1, 6, 7}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 1, 1}).expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 3}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 3, 5, 7, 9}).expect(3);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{7, 11, 12, 9, 5, 2, 7, 17, 22}).expect(8);

}
