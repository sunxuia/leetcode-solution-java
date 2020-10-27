package util.runner.data;

import java.util.ArrayList;
import util.asserthelper.AssertUtils;
import util.asserthelper.ErrorStringBuilder;

class OrList<T> extends ArrayList<T> {

    OrList(T val) {
        super(2);
        add(val);
    }

    static DataAssertMethod<?> OR_EXPECT_ASSERT_METHOD = (expects, actual, originAssertMethod) -> {
        OrList<?> orExpects = (OrList<?>) expects;
        ErrorStringBuilder esb = new ErrorStringBuilder();
        esb.append("OrExpects not match with \n    actual: ");
        esb.append(actual);
        for (Object expect : orExpects) {
            try {
                originAssertMethod.accept(expect, actual);
                return;
            } catch (AssertionError err) {
                esb.append("\n    expect: ")
                        .append(expect)
                        .append("\n      caused by: [%s]", err);

            }
        }
        AssertUtils.fail(esb.toString());
    };
}
