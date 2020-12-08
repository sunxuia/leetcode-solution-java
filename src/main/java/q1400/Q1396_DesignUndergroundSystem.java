package q1400;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1396. Design Underground System
 * https://leetcode.com/problems/design-underground-system/
 *
 * Implement the class UndergroundSystem that supports three methods:
 *
 * 1. checkIn(int id, string stationName, int t)
 *
 * A customer with id card equal to id, gets in the station stationName at time t.
 * A customer can only be checked into one place at a time.
 *
 * 2. checkOut(int id, string stationName, int t)
 *
 * A customer with id card equal to id, gets out from the station stationName at time t.
 *
 * 3. getAverageTime(string startStation, string endStation)
 *
 * Returns the average time to travel between the startStation and the endStation.
 * The average time is computed from all the previous traveling from startStation to endStation that happened directly.
 * Call to getAverageTime is always valid.
 *
 * You can assume all calls to checkIn and checkOut methods are consistent. That is, if a customer gets in at time t1 at
 * some station, then it gets out at time t2 with t2 > t1. All events happen in chronological order.
 *
 * Example 1:
 *
 * Input
 * ["UndergroundSystem","checkIn","checkIn","checkIn","checkOut","checkOut","checkOut","getAverageTime",
 * "getAverageTime","checkIn","getAverageTime","checkOut","getAverageTime"]
 * [[],[45,"Leyton",3],[32,"Paradise",8],[27,"Leyton",10],[45,"Waterloo",15],[27,"Waterloo",20],[32,"Cambridge",22],
 * ["Paradise","Cambridge"],["Leyton","Waterloo"],[10,"Leyton",24],["Leyton","Waterloo"],[10,"Waterloo",38],
 * ["Leyton","Waterloo"]]
 *
 * Output
 * [null,null,null,null,null,null,null,14.00000,11.00000,null,11.00000,null,12.00000]
 *
 * Explanation
 * UndergroundSystem undergroundSystem = new UndergroundSystem();
 * undergroundSystem.checkIn(45, "Leyton", 3);
 * undergroundSystem.checkIn(32, "Paradise", 8);
 * undergroundSystem.checkIn(27, "Leyton", 10);
 * undergroundSystem.checkOut(45, "Waterloo", 15);
 * undergroundSystem.checkOut(27, "Waterloo", 20);
 * undergroundSystem.checkOut(32, "Cambridge", 22);
 * undergroundSystem.getAverageTime("Paradise", "Cambridge");       // return 14.00000. There was only one travel from
 * "Paradise" (at time 8) to "Cambridge" (at time 22)
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 11.00000. There were two travels from
 * "Leyton" to "Waterloo", a customer with id=45 from time=3 to time=15 and a customer with id=27 from time=10 to
 * time=20. So the average time is ( (15-3) + (20-10) ) / 2 = 11.00000
 * undergroundSystem.checkIn(10, "Leyton", 24);
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 11.00000
 * undergroundSystem.checkOut(10, "Waterloo", 38);
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 12.00000
 *
 * Example 2:
 *
 * Input
 * ["UndergroundSystem","checkIn","checkOut","getAverageTime","checkIn","checkOut","getAverageTime","checkIn",
 * "checkOut","getAverageTime"]
 * [[],[10,"Leyton",3],[10,"Paradise",8],["Leyton","Paradise"],[5,"Leyton",10],[5,"Paradise",16],["Leyton",
 * "Paradise"],[2,"Leyton",21],[2,"Paradise",30],["Leyton","Paradise"]]
 *
 * Output
 * [null,null,null,5.00000,null,null,5.50000,null,null,6.66667]
 *
 * Explanation
 * UndergroundSystem undergroundSystem = new UndergroundSystem();
 * undergroundSystem.checkIn(10, "Leyton", 3);
 * undergroundSystem.checkOut(10, "Paradise", 8);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.00000
 * undergroundSystem.checkIn(5, "Leyton", 10);
 * undergroundSystem.checkOut(5, "Paradise", 16);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.50000
 * undergroundSystem.checkIn(2, "Leyton", 21);
 * undergroundSystem.checkOut(2, "Paradise", 30);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 6.66667
 *
 * Constraints:
 *
 * There will be at most 20000 operations.
 * 1 <= id, t <= 10^6
 * All strings consist of uppercase, lowercase English letters and digits.
 * 1 <= stationName.length <= 10
 * Answers within 10^-5 of the actual value will be accepted as correct.
 */
public class Q1396_DesignUndergroundSystem {

    private static class UndergroundSystem {

        // tours[fromStation][toStation]{count, time}
        Map<String, Map<String, int[]>> tours = new HashMap<>();

        // enters[fromStation]{customer, t}
        Map<Integer, Object[]> enters = new HashMap<>();

        public UndergroundSystem() {

        }

        public void checkIn(int id, String stationName, int t) {
            enters.put(id, new Object[]{stationName, t});
        }

        public void checkOut(int id, String stationName, int t) {
            Object[] enter = enters.get(id);
            String fromStationName = (String) enter[0];
            int time = t - (int) enter[1];
            int[] tour = tours
                    .computeIfAbsent(fromStationName, k -> new HashMap<>())
                    .computeIfAbsent(stationName, k -> new int[2]);
            tour[0]++;
            tour[1] += time;
        }

        public double getAverageTime(String startStation, String endStation) {
            return Optional.ofNullable(tours.get(startStation))
                    .map(tos -> tos.get(endStation))
                    .map(tour -> (double) tour[1] / tour[0])
                    .orElse(0.0);
        }
    }

    /**
     * Your UndergroundSystem object will be instantiated and called as such:
     * UndergroundSystem obj = new UndergroundSystem();
     * obj.checkIn(id,stationName,t);
     * obj.checkOut(id,stationName,t);
     * double param_3 = obj.getAverageTime(startStation,endStation);
     */

    @Test
    public void testMethod() {
        UndergroundSystem undergroundSystem = new UndergroundSystem();
        double averageTime;

        undergroundSystem.checkIn(45, "Leyton", 3);
        undergroundSystem.checkIn(32, "Paradise", 8);
        undergroundSystem.checkIn(27, "Leyton", 10);
        undergroundSystem.checkOut(45, "Waterloo", 15);
        undergroundSystem.checkOut(27, "Waterloo", 20);
        undergroundSystem.checkOut(32, "Cambridge", 22);
        averageTime = undergroundSystem.getAverageTime("Paradise", "Cambridge");
        Assert.assertEquals(14.0, averageTime, 0.00001);

        averageTime = undergroundSystem.getAverageTime("Leyton", "Waterloo");
        Assert.assertEquals(11.0, averageTime, 0.00001);

        undergroundSystem.checkIn(10, "Leyton", 24);
        averageTime = undergroundSystem.getAverageTime("Leyton", "Waterloo");
        Assert.assertEquals(11.0, averageTime, 0.00001);

        undergroundSystem.checkOut(10, "Waterloo", 38);
        averageTime = undergroundSystem.getAverageTime("Leyton", "Waterloo");
        Assert.assertEquals(12.0, averageTime, 0.00001);
    }

}
