package q1450;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1418. Display Table of Food Orders in a Restaurant
 * https://leetcode.com/problems/display-table-of-food-orders-in-a-restaurant/
 *
 * Given the array orders, which represents the orders that customers have done in a restaurant. More specifically
 * orders[i]=[customerNamei,tableNumberi,foodItemi] where customerNamei is the name of the customer, tableNumberi is the
 * table customer sit at, and foodItemi is the item customer orders.
 *
 * Return the restaurant's "display table". The "display table" is a table whose row entries
 * denote how many of each food item each table ordered. The first column is the table number and the remaining columns
 * correspond to each food item in alphabetical order. The first row should be a header whose first column is
 * "Table", followed by the names of the food items. Note that the customer names are not part of the table.
 * Additionally, the rows should be sorted in numerically increasing order.
 *
 * Example 1:
 *
 * Input: orders = [["David","3","Ceviche"],["Corina","10","Beef Burrito"],["David","3","Fried
 * Chicken"],["Carla","5","Water"],["Carla","5","Ceviche"],["Rous","3","Ceviche"]]
 * Output: [["Table","Beef Burrito","Ceviche","Fried Chicken","Water"],["3","0","2","1","0"],["5","0","1","0","1"],
 * ["10","1","0","0","0"]]
 * Explanation:
 * The displaying table looks like:
 * Table,Beef Burrito,Ceviche,Fried Chicken,Water
 * 3    ,0           ,2      ,1            ,0
 * 5    ,0           ,1      ,0            ,1
 * 10   ,1           ,0      ,0            ,0
 * For the table 3: David orders "Ceviche" and "Fried Chicken", and Rous orders "Ceviche".
 * For the table 5: Carla orders "Water" and "Ceviche".
 * For the table 10: Corina orders "Beef Burrito".
 *
 * Example 2:
 *
 * Input: orders = [["James","12","Fried Chicken"],["Ratesh","12","Fried Chicken"],["Amadeus","12","Fried
 * Chicken"],["Adam","1","Canadian Waffles"],["Brianna","1","Canadian Waffles"]]
 * Output: [["Table","Canadian Waffles","Fried Chicken"],["1","2","0"],["12","0","3"]]
 * Explanation:
 * For the table 1: Adam and Brianna order "Canadian Waffles".
 * For the table 12: James, Ratesh and Amadeus order "Fried Chicken".
 *
 * Example 3:
 *
 * Input: orders = [["Laura","2","Bean Burrito"],["Jhon","2","Beef Burrito"],["Melissa","2","Soda"]]
 * Output: [["Table","Bean Burrito","Beef Burrito","Soda"],["2","1","1","1"]]
 *
 * Constraints:
 *
 * 1 <= orders.length <= 5 * 10^4
 * orders[i].length == 3
 * 1 <= customerNamei.length, foodItemi.length <= 20
 * customerNamei and foodItemi consist of lowercase and uppercase English letters and the space character.
 * tableNumberi is a valid integer between 1 and 500.
 */
@RunWith(LeetCodeRunner.class)
public class Q1418_DisplayTableOfFoodOrdersInARestaurant {

    @Answer
    public List<List<String>> displayTable(List<List<String>> orders) {
        Map<String, Integer>[] tables = new Map[501];
        Set<String> foodItems = new HashSet<>();
        for (List<String> order : orders) {
            int tableName = Integer.parseInt(order.get(1));
            String foodItem = order.get(2);
            if (tables[tableName] == null) {
                tables[tableName] = new HashMap<>();
            }
            int count = tables[tableName].getOrDefault(foodItem, 0);
            tables[tableName].put(foodItem, count + 1);
            foodItems.add(foodItem);
        }

        List<String> foodList = new ArrayList<>(foodItems);
        Collections.sort(foodList);
        List<List<String>> res = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        headers.add("Table");
        headers.addAll(foodList);
        res.add(headers);

        for (int i = 1; i <= 500; i++) {
            if (tables[i] == null) {
                continue;
            }
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(i));
            for (String foodItem : foodList) {
                int count = tables[i].getOrDefault(foodItem, 0);
                row.add(String.valueOf(count));
            }
            res.add(row);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(
                    List.of("David", "3", "Ceviche"),
                    List.of("Corina", "10", "Beef Burrito"),
                    List.of("David", "3", "Fried Chicken"),
                    List.of("Carla", "5", "Water"),
                    List.of("Carla", "5", "Ceviche"),
                    List.of("Rous", "3", "Ceviche")))
            .expect(List.of(
                    List.of("Table", "Beef Burrito", "Ceviche", "Fried Chicken", "Water"),
                    List.of("3", "0", "2", "1", "0"),
                    List.of("5", "0", "1", "0", "1"),
                    List.of("10", "1", "0", "0", "0")));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(
                    List.of("James", "12", "Fried Chicken"),
                    List.of("Ratesh", "12", "Fried Chicken"),
                    List.of("Amadeus", "12", "Fried Chicken"),
                    List.of("Adam", "1", "Canadian Waffles"),
                    List.of("Brianna", "1", "Canadian Waffles")))
            .expect(List.of(
                    List.of("Table", "Canadian Waffles", "Fried Chicken"),
                    List.of("1", "2", "0"),
                    List.of("12", "0", "3")));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(
                    List.of("Laura", "2", "Bean Burrito"),
                    List.of("Jhon", "2", "Beef Burrito"),
                    List.of("Melissa", "2", "Soda")))
            .expect(List.of(
                    List.of("Table", "Bean Burrito", "Beef Burrito", "Soda"),
                    List.of("2", "1", "1", "1")));

}
