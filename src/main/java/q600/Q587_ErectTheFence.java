package q600;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/erect-the-fence/
 *
 * There are some trees, where each tree is represented by (x,y) coordinate in a two-dimensional garden. Your job is
 * to fence the entire garden using the minimum length of rope as it is expensive. The garden is well fenced only if
 * all the trees are enclosed. Your task is to help find the coordinates of trees which are exactly located on the
 * fence perimeter.
 *
 * Example 1:
 *
 * Input: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
 * Output: [[1,1],[2,0],[4,2],[3,3],[2,4]]
 * Explanation:
 *
 * (图 Q587_PIC1.png)
 *
 * Example 2:
 *
 * Input: [[1,2],[2,2],[4,2]]
 * Output: [[1,2],[2,2],[4,2]]
 * Explanation:
 *
 * (图 Q587_PIC2.png)
 *
 * Even you only have trees in a line, you need to use rope to enclose them.
 *
 *
 * Note:
 *
 * 1. All trees should be enclosed together. You cannot cut the rope to enclose trees that will separate them in
 * more than one group.
 * 2. All input integers will range from 0 to 100.
 * 3. The garden has at least one tree.
 * 4. All coordinates are distinct.
 * 5. Input points have NO order. No order required for output.
 * 6. input types have been changed on April 15, 2019. Please reset to default code definition to get new method
 * signature.
 */
@RunWith(LeetCodeRunner.class)
public class Q587_ErectTheFence {

    /**
     * 这题没有思路, 但其实就是凸包问题, Solution 中给出的几种解答如下, 其中都有图解和动画
     * 另参考资料 (这里还给出了一些其他方法)
     * https://blog.csdn.net/baningtao1470/article/details/101077108
     * https://blog.csdn.net/weixin_44819348/article/details/104673325
     * http://www.csie.ntnu.edu.tw/~u91029/ConvexHull.html
     */

