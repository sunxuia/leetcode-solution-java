package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1744. Can You Eat Your Favorite Candy on Your Favorite Day?
 * https://leetcode.com/problems/can-you-eat-your-favorite-candy-on-your-favorite-day/
 *
 * You are given a (0-indexed) array of positive integers candiesCount where candiesCount[i] represents the number of
 * candies of the ith type you have. You are also given a 2D array queries where queries[i] = [favoriteTypei,
 * favoriteDayi, dailyCapi].
 *
 * You play a game with the following rules:
 *
 * 1. You start eating candies on day 0.
 * 2. You cannot eat any candy of type i unless you have eaten all candies of type i - 1.
 * 3. You must eat at least one candy per day until you have eaten all the candies.
 *
 * Construct a boolean array answer such that answer.length == queries.length and answer[i] is true if you can eat a
 * candy of type favoriteTypei on day favoriteDayi without eating more than dailyCapi candies on any day, and false
 * otherwise. Note that you can eat different types of candy on the same day, provided that you follow rule 2.
 *
 * Return the constructed array answer.
 *
 * Example 1:
 *
 * Input: candiesCount = [7,4,5,3,8], queries = [[0,2,2],[4,2,4],[2,13,1000000000]]
 * Output: [true,false,true]
 * Explanation:
 * 1- If you eat 2 candies (type 0) on day 0 and 2 candies (type 0) on day 1, you will eat a candy of type 0 on day 2.
 * 2- You can eat at most 4 candies each day.
 * If you eat 4 candies every day, you will eat 4 candies (type 0) on day 0 and 4 candies (type 0 and type 1) on day 1.
 * On day 2, you can only eat 4 candies (type 1 and type 2), so you cannot eat a candy of type 4 on day 2.
 * 3- If you eat 1 candy each day, you will eat a candy of type 2 on day 13.
 *
 * Example 2:
 *
 * Input: candiesCount = [5,2,6,4,1], queries = [[3,1,2],[4,10,3],[3,10,100],[4,100,30],[1,3,1]]
 * Output: [false,true,true,false,false]
 *
 * Constraints:
 *
 * 1 <= candiesCount.length <= 10^5
 * 1 <= candiesCount[i] <= 10^5
 * 1 <= queries.length <= 10^5
 * queries[i].length == 3
 * 0 <= favoriteTypei < candiesCount.length
 * 0 <= favoriteDayi <= 10^9
 * 1 <= dailyCapi <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1744_CanYouEatYourFavoriteCandyOnYourFavoriteDay {

    @Answer
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        final int n = candiesCount.length, m = queries.length;

        long[] sums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + candiesCount[i];
        }

        boolean[] res = new boolean[m];
        for (int i = 0; i < m; i++) {
            int type = queries[i][0];
            // (这里的天数从 0 开始)
            int day = queries[i][1] + 1;
            int dailyCap = queries[i][2];
            // 有的吃
            if (candiesCount[type] > 0) {
                // 最慢可以吃到那一天
                if (sums[type + 1] >= day) {
                    // 最快可以吃到那一天
                    if (sums[type] / dailyCap < day) {
                        res[i] = true;
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{7, 4, 5, 3, 8}, new int[][]{{0, 2, 2}, {4, 2, 4}, {2, 13, 1000000000}})
            .expect(new boolean[]{true, false, true});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{5, 2, 6, 4, 1},
            new int[][]{{3, 1, 2}, {4, 10, 3}, {3, 10, 100}, {4, 100, 30}, {1, 3, 1}})
            .expect(new boolean[]{false, true, true, false, false});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
                    46, 5, 47, 48, 43, 34, 15, 26, 11, 25, 41, 47, 15, 25, 16, 50, 32, 42, 32, 21, 36, 34, 50, 45, 46,
                    15, 46, 38, 50, 12, 3, 26, 26, 16, 23, 1, 4, 48, 47, 32, 47, 16, 33, 23, 38, 2, 19, 50, 6, 19, 29,
                    3, 27, 12, 6, 22, 33, 28, 7, 10, 12, 8, 13, 24, 21, 38, 43, 26, 35, 18, 34, 3, 14, 48, 50, 34, 38,
                    4, 50, 26, 5, 35, 11, 2, 35, 9, 11, 31, 36, 20, 21, 37, 18, 34, 34, 10, 21, 8, 5
            },
            new int[][]{{85, 54, 42}})
            .expect(new boolean[]{true});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{7, 11, 5, 3, 8}, new int[][]{{2, 2, 6}, {4, 2, 4}, {2, 13, 1000000000}})
            .expect(new boolean[]{false, false, true});

    private TestDataFile testDataFile = new TestDataFile();

    // 10万种糖果, 10万个查询
    @TestData
    public DataExpectation overflow = DataExpectation.createWith(
            TestDataFileHelper.read(testDataFile, 1, int[].class),
            TestDataFileHelper.read(testDataFile, 2, int[][].class))
            .expect(TestDataFileHelper.read(testDataFile, 3, boolean[].class));

}
