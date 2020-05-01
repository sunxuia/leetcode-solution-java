package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/array-nesting/
 *
 * A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set
 * S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
 *
 * Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S
 * should be A[A[i]], and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.
 *
 *
 *
 * Example 1:
 *
 * Input: A = [5,4,0,3,1,6,2]
 * Output: 4
 * Explanation:
 * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
 *
 * One of the longest S[K]:
 * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 *
 *
 *
 * Note:
 *
 * 1. N is an integer within the range [1, 20,000].
 * 2. The elements of A are all distinct.
 * 3. Each element of A is an integer within the range [0, N-1].
 */
@RunWith(LeetCodeRunner.class)
public class Q565_ArrayNesting {

    @Answer
    public int arrayNesting(int[] nums) {
        int[] length = new int[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (length[i] == 0) {
                dfs(nums, length, i, 1);
            }
            res = Math.max(res, length[i]);
        }
        return res;
    }

    private void dfs(int[] nums, int[] length, int idx, int step) {
        if (length[idx] >= step) {
            return;
        }
        length[idx] = Integer.MAX_VALUE;
        dfs(nums, length, nums[idx], step + 1);
        length[idx] = step;
    }

    // LeetCode 上更快的做法
    @Answer
    public int arrayNesting2(int[] nums) {
        int res = 0;
        boolean[] checked = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = i; !checked[j]; j = nums[j]) {
                checked[j] = true;
                count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{5, 4, 0, 3, 1, 6, 2}).expect(4);

}
