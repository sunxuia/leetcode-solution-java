package q1900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1889. Minimum Space Wasted From Packaging
 * https://leetcode.com/problems/minimum-space-wasted-from-packaging/
 *
 * You have n packages that you are trying to place in boxes, one package in each box. There are m suppliers that each
 * produce boxes of different sizes (with infinite supply). A package can be placed in a box if the size of the package
 * is less than or equal to the size of the box.
 *
 * The package sizes are given as an integer array packages, where packages[i] is the size of the ith package. The
 * suppliers are given as a 2D integer array boxes, where boxes[j] is an array of box sizes that the jth supplier
 * produces.
 *
 * You want to choose a single supplier and use boxes from them such that the total wasted space is minimized. For each
 * package in a box, we define the space wasted to be size of the box - size of the package. The total wasted space is
 * the sum of the space wasted in all the boxes.
 *
 * For example, if you have to fit packages with sizes [2,3,5] and the supplier offers boxes of sizes [4,8], you can fit
 * the packages of size-2 and size-3 into two boxes of size-4 and the package with size-5 into a box of size-8. This
 * would result in a waste of (4-2) + (4-3) + (8-5) = 6.
 *
 * Return the minimum total wasted space by choosing the box supplier optimally, or -1 if it is impossible to fit all
 * the packages inside boxes. Since the answer may be large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: packages = [2,3,5], boxes = [[4,8],[2,8]]
 * Output: 6
 * Explanation: It is optimal to choose the first supplier, using two size-4 boxes and one size-8 box.
 * The total waste is (4-2) + (4-3) + (8-5) = 6.
 *
 * Example 2:
 *
 * Input: packages = [2,3,5], boxes = [[1,4],[2,3],[3,4]]
 * Output: -1
 * Explanation: There is no box that the package of size 5 can fit in.
 *
 * Example 3:
 *
 * Input: packages = [3,5,8,10,11,12], boxes = [[12],[11,9],[10,5,14]]
 * Output: 9
 * Explanation: It is optimal to choose the third supplier, using two size-5 boxes, two size-10 boxes, and two size-14
 * boxes.
 * The total waste is (5-3) + (5-5) + (10-8) + (10-10) + (14-11) + (14-12) = 9.
 *
 * Constraints:
 *
 * n == packages.length
 * m == boxes.length
 * 1 <= n <= 10^5
 * 1 <= m <= 10^5
 * 1 <= packages[i] <= 10^5
 * 1 <= boxes[j].length <= 10^5
 * 1 <= boxes[j][k] <= 10^5
 * sum(boxes[j].length) <= 10^5
 * The elements in boxes[j] are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1889_MinimumSpaceWastedFromPackaging {

    @Answer
    public int minWastedSpace(int[] packages, int[][] boxes) {
        int max = 0;
        for (int packSize : packages) {
            max = Math.max(max, packSize);
        }

        // package 的分布
        int[] buckets = new int[max + 1];
        for (int packSize : packages) {
            buckets[packSize]++;
        }
        // sums[i] 表示 值<=i 的package 数量
        int[] sums = new int[max + 1];
        // dists[i] 表示对于所有<=i 的元素, ∑(i-元素值)
        long[] dists = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            sums[i] = sums[i - 1] + buckets[i];
            dists[i] = dists[i - 1] + sums[i - 1];
        }

        long res = Long.MAX_VALUE;
        for (int[] boxGroup : boxes) {
            Arrays.sort(boxGroup);
            if (boxGroup[boxGroup.length - 1] < max) {
                continue;
            }
            int prev = 0;
            long total = 0;
            for (int box : boxGroup) {
                if (box < max) {
                    long wasted = dists[box] - dists[prev]
                            - (long) (box - prev) * sums[prev];
                    total += wasted;
                    prev = box;
                } else {
                    long wasted = dists[max] - dists[prev]
                            - (long) (max - prev) * sums[prev]
                            + (long) (box - max) * (sums[max] - sums[prev]);
                    total += wasted;
                    break;
                }
            }
            res = Math.min(res, total);
        }
        if (res == Long.MAX_VALUE) {
            return -1;
        }
        return (int) (res % 10_0000_0007);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 3, 5}, new int[][]{{4, 8}, {2, 8}})
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 3, 5}, new int[][]{{1, 4}, {2, 3}, {3, 4}})
            .expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 5, 8, 10, 11, 12}, new int[][]{{12}, {11, 9}, {10, 5, 14}})
            .expect(9);

    private TestDataFile file = new TestDataFile();

    @TestData
    public DataExpectation overflow = DataExpectation
            .createWith(TestDataFileHelper.read(file, 1, int[].class),
                    TestDataFileHelper.read(file, 2, int[][].class))
            .expect(499949986);

}
