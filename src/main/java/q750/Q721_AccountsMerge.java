package q750;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/accounts-merge/
 *
 * Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a
 * name, and the rest of the elements are emails representing emails of the account.
 *
 * Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some
 * email that is common to both accounts. Note that even if two accounts have the same name, they may belong to
 * different people as people could have the same name. A person can have any number of accounts initially, but all
 * of their accounts definitely have the same name.
 *
 * After merging the accounts, return the accounts in the following format: the first element of each account is the
 * name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
 *
 * Example 1:
 *
 * Input:
 * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John",
 * "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
 * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail
 * .com"], ["Mary", "mary@mail.com"]]
 * Explanation:
 * The first and third John's are the same person as they have the common email "johnsmith@mail.com".
 * The second John and Mary are different people as none of their email addresses are used by other accounts.
 * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John',
 * 'johnnybravo@mail.com'],
 * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
 *
 * Note:
 * The length of accounts will be in the range [1, 1000].
 * The length of accounts[i] will be in the range [1, 10].
 * The length of accounts[i][j] will be in the range [1, 30].
 */
@RunWith(LeetCodeRunner.class)
public class Q721_AccountsMerge {

    /**
     * union find 找出相同的账号下标即可
     */
    @Answer
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        final int n = accounts.size();
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        // union 相同的账号
        Map<String, Integer> mailAccounts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);
            for (int j = 1; j < account.size(); j++) {
                String mail = account.get(j);
                if (mailAccounts.containsKey(mail)) {
                    union(parents, i, mailAccounts.get(mail));
                } else {
                    mailAccounts.put(mail, i);
                }
            }
        }

        // 找出合并后的账号和对应的邮箱
        Map<Integer, List<String>> newAccounts = new HashMap<>();
        for (Entry<String, Integer> entry : mailAccounts.entrySet()) {
            int idx = find(parents, entry.getValue());
            newAccounts.computeIfAbsent(idx, k -> new ArrayList<>())
                    .add(entry.getKey());
        }

        // 拼装结果
        List<List<String>> res = new ArrayList<>();
        for (Entry<Integer, List<String>> entry : newAccounts.entrySet()) {
            List<String> account = entry.getValue();
            account.sort(String::compareTo);
            account.add(0, accounts.get(entry.getKey()).get(0));
            res.add(account);
        }
        return res;
    }

    private void union(int[] parents, int i, int j) {
        int pi = find(parents, i);
        int pj = find(parents, j);
        parents[pi] = pj;
    }

    private int find(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = find(parents, parents[i]);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(Arrays.asList(
                    Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"),
                    Arrays.asList("John", "johnnybravo@mail.com"),
                    Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                    Arrays.asList("Mary", "mary@mail.com")
            )).expect(Arrays.asList(
                    Arrays.asList("John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"),
                    Arrays.asList("John", "johnnybravo@mail.com"),
                    Arrays.asList("Mary", "mary@mail.com")
            )).unorderResult("")
            .build();

}
