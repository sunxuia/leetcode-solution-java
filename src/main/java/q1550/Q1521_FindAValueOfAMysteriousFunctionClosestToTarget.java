package q1550;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1521. Find a Value of a Mysterious Function Closest to Target
 * https://leetcode.com/problems/find-a-value-of-a-mysterious-function-closest-to-target/
 *
 * <img src="./Q1521_PIC.png">
 * Winston was given the above mysterious function func. He has an integer array arr and an integer target and he wants
 * to find the values l and r that make the value |func(arr, l, r) - target| minimum possible.
 *
 * Return the minimum possible value of |func(arr, l, r) - target|.
 *
 * Notice that func should be called with the values l and r where 0 <= l, r < arr.length.
 *
 * Example 1:
 *
 * Input: arr = [9,12,3,7,15], target = 5
 * Output: 2
 * Explanation: Calling func with all the pairs of [l,r] = [[0,0],[1,1],[2,2],[3,3],[4,4],[0,1],[1,2],[2,3],[3,4],[0,
 * 2],[1,3],[2,4],[0,3],[1,4],[0,4]],
 * Winston got the following results [9,12,3,7,15,8,0,3,7,0,0,3,0,0,0]. The value closest to 5 is 7 and 3, thus the
 * minimum difference is 2.
 *
 * Example 2:
 *
 * Input: arr = [1000000,1000000,1000000], target = 1
 * Output: 999999
 * Explanation: Winston called the func with all possible values of [l,r] and he always got 1000000, thus the min
 * difference is 999999.
 *
 * Example 3:
 *
 * Input: arr = [1,2,4,8,16], target = 0
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^6
 * 0 <= target <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1521_FindAValueOfAMysteriousFunctionClosestToTarget {

    /**
     * func 就是 arr[l, r] 非空区间的值的 & 结果.
     * 这样元素越多, 与的结果就是单调下降的, 所以可以用滑动窗口来做.
     */
    @Answer
    public int closestToTarget(int[] arr, int target) {
        int res = Integer.MAX_VALUE;
        int[] bits = new int[32];
        offsetBit(bits, arr[0], 1);
        int i = 0, j = 0;
        while (true) {
            int val = getValue(bits, j - i + 1);
            if (val == target) {
                return 0;
            }
            res = Math.min(res, Math.abs(val - target));
            if (val < target) {
                // 结果 < target, 则要减小窗口
                offsetBit(bits, arr[i++], -1);
            }
            if (val > target || i > j) {
                // 结果 > target (或者没有元素) 则要增加窗口
                if (++j == arr.length) {
                    break;
                }
                offsetBit(bits, arr[j], 1);
            }
        }
        return res;
    }

    private void offsetBit(int[] bits, int num, int offset) {
        for (int i = 0; num > 0; i++, num >>>= 1) {
            bits[i] += (num & 1) * offset;
        }
    }

    private int getValue(int[] bits, int len) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i] == len) {
                res |= 1 << i;
            }
        }
        return res;
    }

    /**
     * LeetCode 中的主流解法, 比上面的慢.
     */
    @Answer
    public int closestToTarget2(int[] arr, int target) {
        int res = Integer.MAX_VALUE;
        // 保存与操作的结果
        Set<Integer> masks = new HashSet<>();
        for (int num : arr) {
            Set<Integer> newMasks = new HashSet<>();
            newMasks.add(num);
            // 对原有的结果进行与操作.
            for (int s : masks) {
                newMasks.add(s & num);
            }
            masks = newMasks;
            for (int s : masks) {
                res = Math.min(res, Math.abs(target - s));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{9, 12, 3, 7, 15}, 5)
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1000000, 1000000, 1000000}, 1)
            .expect(999999);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 4, 8, 16}, 0)
            .expect(0);

}
