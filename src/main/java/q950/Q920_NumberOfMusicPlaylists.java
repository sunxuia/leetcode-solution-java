package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 920. Number of Music Playlists
 * https://leetcode.com/problems/number-of-music-playlists/
 *
 * Your music player contains N different songs and she wants to listen to L (not necessarily different) songs during
 * your trip.  You create a playlist so that:
 *
 * Every song is played at least once
 * A song can only be played again only if K other songs have been played
 *
 * Return the number of possible playlists.  As the answer can be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: N = 3, L = 3, K = 1
 * Output: 6
 * Explanation: There are 6 possible playlists. [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1].
 *
 * Example 2:
 *
 * Input: N = 2, L = 3, K = 0
 * Output: 6
 * Explanation: There are 6 possible playlists. [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], [1, 2, 2]
 *
 * Example 3:
 *
 * Input: N = 2, L = 3, K = 1
 * Output: 2
 * Explanation: There are 2 possible playlists. [1, 2, 1], [2, 1, 2]
 *
 * Note:
 *
 * 0 <= K < N <= L <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q920_NumberOfMusicPlaylists {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/11741490.html
     * https://leetcode.com/problems/number-of-music-playlists/discuss/180338/DP-solution-that-is-Easy-to-understand
     */
    @Answer
    public int numMusicPlaylists(int N, int L, int K) {
        final int mod = 10_0000_0007;
        // dp[i][j] 表示总共放了i 首歌, 其中j 首是不同的
        long[][] dp = new long[L + 1][N + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= L; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = (dp[i - 1][j - 1] * (N - (j - 1))) % mod;
                if (j > K) {
                    dp[i][j] = (dp[i][j] + (dp[i - 1][j] * (j - K)) % mod) % mod;
                }
            }
        }
        return (int) dp[L][N];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 3, 1).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 3, 0).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, 3, 1).expect(2);

}
