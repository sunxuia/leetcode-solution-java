package q700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/falling-squares/
 *
 * On an infinite number line (x-axis), we drop given squares in the order they are given.
 *
 * The i-th square dropped (positions[i] = (left, side_length)) is a square with the left-most point being
 * positions[i][0] and sidelength positions[i][1].
 *
 * The square is dropped with the bottom edge parallel to the number line, and from a higher height than all
 * currently landed squares. We wait for each square to stick before dropping the next.
 *
 * The squares are infinitely sticky on their bottom edge, and will remain fixed to any positive length surface they
 * touch (either the number line or another square). Squares dropped adjacent to each other will not stick together
 * prematurely.
 *
 *
 * Return a list ans of heights. Each height ans[i] represents the current highest height of any square we have
 * dropped, after dropping squares represented by positions[0], positions[1], ..., positions[i].
 *
 * Example 1:
 *
 * Input: [[1, 2], [2, 3], [6, 1]]
 * Output: [2, 5, 5]
 * Explanation:
 *
 * After the first drop of positions[0] = [1, 2]: _aa _aa ------- The maximum height of any square is 2.
 *
 * After the second drop of positions[1] = [2, 3]: __aaa __aaa __aaa _aa__ _aa__ -------------- The maximum height of
 * any square is 5. The larger square stays on top of the smaller square despite where its center of gravity is,
 * because squares are infinitely sticky on their bottom edge.
 *
 * After the third drop of positions[1] = [6, 1]: __aaa __aaa __aaa _aa _aa___a -------------- The maximum height of
 * any square is still 5. Thus, we return an answer of [2, 5, 5].
 *
 *
 *
 *
 * Example 2:
 *
 * Input: [[100, 100], [200, 100]]
 * Output: [100, 100]
 * Explanation: Adjacent squares don't get stuck prematurely - only their bottom edge can stick to surfaces.
 *
 *
 *
 * Note:
 *
 * 1 <= positions.length <= 1000.
 * 1 <= positions[i][0] <= 10^8.
 * 1 <= positions[i][1] <= 10^6.
 */
@RunWith(LeetCodeRunner.class)
public class Q699_FallingSquares {

    @Answer
    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> res = new ArrayList<>();
        int max = 0;
        TreeSet<Range> set = new TreeSet<>();
        for (int[] position : positions) {
            final int pos = position[0], length = position[1];
            Range newRange = new Range(pos, pos + length, length);

            // 去掉前面重叠的部分
            Range range = set.lower(newRange);
            if (range != null && range.end > newRange.start) {
                if (range.end <= newRange.end) {
                    range.end = newRange.start;
                    newRange.height += range.height;
                } else {
                    Range tail = new Range(newRange.end, range.end, range.height);
                    range.end = newRange.start;
                    newRange.height += range.height;
                    set.add(tail);
                }
            }

            // 去掉中间的内容
            range = set.ceiling(newRange);
            while (range != null && range.end <= pos + length) {
                newRange.height = Math.max(newRange.height, range.height + length);
                set.remove(range);
                range = set.higher(range);
            }

            // 去掉后面重叠的部分
            if (range != null && range.start < newRange.end) {
                newRange.height = Math.max(newRange.height, range.height + length);
                set.remove(range);
                range.start = newRange.end;
                set.add(range);
            }

            set.add(newRange);
            max = Math.max(max, newRange.height);
            res.add(max);
        }
        return res;
    }

    private static class Range implements Comparable<Range> {

        int start, end, height;

        Range(int start, int end, int height) {
            this.start = start;
            this.end = end;
            this.height = height;
        }

        @Override
        public int compareTo(Range o) {
            return start - o.start;
        }

        @Override
        public int hashCode() {
            return start;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    // Solution 中给出的线段树方式的解答如下
    @Answer
    public List<Integer> fallingSquares2(int[][] positions) {
        // 坐标压缩
        Set<Integer> coords = new HashSet<>();
        for (int[] pos : positions) {
            coords.add(pos[0]);
            coords.add(pos[0] + pos[1] - 1);
        }
        List<Integer> sortedCoords = new ArrayList<>(coords);
        Collections.sort(sortedCoords);

        Map<Integer, Integer> index = new HashMap<>();
        int t = 0;
        for (int coord : sortedCoords) {
            index.put(coord, t++);
        }
        SegmentTree tree = new SegmentTree(sortedCoords.size());
        int best = 0;
        List<Integer> ans = new ArrayList<>();

        for (int[] pos : positions) {
            int left = index.get(pos[0]);
            int right = index.get(pos[0] + pos[1] - 1);
            int h = tree.query(left, right) + pos[1];
            tree.update(left, right, h);
            best = Math.max(best, h);
            ans.add(best);
        }
        return ans;
    }

    class SegmentTree {

        int N, H;
        int[] tree, lazy;

        SegmentTree(int N) {
            this.N = N;
            H = 1;
            while ((1 << H) < N) {
                H++;
            }
            tree = new int[2 * N];
            lazy = new int[N];
        }

        private void apply(int x, int val) {
            tree[x] = Math.max(tree[x], val);
            if (x < N) {
                lazy[x] = Math.max(lazy[x], val);
            }
        }

        private void pull(int x) {
            while (x > 1) {
                x >>= 1;
                tree[x] = Math.max(tree[x * 2], tree[x * 2 + 1]);
                tree[x] = Math.max(tree[x], lazy[x]);
            }
        }

        private void push(int x) {
            for (int h = H; h > 0; h--) {
                int y = x >> h;
                if (lazy[y] > 0) {
                    apply(y * 2, lazy[y]);
                    apply(y * 2 + 1, lazy[y]);
                    lazy[y] = 0;
                }
            }
        }

        public void update(int left, int right, int h) {
            left += N;
            right += N;
            int left0 = left, right0 = right, ans = 0;
            while (left <= right) {
                if ((left & 1) == 1) {
                    apply(left++, h);
                }
                if ((right & 1) == 0) {
                    apply(right--, h);
                }
                left >>= 1;
                right >>= 1;
            }
            pull(left0);
            pull(right0);
        }

        public int query(int left, int right) {
            left += N;
            right += N;
            int res = 0;
            push(left);
            push(right);
            while (left <= right) {
                if ((left & 1) == 1) {
                    res = Math.max(res, tree[left++]);
                }
                if ((right & 1) == 0) {
                    res = Math.max(res, tree[right--]);
                }
                left >>= 1;
                right >>= 1;
            }
            return res;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2}, {2, 3}, {6, 1}
    }).expect(Arrays.asList(2, 5, 5));

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {100, 100}, {200, 100}
    }).expect(Arrays.asList(100, 100));

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {6, 4}, {2, 7}, {6, 9}
    }).expect(Arrays.asList(4, 11, 20));

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {7, 2}, {1, 7}, {9, 5}, {1, 8}, {3, 4}
    }).expect(Arrays.asList(2, 9, 9, 17, 21));

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[][]{
            {4, 1}, {6, 9}, {6, 8}, {1, 9}, {9, 8}
    }).expect(Arrays.asList(1, 9, 17, 26, 34));

}
