package q1100;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1095. Find in Mountain Array
 * https://leetcode.com/problems/find-in-mountain-array/
 *
 * (This problem is an interactive problem.)
 *
 * You may recall that an array A is a mountain array if and only if:
 *
 * A.length >= 3
 * There exists some i with 0 < i < A.length - 1 such that:
 *
 * A[0] < A[1] < ... A[i-1] < A[i]
 * A[i] > A[i+1] > ... > A[A.length - 1]
 *
 * Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target.  If such an
 * index doesn't exist, return -1.
 *
 * You can't access the mountain array directly.  You may only access the array using a MountainArray interface:
 *
 * MountainArray.get(k) returns the element of the array at index k (0-indexed).
 * MountainArray.length() returns the length of the array.
 *
 * Submissions making more than 100 calls to MountainArray.get will be judged Wrong Answer.  Also, any solutions that
 * attempt to circumvent the judge will result in disqualification.
 *
 * Example 1:
 *
 * Input: array = [1,2,3,4,5,3,1], target = 3
 * Output: 2
 * Explanation: 3 exists in the array, at index=2 and index=5. Return the minimum index, which is 2.
 *
 * Example 2:
 *
 * Input: array = [0,1,2,4,2,1], target = 3
 * Output: -1
 * Explanation: 3 does not exist in the array, so we return -1.
 *
 * Constraints:
 *
 * 3 <= mountain_arr.length() <= 10000
 * 0 <= target <= 10^9
 * 0 <= mountain_arr.get(index) <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1095_FindInMountainArray {

    private interface MountainArray {

        int get(int index);

        int length();
    }

//    @DebugWith("normal2")
    @Answer
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int pivot = findPivot(mountainArr);
        int a = findInAsc(mountainArr, target, 0, pivot);
        if (a != -1) {
            return a;
        }
        return findInDesc(mountainArr, target, pivot + 1, mountainArr.length() - 1);
    }

    private int findPivot(MountainArray mountainArr) {
        int start = 0, end = mountainArr.length() - 1;
        while (start < end - 1) {
            int mid = (start + end) / 2;
            int val = mountainArr.get(mid);
            int prev = mountainArr.get(mid - 1);
            if (prev < val) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    private int findInAsc(MountainArray mountainArr, int target, int start, int end) {
        while (start <= end) {
            int mid = (start + end) / 2;
            int val = mountainArr.get(mid);
            if (val < target) {
                start = mid + 1;
            } else if (val == target) {
                return mid;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    private int findInDesc(MountainArray mountainArr, int target, int start, int end) {
        while (start <= end) {
            int mid = (start + end) / 2;
            int val = mountainArr.get(mid);
            if (val > target) {
                start = mid + 1;
            } else if (val == target) {
                return mid;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    private class MountainArrayImpl implements MountainArray {

        final int[] arr;

        int calls;

        public MountainArrayImpl(int[] arr) {
            this.arr = arr;
        }

        @Override
        public int get(int index) {
            calls++;
            return arr[index];
        }

        @Override
        public int length() {
            return arr.length;
        }
    }

    @TestData
    public DataExpectation example1 = createTestData(3, new int[]{1, 2, 3, 4, 5, 3, 1}, 2);

    private DataExpectation createTestData(int target, int[] mountArr, int expect) {
        return DataExpectation.builder()
                .addArgument(target)
                .addArgument(new MountainArrayImpl(mountArr))
                .expect(expect)
                .argumentAssertMethod(1, (MountainArrayImpl e, MountainArrayImpl actual) -> {
                    Assert.assertTrue("Called " + actual.calls + " times.",
                            actual.calls < 100);
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData(3, new int[]{0, 1, 2, 4, 2, 1}, -1);

    @TestData
    public DataExpectation normal1 = createTestData(2, new int[]{1, 2, 3, 4, 5, 3, 1}, 1);

    @TestData
    public DataExpectation normal2 = createTestData(0, new int[]{1, 5, 2}, -1);

}
