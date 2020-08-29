package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1007. Minimum Domino Rotations For Equal Row
 * https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
 *
 * In a row of dominoes, A[i] and B[i] represent the top and bottom halves of the i-th domino.  (A domino is a tile with
 * two numbers from 1 to 6 - one on each half of the tile.)
 *
 * We may rotate the i-th domino, so that A[i] and B[i] swap values.
 *
 * Return the minimum number of rotations so that all the values in A are the same, or all the values in B are the
 * same.
 *
 * If it cannot be done, return -1.
 *
 * Example 1:
 * (图 Q1007_PIC.png)
 * Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
 * Output: 2
 * Explanation:
 * The first figure represents the dominoes as given by A and B: before we do any rotations.
 * If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the
 * second figure.
 *
 * Example 2:
 *
 * Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
 * Output: -1
 * Explanation:
 * In this case, it is not possible to rotate the dominoes to make one row of values equal.
 *
 * Note:
 *
 * 1 <= A[i], B[i] <= 6
 * 2 <= A.length == B.length <= 20000
 */
@RunWith(LeetCodeRunner.class)
public class Q1007_MinimumDominoRotationsForEqualRow {

    /**
     * 参考文档 http://www.noteanddata.com/leetcode-1007-Minimum-Domino-Rotations-For-Equal-Row-java-solution-note-2.html
     *
     * 无论怎么换位置, 如果最后可以让某一行的数字完全一样, 每个位置上只有两个可能, 这个数要么是A的, 要么是B的.
     * 那么, 对第0个数字来说, 要么是A[0], 要么是B[0]. 不管在哪一行, 后面的数字要么和A[0]相同, 要么和B[0]相同, 否则就不能达到效果.
     * 由此可得出解法:
     * 第1 次遍历, 找出A/ B 中与A[0] 相同的数量, 第2 次遍历, 找出A/ B 中与 B[0]相同的数量, 然后比较大小.
     */
    @Answer
    public int minDominoRotations(int[] A, int[] B) {
        int flipA = flipCount(A, B, A[0]);
        // 如果 A[0] == B[0], 则之后的计算不需要判断.
        // 如果 flipA != -1, 说明对于所有 A[i] 和 B[i], 两者至少1 个等于 A[0], 则对于 B[i]的情况:
        // 1. 如果 A[0] != B[0], 对于所有 A[i] 和 B[i], 如果 A[i] B[i] 都和 B[0] 不同, 则结果为-1,
        // 2. 如果 A[i] == A[0], B[i] == B[0], 则 A[i] 在本次情况不需要翻转, 下次也不需要翻转, B[i] 相反.
        // 3. 如果 A[i] == B[0], B[i] == A[0], 则 B[i] 在本次情况不需要翻转, 下次也不需要翻转, A[i] 相反.
        // 因此之后的计算也就不需要了.
        if (A[0] == B[0] || flipA != -1) {
            return flipA;
        }
        return flipCount(A, B, B[0]);
    }

    // 计算 A 翻转到 B 或 B 翻转到 A 的最小次数
    private int flipCount(int[] A, int[] B, int val) {
        final int n = A.length;
        // A 中与val 相同和B 中与val 相同的数量
        int aSame = 0, bSame = 0;
        for (int i = 0; i < n; i++) {
            if (A[i] != val && B[i] != val) {
                return -1;
            }
            aSame += A[i] == val ? 1 : 0;
            bSame += B[i] == val ? 1 : 0;
        }
        return Math.min(n - aSame, n - bSame);
    }

    /**
     * 与上面相同的另一种解法, 相比上面的 flipCount 要计算a 翻到b 和b 翻到a 的数量,
     * 这里的countFlip 只需要计算一种翻转的次数, 这个稍微快一些, 也便于理解.
     */
    @Answer
    public int minDominoRotations2(int[] A, int[] B) {
        int a = countFlip(A, B, A[0]);
        int b = countFlip(B, A, A[0]);
        if (A[0] == B[0] || a > -1 || b > -1) {
            return a > -1 && b > -1
                    ? Math.min(a, b) : Math.max(a, b);
        }
        int c = countFlip(A, B, B[0]);
        int d = countFlip(B, A, B[0]);
        return c > -1 && d > -1
                ? Math.min(c, d) : Math.max(c, d);
    }

    // 计算 B 翻转到 A 的次数.
    private int countFlip(int[] A, int[] B, int val) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == val) {
                // do nothing
            } else if (B[i] == val) {
                res++;
            } else {
                return -1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 1, 2, 4, 2, 2}, new int[]{5, 2, 6, 2, 3, 2})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 5, 1, 2, 3}, new int[]{3, 6, 3, 3, 4})
            .expect(-1);

}
