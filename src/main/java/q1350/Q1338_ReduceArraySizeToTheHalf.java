package q1350;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1338. Reduce Array Size to The Half
 * https://leetcode.com/problems/reduce-array-size-to-the-half/
 *
 * Given an array arr.  You can choose a set of integers and remove all the occurrences of these integers in the array.
 *
 * Return the minimum size of the set so that at least half of the integers of the array are removed.
 *
 * Example 1:
 *
 * Input: arr = [3,3,3,3,5,5,5,2,2,7]
 * Output: 2
 * Explanation: Choosing {3,7} will make the new array [5,5,5,2,2] which has size 5 (i.e equal to half of the size of
 * the old array).
 * Possible sets of size 2 are {3,5},{3,2},{5,2}.
 * Choosing set {2,7} is not possible as it will make the new array [3,3,3,3,5,5,5] which has size greater than half of
 * the size of the old array.
 *
 * Example 2:
 *
 * Input: arr = [7,7,7,7,7,7]
 * Output: 1
 * Explanation: The only possible set you can choose is {7}. This will make the new array empty.
 *
 * Example 3:
 *
 * Input: arr = [1,9]
 * Output: 1
 *
 * Example 4:
 *
 * Input: arr = [1000,1000,3,7]
 * Output: 1
 *
 * Example 5:
 *
 * Input: arr = [1,2,3,4,5,6,7,8,9,10]
 * Output: 5
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * arr.length is even.
 * 1 <= arr[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1338_ReduceArraySizeToTheHalf {

    @Answer
    public int minSetSize(int[] arr) {
        int[] counts = new int[100001];
        for (int num : arr) {
            counts[num]++;
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> counts[b] - counts[a]);
        for (int i = 1; i <= 100000; i++) {
            if (counts[i] > 0) {
                pq.add(i);
            }
        }
        int res = 0, total = 0;
        while (!pq.isEmpty() && total < arr.length / 2) {
            total += counts[pq.poll()];
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 3, 3, 3, 5, 5, 5, 2, 2, 7}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 7, 7, 7, 7, 7}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 9}).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1000, 1000, 3, 7}).expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).expect(5);

}
