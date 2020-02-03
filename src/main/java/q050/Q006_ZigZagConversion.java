package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to
 * display this pattern in a fixed font for better legibility)
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 *
 * string convert(string s, int numRows);
 *
 * Example 1:
 *
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 *
 * Example 2:
 *
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 *
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * 解析:
 * 这道题的目的是将字符串(比如这里是0,1,2,3,4,5,6,...)按照给定的行数(比如下面是4行),
 * 按照锯齿形排列:
 * 0        6        12
 * 1     5  7     11 13
 * 2  4     8  10    14
 * 3        9
 * 然后再按照每行的顺序输出结果(1,7,13,2,5,8,12,14,3...) 这样.
 */
@RunWith(LeetCodeRunner.class)
public class Q006_ZigZagConversion {

    /**
     * 就是计算一下间隔, 注意一下边界条件.
     */
    @Answer
    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        final int sLength = s.length();
        final int interval = 2 * numRows - 2;
        StringBuilder sb = new StringBuilder(sLength);
        for (int i = 0; i < numRows; i++) {
            int j = i;
            if (i == 0 || i == numRows - 1) {
                // 第一行和最后一行没有有中间斜坡的值
                for (; j < sLength; j += interval) {
                    sb.append(s.charAt(j));
                }
            } else {
                // 有中间的斜坡的值
                for (int jMiddle = interval - j;
                        j < sLength && jMiddle < sLength;
                        j += interval, jMiddle += interval) {
                    sb.append(s.charAt(j)).append(s.charAt(jMiddle));
                }
                if (j < sLength) {
                    sb.append(s.charAt(j));
                }
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("PAYPALISHIRING")
            .addArgument(3)
            .expect("PAHNAPLSIIGYIR")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("PAYPALISHIRING")
            .addArgument(4)
            .expect("PINALSIGYAHRPI")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("123456789")
            .addArgument(1)
            .expect("123456789")
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .addArgument(2)
            .expect("")
            .build();
}
