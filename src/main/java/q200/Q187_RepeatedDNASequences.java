package q200;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.asserthelper.TestDataFileHelper;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/repeated-dna-sequences/
 *
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When
 * studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
 *
 * Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.
 *
 * Example:
 *
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 *
 * Output: ["AAAAACCCCC", "CCCCCAAAAA"]
 */
@RunWith(LeetCodeRunner.class)
public class Q187_RepeatedDNASequences {

    /**
     * 这题如果采用子字符串比较会产生超时问题, 所以这里利用数字的位数将每10 位DNA 编码保存到int 类型中,
     * 然后比较int 数字的出现次数, 来判断是否有重复.
     */
    @Answer
    public List<String> findRepeatedDnaSequences(String s) {
        final int len = s.length();
        int[] chain = new int[len];
        for (int i = 0, mask = 0; i < len; i++) {
            chain[i] = mask = (mask * 4 + maskValue(s, i)) & 0xfffff;
        }
        Set<Integer> numSet = new HashSet<>();
        Set<String> resSet = new HashSet<>();
        for (int i = 9; i < len; i++) {
            if (!numSet.add(chain[i])) {
                resSet.add(s.substring(i - 9, i + 1));
            }
        }
        return new ArrayList<>(resSet);
    }

    private int maskValue(String s, int index) {
        switch (s.charAt(index)) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            default:
                return 3;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT")
            .expect(new String[]{"AAAAACCCCC", "CCCCCAAAAA"})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.create("AAAAAAAAAAA").expect(new String[]{"AAAAAAAAAA"});

    @TestData
    public DataExpectation normal2 = DataExpectation.create("AAAAAAAAAAAA").expect(new String[]{"AAAAAAAAAA"});

    // 10 万个字符
    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readString("Q187_LongTestData")).expect(new String[0]);

}
