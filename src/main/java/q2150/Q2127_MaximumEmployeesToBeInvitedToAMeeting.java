package q2150;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * <h3>[Hard] 2127. Maximum Employees to Be Invited to a Meeting</h3>
 * <a href="https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/">
 * https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/
 * </a><br/>
 *
 * <p>A company is organizing a meeting and has a list of <code>n</code> employees, waiting to be invited. They have
 * arranged for a large <strong>circular</strong> table, capable of seating <strong>any number</strong> of
 * employees.</p>
 *
 * <p>The employees are numbered from <code>0</code> to <code>n - 1</code>. Each employee has a
 * <strong>favorite</strong> person and they will attend the meeting <strong>only if</strong> they can sit next to
 * their
 * favorite person at the table. The favorite person of an employee is <strong>not</strong> themself.</p>
 *
 * <p>Given a <strong>0-indexed</strong> integer array <code>favorite</code>, where <code>favorite[i]</code> denotes
 * the
 * favorite person of the <code>i<sup>th</sup></code> employee, return <em>the <strong>maximum number of
 * employees</strong> that can be invited to the meeting</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/14/ex1.png" style="width: 236px; height: 195px;" />
 * <pre>
 * <strong>Input:</strong> favorite = [2,2,1,2]
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong>
 * The above figure shows how the company can invite employees 0, 1, and 2, and seat them at the round table.
 * All employees cannot be invited because employee 2 cannot sit beside employees 0, 1, and 3, simultaneously.
 * Note that the company can also invite employees 1, 2, and 3, and give them their desired seats.
 * The maximum number of employees that can be invited to the meeting is 3.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> favorite = [1,2,0]
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong>
 * Each employee is the favorite person of at least one other employee, and the only way the company can invite them is if they invite every employee.
 * The seating arrangement will be the same as that in the figure given in example 1:
 * - Employee 0 will sit between employees 2 and 1.
 * - Employee 1 will sit between employees 0 and 2.
 * - Employee 2 will sit between employees 1 and 0.
 * The maximum number of employees that can be invited to the meeting is 3.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/14/ex2.png" style="width: 219px; height: 220px;" />
 * <pre>
 * <strong>Input:</strong> favorite = [3,0,1,4,1]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong>
 * The above figure shows how the company will invite employees 0, 1, 3, and 4, and seat them at the round table.
 * Employee 2 cannot be invited because the two spots next to their favorite employee 1 are taken.
 * So the company leaves them out of the meeting.
 * The maximum number of employees that can be invited to the meeting is 4.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == favorite.length</code></li>
 * 	<li><code>2 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>0 &lt;= favorite[i] &lt;=&nbsp;n - 1</code></li>
 * 	<li><code>favorite[i] != i</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2127_MaximumEmployeesToBeInvitedToAMeeting {

    /**
     * 有两种情况: 1->2->3<-4<-5 这样需要连成环的, 只有环路中的 123 可以参加会议(圆桌上人形成一个环).
     * >         ↑-----|
     * 还有一种 1->2->3<->4<-5<-6 这样有两个节点互相依赖的(双向依赖), 以3和4 为出发点,
     * >       7->8-↑
     * 可以分出两个最长生成树来参加会议, 圆桌上环的末尾有两个人互不依赖. 此时是一条无环链路, 因此可以将所有双向依赖的最长生成树连起来.
     *
     * 时间复杂度 O(N)
     */
    @Answer
    public int maximumInvitations(int[] favorite) {
        final int n = favorite.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].no = i;
        }
        // 构建依赖关系
        for (int i = 0; i < n; i++) {
            Node curr = nodes[i];
            Node favor = nodes[favorite[i]];
            curr.favorite = favor;
            favor.favored.add(curr);

            // 检测双向依赖
            if (favorite[favorite[i]] == i) {
                curr.dual = true;
            }
        }

        int res = 0;

        // 检测双向依赖树两端的最长生成树,
        // 所有的双向依赖节点的最长生成树可以连起来坐到圆桌上
        for (int i = 0; i < n; i++) {
            Node node = nodes[i];
            if (node.dual && !node.visited) {
                int thisLen = node.findFavoredChain();
                int thatLen = node.favorite.findFavoredChain();
                res += thisLen + thatLen;
            }
        }

        // 检测环路
        for (int i = 0; i < n; i++) {
            Node node = nodes[i];
            int cycle = node.detectCycle(1);
            res = Math.max(res, cycle);
        }
        return res;
    }

    private static class Node {

        // 节点的编号, 仅用于调试
        int no;

        // 节点是否是 1<->2 这样的双向依赖节点
        boolean dual;

        // 是否被访问过
        boolean visited;

        // 依赖的节点
        Node favorite;

        // 环路检测过程中访问的深度
        int depth;

        // 被依赖的节点
        List<Node> favored = new ArrayList<>();

        // 双向依赖节点用: 从当前节点出发的最长生成树(当前节点没有环).
        int findFavoredChain() {
            visited = true;
            int max = 0;
            for (Node from : favored) {
                if (!from.dual) {
                    int len = from.findFavoredChain();
                    max = Math.max(max, len);
                }
            }
            return max + 1;
        }

        // 环路检测: 返回链路中环路的长度(当前节点不一定在环路中)
        int detectCycle(int depth) {
            if (visited || favorite.visited) {
                return -1;
            }
            this.depth = depth;
            int cycle;
            if (favorite.depth > 0) {
                cycle = depth - favorite.depth + 1;
            } else {
                cycle = favorite.detectCycle(depth + 1);
            }
            visited = true;
            return cycle;
        }

        @Override
        public String toString() {
            return no + "(->" + favorite.no + ")";
        }
    }

    /**
     * leetcode 上的常规解法.
     * 和上面解题思路一样, 代码思路不同, 时间复杂度也是 O(N).
     */
    @Answer
    public int maximumInvitations2(int[] favorite) {
        final int n = favorite.length;

        // 节点被依赖的入度
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            inDegree[favorite[i]]++;
        }

        // 找出入度为0 的节点, 这些节点只能被作为双向依赖节点的链路两端
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 从入度为0 的端点开始, 移除不在环路中/不是双向依赖的节点, 并更新最长生成树的长度
        // maxLength[i] : 链路到 i (不含)的最长路径
        int[] maxLength = new int[n];
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int next = favorite[curr];
            // 更新最长长度
            maxLength[next] = Math.max(maxLength[next], maxLength[curr] + 1);
            inDegree[next]--;
            if (inDegree[next] == 0) {
                queue.offer(next);
            }
        }

        // 现在所有不在环路中的节点都是入度都是0 了
        // 情况1: 双向依赖节点, 计算两个节点最长生成树长度(+自己)的总和
        int cond1 = 0;
        // 情况2: 环路节点, 计算每个环路的长度
        int cond2 = 0;
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                // 入度为 0 的是在上面移除节点的过程中
                // 或这个循环的前面被处理过了
                continue;
            }
            // 找出环路长度
            int ring = 1;
            inDegree[i] = 0;
            for (int j = favorite[i]; j != i; j = favorite[j]) {
                ring++;
                inDegree[j] = 0;
            }

            if (ring == 2) {
                // 双向依赖节点, 长度相加
                cond1 += 2 + maxLength[i] + maxLength[favorite[i]];
            } else {
                // 环路, 找出最大长度
                cond2 = Math.max(ring, cond2);
            }
        }
        return Math.max(cond1, cond2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 1, 2}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 0}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 0, 1, 4, 1}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 0, 0, 2, 1, 4}).expect(6);

    // 这里面有4 个双向依赖节点的链路和一个环.
    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{1, 0, 3, 2, 5, 6, 7, 4, 9, 8, 11, 10, 11, 12, 10})
            .expect(11);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{23, 14, 17, 5, 19, 2, 0, 18, 15, 12, 2, 8, 21, 3, 3, 1, 6, 5, 11, 17, 3, 7, 14, 13})
            .expect(3);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(new int[]{383, 152, 56, 155, 374, 287, 284, 452, 206, 183, 67, 32, 141, 280, 43, 448, 385, 220, 23,
                    302, 408, 351, 412, 386, 425, 310, 223, 320, 207, 292, 335, 123, 99, 71, 334, 351, 382, 446, 325,
                    316, 100, 98, 214, 271, 270, 235, 424, 63, 257, 61, 265, 408, 109, 26, 346, 385, 43, 416, 61, 313,
                    195, 245, 310, 322, 98, 332, 150, 400, 155, 314, 17, 23, 269, 16, 233, 19, 282, 140, 461, 146, 374,
                    209, 323, 8, 315, 406, 370, 165, 375, 290, 399, 212, 343, 234, 87, 253, 191, 80, 71, 165, 238, 455,
                    367, 48, 293, 414, 180, 135, 256, 427, 22, 124, 24, 274, 74, 8, 127, 401, 268, 277, 137, 146, 388,
                    281, 261, 354, 364, 64, 115, 206, 310, 376, 179, 154, 91, 371, 335, 269, 133, 189, 65, 170, 464,
                    288, 123, 91, 453, 18, 97, 162, 64, 357, 154, 198, 0, 421, 406, 26, 29, 292, 72, 405, 413, 433, 62,
                    397, 455, 119, 13, 351, 196, 156, 42, 135, 215, 329, 365, 258, 304, 337, 392, 121, 102, 165, 63, 37,
                    340, 73, 436, 144, 42, 143, 339, 83, 160, 269, 241, 185, 221, 173, 61, 250, 61, 348, 243, 299, 80,
                    287, 316, 452, 24, 370, 260, 181, 289, 333, 416, 250, 51, 48, 77, 188, 129, 62, 360, 395, 28, 2,
                    230, 323, 156, 462, 400, 434, 357, 382, 378, 329, 61, 207, 435, 263, 345, 204, 456, 175, 51, 447,
                    29, 374, 410, 98, 109, 305, 281, 433, 112, 158, 423, 42, 100, 250, 445, 147, 404, 200, 206, 435,
                    217, 267, 152, 30, 84, 441, 71, 431, 172, 368, 46, 111, 98, 384, 399, 288, 405, 429, 63, 175, 147,
                    402, 388, 298, 389, 402, 167, 416, 194, 130, 101, 304, 17, 109, 408, 177, 415, 168, 126, 152, 28,
                    245, 74, 442, 92, 262, 444, 210, 134, 244, 196, 266, 438, 195, 280, 59, 75, 302, 267, 85, 92, 268,
                    200, 272, 281, 214, 8, 56, 433, 459, 84, 436, 308, 85, 258, 274, 459, 194, 192, 226, 248, 10, 161,
                    300, 126, 274, 104, 376, 440, 132, 56, 252, 299, 453, 316, 444, 1, 357, 77, 369, 382, 225, 16, 59,
                    79, 27, 83, 99, 266, 456, 367, 10, 255, 334, 116, 182, 13, 336, 82, 69, 257, 452, 181, 385, 390,
                    157, 308, 441, 398, 36, 130, 185, 97, 179, 198, 438, 168, 306, 389, 35, 172, 117, 188, 130, 63, 84,
                    313, 463, 460, 241, 58, 235, 439, 376, 24, 208, 168, 34, 207, 330, 83, 53, 212, 464, 163, 159, 463,
                    308, 454, 328, 272, 381, 231, 161, 156, 54, 380, 429, 414, 33, 323, 251, 304, 212, 116, 287, 238,
                    199, 194, 303, 147, 230, 301, 200, 219, 84, 442})
            .expect(17);

    @TestData
    public DataExpectation overtime = DataExpectation.create(TestDataFileHelper.read(int[].class)).expect(82);

}
