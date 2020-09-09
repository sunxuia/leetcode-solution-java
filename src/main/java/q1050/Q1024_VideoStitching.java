package q1050;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1024. Video Stitching
 * https://leetcode.com/problems/video-stitching/
 *
 * You are given a series of video clips from a sporting event that lasted T seconds.  These video clips can be
 * overlapping with each other and have varied lengths.
 *
 * Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1].  We can cut
 * these clips into segments freely: for example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
 *
 * Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire sporting
 * event ([0, T]).  If the task is impossible, return -1.
 *
 * Example 1:
 *
 * Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
 * Output: 3
 * Explanation:
 * We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
 * Then, we can reconstruct the sporting event as follows:
 * We cut [1,9] into segments [1,2] + [2,8] + [8,9].
 * Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
 *
 * Example 2:
 *
 * Input: clips = [[0,1],[1,2]], T = 5
 * Output: -1
 * Explanation:
 * We can't cover [0,5] with only [0,1] and [1,2].
 *
 * Example 3:
 *
 * Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T =
 * 9
 * Output: 3
 * Explanation:
 * We can take clips [0,4], [4,7], and [6,9].
 *
 * Example 4:
 *
 * Input: clips = [[0,4],[2,8]], T = 5
 * Output: 2
 * Explanation:
 * Notice you can have extra video after the event ends.
 *
 * Constraints:
 *
 * 1 <= clips.length <= 100
 * 0 <= clips[i][0] <= clips[i][1] <= 100
 * 0 <= T <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1024_VideoStitching {

    @Answer
    public int videoStitching(int[][] clips, int T) {
        // dp[i] 表示到 以i 结尾的单位区间需要的片段数量, dp[0] 是哨兵
        int[] dp = new int[T + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        Arrays.sort(clips, Comparator.comparingInt(a -> a[0]));
        for (int[] clip : clips) {
            int base = clip[0], to = Math.min(clip[1], T);
            if (base > T || dp[base] == Integer.MAX_VALUE) {
                break;
            }
            for (int i = base + 1; i <= to; i++) {
                dp[i] = Math.min(dp[i], dp[base] + 1);
            }
        }
        return dp[T] == Integer.MAX_VALUE ? -1 : dp[T];
    }

    /**
     * LeetCode 上更快的解法, 与上面思路类似, 解法更快.
     */
    @Answer
    public int videoStitching2(int[][] clips, int T) {
        // dp[i] 表示可以在1 个clip 内从时间i 跳跃到的最远记录.
        // 这里只需要为clip[x][0] 对应的下标赋值即可.
        int[] dp = new int[T + 1];
        for (int[] clip : clips) {
            int from = clip[0], to = clip[1];
            if (from <= T) {
                dp[from] = Math.max(dp[from], to);
            }
        }

        // end 表示本跳可以到达的最远位置.
        // farthest 表示可以到达的最远位置.
        int end = 0, farthest = 0, res = 0;
        for (int i = 0; i <= T; i++) {
            if (i > farthest) {
                // 跳不到这里
                return -1;
            }
            farthest = Math.max(farthest, dp[i]);

            // 跳跃次数+1, end == T 的时候不需要再跳了
            if (i == end && i < T) {
                end = farthest;
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{0, 2}, {4, 6}, {8, 10}, {1, 9}, {1, 5}, {5, 9}}, 10)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 1}, {1, 2}}, 5)
            .expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(
            new int[][]{{0, 1}, {6, 8}, {0, 2}, {5, 6}, {0, 4}, {0, 3}, {6, 7}, {1, 3}, {4, 7}, {1, 4}, {2, 5}, {2, 6},
                    {3, 4}, {4, 5}, {5, 7}, {6, 9}}, 9)
            .expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.
            createWith(new int[][]{{0, 4}, {2, 8}}, 5)
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.
            createWith(new int[][]{{0, 2}, {4, 8}}, 5)
            .expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.
            createWith(new int[][]{{0, 5}, {6, 8}}, 7)
            .expect(-1);

    @TestData
    public DataExpectation normal3 = DataExpectation.
            createWith(new int[][]{{5, 7}, {1, 8}, {0, 0}, {2, 3}, {4, 5}, {0, 6}, {5, 10}, {7, 10}}, 5)
            .expect(1);

}
