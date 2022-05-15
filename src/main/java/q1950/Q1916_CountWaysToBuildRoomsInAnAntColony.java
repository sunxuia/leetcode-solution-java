package q1950;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1916. Count Ways to Build Rooms in an Ant Colony
 * https://leetcode.com/problems/count-ways-to-build-rooms-in-an-ant-colony/
 *
 * You are an ant tasked with adding n new rooms numbered 0 to n-1 to your colony. You are given the expansion plan as a
 * 0-indexed integer array of length n, prevRoom, where prevRoom[i] indicates that you must build room prevRoom[i]
 * before building room i, and these two rooms must be connected directly. Room 0 is already built, so prevRoom[0] = -1.
 * The expansion plan is given such that once all the rooms are built, every room will be reachable from room 0.
 *
 * You can only build one room at a time, and you can travel freely between rooms you have already built only if they
 * are connected. You can choose to build any room as long as its previous room is already built.
 *
 * Return the number of different orders you can build all the rooms in. Since the answer may be large, return it modulo
 * 10^9 + 7.
 *
 * Example 1:
 * (图Q1916_PIC1.jpg)
 * Input: prevRoom = [-1,0,1]
 * Output: 1
 * Explanation: There is only one way to build the additional rooms: 0 -> 1 -> 2
 *
 * Example 2:
 * (图Q1916_PIC2.jpg)
 * Input: prevRoom = [-1,0,0,1,2]
 * Output: 6
 * Explanation:
 * The 6 ways are:
 * 0 -> 1 -> 3 -> 2 -> 4
 * 0 -> 2 -> 4 -> 1 -> 3
 * 0 -> 1 -> 2 -> 3 -> 4
 * 0 -> 1 -> 2 -> 4 -> 3
 * 0 -> 2 -> 1 -> 3 -> 4
 * 0 -> 2 -> 1 -> 4 -> 3
 *
 * Constraints:
 *
 * n == prevRoom.length
 * 2 <= n <= 10^5
 * prevRoom[0] == -1
 * 0 <= prevRoom[i] < n for all 1 <= i < n
 * Every room is reachable from room 0 once all the rooms are built.
 */
@RunWith(LeetCodeRunner.class)
public class Q1916_CountWaysToBuildRoomsInAnAntColony {

