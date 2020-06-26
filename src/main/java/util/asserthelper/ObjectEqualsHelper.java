package util.asserthelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class ObjectEqualsHelper {

    /**
     * Check or not a field.
     */
    private Map<String, Boolean> shouldChecks = new HashMap<>();

    /**
     * Check field by default.
     */
    private boolean checkByDefault = true;

    /**
     * Custom validators.
     */
    private List<Validator> customValidators = new ArrayList<>();

    /**
     * Array/ iterable check in order.
     */
    private Map<String, Boolean> hasOrders = new HashMap<>();

    private List<Validator> defaultValidators = Arrays.asList(new ArrayValidator());

    public ObjectEqualsHelper order(String fieldName) {
        hasOrders.put(fieldName, true);
        return this;
    }

    public ObjectEqualsHelper unorder(String fieldName) {
        hasOrders.put(fieldName, false);
        return this;
    }

    public ObjectEqualsHelper check(String fieldName) {
        shouldChecks.put(fieldName, true);
        checkByDefault = false;
        return this;
    }

    public ObjectEqualsHelper uncheck(String fieldName) {
        shouldChecks.put(fieldName, false);
        return this;
    }

    public <T> ObjectEqualsHelper validator(String fieldName, BiPredicate<T, T> validator) {
        customValidators.add(new FieldNameValidator(fieldName, validator));
        return this;
    }

    public String isEquals(Object expected, Object actual) {
        try {
            assertEquals(expected, actual, "");
        } catch (NotEqualException err) {
            return err.getMessage();
        }
        return "";
    }

    private void assertEquals(Object expected, Object actual, String fieldName) {
        Boolean check = shouldChecks.get(fieldName);
        if (!fieldName.isEmpty() && check == null && !checkByDefault || Boolean.FALSE.equals(check)) {
            return;
        }

        if (expected == actual) {
            return;
        }
        if (expected == null) {
            throw new NotEqualException("%s expect null, actual is not.", fieldName);
        }
        if (actual == null) {
            throw new NotEqualException("%s expect not null, actual is null.", fieldName);
        }
        if (expected.equals(actual)) {
            return;
        }

        for (Validator validator : customValidators) {
            if (validator.match(expected, actual, fieldName)) {
                validator.validate(expected, actual, fieldName);
                return;
            }
        }

        for (Validator validator : defaultValidators) {
            if (validator.match(expected, actual, fieldName)) {
                validator.validate(expected, actual, fieldName);
                return;
            }
        }

        throw new NotEqualException("expected and actual not equal");
    }

    static class NotEqualException extends RuntimeException {

        NotEqualException(String message, Object... paras) {
            super(paras.length > 0 ? String.format(message, paras) : message);
        }
    }

    public interface Validator {

        boolean match(Object expected, Object actual, String fieldName);

        void validate(Object expected, Object actual, String fieldName);

    }

    public class FieldNameValidator<T> implements Validator {

        private final String fieldName;

        private final BiPredicate<T, T> predicate;

        public FieldNameValidator(String fieldName, BiPredicate<T, T> predicate) {
            this.fieldName = fieldName;
            this.predicate = predicate;
        }

        @Override
        public boolean match(Object expected, Object actual, String fieldName) {
            return this.fieldName.equals(fieldName);
        }

        @Override
        public void validate(Object expected, Object actual, String fieldName) {
            if (!predicate.test((T) expected, (T) actual)) {
                throw new NotEqualException("%s not equals.", fieldName);
            }
        }
    }

    public class ArrayValidator implements Validator {

        @Override
        public boolean match(Object expected, Object actual, String fieldName) {
            return isInterableType(expected.getClass()) && isInterableType(actual.getClass());
        }

        private boolean isInterableType(Class<?> type) {
            return type.isArray() || Iterable.class.isAssignableFrom(type);
        }

        @Override
        public void validate(Object expected, Object actual, String fieldName) {
            Object[] expectedArray = toArray(expected);
            Object[] actualArray = toArray(actual);
            if (expectedArray.length != actualArray.length) {
                throw new NotEqualException("%s length not equal", fieldName);
            }
            final int length = expectedArray.length;
            boolean hasOrder = hasOrder(expected, actual, fieldName);
            if (hasOrder) {
                for (int i = 0; i < length; i++) {
                    String subFieldName = fieldName + "[" + i + "]";
                    assertEquals(expectedArray[i], actualArray[i], subFieldName);
                }
            } else {
                boolean[] matched = new boolean[length];
                for (int i = 0; i < length; i++) {
                    boolean success = false;
                    String subFieldName = fieldName + "[" + i + "]";
                    for (int j = 0; j < length && !success; j++) {
                        if (!matched[j]) {
                            try {
                                assertEquals(expectedArray[i], actualArray[j], subFieldName);
                            } catch (NotEqualException err) {
                                continue;
                            }
                            success = true;
                            matched[j] = true;
                        }
                    }
                    if (!success) {
                        ErrorStringBuilder error = new ErrorStringBuilder();
                        error.append("%s expected value ", subFieldName)
                                .append(expectedArray[i])
                                .append(" not found in actual");
                        throw new NotEqualException(error.toString());
                    }
                }
            }
        }

        private Object[] toArray(Object obj) {
            if (obj.getClass().isArray()) {
                int length = Array.getLength(obj);
                Object[] res = new Object[length];
                for (int i = 0; i < length; i++) {
                    res[i] = Array.get(obj, i);
                }
                return res;
            }
            List list = new ArrayList();
            for (Object item : ((Iterable) obj)) {
                list.add(item);
            }
            return list.toArray();
        }

        private boolean hasOrder(Object expected, Object actual, String fieldName) {
            Boolean order;
            if ((order = hasOrders.get(fieldName)) != null) {
                return order;
            }
            String[] names = fieldName.split(".");
            for (int i = names.length - 2; i >= 0; i--) {
                StringBuilder sb = new StringBuilder(fieldName.length());
                for (int j = 0; j < i; j++) {
                    sb.append(names[j]).append(".");
                }
                sb.append("*");
                if ((order = hasOrders.get(sb.toString())) != null) {
                    return order;
                }
            }
            if ((order = hasOrders.get("*")) != null) {
                return order;
            }
            Class<?> expectedType = expected.getClass();
            if (expectedType.isArray() || List.class.isAssignableFrom(expectedType)) {
                return true;
            }
            return false;
        }
    }
}
