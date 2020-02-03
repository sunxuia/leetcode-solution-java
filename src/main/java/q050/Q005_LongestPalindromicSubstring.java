package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 *
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example 2:
 *
 * Input: "cbbd"
 * Output: "bb"
 */
@RunWith(LeetCodeRunner.class)
public class Q005_LongestPalindromicSubstring {

    /**
     * 最长回文首先想到的就是两层for 循环对比, 时间复杂度 O(n^2)
     */
    @Answer
    public String twoNestingCycle(String s) {
        final int length = s.length();
        String res = "";
        for (int i = 0; i < length; i++) {
            // "aba" 形式的回文
            int j = 1;
            while (i - j >= 0
                    && i + j < length
                    && s.charAt(i - j) == s.charAt(i + j)) {
                j++;
            }
            j--;
            String t = s.substring(i - j, i + j + 1);
            if (t.length() > res.length()) {
                res = t;
            }

            // "aa" 形式的回文
            j = 1;
            while (i - j + 1 >= 0
                    && i + j < length
                    && s.charAt(i - j + 1) == s.charAt(i + j)) {
                j++;
            }
            j--;
            t = s.substring(i - j + 1, i + j + 1);
            if (t.length() > res.length()) {
                res = t;
            }
        }
        return res;
    }

    /**
     * 最长回文其实是有特定算法的, 这种算法可以实现O(n) 的时间复杂度.
     * Manacher's Algorithm 马拉车(音译)算法:
     * 第一步: 为字符串进行插值, 解决"aba" 和"aa" 不一致的问题.
     * 第二步: 遍历新的字符串, 初始化每个位置的最长回文长度.
     * |    j           id      i   mx
     * |    |           |       |   |
     * |-----------------------------------------(这条线代表字符串)
     * i: 当前遍历位置.
     * id: 是 Mx 对应的子回文串的中点.
     * mx: 遍历到目前, 子回文串的右端达到最远的位置.
     * j: 以id 为中心, mx - id 为半径的是一个回文字符串, i 如果在这个回文字符串中, 则i 与 j 相对称.
     *
     * 遍历算法:
     * p[i] = i < mx ? (p[2 * id - i] < mx - i ? p[2 * id - i] : mx - i) : 1
     *
     * p 表示以p[id] 为中心的最长回文的长度.
     * i < mx : 表示 i 在 ID 对应子回文串的范围之内. 如果不在, 则初始化id 处的最长回文长度为1.
     * p[2 * id - i] < mx - i : id - (i - id) 表示j 的坐标. 这个表达式表示以j 为中心的最长回文的
     * 最左端是否超过了以id 为中心的最长回文最左端. 即j 的回文是否都在id 的回文中.
     *
     * 如果j 的回文在id 的回文中, 则由于id 回文的对称性i 处的回文和j 处的回文一致.
     * |   mx_L   j_L   j   j_R     id          i            mx
     * |   |      <     |    >      |           |            |
     * |------------------------------------------------------------
     * j_L 和j_R 表示j 处回文的左右界, mx_L 表示id 处回文的左界.
     *
     * 如果j 的回文超过了id 回文的范围:
     * 此时对于j_L ~ mx_L 处的回文, id 回文没有对称性, 但是对mx_L ~ j 处的回文对称性仍然存在,
     * 即以i 为中心m, j - mx_L (= mx - i) 长度的字符串是回文, 所以将i 的回文长度初始化为mx - i.
     * |   j_L mx_L j       j_R     id             i    mx
     * |   <    |   |        >      |              |    |
     * |----------------------------------------------------
     * j_L 和j_R 表示j 处回文的左右界, mx_L 表示id 处回文的左界.
     *
     * 第三步: 以i 为中心, 得出最长回文长度.
     * 第四步: 如果i 处的回文的最右端超过了id 处, 则以i 为新的id, 初始化接下来最长回文长度.
     *
     * 这里因为有初始化的过程, 上一个示例中需要遍历的过程已经由上一步直接初始化了,
     * 每一步所需要做的就是扩充上一步没有遍历到的位置, 所以时间复杂度为O(n)
     */
    @Answer
    public String manacherAlg(String s) {
        // 测试用例中没有null 值.

        // 首尾加入哨兵, 避免之后的遍历出界, 每个字符之间加入"#", 解决"aba" 和"aa" 的判断方式不一致的问题.
        StringBuilder sb = new StringBuilder(s.length() * 2 + 3);
        sb.append("^#");
        for (char c : s.toCharArray()) {
            sb.append(c).append('#');
        }
        sb.append('$');
        String str = sb.toString();

        // p 表示以p[i] 为中心的最长回文字符串的半径.
        int[] p = new int[str.length()];
        int id = 0, mx = 0;
        // max 表示最长回文长度, max = p[maxIndex].
        int max = 1, maxIndex = 1;
        for (int i = 1, length = str.length() - 1; i < length; i++) {
            // 初始化i 处的最长回文半径.
            int iMax = i < mx ? (p[2 * id - i] < mx - i ? p[2 * id - i] : mx - i) : 1;
            // 以i 为中心, 寻找最长回文.
            while (str.charAt(i + iMax) == str.charAt(i - iMax)) {
                iMax++;
            }
            // 如果i 处的回文覆盖右界超过了id 处的回文, 则更新id 为当前位置.
            if (i + iMax > mx) {
                mx = i + iMax;
                id = i;
            }
            // 更新最长回文记录
            if (iMax > max) {
                max = iMax;
                maxIndex = i;
            }
            p[i] = iMax;
        }

        // 获得最长回文, 替换哨兵
        return str.substring(maxIndex - max + 1, maxIndex + max - 1)
                .replaceAll("#", "");
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("abad")
            .expect("bab")
            .orExpect("aba")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("cbbd")
            .expect("bb")
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("abc")
            .expect("a")
            .orExpect("b")
            .orExpect("c")
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("")
            .expect("")
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument("上海自来水来自海上")
            .expect("上海自来水来自海上")
            .build();
}
