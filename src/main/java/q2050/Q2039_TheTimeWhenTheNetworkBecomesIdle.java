package q2050;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2039. The Time When the Network Becomes Idle
 * https://leetcode.com/problems/the-time-when-the-network-becomes-idle/
 *
 * There is a network of n servers, labeled from 0 to n - 1. You are given a 2D integer array edges, where edges[i] =
 * [ui, vi] indicates there is a message channel between servers ui and vi, and they can pass any number of messages to
 * each other directly in one second. You are also given a 0-indexed integer array patience of length n.
 *
 * All servers are connected, i.e., a message can be passed from one server to any other server(s) directly or
 * indirectly through the message channels.
 *
 * The server labeled 0 is the master server. The rest are data servers. Each data server needs to send its message to
 * the master server for processing and wait for a reply. Messages move between servers optimally, so every message
 * takes the least amount of time to arrive at the master server. The master server will process all newly arrived
 * messages instantly and send a reply to the originating server via the reversed path the message had gone through.
 *
 * At the beginning of second 0, each data server sends its message to be processed. Starting from second 1, at the
 * beginning of every second, each data server will check if it has received a reply to the message it sent (including
 * any newly arrived replies) from the master server:
 *
 * If it has not, it will resend the message periodically. The data server i will resend the message every patience[i]
 * second(s), i.e., the data server i will resend the message if patience[i] second(s) have elapsed since the last time
 * the message was sent from this server.
 * Otherwise, no more resending will occur from this server.
 *
 * The network becomes idle when there are no messages passing between servers or arriving at servers.
 *
 * Return the earliest second starting from which the network becomes idle.
 *
 * Example 1:
 * (图Q2039_PIC1.png)
 * Input: edges = [[0,1],[1,2]], patience = [0,2,1]
 * Output: 8
 * Explanation:
 * At (the beginning of) second 0,
 * - Data server 1 sends its message (denoted 1A) to the master server.
 * - Data server 2 sends its message (denoted 2A) to the master server.
 *
 * At second 1,
 * - Message 1A arrives at the master server. Master server processes message 1A instantly and sends a reply 1A back.
 * - Server 1 has not received any reply. 1 second (1 < patience[1] = 2) elapsed since this server has sent the message,
 * therefore it does not resend the message.
 * - Server 2 has not received any reply. 1 second (1 == patience[2] = 1) elapsed since this server has sent the
 * message, therefore it resends the message (denoted 2B).
 *
 * At second 2,
 * - The reply 1A arrives at server 1. No more resending will occur from server 1.
 * - Message 2A arrives at the master server. Master server processes message 2A instantly and sends a reply 2A back.
 * - Server 2 resends the message (denoted 2C).
 * ...
 * At second 4,
 * - The reply 2A arrives at server 2. No more resending will occur from server 2.
 * ...
 * At second 7, reply 2D arrives at server 2.
 *
 * Starting from the beginning of the second 8, there are no messages passing between servers or arriving at servers.
 * This is the time when the network becomes idle.
 *
 * Example 2:
 * (图Q2039_PIC2.png)
 * Input: edges = [[0,1],[0,2],[1,2]], patience = [0,10,10]
 * Output: 3
 * Explanation: Data servers 1 and 2 receive a reply back at the beginning of second 2.
 * From the beginning of the second 3, the network becomes idle.
 *
 * Constraints:
 *
 * n == patience.length
 * 2 <= n <= 10^5
 * patience[0] == 0
 * 1 <= patience[i] <= 10^5 for 1 <= i < n
 * 1 <= edges.length <= min(10^5, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= ui, vi < n
 * ui != vi
 * There are no duplicate edges.
 * Each server can directly or indirectly reach another server.
 */
@RunWith(LeetCodeRunner.class)
public class Q2039_TheTimeWhenTheNetworkBecomesIdle {

    @Answer
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        final int n = patience.length;
        // 构造连通图
        List<Integer>[] neighbors = new List[n];
        for (int i = 0; i < n; i++) {
            neighbors[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            neighbors[edge[0]].add(edge[1]);
            neighbors[edge[1]].add(edge[0]);
        }

        // bfs 找出0 到所有点的最短距离(来回)
        int[] dist = new int[n];
        dist[0] = -1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        for (int round = 2; !queue.isEmpty(); round += 2) {
            for (int i = queue.size(); i > 0; i--) {
                int server = queue.poll();
                for (Integer neighbor : neighbors[server]) {
                    if (dist[neighbor] == 0) {
                        dist[neighbor] = round;
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // 计算结果, last 表示最后一个信息到达的时间, 从下一秒开始网络中就没有消息传递了.
        int last = 0;
        for (int i = 1; i < n; i++) {
            // 当返回信息到达的那一秒, 即使该秒要发送消息, 也不会发送.
            int time = (dist[i] - 1) / patience[i] * patience[i] + dist[i];
            last = Math.max(last, time);
        }
        return last + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{0, 1}, {1, 2}}, new int[]{0, 2, 1})
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 1}, {0, 2}, {1, 2}}, new int[]{0, 10, 10})
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{5, 7}, {15, 18}, {12, 6}, {5, 1}, {11, 17}, {3, 9}, {6, 11}, {14, 7}, {19, 13},
                    {13, 3}, {4, 12}, {9, 15}, {2, 10}, {18, 4}, {5, 14}, {17, 5}, {16, 2}, {7, 1}, {0, 16}, {10, 19},
                    {1, 8}}, new int[]{0, 2, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1})
            .expect(67);

}
