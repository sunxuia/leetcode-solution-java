package q800;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/smallest-rotation-with-highest-score/
 *
 * Given an array A, we may rotate it by a non-negative integer K so that the array becomes A[K], A[K+1], A{K+2], ..
 * . A[A.length - 1], A[0], A[1], ..., A[K-1].  Afterward, any entries that are less than or equal to their index
 * are worth 1 point.
 *
 * For example, if we have [2, 4, 1, 3, 0], and we rotate by K = 2, it becomes [1, 3, 0, 2, 4].  This is worth 3
 * points because 1 > 0 [no points], 3 > 1 [no points], 0 <= 2 [one point], 2 <= 3 [one point], 4 <= 4 [one point].
 *
 * Over all possible rotations, return the rotation index K that corresponds to the highest score we could receive.
 * If there are multiple answers, return the smallest such index K.
 *
 * Example 1:
 * Input: [2, 3, 1, 4, 0]
 * Output: 3
 * Explanation:
 * Scores for each K are listed below:
 * K = 0,  A = [2,3,1,4,0],    score 2
 * K = 1,  A = [3,1,4,0,2],    score 3
 * K = 2,  A = [1,4,0,2,3],    score 3
 * K = 3,  A = [4,0,2,3,1],    score 4
 * K = 4,  A = [0,2,3,1,4],    score 3
 *
 * So we should choose K = 3, which has the highest score.
 *
 *
 *
 * Example 2:
 * Input: [1, 3, 0, 2, 4]
 * Output: 0
 * Explanation:  A will always have 3 points no matter how it shifts.
 * So we will choose the smallest K, which is 0.
 *
 * Note:
 *
 * A will have length at most 20000.
 * A[i] will be in the range [0, A.length].
 */
@RunWith(LeetCodeRunner.class)
public class Q798_SmallestRotationWithHighestScore {

    // 暴力枚举, 这种做法会超时
    public int bestRotation(int[] A) {
        final int n = A.length;
        int[] arr = new int[n * 2];
        System.arraycopy(A, 0, arr, 0, n);
        int res = 0, max = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (arr[i + j] <= j % n) {
                    count++;
                }
            }
            if (count > max) {
                max = count;
                res = i;
            }
            arr[i + n] = arr[i];
        }
        return res;
    }

    /**
     * 使用Map 保存差值 (i - A[i]) 的方式, 差值 >= 0 则点数 +1.
     * 因为每当移动一位, 除了首个元素之外其他的差值都 -1, 首位移动后的差值为 (N + i - A[i]) % N.
     * 这种做法可以简化为每次移动1 位, 差值的水位向上移动1, 变成 差值 >= w, 首位移动后的差值也变为了 N + i - A[i].
     * 这样, 使用Map 保存差值, 每次移动都剔除小于新的差值的结果, 并加入新的差值.
     *
     * 示例, 以example1 为例:
     * i    |  0  1  2  3  4 |5  6  7  8  9
     * A[i] |  2  3  1  4  0 |2  3  1  4  0
     * K=0  | -2 -2  1  -1 4
     * K=1  |    -2  1  -1 4  2
     * K=2  |        1  -1 4  2  3
     * ...
     * 比如 K=1 时, 将 A[0] 移动到 A[5], 则点数减去1个-2, 增加1 个2;
     * 并且水位由0 变为1, 点数删除1 个1 (下标2对应的差值).
     */
    @Answer
    public int bestRotation2(int[] A) {
        final int n = A.length;
        // 满足条件的差值和对应数量
        Map<Integer, Integer> higher = new HashMap<>();
        int points = 0;
        for (int i = 0; i < n; i++) {
            int diff = i - A[i];
            if (diff >= 0) {
                points++;
                higher.put(diff, higher.getOrDefault(diff, 0) + 1);
            }
        }

        int res = 0, max = points;
        for (int i = 0; i < n - 1; i++) {
            // 减去原有差值
            int oldDiff = i - A[i];
            if (higher.containsKey(oldDiff)) {
                points--;
                higher.put(oldDiff, higher.get(oldDiff) - 1);
            }

            // 增加新的差值, 此时水位为 i+1, 只要差值 >= i+1 则点数+1
            int newDiff = i + n - A[i];
            if (newDiff > i) {
                points++;
                higher.put(newDiff, higher.getOrDefault(newDiff, 0) + 1);
            }

            // 此时水位为 i+1, 删除小于 i+1 的点数.
            if (higher.containsKey(i)) {
                points -= higher.remove(i);
            }

            if (points > max) {
                max = points;
                res = i + 1;
            }
        }
        return res;
    }

    /**
     * LeetCode 上最快的解答, Solution 中的解法与之类似.
     * 对于A[i], 只有K 在一个区间内变动的时候, 才可以得到点数.
     * 因为 A[i] 的取值范围 [0, A.length], 所以在K 变化的过程中肯定会发生得分变化的情况.
     *
     * 开始位置的计算:
     * 在 A[i] < i 的情况, 当K 增加的时候, A[i] < i 是始终成立的, 除非A[i] 跑到队尾.
     * 在K = i + 1 的时候, A[i] 会跑到队尾.
     *
     * 结束位置的计算:
     * 满足如下情况才会得分: (i - A[i]) + (N - K) >= 0,
     * 转换后得到: K <= i - A[i] + N
     *
     * 参考文档 https://www.cnblogs.com/grandyang/p/9272921.html
     */
    @Answer
    public int bestRotation3(int[] A) {
        final int n = A.length;
        int[] change = new int[n];
        for (int i = 0; i < A.length; i++) {
            // 区间开始
            change[(i + 1) % n]++;
            // 区间结束 (超过之后不得分)
            change[(i - A[i] + n + 1) % n]--;
        }

        int res = 0, max = -1, count = 0;
        for (int i = 0; i < n; i++) {
            count += change[i];
            if (count > max) {
                res = i;
                max = count;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 1, 4, 0}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 3, 0, 2, 4}).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q798_TestData")).expect(14914);

}
