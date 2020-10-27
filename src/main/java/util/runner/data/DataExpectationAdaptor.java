package util.runner.data;

import org.junit.Assert;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;

public class DataExpectationAdaptor extends DataExpectation {

    private static final Object NULL_SYMBOL = new Object();

    private Object expect = NULL_SYMBOL;

    private DefaultDataAssertMethod dataAssertMethod = null;

    DataExpectationAdaptor(Object[] arguments) {
        setArguments(arguments);
    }

    public DataExpectationAdaptor expect(Object e) {
        expect = e;
        setExpect(e);
        return this;
    }

    public DataExpectationAdaptor unOrder(String... patterns) {
        Assert.assertFalse("Please set unOrder before orExpect!", expect instanceof OrList);

        ObjectEqualsHelper helper = new ObjectEqualsHelper();
        if (patterns.length == 0) {
            helper.unorder("*");
        } else {
            for (String s : patterns) {
                helper.unorder(s);
            }
        }
        assertMethod((expect, actual, orig) -> {
            AssertUtils.assertEquals(expect, actual, helper);
        });
        return this;
    }

    public DataExpectationAdaptor assertMethod(DataAssertMethod method) {
        dataAssertMethod = new DefaultDataAssertMethod(method, dataAssertMethod);
        setExpectAssertMethod(dataAssertMethod);
        return this;
    }

    public DataExpectationAdaptor orExpect(Object e) {
        Assert.assertTrue("Please set expect first!", expect != NULL_SYMBOL);

        if (expect instanceof OrList) {
            ((OrList) e).add(e);
        } else {
            OrList orList = new OrList(expect);
            orList.add(e);
            expect(orList);
            assertMethod(OrList.OR_EXPECT_ASSERT_METHOD);
        }
        return this;
    }

}
