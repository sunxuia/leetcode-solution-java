package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1108. Defanging an IP Address
 * https://leetcode.com/problems/defanging-an-ip-address/
 *
 * Given a valid (IPv4) IP address, return a defanged version of that IP address.
 *
 * A defanged IP address replaces every period "." with "[.]".
 *
 * Example 1:
 * Input: address = "1.1.1.1"
 * Output: "1[.]1[.]1[.]1"
 * Example 2:
 * Input: address = "255.100.50.0"
 * Output: "255[.]100[.]50[.]0"
 *
 * Constraints:
 *
 * The given address is a valid IPv4 address.
 */
@RunWith(LeetCodeRunner.class)
public class Q1108_DefangingAnIpAddress {

    @Answer
    public String defangIPaddr(String address) {
        return address.replaceAll("\\.", "[.]");
    }

    @Answer
    public String defangIPaddr2(String address) {
        char[] acs = address.toCharArray();
        char[] resArr = new char[acs.length + 6];
        for (int i = 0, j = 0; i < acs.length; i++) {
            if (acs[i] == '.') {
                resArr[j++] = '[';
                resArr[j++] = '.';
                resArr[j++] = ']';
            } else {
                resArr[j++] = acs[i];
            }
        }
        return new String(resArr);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1.1.1.1").expect("1[.]1[.]1[.]1");

    @TestData
    public DataExpectation example2 = DataExpectation.create("255.100.50.0").expect("255[.]100[.]50[.]0");

}
