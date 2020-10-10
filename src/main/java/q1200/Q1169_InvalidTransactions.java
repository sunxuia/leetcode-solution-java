package q1200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1169. Invalid Transactions
 * https://leetcode.com/problems/invalid-transactions/
 *
 * A transaction is possibly invalid if:
 *
 * the amount exceeds $1000, or;
 * if it occurs within (and including) 60 minutes of another transaction with the same name in a different city.
 *
 * Each transaction string transactions[i] consists of comma separated values representing the name, time (in minutes),
 * amount, and city of the transaction.
 *
 * Given a list of transactions, return a list of transactions that are possibly invalid.  You may return the answer in
 * any order.
 *
 * Example 1:
 *
 * Input: transactions = ["alice,20,800,mtv","alice,50,100,beijing"]
 * Output: ["alice,20,800,mtv","alice,50,100,beijing"]
 * Explanation: The first transaction is invalid because the second transaction occurs within a difference of 60
 * minutes, have the same name and is in a different city. Similarly the second one is invalid too.
 *
 * Example 2:
 *
 * Input: transactions = ["alice,20,800,mtv","alice,50,1200,mtv"]
 * Output: ["alice,50,1200,mtv"]
 *
 * Example 3:
 *
 * Input: transactions = ["alice,20,800,mtv","bob,50,1200,mtv"]
 * Output: ["bob,50,1200,mtv"]
 *
 * Constraints:
 *
 * transactions.length <= 1000
 * Each transactions[i] takes the form "{name},{time},{amount},{city}"
 * Each {name} and {city} consist of lowercase English letters, and have lengths between 1 and 10.
 * Each {time} consist of digits, and represent an integer between 0 and 1000.
 * Each {amount} consist of digits, and represent an integer between 0 and 2000.
 */
@RunWith(LeetCodeRunner.class)
public class Q1169_InvalidTransactions {

    @Answer
    public List<String> invalidTransactions(String[] transactions) {
        // name -> time -> city -> transaction
        var nameMap = new HashMap<String, TreeMap<Integer, Map<String, Set<String>>>>();
        Set<String> resSet = new HashSet<>();
        for (String transaction : transactions) {
            String[] strs = transaction.split(",");
            String name = strs[0];
            int time = Integer.parseInt(strs[1]);
            int amount = Integer.parseInt(strs[2]);
            String city = strs[3];
            // 条件1
            if (amount > 1000) {
                resSet.add(transaction);
            }
            // 条件2
            var timeMap = nameMap.computeIfAbsent(name, k -> new TreeMap<>());
            for (var higherTime = timeMap.ceilingKey(time - 60);
                    higherTime != null && higherTime <= time + 60;
                    higherTime = timeMap.higherKey(higherTime)) {
                var cityMap = timeMap.get(higherTime);
                for (String ct : cityMap.keySet()) {
                    if (!city.equals(ct)) {
                        resSet.add(transaction);
                        resSet.addAll(cityMap.get(ct));
                    }
                }
            }
            timeMap.computeIfAbsent(time, k -> new HashMap<>())
                    .computeIfAbsent(city, k -> new HashSet<>())
                    .add(transaction);
        }
        return new ArrayList<>(resSet);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"alice,20,800,mtv", "alice,50,100,beijing"})
            .expect(Arrays.asList("alice,20,800,mtv", "alice,50,100,beijing"))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"alice,20,800,mtv", "alice,50,1200,mtv"})
            .expect(Arrays.asList("alice,50,1200,mtv"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new String[]{"alice,20,800,mtv", "bob,50,1200,mtv"})
            .expect(Arrays.asList("bob,50,1200,mtv"));

}
