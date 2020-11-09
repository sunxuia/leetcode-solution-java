package q1350;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1311. Get Watched Videos by Your Friends
 * https://leetcode.com/problems/get-watched-videos-by-your-friends/
 *
 * There are n people, each person has a unique id between 0 and n-1. Given the arrays watchedVideos and friends, where
 * watchedVideos[i] and friends[i] contain the list of watched videos and the list of friends respectively for the
 * person with id = i.
 *
 * Level 1 of videos are all watched videos by your friends, level 2 of videos are all watched videos by the friends of
 * your friends and so on. In general, the level k of videos are all watched videos by people with the shortest path
 * exactly equal to k with you. Given your id and the level of videos, return the list of videos ordered by their
 * frequencies (increasing). For videos with the same frequency order them alphabetically from least to greatest.
 *
 * Example 1:
 * <img src="./Q1311_PIC1.png">
 * Input: watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 1
 * Output: ["B","C"]
 * Explanation:
 * You have id = 0 (green color in the figure) and your friends are (yellow color in the figure):
 * Person with id = 1 -> watchedVideos = ["C"]
 * Person with id = 2 -> watchedVideos = ["B","C"]
 * The frequencies of watchedVideos by your friends are:
 * B -> 1
 * C -> 2
 *
 * Example 2:
 * <img src="./Q1311_PIC2.png">
 * Input: watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 2
 * Output: ["D"]
 * Explanation:
 * You have id = 0 (green color in the figure) and the only friend of your friends is the person with id = 3 (yellow
 * color in the figure).
 *
 * Constraints:
 *
 * n == watchedVideos.length == friends.length
 * 2 <= n <= 100
 * 1 <= watchedVideos[i].length <= 100
 * 1 <= watchedVideos[i][j].length <= 8
 * 0 <= friends[i].length < n
 * 0 <= friends[i][j] < n
 * 0 <= id < n
 * 1 <= level < n
 * if friends[i] contains j, then friends[j] contains i
 *
 * 题解: 很无聊的题目描述, 中文表述参见 https://leetcode-cn.com/problems/get-watched-videos-by-your-friends/
 */
@RunWith(LeetCodeRunner.class)
public class Q1311_GetWatchedVideosByYourFriends {

    @Answer
    public List<String> watchedVideosByFriends(
            List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        final int n = watchedVideos.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(id);
        visited[id] = true;
        for (int i = 0; i < level; i++) {
            for (int len = queue.size(); len > 0; len--) {
                int person = queue.poll();
                for (int friend : friends[person]) {
                    if (!visited[friend]) {
                        visited[friend] = true;
                        queue.offer(friend);
                    }
                }
            }
        }

        Map<String, Integer> counts = new HashMap<>();
        for (int person : queue) {
            for (String video : watchedVideos.get(person)) {
                counts.put(video, counts.getOrDefault(video, 0) + 1);
            }
        }
        return counts.entrySet().stream()
                .sorted((a, b) -> a.getValue().equals(b.getValue())
                        ? a.getKey().compareTo(b.getKey())
                        : a.getValue().compareTo(b.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(List.of(List.of("A", "B"), List.of("C"), List.of("B", "C"), List.of("D")),
                    new int[][]{{1, 2}, {0, 3}, {0, 3}, {1, 2}}, 0, 1)
            .expect(List.of("B", "C"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(List.of(List.of("A", "B"), List.of("C"), List.of("B", "C"), List.of("D")),
                    new int[][]{{1, 2}, {0, 3}, {0, 3}, {1, 2}}, 0, 2)
            .expect(List.of("D"));

}
