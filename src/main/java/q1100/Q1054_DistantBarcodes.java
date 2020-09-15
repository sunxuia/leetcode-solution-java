package q1100;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1054. Distant Barcodes
 * https://leetcode.com/problems/distant-barcodes/
 *
 * In a warehouse, there is a row of barcodes, where the i-th barcode is barcodes[i].
 *
 * Rearrange the barcodes so that no two adjacent barcodes are equal.  You may return any answer, and it is guaranteed
 * an answer exists.
 *
 * Example 1:
 *
 * Input: [1,1,1,2,2,2]
 * Output: [2,1,2,1,2,1]
 *
 * Example 2:
 *
 * Input: [1,1,1,1,2,2,3,3]
 * Output: [1,3,1,3,2,1,2,1]
 *
 * Note:
 *
 * 1 <= barcodes.length <= 10000
 * 1 <= barcodes[i] <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q1054_DistantBarcodes {

    @Answer
    public int[] rearrangeBarcodes(int[] barcodes) {
        int[] bucket = new int[1_0001];
        for (int barcode : barcodes) {
            bucket[barcode]++;
        }

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 1_0000; i++) {
            if (bucket[i] > 0) {
                nums.add(i);
            }
        }
        nums.sort((a, b) -> bucket[b] - bucket[a]);

        int i = 0;
        for (int val : nums) {
            while (bucket[val]-- > 0) {
                barcodes[i] = val;
                i = i < barcodes.length - 2 ? i + 2 : 1;
            }
        }
        return barcodes;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[]{1, 1, 1, 2, 2, 2});

    private DataExpectation createTestData(int[] barcodes) {
        return DataExpectation.builder()
                .addArgument(barcodes)
                .assertMethod((int[] actual) -> {
                    for (int i = 1; i < actual.length; i++) {
                        Assert.assertNotEquals(actual[i - 1], actual[i]);
                    }

                    var bucket = new int[1_0001];
                    for (int barcode : barcodes) {
                        bucket[barcode]++;
                    }
                    for (int i : actual) {
                        bucket[i]--;
                    }
                    for (int i = 0; i < 1_0001; i++) {
                        Assert.assertEquals(0, bucket[i]);
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData(new int[]{1, 1, 1, 1, 2, 2, 3, 3});

    @TestData
    public DataExpectation normal1 = createTestData(new int[]{1, 1, 1, 1, 2, 2, 3, 3});

    @TestData
    public DataExpectation normal2 = createTestData(new int[]{7, 7, 7, 8, 5, 7, 5, 5, 5, 8});

    @TestData
    public DataExpectation normal3 = createTestData(new int[]{2, 2, 1, 3});

}
