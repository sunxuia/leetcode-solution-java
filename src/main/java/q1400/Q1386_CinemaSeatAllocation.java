package q1400;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1386. Cinema Seat Allocation
 * https://leetcode.com/problems/cinema-seat-allocation/
 *
 * <img src="./Q1386_PIC1.png">
 * A cinema has n rows of seats, numbered from 1 to n and there are ten seats in each row, labelled from 1 to 10 as
 * shown in the figure above.
 *
 * Given the array reservedSeats containing the numbers of seats already reserved, for example, reservedSeats[i] = [3,8]
 * means the seat located in row 3 and labelled with 8 is already reserved.
 *
 * Return the maximum number of four-person groups you can assign on the cinema seats. A four-person group occupies four
 * adjacent seats in one single row. Seats across an aisle (such as [3,3] and [3,4]) are not considered to be adjacent,
 * but there is an exceptional case on which an aisle split a four-person group, in that case, the aisle split a
 * four-person group in the middle, which means to have two people on each side.
 *
 * Example 1:
 * <img src="./Q1386_PIC2.png">
 * Input: n = 3, reservedSeats = [[1,2],[1,3],[1,8],[2,6],[3,1],[3,10]]
 * Output: 4
 * Explanation: The figure above shows the optimal allocation for four groups, where seats mark with blue are already
 * reserved and contiguous seats mark with orange are for one group.
 *
 * Example 2:
 *
 * Input: n = 2, reservedSeats = [[2,1],[1,8],[2,6]]
 * Output: 2
 *
 * Example 3:
 *
 * Input: n = 4, reservedSeats = [[4,3],[1,4],[4,6],[1,7]]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= n <= 10^9
 * 1 <= reservedSeats.length <= min(10*n, 10^4)
 * reservedSeats[i].length == 2
 * 1 <= reservedSeats[i][0] <= n
 * 1 <= reservedSeats[i][1] <= 10
 * All reservedSeats[i] are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1386_CinemaSeatAllocation {

    @Answer
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> maskMap = new HashMap<>();
        for (int[] reservedSeat : reservedSeats) {
            int mask = maskMap.getOrDefault(reservedSeat[0], 0);
            mask |= 1 << reservedSeat[1] - 1;
            maskMap.put(reservedSeat[0], mask);
        }
        int res = 2 * (n - maskMap.size());
        for (int mask : maskMap.values()) {
            if ((mask & 0b0111111110) == 0) {
                res += 2;
            } else if ((mask & 0b0001111000) == 0
                    || (mask & 0b0000011110) == 0
                    || (mask & 0b0111100000) == 0) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{1, 2}, {1, 3}, {1, 8}, {2, 6}, {3, 1}, {3, 10}})
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{{2, 1}, {1, 8}, {2, 6}})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(4, new int[][]{{4, 3}, {1, 4}, {4, 6}, {1, 7}})
            .expect(4);

    @TestData
    public DataExpectation overMemory = DataExpectation
            .createWith(1000000000, TestDataFileHelper.read(int[][].class))
            .expect(1999994439);

}
