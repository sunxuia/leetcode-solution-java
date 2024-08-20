package q2100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * <h3>[Hard] 2097. Valid Arrangement of Pairs</h3>
 * <a href="https://leetcode.com/problems/valid-arrangement-of-pairs/">
 * https://leetcode.com/problems/valid-arrangement-of-pairs/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> 2D integer array <code>pairs</code> where <code>pairs[i] =
 * [start<sub>i</sub>, end<sub>i</sub>]</code>. An arrangement of <code>pairs</code> is <strong>valid</strong> if for
 * every index <code>i</code> where <code>1 &lt;= i &lt; pairs.length</code>, we have <code>end<sub>i-1</sub> ==
 * start<sub>i</sub></code>.</p>
 *
 * <p>Return <em><strong>any</strong> valid arrangement of </em><code>pairs</code>.</p>
 *
 * <p><strong>Note:</strong> The inputs will be generated such that there exists a valid arrangement of
 * <code>pairs</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> pairs = [[5,1],[4,5],[11,9],[9,4]]
 * <strong>Output:</strong> [[11,9],[9,4],[4,5],[5,1]]
 * <strong>Explanation:
 * </strong>This is a valid arrangement since end<sub>i-1</sub> always equals start<sub>i</sub>.
 * end<sub>0</sub> = 9 == 9 = start<sub>1</sub>
 * end<sub>1</sub> = 4 == 4 = start<sub>2</sub>
 * end<sub>2</sub> = 5 == 5 = start<sub>3</sub>
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> pairs = [[1,3],[3,2],[2,1]]
 * <strong>Output:</strong> [[1,3],[3,2],[2,1]]
 * <strong>Explanation:</strong>
 * This is a valid arrangement since end<sub>i-1</sub> always equals start<sub>i</sub>.
 * end<sub>0</sub> = 3 == 3 = start<sub>1</sub>
 * end<sub>1</sub> = 2 == 2 = start<sub>2</sub>
 * The arrangements [[2,1],[1,3],[3,2]] and [[3,2],[2,1],[1,3]] are also valid.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> pairs = [[1,2],[1,3],[2,1]]
 * <strong>Output:</strong> [[1,2],[2,1],[1,3]]
 * <strong>Explanation:</strong>
 * This is a valid arrangement since end<sub>i-1</sub> always equals start<sub>i</sub>.
 * end<sub>0</sub> = 2 == 2 = start<sub>1</sub>
 * end<sub>1</sub> = 1 == 1 = start<sub>2</sub>
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= pairs.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>pairs[i].length == 2</code></li>
 * 	<li><code>0 &lt;= start<sub>i</sub>, end<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
 * 	<li><code>start<sub>i</sub> != end<sub>i</sub></code></li>
 * 	<li>No two pairs are exactly the same.</li>
 * 	<li>There <strong>exists</strong> a valid arrangement of <code>pairs</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2097_ValidArrangementOfPairs {

    /**
     * 欧拉路径(一笔画问题), DFS 带非环路检测.
     */
    @Answer
    public int[][] validArrangement(int[][] pairs) {
        Map<Integer, Node> nodes = new HashMap<>();
        for (int[] pair : pairs) {
            Node from = nodes.computeIfAbsent(pair[0], Node::new);
            Node to = nodes.computeIfAbsent(pair[1], Node::new);
            from.out.add(to);
            from.outDegree++;
            to.inDegree++;
        }

        Node start = null;
        for (Node node : nodes.values()) {
            start = node;
            // 出发节点的出度大于入度
            if (node.outDegree > node.inDegree) {
                break;
            }
        }

        startPath = new Path(start.no);
        tailPath = startPath;
        start.findPath();

        int[][] res = new int[pairs.length][];
        for (int i = 0; i < pairs.length; i++) {
            res[i] = new int[]{startPath.no, startPath.next.no};
            startPath = startPath.next;
        }
        return res;
    }

    private Path startPath, tailPath;

    // 图节点
    private class Node {

        // 编号
        final int no;

        // 入度/出度
        int inDegree, outDegree;

        // 出发的边
        List<Node> out = new ArrayList<>();

        // 当前已经遍历到的边
        int scanIndex;

        Node(int no) {
            this.no = no;
        }

        void findPath() {
            if (scanIndex == outDegree) {
                return;
            }

            Node next = out.get(scanIndex++);
            Path nodePrev = tailPath;
            Path nodeNext = new Path(next.no);
            tailPath.next = nodeNext;
            tailPath = nodeNext;

            next.findPath();

            if (scanIndex != outDegree) {
                // 还有其他路径要遍历, 说明刚才走的是到终点的路,
                // 要将这个路径挪到最后去走.
                // 如果是环路再到这个节点, 会在dfs 中继续调用 findPath() 因此会将scanIndex 消耗光.
                Path nodeTail = tailPath;
                tailPath = nodePrev;
                findPath();
                tailPath.next = nodeNext;
                tailPath = nodeTail;
            }
        }
    }

    // 路径节点
    private static class Path {

        // 当前节点的编号
        final int no;

        // 下一个路径
        Path next;

        private Path(int no) {
            this.no = no;
        }
    }

    @TestData
    public DataExpectation example1 = createTestCase(new int[][]{{5, 1}, {4, 5}, {11, 9}, {9, 4}});

    @TestData
    public DataExpectation example2 = createTestCase(new int[][]{{1, 3}, {3, 2}, {2, 1}});

    @TestData
    public DataExpectation example3 = createTestCase(new int[][]{{1, 2}, {1, 3}, {2, 1}});

    @TestData
    public DataExpectation normal1 = createTestCase(
            new int[][]{{8, 5}, {8, 7}, {0, 8}, {0, 5}, {7, 0}, {5, 0}, {0, 7}, {8, 0}, {7, 8}});

    @TestData
    public DataExpectation normal2 = createTestCase(new int[][]{
            {999, 990}, {356, 511}, {192, 879}, {246, 945}, {322, 602}, {776, 246}, {248, 126}, {503, 284}, {126, 164},
            {494, 731}, {862, 382}, {731, 578}, {511, 277}, {122, 731}, {578, 99}, {731, 277}, {847, 538}, {246, 494},
            {284, 138}, {382, 899}, {811, 439}, {164, 99}, {485, 307}, {618, 320}, {494, 511}, {413, 248}, {945, 356},
            {990, 614}, {138, 18}, {164, 862}, {277, 164}, {826, 737}, {277, 322}, {618, 122}, {291, 639}, {288, 11},
            {624, 485}, {740, 452}, {614, 740}, {307, 903}, {457, 412}, {538, 961}, {439, 122}, {961, 999}, {639, 822},
            {903, 503}, {961, 776}, {138, 538}, {122, 826}, {99, 138}, {949, 175}, {452, 847}, {320, 624}, {879, 457},
            {511, 961}, {835, 692}, {18, 949}, {737, 413}, {822, 999}, {11, 726}, {692, 618}, {899, 835}, {726, 192},
            {999, 452}, {602, 811}, {452, 618}, {175, 246}, {99, 291}, {412, 494}});

    @TestData
    public DataExpectation normal3 = createTestCase(new int[][]{
            {874, 518}, {649, 247}, {621, 559}, {774, 166}, {241, 168}, {835, 421}, {168, 835}, {835, 399}, {741, 436},
            {958, 526}, {166, 578}, {734, 812}, {436, 297}, {813, 774}, {166, 559}, {518, 548}, {882, 719}, {559, 741},
            {819, 621}, {720, 168}, {964, 187}, {518, 781}, {166, 781}, {781, 436}, {719, 958}, {342, 241}, {659, 392},
            {27, 513}, {812, 468}, {513, 910}, {187, 848}, {510, 741}, {835, 392}, {813, 559}, {392, 848}, {964, 813},
            {241, 958}, {958, 436}, {854, 241}, {813, 719}, {781, 421}, {421, 649}, {720, 910}, {510, 297}, {725, 835},
            {848, 271}, {483, 578}, {848, 336}, {854, 592}, {559, 720}, {436, 399}, {297, 958}, {592, 483}, {526, 734},
            {854, 813}, {40, 720}, {719, 510}, {548, 964}, {910, 882}, {342, 854}, {578, 518}, {399, 514}, {514, 813},
            {22, 854}, {399, 342}, {336, 297}, {392, 271}, {813, 835}, {27, 166}, {436, 725}, {271, 854}, {468, 659},
            {421, 166}, {168, 548}, {297, 526}, {271, 964}, {741, 725}, {548, 27}, {910, 510}, {559, 27}, {73, 40},
            {526, 510}, {247, 819}, {725, 874}, {578, 342}, {297, 22}, {510, 813}});

    @TestData
    public DataExpectation overtime() {
        return createTestCase(TestDataFileHelper.read(int[][].class).get());
    }

    private static DataExpectation createTestCase(int[][] pairs) {
        return DataExpectation.builder()
                .addArgument(pairs.clone())
                .assertMethod((int[][] actual) -> {
                    final int n = actual.length;
                    if (pairs.length != n) {
                        throw new AssertionError("Length not match.");
                    }
                    for (int[] pair : actual) {
                        if (pair.length != 2) {
                            throw new AssertionError("Not valid pair.");
                        }
                        boolean match = false;
                        for (int i = 0; i < n; i++) {
                            if (pairs[i] != null
                                    && pairs[i][0] == pair[0]
                                    && pairs[i][1] == pair[1]) {
                                pairs[i] = null;
                                match = true;
                                break;
                            }
                        }
                        if (!match) {
                            throw new AssertionError(
                                    "Pair [" + pair[0] + "," + pair[1] + "] not exist in argument pairs.");
                        }
                    }
                    for (int i = 1; i < n; i++) {
                        if (actual[i - 1][1] != actual[i][0]) {
                            throw new AssertionError("Not valid arrangement.");
                        }
                    }
                }).build();
    }

}
