package util.asserthelper;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertUtilsTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFail() {
        boolean exceptionThrown = false;
        try {
            AssertUtils.fail("%s message", "fail");
        } catch (AssertionError err) {
            exceptionThrown = true;
            Assert.assertEquals(err.getStackTrace()[0].getClassName(), AssertUtilsTest.class.getName());
            Assert.assertEquals(err.getMessage(), "fail message");
        } finally {
            if (!exceptionThrown) {
                Assert.fail("Expect AssertionError not thrown.");
            }
        }
    }

    @Test
    public void assertIn_notIn_throwException() {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("1 not in range [2, 3, 4]");

        AssertUtils.assertIn(1, 2, 3, 4);
    }

    @Test
    public void assertIn_in_pass() {
        AssertUtils.assertIn(1, 1, 2);
        AssertUtils.assertIn(1, null, 2, 1);
        AssertUtils.assertIn(null, 1, null);
    }

    @Test
    public void assertEquals_equal_pass() {
        AssertUtils.assertEquals(1, 1);

        AssertUtils.assertEquals(new Integer[]{1, 2, 3, 4}, new Integer[]{1, 2, 3, 4});
        AssertUtils.assertEquals(new Integer[]{1, 2, 3, 4}, new Integer[]{4, 3, 2, 1},
                new ObjectEqualsHelper().unorder("*"));
    }

    @Test
    public void assertEquals_notEqual_throwException() {
        expectedException.expect(AssertionError.class);
        AssertUtils.assertEquals(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 4});
    }
}
