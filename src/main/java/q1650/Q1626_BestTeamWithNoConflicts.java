package q1650;

import java.util.Arrays;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1626. Best Team With No Conflicts
 * https://leetcode.com/problems/best-team-with-no-conflicts/
 *
 * You are the manager of a basketball team. For the upcoming tournament, you want to choose the team with the highest
 * overall score. The score of the team is the sum of scores of all the players in the team.
 *
 * However, the basketball team is not allowed to have conflicts. A conflict exists if a younger player has a strictly
 * higher score than an older player. A conflict does not occur between players of the same age.
 *
 * Given two lists, scores and ages, where each scores[i] and ages[i] represents the score and age of the ith player,
 * respectively, return the highest overall score of all possible basketball teams.
 *
 * Example 1:
 *
 * Input: scores = [1,3,5,10,15], ages = [1,2,3,4,5]
 * Output: 34
 * Explanation: You can choose all the players.
 *
 * Example 2:
 *
 * Input: scores = [4,5,6,5], ages = [2,1,2,1]
 * Output: 16
 * Explanation: It is best to choose the last 3 players. Notice that you are allowed to choose multiple people of the
 * same age.
 *
 * Example 3:
 *
 * Input: scores = [1,2,3,5], ages = [8,9,10,1]
 * Output: 6
 * Explanation: It is best to choose the first 3 players.
 *
 * Constraints:
 *
 * 1 <= scores.length, ages.length <= 1000
 * scores.length == ages.length
 * 1 <= scores[i] <= 10^6
 * 1 <= ages[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1626_BestTeamWithNoConflicts {

    @Answer
    public int bestTeamScore(int[] scores, int[] ages) {
        final int n = scores.length;
        Person[] persons = new Person[n];
        for (int i = 0; i < n; i++) {
            persons[i] = new Person();
            persons[i].score = scores[i];
            persons[i].age = ages[i];
        }
        Arrays.sort(persons, (a, b) -> a.score == b.score ?
                a.age - b.age : a.score - b.score);

        int res = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (Person p : persons) {
            int teamScore = p.score;
            Integer floor = map.floorKey(p.age);
            if (floor != null) {
                teamScore += map.get(floor);
            }
            Integer higher = map.higherKey(p.age);
            while (higher != null && map.get(higher) <= teamScore) {
                map.remove(higher);
                higher = map.higherKey(higher);
            }
            map.put(p.age, teamScore);
            res = Math.max(teamScore, res);
        }
        return res;
    }

    private static class Person {

        int score, age;

        @Override
        public String toString() {
            return age + "-" + score;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 3, 5, 10, 15}, new int[]{1, 2, 3, 4, 5})
            .expect(34);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, 5, 6, 5}, new int[]{2, 1, 2, 1})
            .expect(16);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 5}, new int[]{8, 9, 10, 1})
            .expect(6);

}
