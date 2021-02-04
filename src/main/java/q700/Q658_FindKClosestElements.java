package q700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-k-closest-elements/
 *
 * Given a sorted array arr, two integers k and x, find the k closest elements to x in the array. The result should
 * also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [1,2,3,4,5], k = 4, x = 3
 * Output: [1,2,3,4]
 *
 * Example 2:
 *
 * Input: arr = [1,2,3,4,5], k = 4, x = -1
 * Output: [1,2,3,4]
 *
 *
 *
 * Constraints:
 *
 * 1 <= k <= arr.length
 * 1 <= arr.length <= 10^4
 * Absolute value of elements in the array and x will not exceed 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q658_FindKClosestElements {

    @Answer
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int idx = Arrays.binarySearch(arr, x);
        idx = idx >= 0 ? idx : -idx - 1;
        int left = idx - 1, right = idx;
        while (right - left - 1 < k) {
            if (left == -1 || right < arr.length
                    && x - arr[left] > arr[right] - x) {
                right++;
            } else {
                left--;
            }
        }
        List<Integer> res = new ArrayList<>(k);
        while (++left < right) {
            res.add(arr[left]);
        }
        return res;
    }

    // LeetCode 上最快的方法
    //  这个相对上面的解答不同点在于是从两边逼近的, 对于LeetCode 中的测试用例跑得更快一些.
    @Answer
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        int left = 0, right = arr.length - 1;
        while (right - left + 1 > k) {
            if (x - arr[left] > arr[right] - x) {
                left++;
            } else {
                right--;
            }
        }
        List<Integer> res = new ArrayList<>(k);
        while (left <= right) {
            res.add(arr[left++]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, 4, 3)
            .expect(Arrays.asList(1, 2, 3, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, 4, -1)
            .expect(Arrays.asList(1, 2, 3, 4));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 8}, 3, 5)
            .expect(Arrays.asList(3, 3, 4));

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1}, 1, 1)
            .expect(Arrays.asList(1));

}
