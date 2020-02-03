package q100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/restore-ip-addresses/
 *
 * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
 *
 * Example:
 *
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 */
@RunWith(LeetCodeRunner.class)
public class Q093_RestoreIPAddresses {

    @Answer
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        dfs(res, buffer, s, 0, 0, 1);
        dfs(res, buffer, s, 0, 0, 2);
        dfs(res, buffer, s, 0, 0, 3);
        return res;
    }

    private void dfs(List<String> res, StringBuilder buffer, String s, int part, int start, int end) {
        if (end > s.length()
                || part == 3 && end < s.length()
                || s.charAt(start) == '0' && start < end - 1) {
            return;
        }
        String address = s.substring(start, end);
        if (Integer.parseInt(address) > 255) {
            return;
        }
        int bufLen = buffer.length();
        if (bufLen > 0) {
            buffer.append('.');
        }
        buffer.append(address);
        if (part == 3) {
            res.add(buffer.toString());
        } else {
            dfs(res, buffer, s, part + 1, end, end + 1);
            dfs(res, buffer, s, part + 1, end, end + 2);
            dfs(res, buffer, s, part + 1, end, end + 3);
        }
        buffer.setLength(bufLen);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument("25525511135")
            .expect(Arrays.asList("255.255.11.135", "255.255.111.35"))
            .unorderResult()
            .build();

}
