package util.runner.data;

import org.junit.Assert;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;

public class DataExpectationAdaptor extends DataExpectation {

    private static final Object NULL_SYMBOL = new Object();

    private Object expect = NULL_SYMBOL;

    private DefaultDataAssertMethod dataAssertMethod = DataExpectation.DEFAULT_ASSERT_METHOD;

    DataExpectationAdaptor(Object[] arguments) {
        setArguments(arguments);
    }

    public DataExpectationAdaptor expect(Object e) {
        expect = e;
        setExpect(e);
        return this;
    }

    public DataExpectationAdaptor expectDouble(double e, double delta) {
        Assert.assertEquals("Should not set assert method before expectDouble.",
                DataExpectation.DEFAULT_ASSERT_METHOD, dataAssertMethod);
        expect(e);
        assertMethod((expect, actual, orig) -> {
            Assert.assertEquals((double) expect, (double) actual, delta);
        });
        return this;
    }

    public DataExpectationAdaptor expectDouble(double e) {
        return expectDouble(e, 0.00001);
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

    public <T> DataExpectationAdaptor assertMethod(DataAssertMethod<T> method) {
        dataAssertMethod = new DefaultDataAssertMethod(method, dataAssertMethod);
        setExpectAssertMethod(dataAssertMethod);
        return this;
    }

    public DataExpectationAdaptor orExpect(Object e) {
        Assert.assertTrue("Please set expect first!", expect != NULL_SYMBOL);

        if (expect instanceof OrList) {
            ((OrList) expect).add(e);
        } else {
            OrList orList = new OrList(expect);
            orList.add(e);
            expect(orList);
            assertMethod(OrList.OR_EXPECT_ASSERT_METHOD);
        }
        return this;
    }

}
