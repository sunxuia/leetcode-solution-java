package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/construct-the-rectangle/
 *
 * For a web developer, it is very important to know how to design a web page's size. So, given a specific
 * rectangular web page’s area, your job by now is to design a rectangular web page, whose length L and width W
 * satisfy the following requirements:
 *
 * 1. The area of the rectangular web page you designed must equal to the given target area.
 *
 * 2. The width W should not be larger than the length L, which means L >= W.
 *
 * 3. The difference between length L and width W should be as small as possible.
 *
 * You need to output the length L and the width W of the web page you designed in sequence.
 *
 * Example:
 *
 * Input: 4
 * Output: [2, 2]
 * Explanation: The target area is 4, and all the possible ways to construct it are [1,4], [2,2], [4,1].
 * But according to requirement 2, [1,4] is illegal; according to requirement 3,  [4,1] is not optimal compared to
 * [2,2]. So the length L is 2, and the width W is 2.
 *
 * Note:
 *
 * The given area won't exceed 10,000,000 and is a positive integer
 * The web page's width and length you designed must be positive integers.
 */
@RunWith(LeetCodeRunner.class)
public class Q492_ConstructTheRectangle {

    // 就是求面积是 area 的矩形, 这个矩形要尽量像一个正方形(长 >= 宽)
    @Answer
    public int[] constructRectangle(int area) {
        double sqrt = Math.sqrt(area);
        long length = (long) Math.ceil(sqrt);
        long width = (long) Math.floor(sqrt);
        while (length * width != area) {
            if (length * width < area) {
                length++;
            } else {
                width--;
            }
        }
        return new int[]{(int) length, (int) width};
    }

    // LeetCode 上比较快的方法
    @Answer
    public int[] constructRectangle2(int area) {
        int width = (int) Math.sqrt(area), k = 1;
        // area 是奇数, 则宽和高都必须是奇数
        if (area % 2 != 0) {
            width = width % 2 == 0 ? width + 1 : width;
            // k 表示步进
            k = 2;
        }
        // 宽度逐渐缩小, 尝试是否能够得到整数解的长
        for (; width > 0; width -= k) {
            if (area % width == 0) {
                return new int[]{area / width, width};
            }
        }
        return new int[]{};
    }

    @TestData
    public DataExpectation example = DataExpectation.create(4).expect(new int[]{2, 2});

    @TestData
    public DataExpectation border = DataExpectation.create(1).expect(new int[]{1, 1});

}
