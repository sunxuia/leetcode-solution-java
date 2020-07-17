package q900;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 871. Minimum Number of Refueling Stops
 * https://leetcode.com/problems/minimum-number-of-refueling-stops/
 *
 * A car travels from a starting position to a destination which is target miles east of the starting position.
 *
 * Along the way, there are gas stations.  Each station[i] represents a gas station that is station[i][0] miles east of
 * the starting position, and has station[i][1] liters of gas.
 *
 * The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.  It uses 1 liter of
 * gas per 1 mile that it drives.
 *
 * When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.
 *
 * What is the least number of refueling stops the car must make in order to reach its destination?  If it cannot reach
 * the destination, return -1.
 *
 * Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.  If the car reaches the
 * destination with 0 fuel left, it is still considered to have arrived.
 *
 * Example 1:
 *
 * Input: target = 1, startFuel = 1, stations = []
 * Output: 0
 * Explanation: We can reach the target without refueling.
 *
 * Example 2:
 *
 * Input: target = 100, startFuel = 1, stations = [[10,100]]
 * Output: -1
 * Explanation: We can't reach the target (or even the first gas station).
 *
 * Example 3:
 *
 * Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
 * Output: 2
 * Explanation:
 * We start with 10 liters of fuel.
 * We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
 * Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
 * and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
 * We made 2 refueling stops along the way, so we return 2.
 *
 * Note:
 *
 * 1 <= target, startFuel, stations[i][1] <= 10^9
 * 0 <= stations.length <= 500
 * 0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target
 */
@RunWith(LeetCodeRunner.class)
public class Q871_MinimumNumberOfRefuelingStops {

    // bfs 会遇到超时或者超过内存限制的问题, 这题可以使用dp 来做
    // 参考文档 https://www.cnblogs.com/grandyang/p/11186533.html
    @Answer
    public int minRefuelStops_dp(int target, int startFuel, int[][] stations) {
        final int n = stations.length;
        // dp[i] 表示加i 次油可以走的最远距离. int 值可能会溢出
        long[] dp = new long[n + 1];
        // 0 个加油站的时候数量是0
        for (int i = 0; i <= n; i++) {
            dp[i] = startFuel;
        }
        // 遍历每个加油站
        for (int s = 0; s < n; s++) {
            // 当前加油站 + 第i 次加油
            for (int i = s; i >= 0 && dp[i] >= stations[s][0]; i--) {
                // 算上这个加油站能走的最远距离
                dp[i + 1] = Math.max(dp[i + 1], dp[i] + stations[s][1]);
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    // 这题还可以通过最大堆来做, 参考文档同上
    @Answer
    public int minRefuelStops_pq(int target, int startFuel, int[][] stations) {
        final int n = stations.length;
        int res = 0, i = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        while (startFuel < target) {
            while (i < n && stations[i][0] <= startFuel) {
                pq.add(stations[i++][1]);
            }
            if (pq.isEmpty()) {
                return -1;
            }
            startFuel += pq.poll();
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 1, new int[0][]).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(100, 1, new int[][]{{10, 100}}).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(100, 10, new int[][]{{10, 60}, {20, 30}, {30, 30}, {60, 40}})
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(1000, 5, new int[][]{
                    {1, 148}, {40, 32}, {115, 176}, {121, 235}, {132, 69}, {206, 144}, {213, 158}, {277, 152},
                    {427, 85}, {434, 138}, {466, 231}, {539, 138}, {605, 43}, {682, 143}, {718, 187}, {729, 20},
                    {802, 216}, {828, 119}, {906, 54}, {914, 56}, {931, 21}, {937, 213}, {946, 205}, {958, 104},
                    {999, 247}})
            .expect(6);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(1000, 1, new int[][]{
                    {1, 186}, {145, 161}, {183, 43}, {235, 196}, {255, 169}, {263, 200}, {353, 161}, {384, 190},
                    {474, 44}, {486, 43}, {567, 48}, {568, 96}, {592, 36}, {634, 181}, {645, 167}, {646, 69}, {690, 52},
                    {732, 28}, {800, 42}, {857, 55}, {922, 63}, {960, 141}, {973, 13}, {977, 112}, {997, 162}})
            .expect(6);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(1000000000, 38810519, new int[][]{
                    {7628755, 101639744}, {11751117, 156654090}, {12241114, 123929400}, {14990938, 170128657},
                    {24195531, 161701399}, {32308772, 43624544}, {66433584, 134938427}, {68396870, 156484817},
                    {78204256, 180589858}, {109893935, 35580185}, {111671743, 24666035}, {118210724, 27969405},
                    {124496566, 8079140}, {128874722, 79094666}, {143090505, 79704592}, {145352879, 163599886},
                    {151322145, 772299}, {167914961, 25673486}, {187687120, 198387302}, {194331315, 244001153},
                    {198438320, 161291448}, {208158728, 239205669}, {215913832, 230073357}, {218199341, 164065449},
                    {232978814, 226762856}, {245228323, 5620801}, {246391469, 73385898}, {255158244, 28568031},
                    {259408313, 153232722}, {263684268, 248715958}, {269953038, 75587893}, {276497343, 53200888},
                    {277374123, 145359059}, {285214203, 55186339}, {290062860, 192833517}, {293372122, 221628282},
                    {300249473, 86505102}, {304959311, 4345873}, {319900948, 179848231}, {333190950, 110834576},
                    {352164877, 42012442}, {357057556, 87025423}, {361655495, 119455162}, {375567556, 169601951},
                    {382448720, 193431526}, {391326308, 102884542}, {399935561, 20412948}, {415217543, 235164692},
                    {428156887, 69697057}, {440142560, 222180666}, {457124108, 233594726}, {479646931, 14170039},
                    {486708367, 125850253}, {495778067, 48934614}, {497913863, 200941433}, {500796422, 219374647},
                    {503208872, 191165692}, {503693789, 42268837}, {521824953, 101883106}, {554587849, 10249728},
                    {567818906, 134165043}, {575807886, 212453409}, {580772042, 203633463}, {592223655, 23837437},
                    {598507993, 171029941}, {599462149, 118000389}, {602004332, 152010839}, {606146618, 30388416},
                    {611617165, 54727655}, {627499060, 64582627}, {682104424, 28452044}, {690208375, 121220328},
                    {706329620, 65697174}, {708333643, 109151902}, {712003414, 32345338}, {718544747, 156843700},
                    {719902426, 200157751}, {733841205, 164229927}, {762450428, 189657429}, {783181120, 36063018},
                    {812537469, 137803252}, {815526433, 120952170}, {853730079, 54211866}, {853816837, 217187004},
                    {859759135, 10549150}, {861814976, 86757900}, {883115022, 222310472}, {897314129, 73886212},
                    {904499666, 215174634}, {909186227, 179120603}, {911670663, 97564252}, {920784136, 28872274},
                    {922676703, 115321708}, {923085330, 158669494}, {954955029, 60706177}, {959087932, 236512493},
                    {973937404, 157896841}, {986787536, 142182347}, {991596618, 222478376}, {999438640, 39863176}})
            .expect(5);

}
