import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

@RunWith(LeetCodeRunner.class)
public class Test {

    @Answer

    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        List<Integer> profits = new ArrayList<>();
        int buy = 0;
        while (buy < prices.length - 1 && prices[buy] >= prices[buy + 1]) {
            buy++;
        }

        for (int sell = buy + 1; sell < prices.length; sell++) {
            if (prices[sell - 1] > prices[sell]) {
                profits.add(prices[sell - 1] - prices[buy]);
                buy = sell;
            }
        }
        profits.add(prices[prices.length - 1] - prices[buy]);
        profits.sort((a, b) -> b - a);
        int res = 0;
        for (int i = 0; i < profits.size() && i < k; i++) {
            res += profits.get(i);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(1)
            .addArgument(new int[]{2, 4, 1})
            .expect(2)
            .build();

    @TestData
    public DataExpectation test = DataExpectation.builder()
            .addArgument(1)
            .addArgument(new int[]{1, 2, 3})
            .expect(2)
            .build();

    @TestData
    public DataExpectation test2 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(new int[]{1, 2, 3})
            .expect(2)
            .build();

    @TestData
    public DataExpectation test3 = DataExpectation.builder()
            .addArgument(1)
            .addArgument(new int[]{3, 2, 1})
            .expect(0)
            .build();

    @TestData
    public DataExpectation test4 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(new int[]{1, 2, 3, 4, 6})
            .expect(5)
            .build();

    @TestData
    public DataExpectation test5 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(new int[]{1, 3, 2, 1})
            .expect(2)
            .build();

    @TestData
    public DataExpectation test6 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(new int[]{1, 2, 3, 4, 1, 6})
            .expect(8)
            .build();

}
