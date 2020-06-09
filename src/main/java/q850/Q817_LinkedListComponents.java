package q850;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/linked-list-components/
 *
 * We are given head, the head node of a linked list containing unique integer values.
 *
 * We are also given the list G, a subset of the values in the linked list.
 *
 * Return the number of connected components in G, where two values are connected if they appear consecutively in the
 * linked list.
 *
 * Example 1:
 *
 * Input:
 * head: 0->1->2->3
 * G = [0, 1, 3]
 * Output: 2
 * Explanation:
 * 0 and 1 are connected, so [0, 1] and [3] are the two connected components.
 *
 * Example 2:
 *
 * Input:
 * head: 0->1->2->3->4
 * G = [0, 3, 1, 4]
 * Output: 2
 * Explanation:
 * 0 and 1 are connected, 3 and 4 are connected, so [0, 1] and [3, 4] are the two connected components.
 *
 * Note:
 *
 * If N is the length of the linked list given by head, 1 <= N <= 10000.
 * The value of each node in the linked list will be in the range [0, N - 1].
 * 1 <= G.length <= 10000.
 * G is a subset of all values in the linked list.
 */
@RunWith(LeetCodeRunner.class)
public class Q817_LinkedListComponents {

    @Answer
    public int numComponents(ListNode head, int[] G) {
        Set<Integer> set = Arrays.stream(G).boxed().collect(Collectors.toSet());
        int res = 0;
        boolean inG = false;
        while (head != null) {
            if (set.contains(head.val)) {
                if (!inG) {
                    res++;
                    inG = true;
                }
            } else {
                inG = false;
            }
            head = head.next;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(ListNode.createListNode(0, 1, 2, 3), new int[]{0, 1, 3}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(ListNode.createListNode(0, 1, 2, 3, 4), new int[]{0, 3, 1, 4}).expect(2);

}