    /**
     * (会超时的解法)
     */
    public int waysToBuildRooms_overTime(int[] prevRoom) {
        final int n = prevRoom.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 1; i < n; i++) {
            nodes[prevRoom[i]].children.add(nodes[i]);
        }
        countChildren(nodes[0]);
        return (int) dfs(nodes[0]);
    }

    private static class Node {

        List<Node> children = new ArrayList<>();

        int count = 1;

    }

    private void countChildren(Node node) {
        for (Node child : node.children) {
            countChildren(child);
            node.count += child.count;
        }
    }

    private long dfs(Node node) {
        long res = 1;
        int n = node.count - 1;
        for (Node child : node.children) {
            int comb = getCombinations(n, child.count);
            res = comb * dfs(child) % MOD * res % MOD;
            n -= child.count;
        }
        return res;
    }

    private static final int MOD = 10_0000_0007;

    private static Map<Integer, Integer>[] COMBINATIONS = new Map[10_0001];

    private static int getCombinations(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        }
        if (COMBINATIONS[n] == null) {
            COMBINATIONS[n] = new HashMap<>();
        }
        return COMBINATIONS[n]
                .computeIfAbsent(k, ig -> {
                    int set0 = getCombinations(n - 1, k);
                    int set1 = getCombinations(n - 1, k - 1);
                    return (set0 + set1) % MOD;
                });
    }

    /**
     * 参考文档
     * <a href="https://leetcode.com/problems/count-ways-to-build-rooms-in-an-ant-colony/discuss/1300738/Concise-Java-solution-using-BigInteger"></a>
     */
    @Answer
    public int waysToBuildRooms(int[] prevRoom) {
        final int n = prevRoom.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 1; i < n; i++) {
            nodes[prevRoom[i]].children.add(nodes[i]);
        }
        countChildren(nodes[0]);

        long denomin = 1;
        for (int i = 0; i < n; i++) {
            denomin = (denomin * nodes[i].count) % MOD;
        }
        BigInteger den = BigInteger.valueOf(denomin);

        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = (fact * i) % MOD;
        }

        den = den.modInverse(BigInteger.valueOf(MOD));
        return (int) ((fact * den.longValue()) % MOD);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{-1, 0, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-1, 0, 0, 1, 2}).expect(6);

    @TestData
    public DataExpectation normal1() {
        return DataExpectation.create(new int[]{
                -1, 0, 0, 1, 2, 0, 1, 5, 0, 7, 3, 2, 9, 12, 1, 1, 9, 7, 14, 5, 5, 0, 9, 22, 0, 5, 9, 1, 19, 6, 13, 22,
                5, 9, 13, 11, 27, 13, 32, 20, 38, 34, 17, 23, 8, 38, 34, 40, 37, 39, 29, 8, 48, 24, 48, 19, 3, 2, 15,
                29, 16, 53, 13, 8, 46, 17, 13, 21, 9, 38, 41, 6, 54, 24, 17, 68, 8, 70, 10, 30, 61, 45, 14, 79, 78, 57,
                17, 84, 65, 70, 22, 45, 88, 21, 79, 28, 87, 48, 42, 93, 97, 64, 8, 91, 61, 71, 80, 37, 23, 43, 94, 82,
                18, 20, 75, 39, 71, 12, 2, 111, 24, 73, 59, 7, 63, 59, 54, 65, 54, 105, 23, 111, 96, 54, 28, 27, 101,
                80, 85, 111, 109, 68, 121, 88, 113, 93, 138, 64, 41, 129, 5, 135, 2, 115, 129, 138, 103, 98, 105, 78,
                39, 102, 116, 155, 145, 99, 154, 122, 90, 132, 70, 18, 98, 77, 111, 172, 90, 85, 147, 129, 99, 42, 50,
                121, 54, 121, 140, 68, 146, 110, 143, 100, 112, 152, 162, 184, 74, 10, 118, 165, 98, 12, 46, 78, 3, 9,
                157, 112, 201, 11, 199, 136, 170, 167, 201, 87, 26, 1, 129, 97, 148, 20, 8, 142, 216, 179, 27, 209, 211,
                163, 116, 38, 19, 130, 81, 148, 231, 29, 197, 20, 112, 113, 179, 90, 223, 102, 173, 245, 102, 133, 96,
                185, 121, 240, 234, 185, 169, 139, 152, 51, 239, 206, 34, 153, 74, 221, 90, 87, 252, 267, 255, 23, 67,
                136, 60, 106, 163, 56, 189, 214, 7, 145, 70, 117, 237, 91, 185, 107, 72, 56, 59, 102, 70, 232, 253, 13,
                89, 137, 258, 208, 59, 161, 226, 132, 115, 41, 57, 22, 129, 164, 51, 69, 187, 191, 217, 244, 47, 93,
                186, 270, 281, 0, 166, 117, 90, 117, 195, 138, 232, 0, 253, 205, 331, 253, 333, 66, 101, 335, 30, 311,
                116, 282, 330, 44, 326, 149, 1, 274, 320, 230, 270, 128, 223, 347, 300, 256, 66, 314, 124, 124, 263,
                294, 338, 330, 177, 116, 262, 161, 11, 163, 205, 260, 58, 97, 337, 301, 96, 298, 59, 48, 113, 238, 165,
                243, 128, 62, 300, 364, 210, 254, 58, 298, 296, 154, 294, 35, 228, 325, 11, 105, 104, 167, 184, 157, 99,
                34, 166, 244, 193, 154, 126, 181, 303, 117, 222, 299, 149, 38, 247, 282, 204, 313, 355, 81, 73, 193, 17,
                274, 145, 100, 411, 319, 166, 349, 204, 135, 424, 341, 38, 245, 337, 334, 74, 88, 54, 147, 4, 300, 145,
                397, 94, 27, 55, 439, 397, 341, 346, 416, 451, 115, 301, 405, 77, 400, 216, 218, 460, 369, 201, 219,
                356, 67, 41, 147, 236, 110, 171, 407, 223, 467, 479, 75, 136, 340, 379, 378, 436, 117, 482, 160, 407,
                83, 55, 8, 201, 430, 133, 220, 474, 99
        }).expect(732023092);
    }

}
