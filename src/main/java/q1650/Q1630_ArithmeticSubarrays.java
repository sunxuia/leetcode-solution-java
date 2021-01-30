package q1650;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1630. Arithmetic Subarrays
 * https://leetcode.com/problems/arithmetic-subarrays/
 *
 * A sequence of numbers is called arithmetic if it consists of at least two elements, and the difference between every
 * two consecutive elements is the same. More formally, a sequence s is arithmetic if and only if s[i+1] - s[i] == s[1]
 * - s[0] for all valid i.
 *
 * For example, these are arithmetic sequences:
 *
 * 1, 3, 5, 7, 9
 * 7, 7, 7, 7
 * 3, -1, -5, -9
 *
 * The following sequence is not arithmetic:
 *
 * 1, 1, 2, 5, 7
 *
 * You are given an array of n integers, nums, and two arrays of m integers each, l and r, representing the m range
 * queries, where the ith query is the range [l[i], r[i]]. All the arrays are 0-indexed.
 *
 * Return a list of boolean elements answer, where answer[i] is true if the subarray nums[l[i]], nums[l[i]+1], ... ,
 * nums[r[i]] can be rearranged to form an arithmetic sequence, and false otherwise.
 *
 * Example 1:
 *
 * Input: nums = [4,6,5,9,3,7], l = [0,0,2], r = [2,3,5]
 * Output: [true,false,true]
 * Explanation:
 * In the 0th query, the subarray is [4,6,5]. This can be rearranged as [6,5,4], which is an arithmetic sequence.
 * In the 1st query, the subarray is [4,6,5,9]. This cannot be rearranged as an arithmetic sequence.
 * In the 2nd query, the subarray is [5,9,3,7]. This can be rearranged as [3,5,7,9], which is an arithmetic sequence.
 *
 * Example 2:
 *
 * Input: nums = [-12,-9,-3,-12,-6,15,20,-25,-20,-15,-10], l = [0,1,6,4,8,7], r = [4,4,9,7,9,10]
 * Output: [false,true,false,false,true,true]
 *
 * Constraints:
 *
 * n == nums.length
 * m == l.length
 * m == r.length
 * 2 <= n <= 500
 * 1 <= m <= 500
 * 0 <= l[i] < r[i] < n
 * -10^5 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1630_ArithmeticSubarrays {

    @Answer
    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        final int n = nums.length, m = l.length;
        List<Boolean> res = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            int[] subArr = Arrays.copyOfRange(nums, l[i], r[i] + 1);
            Arrays.sort(subArr);
            res.add(true);
            for (int j = 2; j < subArr.length; j++) {
                if (subArr[j - 2] - subArr[j - 1] != subArr[j - 1] - subArr[j]) {
                    res.set(res.size() - 1, false);
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 参考LeetCode, 优化上面排序的做法, 降低了排序的时间复杂度
     */
    @Answer
    public List<Boolean> checkArithmeticSubarrays2(int[] nums, int[] l, int[] r) {
        final int m = l.length;
        List<Boolean> res = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            int max = nums[l[i]], min = nums[l[i]];
            for (int j = l[i]; j <= r[i]; j++) {
                max = Math.max(max, nums[j]);
                min = Math.min(min, nums[j]);
            }
            if (max == min) {
                res.add(true);
                continue;
            }
            // value range, index range
            int vr = max - min, ir = r[i] - l[i];
            if (vr % ir != 0) {
                res.add(false);
                continue;
            }
            int interval = vr / ir;
            boolean isValid = true;
            boolean[] visited = new boolean[ir + 1];
            for (int j = l[i]; j <= r[i] && isValid; j++) {
                int idx = (nums[j] - min) / interval;
                isValid = (nums[j] - min) % interval == 0 && !visited[idx];
                visited[idx] = true;
            }
            res.add(isValid);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 6, 5, 9, 3, 7}, new int[]{0, 0, 2}, new int[]{2, 3, 5})
            .expect(List.of(true, false, true));

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            new int[]{-12, -9, -3, -12, -6, 15, 20, -25, -20, -15, -10},
            new int[]{0, 1, 6, 4, 8, 7},
            new int[]{4, 4, 9, 7, 9, 10}
    ).expect(List.of(false, true, false, false, true, true));

}
