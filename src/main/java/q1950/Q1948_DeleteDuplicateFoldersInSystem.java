package q1950;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1948. Delete Duplicate Folders in System
 * https://leetcode.com/problems/delete-duplicate-folders-in-system/
 *
 * Due to a bug, there are many duplicate folders in a file system. You are given a 2D array paths, where paths[i] is an
 * array representing an absolute path to the ith folder in the file system.
 *
 * For example, ["one", "two", "three"] represents the path "/one/two/three".
 *
 * Two folders (not necessarily on the same level) are identical if they contain the same non-empty set of identical
 * subfolders and underlying subfolder structure. The folders do not need to be at the root level to be identical. If
 * two or more folders are identical, then mark the folders as well as all their subfolders.
 *
 * For example, folders "/a" and "/b" in the file structure below are identical. They (as well as their subfolders)
 * should all be marked:
 *
 * > 		/a
 * > 		/a/x
 * > 		/a/x/y
 * > 		/a/z
 * > 		/b
 * > 		/b/x
 * > 		/b/x/y
 * > 		/b/z
 *
 *
 * However, if the file structure also included the path "/b/w", then the folders "/a" and "/b" would not be identical.
 * Note that "/a/x" and "/b/x" would still be considered identical even with the added folder.
 *
 * Once all the identical folders and their subfolders have been marked, the file system will delete all of them. The
 * file system only runs the deletion once, so any folders that become identical after the initial deletion are not
 * deleted.
 *
 * Return the 2D array ans containing the paths of the remaining folders after deleting all the marked folders. The
 * paths may be returned in any order.
 *
 * Example 1:
 * (图Q1948_PIC1.jpg)
 * Input: paths = [["a"],["c"],["d"],["a","b"],["c","b"],["d","a"]]
 * Output: [["d"],["d","a"]]
 * Explanation: The file structure is as shown.
 * Folders "/a" and "/c" (and their subfolders) are marked for deletion because they both contain an empty
 * folder named "b".
 *
 * Example 2:
 * (图Q1948_PIC2.jpg)
 * Input: paths = [["a"],["c"],["a","b"],["c","b"],["a","b","x"],["a","b","x","y"],["w"],["w","y"]]
 * Output: [["c"],["c","b"],["a"],["a","b"]]
 * Explanation: The file structure is as shown.
 * Folders "/a/b/x" and "/w" (and their subfolders) are marked for deletion because they both contain an empty folder
 * named "y".
 * Note that folders "/a" and "/c" are identical after the deletion, but they are not deleted because they were not
 * marked beforehand.
 *
 * Example 3:
 * (图Q1948_PIC3.jpg)
 * Input: paths = [["a","b"],["c","d"],["c"],["a"]]
 * Output: [["c"],["c","d"],["a"],["a","b"]]
 * Explanation: All folders are unique in the file system.
 * Note that the returned array can be in a different order as the order does not matter.
 *
 * Constraints:
 *
 * 1 <= paths.length <= 2 * 10^4
 * 1 <= paths[i].length <= 500
 * 1 <= paths[i][j].length <= 10
 * 1 <= sum(paths[i][j].length) <= 2 * 10^5
 * path[i][j] consists of lowercase English letters.
 * No two paths lead to the same folder.
 * For any folder not at the root level, its parent folder will also be in the input.
 */
@RunWith(LeetCodeRunner.class)
public class Q1948_DeleteDuplicateFoldersInSystem {

    @Answer
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        Dir root = new Dir();
        root.path = Collections.singletonList("/");
        for (List<String> path : paths) {
            Dir dir = root;
            for (String dirName : path) {
                dir = dir.subDirs.computeIfAbsent(dirName, k -> new Dir());
            }
            dir.path = path;
        }

        root.initialHashCode();
        root.clear(new HashMap<>());

