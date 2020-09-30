package q1150;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1125. Smallest Sufficient Team
 * https://leetcode.com/problems/smallest-sufficient-team/
 *
 * In a project, you have a list of required skills req_skills, and a list of people.  The i-th person people[i]
 * contains a list of skills that person has.
 *
 * Consider a sufficient team: a set of people such that for every required skill in req_skills, there is at least one
 * person in the team who has that skill.  We can represent these teams by the index of each person: for example, team =
 * [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
 *
 * Return any sufficient team of the smallest possible size, represented by the index of each person.
 *
 * You may return the answer in any order.  It is guaranteed an answer exists.
 *
 * Example 1:
 * Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
 * Output: [0,2]
 * Example 2:
 * Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"], people =
 * [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],
 * ["csharp","math"],["aws","java"]]
 * Output: [1,2]
 *
 * Constraints:
 *
 * 1 <= req_skills.length <= 16
 * 1 <= people.length <= 60
 * 1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
 * Elements of req_skills and people[i] are (respectively) distinct.
 * req_skills[i][j], people[i][j][k] are lowercase English letters.
 * Every skill in people[i] is a skill in req_skills.
 * It is guaranteed a sufficient team exists.
 */
@RunWith(LeetCodeRunner.class)
public class Q1125_SmallestSufficientTeam {

    @Answer
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        final int m = req_skills.length, n = people.size();
        Map<String, Integer> skillMap = new HashMap<>(m);
        for (int i = 0; i < m; i++) {
            skillMap.put(req_skills[i], i);
        }
        int[] skills = new int[n];
        for (int i = 0; i < n; i++) {
            int mask = 0;
            for (String skill : people.get(i)) {
                mask |= 1 << skillMap.get(skill);
            }
            skills[i] = mask;
        }

        long mask = dfs(new long[n][65536], 0, skills, 0L, (1 << m) - 1);
        return maskToArray(mask);
    }

    private long dfs(long[][] cache, int i, int[] skills, long peopleMask, int expectSkills) {
        if (expectSkills == 0) {
            return 0;
        }
        if (i == skills.length) {
            return Long.MAX_VALUE;
        }
        if (cache[i][expectSkills] == 0) {
            long a = dfs(cache, i + 1, skills, peopleMask, expectSkills);
            long b = (1L << i) | dfs(cache, i + 1, skills, peopleMask | (1 << i), ~skills[i] & expectSkills);
            cache[i][expectSkills] = count(a) < count(b) ? a : b;
        }
        return cache[i][expectSkills];
    }

    private int count(long mask) {
        int res = 0;
        while (mask > 0) {
            res += mask & 1;
            mask >>= 1;
        }
        return res;
    }

    private int[] maskToArray(long mask) {
        int[] res = new int[count(mask)];
        int next = 0;
        for (int i = 0; mask > 0; i++, mask >>= 1) {
            if ((mask & 1) == 1) {
                res[next++] = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"java", "nodejs", "reactjs"})
            .addArgument(Arrays.asList(
                    Arrays.asList("java"),
                    Arrays.asList("nodejs"),
                    Arrays.asList("nodejs", "reactjs")))
            .expect(new int[]{0, 2})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"algorithms", "math", "java", "reactjs", "csharp", "aws"})
            .addArgument(Arrays.asList(
                    Arrays.asList("algorithms", "math", "java"),
                    Arrays.asList("algorithms", "math", "reactjs"),
                    Arrays.asList("java", "csharp", "aws"),
                    Arrays.asList("reactjs", "csharp"),
                    Arrays.asList("csharp", "math"),
                    Arrays.asList("aws", "java")))
            .expect(new int[]{1, 2})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new String[]{"mwobudvo", "goczubcwnfze", "yspbsez", "pf", "ey", "hkq"})
            .addArgument(Arrays.asList(
                    Arrays.asList()/* 0 */, Arrays.asList("mwobudvo"), Arrays.asList("hkq"), Arrays.asList("pf"),
                    Arrays.asList("pf"), Arrays.asList("mwobudvo", "pf")/* 5 */, Arrays.asList(),
                    Arrays.asList("yspbsez"), Arrays.asList(), Arrays.asList("hkq"), Arrays.asList()/* 10 */,
                    Arrays.asList(), Arrays.asList("goczubcwnfze", "pf", "hkq"), Arrays.asList("goczubcwnfze"),
                    Arrays.asList("hkq"), Arrays.asList("mwobudvo")/* 15 */, Arrays.asList(),
                    Arrays.asList("mwobudvo", "pf"), Arrays.asList("pf", "ey"), Arrays.asList("mwobudvo"),
                    Arrays.asList("hkq")/* 20 */, Arrays.asList(), Arrays.asList("pf"),
                    Arrays.asList("mwobudvo", "yspbsez"), Arrays.asList("mwobudvo", "goczubcwnfze"),
                    Arrays.asList("goczubcwnfze", "pf")/* 25 */, Arrays.asList("goczubcwnfze"),
                    Arrays.asList("goczubcwnfze"), Arrays.asList("mwobudvo"),
                    Arrays.asList("mwobudvo", "goczubcwnfze"), Arrays.asList()/* 30 */, Arrays.asList("goczubcwnfze"),
                    Arrays.asList(), Arrays.asList("goczubcwnfze"), Arrays.asList("mwobudvo"), Arrays.asList()/* 35 */,
                    Arrays.asList("hkq"), Arrays.asList("yspbsez"), Arrays.asList("mwobudvo"),
                    Arrays.asList("goczubcwnfze", "ey")/* 39 */))
            .expect(new int[]{12, 23, 39})
            .orExpect(new int[]{12, 18, 23})
            .unorderResult()
            .build();

}