    /**
     * 1. Jarvis Algorithm, 又称Gift Wrapping Algorithm (礼品包装算法)
     *
     * 从最左边的点 p0 开始(只要确保这个点在边界上即可), 然后逆时针顺序找到下一个点 q, 以点p(i-1) 和 p(i) 这条线为基准,
     * 构建极坐标, 并找出逆时针方向角度最大点q, 如果有多个相同最大角度的则取最近的点, 这样q 就是 p(i+1) 了.
     * 继续这个过程直到最后找到的点q 就是p0 位置.
     */
    @Answer
    public int[][] outerTrees(int[][] points) {
        final int n = points.length;
        if (n < 4) {
            return points;
        }
        List<int[]> res = new ArrayList<>();

        // 找出最左边的点
        int p0 = 0;
        for (int i = 1; i < n; i++) {
            if (points[p0][0] > points[i][0]) {
                p0 = i;
            }
        }

        // 从左边的点开始, 遍历剩下的点, 找出逆时针角度最小的点
        int p = p0;
        do {
            int q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                if (orientation(points[p], points[i], points[q]) < 0) {
                    q = i;
                }
            }
            // 找出和剩下的点中和 p->q 在一条直线上的点
            for (int i = 0; i < points.length; i++) {
                if (i != p && i != q
                        && orientation(points[p], points[i], points[q]) == 0
                        && inBetween(points[p], points[i], points[q])) {
                    res.add(points[i]);
                }
            }
            res.add(points[q]);
            p = q;
        }
        while (p != p0);
        return res.toArray(new int[res.size()][]);
    }

    // 比较p->r 和 p->q 链条设设想之间的角度
    // 如果结果 >0 则表示逆时针角度q 大于r, =0 则相等, <0 则 q < r.
    // 下面的算式可以转换为: (q1-p1)/(q0-p0) - (r1-q1)/(r0-q0), 即 tan(角 q1-p0-x轴) - tan(角 r1-po-x轴)
    private int orientation(int[] p, int[] q, int[] r) {
        return (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1]);
    }

    // 判断点 i 是否在 p 和q 之间 (p和q 组成的矩形空间内)
    private boolean inBetween(int[] p, int[] i, int[] q) {
        boolean a = i[0] >= p[0] && i[0] <= q[0] || i[0] <= p[0] && i[0] >= q[0];
        boolean b = i[1] >= p[1] && i[1] <= q[1] || i[1] <= p[1] && i[1] >= q[1];
        return a && b;
    }

    /**
     * 2. Graham Scan (葛立恒扫描法)
     *
     * 和 Jarvis 步进法类似, 也是先找到凸包上的一个点，然后从那个点开始按逆时针方向逐个找凸包上的点, 但它不是利用夹角,
     * 而是通过一个肯定在边界上的点p0 构建坐标(一般为了便于计算选择最下面的点), 坐标以这个点为中心, 方向和原来的左边轴一样,
     * 计算其他点的极坐标, 然后按照角度小的在前, 角度相同则长度小的在前来排序, 分别命名为 p1, p2, p3 ... pn,
     * 易知 p1 肯定也是在边界上的, 之后使用一个栈, 将p0, p1 入栈, 将其余点放入队列中, 执行如下步骤:
     * 1. 用点pi 表示栈顶的点 (第一次的情况就是 p1) 并出栈, 用点 pj 表示此时栈顶的点(第一次的情况就是 p0).
     * 2. 连接点 pj-pi, 构造射线 L.
     * 3. 从队列中取出点 q, 判断点是在 L 左边、右边、还是在线上.
     * 3.1. 如果q 在 L 右边, 说明pi 这个点不在边界上, 则把pi 出栈.
     * 3.2. 如果q 在线 L 上, 如果pi 在边界上, 则q 也肯定在边界上, 将 pi 和 q 入栈.
     * (根据题目的不同, 如果边界线段只需要首尾 2 个点, 中间的点不要, 则可以只入栈 q).
     * 3.3. 如果q 在线L 左边, 则q 可能是下一个边界点, 将 pi 和 q 入栈.
     * 4. 继续执行第1 步, 直到队列中没有元素位置. 此时栈中的点就是结果了.
     * (根据题目的不同, 如果边界线段需要其上的所有点, 则如果末尾的几个点如果角度相同, 则需要将这个角度相同的点按照长度逆序排列, 否则当遍历
     * 到这几个点的时候, 距离较长的点就会在L 的右边, 从而执行 3.1. 的步骤导致较短距离的点缺失)
     */
    @Answer
    public int[][] outerTrees2(int[][] points) {
        final int n = points.length;
        if (n < 4) {
            return points;
        }

        // 找出最下面的点 p0
        int[] bottom = points[0];
        for (int[] p : points) {
            if (p[1] < bottom[1]) {
                bottom = p;
            }
        }
        final int[] p0 = bottom;

        // 按照极坐标的角度和距离进行排序
        Arrays.sort(points, (p, q) -> {
            int diff = orientation(p0, p, q) - orientation(p0, q, p);
            if (diff == 0) {
                return distance(p0, p) - distance(p0, q);
            } else {
                return diff > 0 ? 1 : -1;
            }
        });

        // 末尾逆序
        {
            int i = n - 1;
            while (i >= 0 && orientation(p0, points[n - 1], points[i]) == 0) {
                i--;
            }
            i++;
            for (int j = n - 1; i < j; j--, i++) {
                int[] temp = points[j];
                points[j] = points[i];
                points[i] = temp;
            }
        }

        // 栈操作
        Stack<int[]> stack = new Stack<>();
        stack.push(points[0]);
        stack.push(points[1]);
        // 这里用遍历数组的方式来取代队列, 效果一样
        for (int i = 2; i < n; i++) {
            int[] pi = stack.pop();
            // 如果点在 L 的右边, 则点pi 就不在边界上.
            while (orientation(stack.peek(), pi, points[i]) > 0) {
                pi = stack.pop();
            }
            stack.push(pi);
            stack.push(points[i]);
        }
        return stack.toArray(new int[stack.size()][]);
    }

    // 计算点 p 和q 之间的距离的平方值
    private int distance(int[] p, int[] q) {
        return (p[0] - q[0]) * (p[0] - q[0]) + (p[1] - q[1]) * (p[1] - q[1]);
    }

    /**
     * 3. Andrew's Monotone Chain二维凸包算法
     *
     * 类似Graham Scan 算法, 不过是根据坐标轴进行排序(先x 轴后y 轴).
     * 遍历顺序是: 先从起点开始, 按照顺序扫描, 找到下半部分的凸包, 再从重点开始, 反序扫描, 找到上半部分的凸包, 合起来就是完整的凸包.
     */
    @Answer
    public int[][] outerTrees3(int[][] points) {
        Arrays.sort(points, (p, q) -> p[0] - q[0] == 0 ? p[1] - q[1] : p[0] - q[0]);
        Stack<int[]> stack = new Stack<>();

        // 顺序, 寻找下边界
        for (int i = 0; i < points.length; i++) {
            // 如果点q 在L 右边, 说明pi 边界上边
            while (stack.size() >= 2
                    && orientation(stack.get(stack.size() - 2), stack.get(stack.size() - 1), points[i]) > 0) {
                stack.pop();
            }
            stack.push(points[i]);
        }

        // 反序, 寻找上边界
        for (int i = points.length - 1; i >= 0; i--) {
            // 如果点q 在L 右边, 则说明pi 在边界下 (因为是从后往前逆序, 所以是下)
            while (stack.size() >= 2
                    && orientation(stack.get(stack.size() - 2), stack.get(stack.size() - 1), points[i]) > 0) {
                stack.pop();
            }
            stack.push(points[i]);
        }

        // 删除的重复数据
        HashSet<int[]> res = new HashSet<>(stack);
        return res.toArray(new int[res.size()][]);
    }


    /**
     * 4. 分治法.
     *
     * 是对上面 Monotone Chain方法的一种改进, 按照坐标轴的顺序排列(先x 后y), 那么第一个和最后一个点肯定是在边界上的,
     * 同Monotone Chain, 遍历2次, 找出上边界和下边界,
     * 每次都找出相隔最远的点, 这2 个点肯定是边界上的点, 然后连接这2 个点, 剩余点中离这条线最远的点就是边界上的点,
     * 然后拆分成2 段继续寻找.
     */
    @Answer
    public int[][] outerTrees4(int[][] points) {
        Arrays.sort(points, (p, q) -> p[0] - q[0] == 0 ? p[1] - q[1] : p[0] - q[0]);
        Set<int[]> res = new HashSet<>();
        // 上边界
        findPoints(points, 0, points.length - 1, res, -1);
        // 下边界
        findPoints(points, 0, points.length - 1, res, 1);
        return res.toArray(new int[res.size()][]);
    }

    private void findPoints(int[][] points, int start, int end, Set<int[]> res, int direction) {
        if (start > end) {
            return;
        }
        res.add(points[start]);
        res.add(points[end]);
        double height = -1;
        int p = 0;
        for (int i = start + 1; i < end; i++) {
            if (direction * orientation(points[start], points[end], points[i]) >= 0) {
                double curHeight = distance(points[start], points[end], points[i]);
                if (height < curHeight) {
                    height = curHeight;
                    p = i;
                }
            }
        }
        if (p > 0) {
            findPoints(points, start, p, res, direction);
            findPoints(points, p, end, res, direction);
        }
    }

    // 计算点 r 到直线 p-q 的距离的平方, 公式是三角形中求高的公式.
    private double distance(int[] p, int[] q, int[] r) {
        int a2 = (r[0] - p[0]) * (r[0] - p[0]) + (r[1] - p[1]) * (r[1] - p[1]);
        int b2 = (r[0] - q[0]) * (r[0] - q[0]) + (r[1] - q[1]) * (r[1] - q[1]);
        int c2 = (q[0] - p[0]) * (q[0] - p[0]) + (q[1] - p[1]) * (q[1] - p[1]);
        return a2 - 1.0 * (c2 - b2 + a2) * (c2 - b2 + a2) / c2 / 4;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}
            }).expect(new int[][]{
                    {1, 1}, {2, 0}, {4, 2}, {3, 3}, {2, 4}
            }).unorderResult("")
            .build();

    // 这个示例说明如果点在一条线上, 则返回所有的点
    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 2}, {2, 2}, {4, 2}
            }).expect(new int[][]{
                    {1, 2}, {2, 2}, {4, 2}
            }).unorderResult("")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {3, 0}, {4, 0}, {5, 0}, {6, 1}, {7, 2}, {7, 3}, {7, 4}, {6, 5}, {5, 5}, {4, 5}, {3, 5}, {2, 5},
                    {1, 4}, {1, 3}, {1, 2}, {2, 1}, {4, 2}, {0, 3}
            }).expect(new int[][]{
                    {0, 3}, {6, 1}, {3, 5}, {7, 2}, {5, 0}, {1, 4}, {1, 2}, {7, 3}, {4, 5}, {5, 5}, {6, 5}, {4, 0},
                    {3, 0}, {2, 1}, {2, 5}, {7, 4}
            }).unorderResult("")
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {3, 2}, {3, 1}, {3, 0}, {2, 0}, {1, 0}, {1, 1}, {4, 3}
            }).expect(new int[][]{
                    {0, 0}, {0, 1}, {0, 2}, {4, 3}, {3, 0}, {2, 0}, {1, 0}
            }).unorderResult("")
            .build();

}
