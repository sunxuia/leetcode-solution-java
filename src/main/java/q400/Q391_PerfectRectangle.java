package q400;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/perfect-rectangle/
 *
 * Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular
 * region.
 *
 * Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is
 * represented as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).
 *
 * Example 1:
 *
 * >  rectangles = [
 * >    [1,1,3,3],
 * >    [3,1,4,2],
 * >    [3,2,4,4],
 * >    [1,3,2,4],
 * >    [2,3,3,4]
 * >  ]
 *
 * (图 Q390_PIC1.png)
 *
 * Return true. All 5 rectangles together form an exact cover of a rectangular region.
 *
 *
 *
 *
 * Example 2:
 *
 * >  rectangles = [
 * >    [1,1,2,3],
 * >    [1,3,2,4],
 * >    [3,1,4,2],
 * >    [3,2,4,4]
 * >  ]
 *
 * (图 Q390_PIC2.png)
 *
 * Return false. Because there is a gap between the two rectangular regions.
 *
 *
 *
 *
 * Example 3:
 *
 * >  rectangles = [
 * >    [1,1,3,3],
 * >    [3,1,4,2],
 * >    [1,3,2,4],
 * >    [3,2,4,4]
 * >  ]
 *
 * (图 Q390_PIC3.png)
 *
 * Return false. Because there is a gap in the top center.
 *
 *
 *
 *
 * Example 4:
 *
 * >  rectangles = [
 * >    [1,1,3,3],
 * >    [3,1,4,2],
 * >    [1,3,2,4],
 * >    [2,2,4,4]
 * >  ]
 *
 * (图 Q390_PIC4.png)
 *
 * Return false. Because two of the rectangles overlap with each other.
 */
@RunWith(LeetCodeRunner.class)
public class Q391_PerfectRectangle {

    /**
     * https://www.cnblogs.com/grandyang/p/5825619.html
     *
     * 根据矩形的点去进行判断是否能够组合成一个大矩形, 最后通过面积判断中间是否是空心的.
     * 参考图 Q391_PIC5.jpg, 最终组成大矩形的小矩形的顶点组合共有3 种:
     * 1. 蓝色: 大矩形的顶点, 这种顶点有且仅有4 个.
     * 2. 绿色: 2 个顶点的组合.
     * 3. 红色: 4 个顶点的组合.
     *
     * 根据绿色和红色都需要双数个顶点组合的特性,
     * 直接用一个set, 对于遍历到的任意一个顶点, 如果set中已经存在了, 则删去这个点, 如果没有就加上,
     * 这样最后会把绿点和红点都滤去, 剩下的都是蓝点, 我们只要看蓝点的个数是否为四个,
     * 最后看一下每个矩形面积累加和是否要等于最后的大矩形的面积即可.
     *
     * (参考链接中的set 使用字符串比较慢, 所以这里使用一个自定义的Node 类来代替, 根据LeetCode 中测试用例的特性, 最快的set 是BitSet)
     */
    @Answer
    public boolean isRectangleCover(int[][] rectangles) {
        Set<Node> set = new HashSet<>();
        int left = Integer.MAX_VALUE,
                bottom = Integer.MAX_VALUE,
                right = Integer.MIN_VALUE,
                top = Integer.MIN_VALUE,
                area = 0;
        for (int[] rect : rectangles) {
            // 求大矩形的4 个蓝色顶点
            left = Math.min(left, rect[0]);
            bottom = Math.min(bottom, rect[1]);
            right = Math.max(right, rect[2]);
            top = Math.max(top, rect[3]);
            // 矩形面积
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            // 左下
            reverseExistance(set, rect[0], rect[1]);
            // 左上
            reverseExistance(set, rect[0], rect[3]);
            // 右上
            reverseExistance(set, rect[2], rect[3]);
            // 右下
            reverseExistance(set, rect[2], rect[1]);
        }
        return set.size() == 4
                && exist(set, left, bottom)
                && exist(set, left, top)
                && exist(set, right, top)
                && exist(set, right, bottom)
                && area == (right - left) * (top - bottom);
    }

    private static class Node {

        private int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node otherNode = (Node) obj;
                return x == otherNode.x && y == otherNode.y;
            }
            return false;
        }
    }

    private void reverseExistance(Set<Node> set, int x, int y) {
        Node pos = new Node(x, y);
        if (set.contains(pos)) {
            set.remove(pos);
        } else {
            set.add(pos);
        }
    }

    private boolean exist(Set<Node> set, int x, int y) {
        return set.contains(new Node(x, y));
    }

    // LeetCode 上最快的做法, 解法与上面一样, 唯一不同的是使用long 中的位来代替Set,
    // 这种做法是目前最快的解法, 但是和 LeetCode 中的测试用例有关, 具有特殊性.
    @Answer
    public boolean isRectangleCoverWithBitSet(int[][] rectangles) {
        // 根据测试用例中高都是个位数的特点, 个位数表示y 轴坐标, 十位数表示x 轴坐标
        final int offset = 10;

        int left = Integer.MAX_VALUE,
                bottom = Integer.MAX_VALUE,
                right = Integer.MIN_VALUE,
                top = Integer.MIN_VALUE;
        for (int[] rect : rectangles) {
            // 求大矩形的4 个蓝色顶点
            left = Math.min(left, rect[0]);
            bottom = Math.min(bottom, rect[1]);
            right = Math.max(right, rect[2]);
            top = Math.max(top, rect[3]);
        }
        // 计算相对位置, 避免负数和大数的测试用例. 这样left 和bottom 的相对值就是0 了.
        right -= left;
        top -= bottom;
        // 用一个数字(或者BitSet) 来代替Set.
        long expect = 1L
                | 1L << top
                | 1L << offset * right + top
                | 1L << offset * right;

        // 用一个数字(或者BitSet) 来代替Set.
        long set = 0;
        int area = 0;
        for (int[] rect : rectangles) {
            // 计算相对位置
            rect[0] -= left;
            rect[1] -= bottom;
            rect[2] -= left;
            rect[3] -= bottom;

            // 矩形面积
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            // 左下
            set ^= 1L << offset * rect[0] + rect[1];
            // 左上
            set ^= 1L << offset * rect[0] + rect[3];
            // 右上
            set ^= 1L << offset * rect[2] + rect[3];
            // 右下
            set ^= 1L << offset * rect[2] + rect[1];
        }

        return set == expect && area == right * top;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 3, 3},
            {3, 1, 4, 2},
            {3, 2, 4, 4},
            {1, 3, 2, 4},
            {2, 3, 3, 4}
    }).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 1, 2, 3},
            {1, 3, 2, 4},
            {3, 1, 4, 2},
            {3, 2, 4, 4}
    }).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 3, 3},
            {3, 1, 4, 2},
            {1, 3, 2, 4},
            {3, 2, 4, 4}
    }).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 1, 3, 3},
            {3, 1, 4, 2},
            {1, 3, 2, 4},
            {2, 2, 4, 4}
    }).expect(false);

}