        List<List<String>> res = new ArrayList<>();
        Queue<Dir> queue = new LinkedList<>(root.subDirs.values());
        while (!queue.isEmpty()) {
            Dir dir = queue.poll();
            if (!dir.remove) {
                res.add(dir.path);
                queue.addAll(dir.subDirs.values());
            }
        }
        return res;
    }

    private static class Dir {

        boolean remove;

        int hash;

        List<String> path;

        Map<String, Dir> subDirs = new HashMap<>();

        void initialHashCode() {
            if (subDirs.isEmpty()) {
                String name = path.get(path.size() - 1);
                hash = name.hashCode();
            } else {
                List<String> names = new ArrayList<>(subDirs.keySet());
                names.sort(null);
                // 这种hash 算法能通过oj
                for (String name : names) {
                    Dir dir = subDirs.get(name);
                    dir.initialHashCode();
                    hash = hash * 7 * 7 + dir.hash * 7 + name.hashCode();
                }
                for (String name : names) {
                    hash = hash * 17 + name.hashCode();
                }
            }
        }

        void clear(HashMap<Integer, Dir> remove) {
            for (Dir subDir : subDirs.values()) {
                if (!subDir.subDirs.isEmpty()) {
                    Dir exist = remove.get(subDir.hash);
                    if (exist == null) {
                        remove.put(subDir.hash, subDir);
                        subDir.clear(remove);
                    } else {
                        exist.remove = true;
                        subDir.remove = true;
                    }
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(
                    List.of(List.of("a"), List.of("c"), List.of("d"),
                            List.of("a", "b"), List.of("c", "b"), List.of("d", "a")))
            .expect(List.of(List.of("d"), List.of("d", "a")))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.create(
                    List.of(List.of("a"), List.of("c"), List.of("a", "b"), List.of("c", "b"), List.of("a", "b", "x"),
                            List.of("a", "b", "x", "y"), List.of("w"), List.of("w", "y")))
            .expect(List.of(List.of("c"), List.of("c", "b"), List.of("a"), List.of("a", "b")))
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation.create(
                    List.of(List.of("a", "b"), List.of("c", "d"), List.of("c"), List.of("a")))
            .expect(List.of(List.of("c"), List.of("c", "d"), List.of("a"), List.of("a", "b")))
            .unOrder();

    @TestData
    public DataExpectation normal1 = DataExpectation.create(
                    List.of(List.of("a"), List.of("a", "x"), List.of("a", "x", "y"), List.of("a", "z"), List.of("b"),
                            List.of("b", "x"), List.of("b", "x", "y"), List.of("b", "z"), List.of("b", "w")))
            .expect(List.of(List.of("a"), List.of("b"), List.of("a", "z"), List.of("b", "z"), List.of("b", "w")))
            .unOrder();

    @TestData
    public DataExpectation normal2 = DataExpectation.create(
                    List.of(List.of("a"), List.of("b"), List.of("a", "b"), List.of("a", "d"), List.of("b", "a"),
                            List.of("b", "e")))
            .expect(List.of(List.of("a"), List.of("b"), List.of("a", "b"), List.of("a", "d"), List.of("b", "a"),
                    List.of("b", "e")))
            .unOrder();

    @TestData
    public DataExpectation normal3() {
        return DataExpectation.create(List.of(
                        List.of("a"),
                        List.of("a", "a"),
                        List.of("a", "c"),
                        List.of("a", "c", "a"),
                        List.of("a", "e"),
                        List.of("a", "e", "b"),
                        List.of("a", "e", "e"),
                        List.of("b"),
                        List.of("b", "a"),
                        List.of("b", "b"),
                        List.of("c"),
                        List.of("d"),
                        List.of("d", "a"),
                        List.of("d", "a", "a"),
                        List.of("d", "a", "a", "b"),
                        List.of("d", "a", "b"),
                        List.of("d", "a", "b", "a"),
                        List.of("d", "a", "b", "a", "c"),
                        List.of("d", "a", "b", "a", "d"),
                        List.of("d", "a", "b", "a", "d", "a"),
                        List.of("d", "a", "b", "a", "e"),
                        List.of("d", "a", "b", "a", "e", "a"),
                        List.of("d", "a", "b", "b"),
                        List.of("d", "a", "b", "d"),
                        List.of("d", "a", "c"),
                        List.of("d", "a", "c", "a"),
                        List.of("d", "a", "c", "a", "d"),
                        List.of("d", "a", "c", "c"),
                        List.of("d", "a", "c", "c", "a"),
                        List.of("d", "a", "c", "c", "c"),
                        List.of("d", "a", "c", "c", "c", "b"),
                        List.of("d", "a", "c", "c", "e"),
                        List.of("d", "a", "c", "c", "e", "a"),
                        List.of("d", "a", "c", "c", "e", "e"),
                        List.of("d", "a", "d"),
                        List.of("d", "a", "d", "c"),
                        List.of("d", "a", "e"),
                        List.of("d", "b"),
                        List.of("e")))
                .expect(List.of(
                        List.of("a"),
                        List.of("a", "a"),
                        List.of("a", "e"),
                        List.of("a", "e", "b"),
                        List.of("a", "e", "e"),
                        List.of("b"),
                        List.of("b", "a"),
                        List.of("b", "b"),
                        List.of("c"),
                        List.of("d"),
                        List.of("d", "a"),
                        List.of("d", "a", "b"),
                        List.of("d", "a", "b", "a"),
                        List.of("d", "a", "b", "a", "c"),
                        List.of("d", "a", "b", "b"),
                        List.of("d", "a", "b", "d"),
                        List.of("d", "a", "c"),
                        List.of("d", "a", "c", "a"),
                        List.of("d", "a", "c", "a", "d"),
                        List.of("d", "a", "c", "c"),
                        List.of("d", "a", "c", "c", "a"),
                        List.of("d", "a", "c", "c", "e"),
                        List.of("d", "a", "c", "c", "e", "a"),
                        List.of("d", "a", "c", "c", "e", "e"),
                        List.of("d", "a", "d"),
                        List.of("d", "a", "d", "c"),
                        List.of("d", "a", "e"),
                        List.of("d", "b"),
                        List.of("e")))
                .unOrder();
    }
}
