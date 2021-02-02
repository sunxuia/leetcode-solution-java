package q1700;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Easy] 1656. Design an Ordered Stream
 * https://leetcode.com/problems/design-an-ordered-stream/
 *
 * There is a stream of n (id, value) pairs arriving in an arbitrary order, where id is an integer between 1 and n and
 * value is a string. No two pairs have the same id.
 *
 * Design a stream that returns the values in increasing order of their IDs by returning a chunk (list) of values after
 * each insertion. The concatenation of all the chunks should result in a list of the sorted values.
 *
 * Implement the OrderedStream class:
 *
 * OrderedStream(int n) Constructs the stream to take n values.
 * String[] insert(int id, String value) Inserts the pair (id, value) into the stream, then returns the largest possible
 * chunk of currently inserted values that appear next in the order.
 *
 * Example:
 * <img src="./Q1656_PIC.gif">
 * Input
 * ["OrderedStream", "insert", "insert", "insert", "insert", "insert"]
 * [[5], [3, "ccccc"], [1, "aaaaa"], [2, "bbbbb"], [5, "eeeee"], [4, "ddddd"]]
 * Output
 * [null, [], ["aaaaa"], ["bbbbb", "ccccc"], [], ["ddddd", "eeeee"]]
 *
 * Explanation
 * // Note that the values ordered by ID is ["aaaaa", "bbbbb", "ccccc", "ddddd", "eeeee"].
 * OrderedStream os = new OrderedStream(5);
 * os.insert(3, "ccccc"); // Inserts (3, "ccccc"), returns [].
 * os.insert(1, "aaaaa"); // Inserts (1, "aaaaa"), returns ["aaaaa"].
 * os.insert(2, "bbbbb"); // Inserts (2, "bbbbb"), returns ["bbbbb", "ccccc"].
 * os.insert(5, "eeeee"); // Inserts (5, "eeeee"), returns [].
 * os.insert(4, "ddddd"); // Inserts (4, "ddddd"), returns ["ddddd", "eeeee"].
 * // Concatentating all the chunks returned:
 * // [] + ["aaaaa"] + ["bbbbb", "ccccc"] + [] + ["ddddd", "eeeee"] = ["aaaaa", "bbbbb", "ccccc", "ddddd", "eeeee"]
 * // The resulting order is the same as the order above.
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 * 1 <= id <= n
 * value.length == 5
 * value consists only of lowercase letters.
 * Each call to insert will have a unique id.
 * Exactly n calls will be made to insert.
 */
public class Q1656_DesignAnOrderedStream {

    private static class OrderedStream {

        String[] arr;

        int ptr = 1;

        public OrderedStream(int n) {
            arr = new String[n];
        }

        public List<String> insert(int id, String value) {
            final int n = arr.length;
            arr[id % n] = value;
            if (arr[ptr % n] == null) {
                return List.of();
            }
            List<String> res = new ArrayList<>();
            while (arr[ptr % n] != null) {
                res.add(arr[ptr % n]);
                arr[ptr % n] = null;
                ptr++;
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
        OrderedStream os = new OrderedStream(5);
        AssertUtils.assertEquals(List.of(), os.insert(3, "ccccc"));
        AssertUtils.assertEquals(List.of("aaaaa"), os.insert(1, "aaaaa"));
        AssertUtils.assertEquals(List.of("bbbbb", "ccccc"), os.insert(2, "bbbbb"));
        AssertUtils.assertEquals(List.of(), os.insert(5, "eeeee"));
        AssertUtils.assertEquals(List.of("ddddd", "eeeee"), os.insert(4, "ddddd"));
    }

}
