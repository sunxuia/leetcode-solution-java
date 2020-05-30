package q800;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/cracking-the-safe/
 *
 * There is a box protected by a password. The password is a sequence of n digits where each digit can be one of the
 * first k digits 0, 1, ..., k-1.
 *
 * While entering a password, the last n digits entered will automatically be matched against the correct password.
 *
 * For example, assuming the correct password is "345", if you type "012345", the box will open because the correct
 * password matches the suffix of the entered password.
 *
 * Return any password of minimum length that is guaranteed to open the box at some point of entering it.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 1, k = 2
 * Output: "01"
 * Note: "10" will be accepted too.
 *
 * Example 2:
 *
 * Input: n = 2, k = 2
 * Output: "00110"
 * Note: "01100", "10011", "11001" will be accepted too.
 *
 *
 *
 * Note:
 *
 * n will be in the range [1, 4].
 * k will be in the range [1, 10].
 * k^n will be at most 4096.
 */
@RunWith(LeetCodeRunner.class)
public class Q753_CrackingTheSafe {


    // https://www.cnblogs.com/grandyang/p/8452361.html
    @Answer
    public String crackSafe(int n, int k) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append('0');
        }
        Set<String> visited = new HashSet<>();
        visited.add(sb.toString());
        for (int i = 0; i < Math.pow(k, n); i++) {
            // 取后 n-1 位, 加上一位新的数字
            String pre = sb.substring(sb.length() - n + 1);
            // 新的数字现在还不存在, 则可以加入
            for (int j = k - 1; j >= 0; j--) {
                if (visited.add(pre + j)) {
                    sb.append(j);
                    break;
                }
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.createWith(1, 2).expect("01");

    @TestData
    public DataExpectation exmaple2 = DataExpectation.createWith(2, 2).expect("00110");

}
