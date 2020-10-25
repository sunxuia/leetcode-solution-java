package q900;

import java.util.PriorityQueue;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/exam-room/
 *
 * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
 *
 * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person.  If
 * there are multiple such seats, they sit in the seat with the lowest number.  (Also, if no one is in the room, then
 * the student sits at seat number 0.)
 *
 * Return a class ExamRoom(int N) that exposes two functions: ExamRoom.seat() returning an int representing what seat
 * the student sat in, and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room.
 * It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.
 *
 *
 *
 * Example 1:
 *
 * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 * Output: [null,0,9,4,2,null,5]
 * Explanation:
 * ExamRoom(10) -> null
 * seat() -> 0, no one is in the room, then the student sits at seat number 0.
 * seat() -> 9, the student sits at the last seat number 9.
 * seat() -> 4, the student sits at the last seat number 4.
 * seat() -> 2, the student sits at the last seat number 2.
 * leave(4) -> null
 * seat() -> 5, the student sits at the last seat number 5.
 *
 * ​​​​​​​
 *
 * Note:
 *
 * 1 <= N <= 10^9
 * ExamRoom.seat() and ExamRoom.leave() will be called at most 10^4 times across all test cases.
 * Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.
 */
public class Q855_ExamRoom {

    public static class ExamRoom {

        final int n;

        TreeSet<Integer> allocated;

        PriorityQueue<Range> pq;

        public ExamRoom(int N) {
            n = N;
            allocated = new TreeSet<>();
            pq = new PriorityQueue<>((a, b) -> {
                if (b.margin == a.margin) {
                    return a.pos - b.pos;
                }
                return b.margin - a.margin;
            });
            pq.add(new Range(null, null));
        }

        public int seat() {
            Range range = pq.poll();
            while (!range.isValid()) {
                range = pq.poll();
            }
            allocated.add(range.pos);
            int left = range.left == null ? 0 : range.left;
            if (left + 1 < range.pos) {
                pq.add(new Range(range.left, range.pos));
            }
            int right = range.right == null ? n : range.right;
            if (range.pos + 1 < right) {
                pq.add(new Range(range.pos, range.right));
            }
            return range.pos;
        }

        public void leave(int p) {
            allocated.remove(p);
            pq.add(new Range(allocated.lower(p), allocated.higher(p)));
        }

        class Range {

            // 表示这个区间的左右边界(不包含, 如果没有则为null)
            final Integer left, right;
            // pos: 当前区间应当选择的座位
            // margin: 当前区间距离其他坐人座位的长度
            final int pos, margin;

            Range(Integer left, Integer right) {
                this.left = left;
                this.right = right;
                if (left == null && right == null) {
                    pos = 0;
                    margin = n;
                } else if (left == null) {
                    pos = 0;
                    margin = right;
                } else if (right == null) {
                    pos = n - 1;
                    margin = pos - left;
                } else {
                    pos = (left + right) / 2;
                    margin = pos - left;
                }
            }

            // 这个区间是否还是有效的
            boolean isValid() {
                if (left == null && right == null) {
                    return allocated.isEmpty();
                } else if (left == null) {
                    return allocated.contains(right) && allocated.lower(right) == null;
                } else if (right == null) {
                    return allocated.contains(left) && allocated.higher(left) == null;
                } else {
                    return allocated.contains(left)
                            && allocated.contains(right)
                            && right.equals(allocated.higher(left));
                }
            }

            // (调试用)
            @Override
            public String toString() {
                return String.format("(%d - %d - %d) [%d]", left, pos, right, margin);
            }
        }
    }

    @Test
    public void testMethod() {
        ExamRoom examRoom;

        examRoom = new ExamRoom(10);
        Assert.assertEquals(0, examRoom.seat());
        Assert.assertEquals(9, examRoom.seat());
        Assert.assertEquals(4, examRoom.seat());
        Assert.assertEquals(2, examRoom.seat());
        examRoom.leave(4);
        Assert.assertEquals(5, examRoom.seat());

        examRoom = new ExamRoom(10);
        Assert.assertEquals(0, examRoom.seat());
        Assert.assertEquals(9, examRoom.seat());
        Assert.assertEquals(4, examRoom.seat());
        examRoom.leave(0);
        examRoom.leave(4);
        Assert.assertEquals(0, examRoom.seat());
        Assert.assertEquals(4, examRoom.seat());
        Assert.assertEquals(2, examRoom.seat());
        Assert.assertEquals(6, examRoom.seat());
        Assert.assertEquals(1, examRoom.seat());
        Assert.assertEquals(3, examRoom.seat());
        Assert.assertEquals(5, examRoom.seat());
        Assert.assertEquals(7, examRoom.seat());
        Assert.assertEquals(8, examRoom.seat());
        examRoom.leave(0);
    }

}
