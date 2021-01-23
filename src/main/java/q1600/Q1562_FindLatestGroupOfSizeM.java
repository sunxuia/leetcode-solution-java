package q1600;

import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1562. Find Latest Group of Size M
 * https://leetcode.com/problems/find-latest-group-of-size-m/
 *
 * Given an array arr that represents a permutation of numbers from 1 to n. You have a binary string of size n that
 * initially has all its bits set to zero.
 *
 * At each step i (assuming both the binary string and arr are 1-indexed) from 1 to n, the bit at position arr[i] is set
 * to 1. You are given an integer m and you need to find the latest step at which there exists a group of ones of length
 * m. A group of ones is a contiguous substring of 1s such that it cannot be extended in either direction.
 *
 * Return the latest step at which there exists a group of ones of length exactly m. If no such group exists, return
 * -1.
 *
 * Example 1:
 *
 * Input: arr = [3,5,1,2,4], m = 1
 * Output: 4
 * Explanation:
 * Step 1: "00100", groups: ["1"]
 * Step 2: "00101", groups: ["1", "1"]
 * Step 3: "10101", groups: ["1", "1", "1"]
 * Step 4: "11101", groups: ["111", "1"]
 * Step 5: "11111", groups: ["11111"]
 * The latest step at which there exists a group of size 1 is step 4.
 *
 * Example 2:
 *
 * Input: arr = [3,1,5,4,2], m = 2
 * Output: -1
 * Explanation:
 * Step 1: "00100", groups: ["1"]
 * Step 2: "10100", groups: ["1", "1"]
 * Step 3: "10101", groups: ["1", "1", "1"]
 * Step 4: "10111", groups: ["1", "111"]
 * Step 5: "11111", groups: ["11111"]
 * No group of size 2 exists during any step.
 *
 * Example 3:
 *
 * Input: arr = [1], m = 1
 * Output: 1
 *
 * Example 4:
 *
 * Input: arr = [2,1], m = 2
 * Output: 2
 *
 * Constraints:
 *
 * n == arr.length
 * 1 <= n <= 10^5
 * 1 <= arr[i] <= n
 * All integers in arr are distinct.
 * 1 <= m <= arr.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1562_FindLatestGroupOfSizeM {

    @Answer
    public int findLatestStep(int[] arr, int m) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(-2, -2);
        map.put(Integer.MAX_VALUE, Integer.MAX_VALUE);
        int res = -1, count = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = arr[i], end = arr[i] + 1;

            int lStart = map.lowerKey(start);
            int lEnd = map.get(lStart);
            if (lEnd == start) {
                if (lEnd - lStart == m) {
                    count--;
                }
                start = lStart;
            }

            int hStart = map.higherKey(start);
            int hEnd = map.get(hStart);
            if (end == hStart) {
                end = map.remove(hStart);
                if (hEnd - hStart == m) {
                    count--;
                }
            }
            map.put(start, end);

            if (end - start == m) {
                count++;
            }
            if (count > 0) {
                res = i + 1;
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法.
     */
    @Answer
    public int findLatestStep2(int[] arr, int m) {
        final int n = arr.length;
        if (m == n) {
            return n;
        }
        int res = -1;
        // lens[v] 表示值 v 所在的区间的长度
        int[] lens = new int[n + 2];
        for (int i = 0; i < n; i++) {
            int v = arr[i];
            int left = lens[v - 1], right = lens[v + 1];
            lens[v - left] = lens[v + right]
                    = left + 1 + right;
            if (left == m || right == m) {
                res = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 5, 1, 2, 4}, 1).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 1, 5, 4, 2}, 2).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1}, 1).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{2, 1}, 2).expect(2);

}
