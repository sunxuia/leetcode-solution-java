package q1500;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1487. Making File Names Unique
 * https://leetcode.com/problems/making-file-names-unique/
 *
 * Given an array of strings names of size n. You will create n folders in your file system such that, at the ith
 * minute, you will create a folder with the name names[i].
 *
 * Since two files cannot have the same name, if you enter a folder name which is previously used, the system will have
 * a suffix addition to its name in the form of (k), where, k is the smallest positive integer such that the obtained
 * name remains unique.
 *
 * Return an array of strings of length n where ans[i] is the actual name the system will assign to the ith folder when
 * you create it.
 *
 * Example 1:
 *
 * Input: names = ["pes","fifa","gta","pes(2019)"]
 * Output: ["pes","fifa","gta","pes(2019)"]
 * Explanation: Let's see how the file system creates folder names:
 * "pes" --> not assigned before, remains "pes"
 * "fifa" --> not assigned before, remains "fifa"
 * "gta" --> not assigned before, remains "gta"
 * "pes(2019)" --> not assigned before, remains "pes(2019)"
 *
 * Example 2:
 *
 * Input: names = ["gta","gta(1)","gta","avalon"]
 * Output: ["gta","gta(1)","gta(2)","avalon"]
 * Explanation: Let's see how the file system creates folder names:
 * "gta" --> not assigned before, remains "gta"
 * "gta(1)" --> not assigned before, remains "gta(1)"
 * "gta" --> the name is reserved, system adds (k), since "gta(1)" is also reserved, systems put k = 2. it becomes
 * "gta(2)"
 * "avalon" --> not assigned before, remains "avalon"
 *
 * Example 3:
 *
 * Input: names = ["onepiece","onepiece(1)","onepiece(2)","onepiece(3)","onepiece"]
 * Output: ["onepiece","onepiece(1)","onepiece(2)","onepiece(3)","onepiece(4)"]
 * Explanation: When the last folder is created, the smallest positive valid k is 4, and it becomes "onepiece(4)".
 *
 * Example 4:
 *
 * Input: names = ["wano","wano","wano","wano"]
 * Output: ["wano","wano(1)","wano(2)","wano(3)"]
 * Explanation: Just increase the value of k each time you create folder "wano".
 *
 * Example 5:
 *
 * Input: names = ["kaido","kaido(1)","kaido","kaido(1)"]
 * Output: ["kaido","kaido(1)","kaido(2)","kaido(1)(1)"]
 * Explanation: Please note that system adds the suffix (k) to current name even it contained the same suffix before.
 *
 * Constraints:
 *
 * 1 <= names.length <= 5 * 10^4
 * 1 <= names[i].length <= 20
 * names[i] consists of lower case English letters, digits and/or round brackets.
 */
@RunWith(LeetCodeRunner.class)
public class Q1487_MakingFileNamesUnique {

    /**
     * 很无聊的题目.
     */
    @Answer
    public String[] getFolderNames(String[] names) {
        final int n = names.length;
        Map<String, Set<Integer>> map = new HashMap<>();
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            String name = names[i];
            if (map.containsKey(name)) {
                int no = 0;
                Set<Integer> nos = map.get(name);
                while (nos.contains(no)) {
                    no++;
                }
                if (no > 0) {
                    name = name + "(" + no + ")";
                }
                nos.add(no);
            }
            String[] parts = split(name);
            if (parts[1] != null) {
                int subNo = Integer.parseInt(parts[1]);
                if (subNo > 0) {
                    map.computeIfAbsent(parts[0], k -> new HashSet<>()).add(subNo);
                }
            }
            map.computeIfAbsent(name, k -> new HashSet<>()).add(0);
            res[i] = name;
        }
        return res;
    }

    private String[] split(String str) {
        if (str.endsWith(")")) {
            for (int i = str.length() - 1; i >= 0; i--) {
                if (str.charAt(i) == '(') {
                    return new String[]{
                            str.substring(0, i),
                            str.substring(i + 1, str.length() - 1)
                    };
                }
            }
        }
        return new String[]{str, null};
    }

    /**
     * LeetCode  上最快的解法.
     */
    @Answer
    public String[] getFolderNames2(String[] names) {
        final int n = names.length;
        String[] res = new String[n];
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            res[i] = getUnique(map, names[i]);
        }
        return res;
    }

    private static String getUnique(Map<String, Integer> map, String name) {
        if (map.containsKey(name)) {
            String origName = name;
            int no = map.get(name);
            do {
                name = origName + "(" + no + ")";
                no++;
            } while (map.containsKey(name));
            map.put(origName, no);
        }
        map.put(name, 1);
        return name;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"pes", "fifa", "gta", "pes(2019)"})
            .expect(new String[]{"pes", "fifa", "gta", "pes(2019)"});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"gta", "gta(1)", "gta", "avalon"})
            .expect(new String[]{"gta", "gta(1)", "gta(2)", "avalon"});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new String[]{"onepiece", "onepiece(1)", "onepiece(2)", "onepiece(3)", "onepiece"})
            .expect(new String[]{"onepiece", "onepiece(1)", "onepiece(2)", "onepiece(3)", "onepiece(4)"});

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new String[]{"wano", "wano", "wano", "wano"})
            .expect(new String[]{"wano", "wano(1)", "wano(2)", "wano(3)"});

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new String[]{"kaido", "kaido(1)", "kaido", "kaido(1)"})
            .expect(new String[]{"kaido", "kaido(1)", "kaido(2)", "kaido(1)(1)"});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new String[]{"kaido", "kaido(1)", "kaido", "kaido(1)", "kaido(2)"})
            .expect(new String[]{"kaido", "kaido(1)", "kaido(2)", "kaido(1)(1)", "kaido(2)(1)"});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new String[]{"kingston(0)", "kingston", "kingston"})
            .expect(new String[]{"kingston(0)", "kingston", "kingston(1)"});

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, String[].class))
            .expect(TestDataFileHelper.read(testDataFile, 2, String[].class));

}
