package q250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/the-skyline-problem/
 *
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from
 * a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo
 * (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).
 *
 * (图 Q218_PIC.png)
 * Buildings Skyline Contour
 *
 * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri
 * are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is
 * guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect
 * rectangles grounded on an absolutely flat surface at height 0.
 *
 * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20
 * 10], [19 24 8] ] .
 *
 * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ]
 * that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last
 * key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always
 * has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline
 * contour.
 *
 * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8],
 * [24, 0] ].
 *
 * Notes:
 *
 * The number of buildings in any input list is guaranteed to be in the range [0, 10000].
 * The input list is already sorted in ascending order by the left x position Li.
 * The output list must be sorted by the x position.
 * There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3],
 * [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in
 * the final output as such: [...[2 3], [4 5], [12 7], ...]
 *
 * 题解:
 * 求天际线, 城市的楼房简化为一个个矩形, 多个矩形叠加起来组成了城市的天际线, 现在要求这个天际线.
 * 输入 buildings 中每个数组都表示一个楼房矩形, 元素是 : [ 起始下标 结束下标 高度 ], 简写为 [Li Ri Hi].
 * 其中 0 <= Li < Ri <= INT_MAX, 0 < Hi ≤ INT_MAX; 0 <= buildings.length <= 1_0000, 且buildings 按照 Li 从小到大排序.
 * 输出数组的元素为: [ 起始下标 高度 ], 表示在这个下标高度(发生了变化) 变为了新的高度.
 * 输出必须按照起始下标从小到大排序, 相邻的数组高度不能相同(即每个数组都表示高度变化了), 最开始的 [0 0] 不用写.
 */
@RunWith(LeetCodeRunner.class)
public class Q218_TheSkylineProblem {

    // LeetCode 上的解法: 使用到了优先队列.
    @Answer
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        //默认输入已经按照起点排序
        //按照高度降序，同高度根据起点升序
        PriorityQueue<int[]> heightHeap = new PriorityQueue<>((a, b) -> b[2] - a[2]);

        //默认起点, pre 保存前面能看见的最高建筑和他的终点起点
        int[] pre = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0};
        for (int[] b : buildings) {
            // 当出现断点情况, 需要清空之前建筑群
            while (!heightHeap.isEmpty() && b[0] > pre[1]) {
                //获取之前最高建筑
                int[] curHighest = heightHeap.poll();
                //如果最高的终点在PRE之前，说明已经处理
                if (curHighest[1] <= pre[1]) {
                    continue;
                }
                //如果遇到PRE之后的点，加入结果并更新PRE
                res.add(Arrays.asList(pre[1], curHighest[2]));
                pre = curHighest;
            }

            //当前建筑比之前建筑高
            if (b[2] > pre[2]) {
                if (b[0] == pre[0]) {
                    //同起点情况下，矮建筑必然被挡住，直接删除
                    res.remove(res.size() - 1);
                }
                res.add(Arrays.asList(b[0], b[2])); //未被之后遮挡前先加入结果
                if (b[1] < pre[1]) {
                    heightHeap.offer(pre); //如果终点小于前终点，将前值入堆
                }
                pre = b;//更新前值因为发现了更高的
            } else if (b[2] == pre[2]) { //同高度继续延伸END
                pre[1] = b[1];
            } else if (b[1] > pre[1]) {
                heightHeap.offer(b); //矮建筑直接入堆
            }
        }

        while (!heightHeap.isEmpty()) {
            //如果堆不为空，重复之前操作
            int[] cur = heightHeap.poll();
            if (cur[1] <= pre[1]) {
                continue;
            }
            res.add(Arrays.asList(pre[1], cur[2]));
            pre = cur;
        }
        //最后有剩余
        if (pre[2] > 0) {
            res.add(Arrays.asList(pre[1], 0));
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[][]{
                    {2, 9, 10},
                    {3, 7, 15},
                    {5, 12, 12},
                    {15, 20, 10},
                    {19, 24, 8}
            }).expect(new int[][]{
                    {2, 10},
                    {3, 15},
                    {7, 12},
                    {12, 0},
                    {15, 10},
                    {20, 8},
                    {24, 0}
            }).build();

}
