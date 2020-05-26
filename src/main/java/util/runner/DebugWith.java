package util.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在方法或类上指定测试使用的测试用例.
 * 一般用于调试.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DebugWith {

    /**
     * Test data name.
     */
    String[] value() default {};

    /**
     * Test class, for class test.
     */
    Class<?>[] testFor() default {};

}
