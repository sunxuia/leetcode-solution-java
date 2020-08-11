package q950;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 923. 3Sum With Multiplicity
 * https://leetcode.com/problems/3sum-with-multiplicity/
 *
 * Given an integer array A, and an integer target, return the number of tuples i, j, k  such that i < j < k and A[i] +
 * A[j] + A[k] == target.
 *
 * As the answer can be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: A = [1,1,2,2,3,3,4,4,5,5], target = 8
 * Output: 20
 * Explanation:
 * Enumerating by the values (A[i], A[j], A[k]):
 * (1, 2, 5) occurs 8 times;
 * (1, 3, 4) occurs 8 times;
 * (2, 2, 4) occurs 2 times;
 * (2, 3, 3) occurs 2 times.
 *
 * Example 2:
 *
 * Input: A = [1,1,2,2,2,2], target = 5
 * Output: 12
 * Explanation:
 * A[i] = 1, A[j] = A[k] = 2 occurs 12 times:
 * We choose one 1 from [1,1] in 2 ways,
 * and two 2s from [2,2,2,2] in 6 ways.
 *
 * Note:
 *
 * 3 <= A.length <= 3000
 * 0 <= A[i] <= 100
 * 0 <= target <= 300
 */
@RunWith(LeetCodeRunner.class)
public class Q923_3sumWithMultiplicity {

    @Answer
    public int threeSumMulti(int[] A, int target) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : A) {
            Integer cnt = counts.get(i);
            if (cnt == null) {
                list.add(i);
                counts.put(i, 1);
            } else {
                counts.put(i, cnt + 1);
            }
        }
        list.sort(Comparator.naturalOrder());

        long res = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                int a = list.get(i), ac = counts.get(a);
                int b = list.get(j), bc = counts.get(b);
                int c = target - a - b;
                Integer cc = counts.get(c);
                if (cc == null || c < b) {
                    continue;
                }
                int divide = 1;
                if (a == b) {
                    bc = ac - 1;
                    divide = 2;
                }
                if (b == c) {
                    cc = bc - 1;
                    divide = divide == 2 ? 6 : 2;
                }
                if (bc <= 0 || cc <= 0) {
                    continue;
                }
                res += (long) ac * bc * cc / divide;
                res %= 10_0000_0007;
            }
        }
        return (int) res;
    }

    /**
     * LeetCode 上比较快的解法, 因为0 <= A[i] <= 100 所以直接桶排序就好了
     */
    @Answer
    public int leetCode(int[] A, int target) {
        long[] c = new long[101];
        for (int a : A) {
            c[a]++;
        }
        long res = 0;
        for (int i = 0; i <= 100; i++) {
            for (int j = i; j <= 100; j++) {
                int k = target - i - j;
                if (k > 100 || k < 0) {
                    continue;
                }
                if (i == j && j == k) {
                    res += c[i] * (c[i] - 1) * (c[i] - 2) / 6;
                } else if (i == j) {
                    res += c[i] * (c[i] - 1) / 2 * c[k];
                } else if (j < k) {
                    res += c[i] * c[j] * c[k];
                }
            }
        }
        return (int) (res % (1e9 + 7));
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 8)
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 2, 2, 2, 2}, 5)
            .expect(12);

    @TestData
    public DataExpectation zero3000 = DataExpectation
            .createWith(new int[3000], 0)
            .expect(495500972);

}
